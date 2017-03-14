/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var username;
var theSocket = new WebSocket('ws://localhost:8080/mavenproject7/endpoint');
$(document).ready(function(){
   username = getCookie("uname");
   getProfileTag();
   getNoti(username);
   $('#noti-place div p').click(function(){
        $.ajax({
           url: 'api/noti/view/'+username,
           type: 'PUT'
        });
        $('#noti-place a span').attr('class','badge badge-default');
        $('#noti-place a span').html(0);
        $('#noti-place div p').html('No notification.');
        setTimeout(function() { window.location.reload(); }, 10);
    });
});
theSocket.onmessage = function(e){
        if (username == e.data){
            getNoti(e.data);
        }
    };

//Get unviewed notification, change the DOM. Requires the user to click on the clear notification box to clear it
function getNoti(name){
    $.get('api/noti/unviewed/'+name,function(data){
           console.log(data); 
           $xml = $(data);
           $noti = $xml.find('notification');
           var numofnoti = $noti.length;
           console.log(numofnoti);
           if(numofnoti == 0){
            $('#noti-place a span').attr('class','badge badge-default');
            $('#noti-place a span').html(0);
            $('#noti-place div p').html('No notification.');  
           } else {
            $('#noti-place a span').attr('class','badge badge-danger');
            $('#noti-place a span').html(numofnoti);
            $('#noti-place div p').html('You have new notifications! Click to clear notification');
           }
        });
}
//change the profile tag to the current user.
function getProfileTag(){
    username = getCookie("uname");
    console.log(username);
    $.get("api/user/getbyname/"+username,function(data){
        $xml=$(data);
        $firstname = $xml.find("firstname");
        $lastname = $xml.find("lastname");
        var fullName = $firstname.text() + " " + $lastname.text();        
        $("#userid").html(fullName);
    });
}

function getCookie(cname) {
    var name = cname     + "=";
    var decodedCookie = decodeURIComponent(document.cookie);
    var ca = decodedCookie.split(';');
    for(var i = 0; i <ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) == ' ') {
            c = c.substring(1);
        }
        if (c.indexOf(name) == 0) {
            return c.substring(name.length, c.length);
        }
    }
    return "";
}