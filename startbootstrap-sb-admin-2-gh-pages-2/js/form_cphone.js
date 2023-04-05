$(document).ready(function () {
    $("form_cphone").submit(function (event) {
      var formData = {
        status: $("#status").val(),
        phone: $("#phone").val(),
      
      };
      
      $.ajax({
        type: "POST",
        url: "http://localhost:8080/phone/addPhone",
        headers:{"Authen": "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJxdXlldHRvYmVsZXQiLCJleHAiOjE2ODE0NDMwODJ9.B1BX_aBQ8gKVR4bsi2ZMdQycwu0PHs7W1jBT826SDEYZpPQniGkOJbKMyWcLp0oxWK_-TNGT2Bod2W6F8-ayIQ"},
        data: formData,
        dataType: "json",
        encode: true,
        
          
      
      }).done(function (data) {
        
        console.log(data);
        alert("Hello! I am an alert box!!");
      });
  
      event.preventDefault();
    });
  });








  