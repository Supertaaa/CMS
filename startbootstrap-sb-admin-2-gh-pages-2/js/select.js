$(document).ready(function(){
    $.ajax({
        method: "GET",
        url: "http://localhost:8080/Campaign/getAllCampaign",
        headers:{"Authorization": "Bearer " + $.cookie("token")},
        success: function(res){
            console.log(res)
            console.log(res[0]["name"])
            html = ""

            for (let i = 0; i < res.length; i++) {
                html += "<option value=" + res[i]["id"]  + ">" +res[i]["name"] + "</option>"
            }
                                
        
            document.getElementById("datas").innerHTML = html;
        }
    })

    
    
})