package com.example.SimpleProject.Thread;


import com.example.SimpleProject.Entities.Campaign;
import com.example.SimpleProject.Entities.Email;
import com.example.SimpleProject.Entities.Phone;
import com.example.SimpleProject.Repository.CampaignReposirity;
import com.example.SimpleProject.Repository.EmailReposirity;
import com.example.SimpleProject.Repository.PhoneReposirity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class workThread implements Runnable {


    Logger logger = LoggerFactory.getLogger(workThread.class);
    private String idPhone;
    private String idEmail;
    private String idCampaign;
    private String smsMethod;
    CampaignReposirity campaignReposirity;

    EmailReposirity emailReposirity;

    PhoneReposirity phoneReposirity;

    public workThread(String idPhone, String idCampaign, CampaignReposirity campaignReposirity, PhoneReposirity phoneReposirity,String smsMethod){
        super();
        this.phoneReposirity = phoneReposirity;
        this.idCampaign = idCampaign;
        this.idPhone = idPhone;
        this.campaignReposirity = campaignReposirity;
        this.smsMethod = smsMethod;
    }

    public workThread(String idEmail, String idCampaign, CampaignReposirity campaignReposirity, EmailReposirity emailReposirity){
        super();
        this.emailReposirity = emailReposirity;
        this.idCampaign = idCampaign;
        this.idEmail = idEmail;
        this.campaignReposirity = campaignReposirity;
    }



    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public Boolean sendEmail(String idEmail) throws InterruptedException {
        int randomNum  = getRandomNumber(0, 100);
        TimeUnit.SECONDS.sleep(1);

        return randomNum < 51;

    }

    public Boolean sendJsonSms(String idPhone) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        Phone phone = phoneReposirity.findById(Integer.parseInt(idPhone)).get();
        String content = campaignReposirity.findById(phone.getCampaignId()).get().getContent();
        HttpHeaders headers = new HttpHeaders();
        headers.add("api-key", "Vg@SMS2022");
        headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE);
        headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
        Map<String, String> map = new HashMap<String, String>();
        map.put("phone", phone.getPhone());
        map.put("content", content);
        MultiValueMap<String, String> inputMap = new LinkedMultiValueMap<>();
        for (Map.Entry<String, String> value : map.entrySet()) {
            inputMap.add(value.getKey(), value.getValue());
        }
        HttpEntity request = new HttpEntity(inputMap, headers);
        ResponseEntity<String> response = restTemplate.exchange("https://apivclick.topdev.site/sms/send", HttpMethod.POST, request, String.class);
        ObjectMapper mapper = new ObjectMapper();

        System.out.print(response);
        return mapper.readTree(response.getBody()).get("error_code").asText().equals("0");

    }

    public Boolean sendSoapSms(String idPhone) throws IOException {

        Phone phone = phoneReposirity.findById(Integer.parseInt(idPhone)).get();

        String request  = "<soapenv:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:sms=\"SmsSoapControllerwsdl\">\n" +
                "   <soapenv:Header/>\n" +
                "   <soapenv:Body>\n" +
                "      <sms:send soapenv:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">\n" +
                "         <parameters xsi:type=\"sms:Sendsms\">\n";
        String inline;
        StringBuffer response = new StringBuffer();
        String content = String.format("<username xsi:type='xsd:string'>%s</username><password xsi:type='xsd:string'>%s</password><phone xsi:type='xsd:string'>%s</phone><content xsi:type='xsd:string'>%s</content>\n", "sms", "test@123", phone.getPhone(), campaignReposirity.findById(phone.getCampaignId()).get().getContent());
        request = request + content;
        request = request + "        </parameters>\n" +
                "      </sms:send>\n" +
                "   </soapenv:Body>\n" +
                "</soapenv:Envelope>\n";


        URL url = new URL("http://api.vinaphone.vegaid.vn/sms-soap/index?ws=1");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setRequestProperty("Content-Type","text/xml; charset=utf-8");


        DataOutputStream writer = new DataOutputStream(connection.getOutputStream());
        writer.writeBytes(request);
        writer.flush();
        writer.close();

        if(connection.getResponseCode() == 200){
            BufferedReader readResponse = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while ((inline = readResponse.readLine()) != null){
                response.append(inline);
            }
            readResponse.close();
        }


        return response.indexOf("0|Success") != -1;
    }

    public void processData() throws InterruptedException, IOException {

        if(idEmail != null){

            Email email = emailReposirity.findById(Integer.parseInt(idEmail)).get();
            if(sendEmail(idEmail)){

                email.setStatus(1);
                email.setRepeat(0);
                emailReposirity.save(email);
                logger.info("Send email to " + email.getEmail() + " successfully");

            }
            else {

                int retryCount = 1;
                while(!sendEmail(idEmail) && retryCount <= 3){
                    retryCount = retryCount + 1;
                }
                if(retryCount > 3){
                    email.setStatus(2);
                    logger.info("Send email to " + email.getEmail() + " failed");
                }
                else{
                    email.setStatus(1);
                    logger.info("Send email to " + email.getEmail() + " successfully");
                }

                email.setRepeat(retryCount);

                emailReposirity.save(email);

            }

        }
        else if(idPhone != null){

            if(smsMethod.equals("SOAP")){
                Phone phone = phoneReposirity.findById(Integer.parseInt(idPhone)).get();
                if(sendSoapSms(idPhone)){

                    phone.setStatus(1);
                    phone.setRetry(0);
                    phoneReposirity.save(phone);

                    logger.info("Send SOAP SMS to " + phone.getPhone() + " successfully");
                }
                else{

                    int retryCount = 1;
                    while(!sendSoapSms(idPhone) && retryCount <= 3){
                        retryCount = retryCount + 1;
                    }
                    if(retryCount > 3){
                        logger.info("Send SOAP SMS to " + phone.getPhone() + " failed");
                        phone.setStatus(2);
                    }
                    else{
                        logger.info("Send SOAP SMS to " + phone.getPhone() + " successfully");
                        phone.setStatus(1);
                    }

                    phone.setRetry(retryCount);
                    phoneReposirity.save(phone);

                }

            }

            else if(smsMethod.equals("JSON")){
                Phone phone = phoneReposirity.findById(Integer.parseInt(idPhone)).get();
                if(sendJsonSms(idPhone)){

                    phone.setStatus(1);
                    phone.setRetry(0);
                    phoneReposirity.save(phone);

                    logger.info("Send JSON SMS to " + phone.getPhone() + " successfully");
                }
                else{

                    int retryCount = 1;
                    while(!sendJsonSms(idPhone) && retryCount < 3){
                        retryCount = retryCount + 1;
                    }
                    if(retryCount >= 3){
                        logger.info("Send JSON SMS to " + phone.getPhone() + " failed");
                        phone.setStatus(2);
                    }
                    else{
                        logger.info("Send JSON SMS to " + phone.getPhone() + " successfully");
                        phone.setStatus(1);
                    }

                    phone.setRetry(retryCount);
                    phoneReposirity.save(phone);

                }


            }


        }


    }

    @Override
    public void run() {

        try {

            logger.info("Thread " +  Thread.currentThread().getName());
            processData();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
