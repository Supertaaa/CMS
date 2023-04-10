// $(document).ready(function(){
//     fetch("./package.json")
//     .then(response => response.json())
//     .then((json) => console.log(json.deploy.url));

    

// })

// function checkUserHosting(hostEmail, callback) {
//     fetch("./package.json")
//     .then(response => response.json())
//     .then((json) => console.log(json.deploy.url));
// }

function checkUserHosting() {
    return fetch("./package.json")
        .then((response) => { 
            return response.json().then((data) => {
                console.log(data);
                return data;
            }).catch((err) => {
                console.log(err);
            }) 
        });
}

// let jsonData;
// checkUserHosting().then((data) => {
//     jsonData = data.deploy.url;
//     console.log(jsonData)
// })




