$(document).ready(function(){

    function checkUserHosting() {
        return fetch("./package.json")
            .then((response) => { 
                return response.json().then((data) => {
                    
                    return data;
                }).catch((err) => {
                    console.log(err);
                }) 
            });
    }


   
    checkUserHosting().then((data) => {
        jsonUrl = data.deploy.url;
        jsonPort = data.deploy.port

        $.ajax({
            method: "GET",
            url: jsonUrl+":"+jsonPort+"/Campaign/getAllCampaign",
            //url: "http://localhost:8080/Campaign/getAllCampaign",
            headers:{"Authorization": "Bearer " + $.cookie("token")},
            success: function(res){
                html = '<option value="">None</option>'
    
                for (let i = 0; i < res.length; i++) {
                    html += "<option value=" + res[i]["id"]  + ">" +res[i]["name"] + "</option>"
                }
                console.log(html)
                //document.getElementById("datasImportCampaign").innerHTML = html
                document.getElementById("datasCampaignSelection").innerHTML = html
            }
        })


        $.ajax({
            method: "GET",
            url: jsonUrl+":"+jsonPort+"/Campaign/getServiceId",
            //url: "http://localhost:8080/Campaign/getAllCampaign",
            headers:{"Authorization": "Bearer " + $.cookie("token")},
            success: function(res){
                


                htmL = '<option value="">All</option>'
                for (let i = 0; i < res.length; i++) {
                    htmL += "<option value=" +'"'+ res[i]["name"]  +'"'+ ">" +res[i]["name"] + "</option>"
                }

                document.getElementById("datasServiceSelection").innerHTML = htmL
                //document.getElementById("datasServiceSelectionE").innerHTML = htmL
                
            }
        })

        







    })


    

    
    
})