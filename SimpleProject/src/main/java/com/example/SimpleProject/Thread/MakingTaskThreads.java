package com.example.SimpleProject.Thread;
import com.example.SimpleProject.Entities.Campaign;
import com.example.SimpleProject.Entities.Email;
import com.example.SimpleProject.Entities.Phone;
import com.example.SimpleProject.Projection.DataProjection;
import com.example.SimpleProject.Repository.CampaignReposirity;
import com.example.SimpleProject.Repository.EmailReposirity;
import com.example.SimpleProject.Repository.PhoneReposirity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Service
public class MakingTaskThreads {

    Logger logger = LoggerFactory.getLogger(MakingTaskThreads.class);

    int core_pool_size = 10;
    int keep_alive = 10;
    int max_pool_size = 20;
    int queue_capacity = 150;
    @Autowired
    EmailReposirity emailReposirity;
    @Autowired
    PhoneReposirity phoneReposirity;

    @Autowired
    CampaignReposirity campaignReposirity;

    @Value("${sendsmsmethod}")
    private String smsMethod;

    public void threadDataProcess(int size, List<DataProjection> listData) throws InterruptedException {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(core_pool_size, max_pool_size, keep_alive, TimeUnit.SECONDS, new ArrayBlockingQueue<>(queue_capacity));

        for(int i = 0; i < size; i++){


            if(listData.get(i).getEmailId() != null && listData.get(i).getPhoneId() == null){
                threadPoolExecutor.execute(new workThread(Integer.toString(listData.get(i).getEmailId()),Integer.toString(listData.get(i).getCampaignId()),campaignReposirity,emailReposirity));

            }
            else if(listData.get(i).getEmailId() == null && listData.get(i).getPhoneId() != null){
                threadPoolExecutor.execute(new workThread(Integer.toString(listData.get(i).getPhoneId()),Integer.toString(listData.get(i).getCampaignId()),campaignReposirity,phoneReposirity, smsMethod));

            }
            Thread.sleep(100);

            logger.info(String.format(
                    "[%d/%d] Active: %d, Task: %d",
                    threadPoolExecutor.getPoolSize(), threadPoolExecutor.getCorePoolSize(), threadPoolExecutor.getActiveCount(),threadPoolExecutor.getTaskCount()));



//            System.out.println(String.format(
//                    "[%d/%d] Active: %d, Task: %d",
//                    threadPoolExecutor.getPoolSize(), threadPoolExecutor.getCorePoolSize(), threadPoolExecutor.getActiveCount(),threadPoolExecutor.getTaskCount()));
        }
        threadPoolExecutor.shutdown();

    }

    @Scheduled(fixedDelay = 5000)
    public void threadExecutor() throws InterruptedException {


        LocalDateTime now = LocalDateTime.now();
        List<DataProjection> listEmail = new ArrayList<>();
        List<DataProjection> listPhone = new ArrayList<>();

        List<Campaign> campaigns = campaignReposirity.findAll();

        for(Campaign campaign: campaigns){

            if ((now.isEqual(campaign.getStartTime()) || now.isAfter(campaign.getStartTime())) && (now.isEqual(campaign.getEndTime()) || now.isBefore(campaign.getEndTime()))){
                if(campaign.getType() == 0){ //Email
                    listEmail.addAll(emailReposirity.listNotStartedEmail(campaign.getId()));
                }
                else if(campaign.getType() == 1){ //Phone
                    listPhone.addAll(phoneReposirity.listNotStartedPhone(campaign.getId()));
                }
                if(campaign.getStatus() == 0 ||campaign.getStatus() == 1 || campaign.getStatus() == 2){
                    campaign.setStatus(3);
                    campaignReposirity.save(campaign);
                }


            }
            else if(now.isAfter(campaign.getEndTime()) && campaign.getStatus() == 3){
                if(campaign.getType() == 0){
                    List<Email> ListEmailAfterRun = emailReposirity.findEmailByCampaignId(campaign.getId());
                    for(Email email: ListEmailAfterRun){
                        if (email.getStatus() != 1){
                            campaign.setStatus(2);
                            break;
                        }
                        if(campaign.getStatus() == 2){
                            campaignReposirity.save(campaign);
                        }
                        else {
                            campaign.setStatus(1);
                            campaignReposirity.save(campaign);
                        }
                    }

                }
                else if (campaign.getType() == 1){
                    List<Phone> ListPhoneAfterRun = phoneReposirity.findPhoneByCampaignId(campaign.getId());
                    for(Phone phone: ListPhoneAfterRun){
                        if(phone.getStatus() != 1){
                            campaign.setStatus(2);
                            break;
                        }

                        if(campaign.getStatus() == 2){
                            campaignReposirity.save(campaign);
                        }
                        else {
                            campaign.setStatus(1);
                            campaignReposirity.save(campaign);
                        }

                    }
                }
                campaignReposirity.save(campaign);
            }

        }

        if(listEmail.size() > 0 && listPhone.size() > 0){

            //System.out.println("Handle phone and email");
            CompletableFuture<Void> futureEmail = CompletableFuture.runAsync(() -> {
                try {

                    threadDataProcess(listEmail.size(), listEmail);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            });
            CompletableFuture<Void> futurePhone = CompletableFuture.runAsync(() -> {
                try {

                    threadDataProcess(listPhone.size(), listPhone);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            });
        }
        else if(listEmail.size() > 0){

            threadDataProcess(listEmail.size(), listEmail);
        }
        else if(listPhone.size() > 0){

            threadDataProcess(listPhone.size(), listPhone);
        }
    }

}
