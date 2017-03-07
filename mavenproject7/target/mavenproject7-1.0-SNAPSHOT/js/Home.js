
/**
 * Created by Khanh on 3/4/2017.
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
        if (username && checker=="username"){
        document.cookie = "uname="+username+";path=/;";
        console.log(username);
        console.log(document.cookie);
        }
        username = getCookie('uname');
    websitebuild(); 
});
function websitebuild(){
    console.log("doingit");
    getProfileTag();
    showTaskList();
    showMessages();
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
            var textID = $(this).find('taskID').text();
            var theTask = document.createElement('a');
            theTask.href ="#";
            theTask.className = "list-group-item list-group-item-action flex-column align-items-start";
            theTask.id = "task" + textID;
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
            doneButton.onclick = finishTask;
            doneButton.appendChild(document.createTextNode("Done"));
            theTask.appendChild(taskNameAndTimeContainer);
            theTask.appendChild(taskDescription);
            theTask.appendChild(doneButton);
            $("#task-content .list-group").append(theTask);
        });
    });
    
}
function finishTask(){
    var taskID = $(this).parent().attr('id').substring(4);
    $.ajax({url:'api/task/done/'+taskID,type:'PUT',success: function(response){
            console.log(response);
    }});
}
function showMessages(){
    
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
function sendFakeForm(){
    console.log("dude");
    console.log($.now());
    var fakeForm = $("<form></form>");
    
    fakeForm.attr("method","post");
    fakeForm.attr("action","api/message/new");
    fakeForm.css("display","none");
    fakeForm.attr("enctype","multipart/form-data");
    
    $("#sendmess").children().each(function(){
        console.log($(this).clone());
        var dummy = $(this).clone();
        fakeForm.append(dummy);
    });
    var field = $('<input></input>');
    field.attr('type','hidden');
    field.attr('name','sender');
    field.attr('value',username);
    
    fakeForm.append(field);
    
    var field1 = $('<input></input>');
    field1.attr('type','hidden');
    field1.attr('name','date');
    field1.attr('value',$.now());
    
    fakeForm.append(field);
    
    $(document.body).append(fakeForm);
    fakeForm.submit();
    console.log("dud");
    return false;
}