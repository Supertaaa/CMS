$(document).ready(function(){
    var frm = $('.user');

    frm.submit(function (e) {

        e.preventDefault();

        var formData = {
            userName: $("#exampleInputEmail").val(),
            password: $("#exampleInputPassword").val(),
          
        };

        
        
        $.ajax({
            type: frm.attr('method'),
            url: frm.attr('action'),
            data: formData,
            dataType: "json",
            encode: true,
            error: function (data) {
                console.log(data.responseText);
                console.log(data)
                $.cookie("token", data.responseText)
                console.log($.cookie("token"))
                if (data.responseText.localeCompare("") != 0){
                    
                    //window.location.href = "/index.html";
                    
                }
                
            },
            
        })
    
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

  