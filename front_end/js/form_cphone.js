$(document).ready(function () {
    $("form_cphone").submit(function (event) {
      var formData = {
        status: $("#status").val(),
        phone: $("#phone").val(),
      
      };
      
      $.ajax({
        type: "POST",
        url: "http://localhost:8080/phone/addPhone",
        headers:{"Authorization": "Bearer " + $.cookie("token")},
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








  