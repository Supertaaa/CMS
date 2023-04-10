
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
                //url: "http://localhost:8080/user/getRole",
                data: { "token": $.cookie("token")},
                headers:{"Authorization": "Bearer " + $.cookie("token")},
                success: function(data){
                    if(data == "USER"){
                        window.location.href = "index.html"
                    }
                    else{
                        $.ajax({
                            type: "GET",
                            url: "http://localhost:8080/user/getUserName",
                            data: { "token": $.cookie("token")},
                            headers:{"Authorization": "Bearer " + $.cookie("token")},
                            success: function(data){
                                $("#spantag").text(data)
                            },
                        })
                    }
                },
            })
            
        }
        
    })

    
    

    
    
})
