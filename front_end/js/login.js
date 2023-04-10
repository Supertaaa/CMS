

$(document).ready(function(){
    var frm = $('.user');

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
    })

    

    frm.submit(function (e) {

        e.preventDefault();

        var formData = {
            userName: $("#exampleInputEmail").val(),
            password: $("#exampleInputPassword").val(),
          
        };

        console.log(jsonPort)
        console.log(jsonUrl)

        if($("#exampleInputEmail").val().localeCompare("") == 0){
            alert("Empty Username")
        }

        else if($("#exampleInputPassword").val().localeCompare("") == 0){
            alert("Empty Password")
        }

        

        

        else{
            $.ajax({
                type: frm.attr('method'),
                url: jsonUrl+":"+jsonPort+"/user/login",
                // url: frm.attr('action'),
                data: formData,
                dataType: "json",
                encode: true,
                
                error: function (data, status, xhr) {
                    console.log(data.responseText);
                    console.log(data)
                    $.cookie("token", data.responseText)
                    
                    console.log(data.getAllResponseHeaders());
                    
    
                    if (data.responseText.localeCompare("") != 0){
                        
                        window.location.href = "/index.html";
                        
                        
                    }
                    else{
                        alert("Not correct username or password")
                    }
                    
                },
                
            })
        }

        
        
        
    
    })
})
//Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJxdXlldHRvYmVsZXQiLCJleHAiOjE2ODE0NDMwODJ9.B1BX_aBQ8gKVR4bsi2ZMdQycwu0PHs7W1jBT826SDEYZpPQniGkOJbKMyWcLp0oxWK_-TNGT2Bod2W6F8-ayIQ



//         $.ajax({
//             type: frm.attr('method'),
//             url: frm.attr('action'),
//             data: frm.serialize(),
//             success: function(res, status, xhr) { 
//                 $.cookie('token',xhr.getResponseHeader("Authorization"))
//                 window.location.href("/index.html");
//             },
//             error: function (data) {
//                 alert("Not correct username or password !");
                
//             },
            
//         });



// $(document).ready(function () {
//     $("login").submit(function (event) {
//       var formData = {
//         username: "quyettobelet",
//         password: "Ah123456",
      
//       };
//       $.ajax({
//         type: "POST",
//         url: "http://localhost:8080/login",
//         data: formData,
//         dataType: "json",
//         encode: true,
//         success: function(res, status, xhr) { 
//             $.cookie('token',xhr.getResponseHeader("Authorization"))
//             window.location.href("/index.html");
//         }
//       }).done(function (data) {
//         console.log(data);
//         alert("Hello! I am an alert box!!");
//         window.location = "index.html";
//       });
  
//       event.preventDefault();
//     });
//   });

  