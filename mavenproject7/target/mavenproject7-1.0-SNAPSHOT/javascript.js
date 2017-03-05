/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var uri = "ws://" + document.location.host + document.location.pathname + "endpoint";

if (window.WebSocket) {
    var ws = new WebSocket(uri);
    console.log(uri);
    ws.onopen = function(event){
        console.log("It works !");
    };
    ws.onmessage = function(event) {
        var text = event.data;
        console.log(text);
    };
}
else{
    alert("It's doomed.");
}
