
/**
 * Created by Khanh on 3/4/2017.
 */

var username;
var req;
$(document).ready(function(){
    var getSearchText = window.location.search.substring(1);
    var uRLParameters = getSearchText.split("&");
    var usernameSearch = uRLParameters[0].split("=");
    username = usernameSearch[1];
    console.log(username);
    websitebuild(username); 
});
function websitebuild(username){
    console.log("doingit")
    eventHandler("GET","user/getbyname/"+username);
    $('#sidebar-home').attr('href','Home.html?user='+username);
    $('#sidebar-task').attr('href','Task.html?user='+username);
    $('#sidebar-message').attr('href','Messages.html?user='+username);
    $('#sidebar-calendar').attr('href','Calendar.html?user='+username);
    console.log($('#sidebar-home').attr('href'));
}
function initRequest() {
    if (window.XMLHttpRequest) {
        if (navigator.userAgent.indexOf('MSIE') != -1) {
            isIE = true;
        }
        return new XMLHttpRequest();
    } else if (window.ActiveXObject) {
        isIE = true;
        return new ActiveXObject("Microsoft.XMLHTTP");
    }
}
function eventHandler(method,actionname){
    var url = "api/" + actionname;
    console.log(url);
    req = initRequest();
    req.open(method, url, true);
    req.onreadystatechange = callback;
    req.send(null);
    
}
function callback(){
    if (req.readyState == 4) {
        if (req.status == 200) {
            parseMesseges(req.responseXML);
        }
    }
}
function parseMesseges(responseXML){
   
    $xml =$(responseXML);
    console.log(responseXML);
    $firstName = $xml.find("firstname");
    $lastName = $xml.find("lastname");
    var returnText = $firstName.text() + " " + $lastName.text();
        console.log(returnText);
    $("#profile-tag a p").html(returnText);
}