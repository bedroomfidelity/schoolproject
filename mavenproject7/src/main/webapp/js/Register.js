/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var username;
$(document).ready(function(){
    if(window.location.search != null){
        var getSearchText = window.location.search.substring(1);
        var uRLParameters = getSearchText.split("&");
        var usernameSearch = uRLParameters[0].split("=");
        username = usernameSearch[1];
        document.cookie = "uname="+username+";path=/;";
        console.log(username);
        console.log(document.cookie);
    } else {
        username = getCookie('uname');
    }
});


