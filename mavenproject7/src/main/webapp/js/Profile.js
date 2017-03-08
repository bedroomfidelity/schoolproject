/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var username;
var req;
$(document).ready(function(){
    console.log(window.location.search);
        var getSearchText = window.location.search.substring(1);
        var uRLParameters = getSearchText.split("&");
        var usernameSearch = uRLParameters[0].split("=");
        var checker = usernameSearch[0];
        username = usernameSearch[1];
        if (username && checker=="user"){
        document.cookie = "uname="+username+";path=/;";
        console.log(username);
        console.log(document.cookie);
        }
        console.log(document.cookie);
        username = getCookie('uname');
    websitebuild(); 
});
function websitebuild(){
    console.log("fetching");
    getProfileTag();
    getAvatar();
    getUserProfile();
}

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

function getAvatar(){
    username = getCookie("uname");
    console.log("beginava");
    $.get("api/user/avatar/"+username,function(data){
        
        //$("#avatar").attr("src","");
    });    
    console.log("endava");
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

function getUserProfile(){
    username = getCookie("uname");
    console.log(username+"'s profile");
    $.get("api/user/getbyname/"+username,function(data){
        $xml=$(data);
        
        $firstname = $xml.find("firstname");
        $lastname = $xml.find("lastname");
        $uname = $xml.find("username");
        $job = $xml.find("title");
        $address = $xml.find("address");
        $email = $xml.find("email");
        $pnumber = $xml.find("phoneNumber");
        
        //var fullName = $firstname.text() + " " + $lastname.text();
        //console.log(fullName);
        //$("#userid").html(fullName);
        
        var firstName = $firstname.text();
        var lastName = $lastname.text();
        var job = $job.text();
        var address = $address.text();
        var email = $email.text();
        var uname = $uname.text();        
        var pnumber = $pnumber.text();
        
        $("#fname").html(firstName);
        $("#lname").html(lastName);
        $("#uname").html(uname);
        $("#title").html(job);
        $("#hadd").html(address);
        $("#mail").html(email);
        $("#pnumber").html(pnumber);
    });
}