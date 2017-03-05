
/**
 * Created by Khanh on 3/4/2017.
 */
var username;
var req;
$(document).ready(function(){
    if(window.location.search != null){
        var getSearchText = window.location.search.substring(1);
        var uRLParameters = getSearchText.split("&");
        var usernameSearch = uRLParameters[0].split("=");
        username = usernameSearch[1];
        document.cookie = "uname="+username+";path=/;";
        console.log(username);
        console.log(document.cookie);
     }
    websitebuild(username); 
});
function websitebuild(username){
    console.log("doingit");
    getProfileTag();
    showTaskList();
    
}
function getProfileTag(){
    username = getCookie("uname");
    console.log(username);
    $.get("api/user/getbyname/"+username,function(data){
        $xml=$(data);
        $firstname = $xml.find("firstname");
        $lastname = $xml.find("lastname");
        var fullName = $firstname.text() + " " + $lastname.text();
        console.log(fullName);
        $("#userid").html(fullName);
    });
}
function showTaskList(){
    username = getCookie("uname");
    console.log(username);
    $.get("api/task/undone/"+username,function(data){
        console.log(data);
        $xml=$(data);
        $tasks = $xml.find("task").each(function(index){
            var taskName = $(this).find('taskname').text();
            var startDate = $(this).find('startdate').text();
            var endDate = $(this).find('deadline').text();
            var description = $(this).find('description').text();
            var theTask = document.createElement('a');
            theTask.href ="#";
            theTask.className = "list-group-item list-group-item-action flex-column align-items-start";
            var taskNameAndTimeContainer = document.createElement('div');
            taskNameAndTimeContainer.className = "d-flex w-100 justify-content-between";
            var taskNameContainer = document.createElement('h5');
            taskNameContainer.className ="mb-1";
            taskNameContainer.appendChild(document.createTextNode(taskName));
            var timeContainer = document.createElement('small');
            timeContainer.appendChild(document.createTextNode(startDate+"-"+endDate));
            taskNameAndTimeContainer.appendChild(taskNameContainer);
            taskNameAndTimeContainer.appendChild(timeContainer);
            var taskDescription = document.createElement('p');
            taskDescription.className="mb-1";
            taskDescription.appendChild(document.createTextNode(description));
            var doneButton = document.createElement('button');
            doneButton.className="btn btn-default btn-sm";
            doneButton.type = "button";
            doneButton.name = "done-button";
            doneButton.appendChild(document.createTextNode("Done"));
            theTask.appendChild(taskNameAndTimeContainer);
            theTask.appendChild(taskDescription);
            theTask.appendChild(doneButton);
            console.log(theTask);
            $("#task-content .list-group").append(theTask);
        });
    });
    
}
function getCookie(cname) {
    var name = cname + "=";
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