
$(document).ready(function(){


    checkUserHosting().then((data) => {
        jsonUrl = data.deploy.url;
        jsonPort = data.deploy.port
        if(!$.cookie("token")){
        window.location.href = "login.html"
        }
        else{
            
            $.ajax({
                type: "GET",
                url:jsonUrl+":"+jsonPort+"/user/getRole",
                
                data: { "token": $.cookie("token")},
                headers:{"Authorization": "Bearer " + $.cookie("token")},
                success: function(data){
                    $.ajax({
                        type: "GET",
                        url:jsonUrl+":"+jsonPort+"/user/getUserName",
                        data: { "token": $.cookie("token")},
                        headers:{"Authorization": "Bearer " + $.cookie("token")},
                        success: function(data){
                            $("#spantag").text(data)
                        },
                    })
                    
                },
            })
            
        }
        
    })
})
