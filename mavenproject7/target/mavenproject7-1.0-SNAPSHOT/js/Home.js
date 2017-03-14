
/**
 * Created by Khanh on 3/4/2017.
 */
var username;
// Create a new WebSocket
var theSocket = new WebSocket('ws://localhost:8080/mavenproject7/endpoint');
$(document).ready(function(){
// If the user enter the username and password correctly, the server will redirect to the homepage + ?user=<username>
        var getSearchText = window.location.search.substring(1);
        var uRLParameters = getSearchText.split("&");
        var usernameSearch = uRLParameters[0].split("=");
        var checker = usernameSearch[0];
        username = usernameSearch[1];
        if (username && checker=="user"){
        //if username is not null/undefined, 
        //and the parameter is user then the cookie will register new value
        // and clear the query to prevent http status 412 when we refresh the page
        document.cookie = "uname="+username+";path=/;";
        window.location.search = '';
        }
        username = getCookie('uname');
    websitebuild();
});
theSocket.onmessage = function(e){
        if (username == e.data){
            getNoti(e.data);
        }
    };

//Get unviewed notification, change the DOM. Requires the user to click on the clear notification box to clear it
function getNoti(name){
    $.get('api/noti/unviewed/'+name,function(data){ 
           $xml = $(data);
           $noti = $xml.find('notification');
           var numofnoti = $noti.length;
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
// This runs every time the page is loaded
function websitebuild(){
    getProfileTag();
    showTaskList();
    showMessages();
    getNoti(username);
    // The onclick function below will clear the noti and change all unviewed noti to viewed
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
}
//change the profile tag to the current user.
function getProfileTag(){
    username = getCookie("uname");
    $.get("api/user/getbyname/"+username,function(data){
        $xml=$(data);
        $firstname = $xml.find("firstname");
        $lastname = $xml.find("lastname");
        var fullName = $firstname.text() + " " + $lastname.text();
        $("#userid").html(fullName);
    });
}
//show undone task list
function showTaskList(){
    //get undone task list by username
    $.get("api/task/undone/"+username,function(data){
        $xml=$(data);
        //for each task tag in returned xml document
        $tasks = $xml.find("task").each(function(){
            var taskName = $(this).find('taskname').text();
            var startDate = $(this).find('startdate').text();
            var endDate = $(this).find('deadline').text();
            var description = $(this).find('description').text();
            var textID = $(this).find('taskID').text();
            //Dynamically create new element to add in the Task list div
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
            //the dynamically created Button will run the function onclick
            doneButton.onclick = finishTask;
            doneButton.appendChild(document.createTextNode("Done"));
            theTask.appendChild(taskNameAndTimeContainer);
            theTask.appendChild(taskDescription);
            theTask.appendChild(doneButton);
            $("#task-content .list-group").prepend(theTask);
        });
    });
}
//This change the clicked task from undone to done
function finishTask(){
    var taskID = $(this).parent().attr('id').substring(4);
    $.ajax({url:'api/task/done/'+taskID,type:'PUT',success: function(response){
    }});
    $("#task-content .list-group").empty();
    showTaskList();
    //Refreshing the page to get the responsive feel
    setTimeout(function() { window.location.reload(); }, 10);
}
function showMessages(){
    //get the messages and show it on the page
    $.get("api/message/byreceiver/"+username,function(data){
        $xml=$(data);
        $tasks = $xml.find("message").each(function(index){
            var sender = $(this).find('sender').find('firstname').text() + " " + $(this).find('sender').find('lastname').text();
            var sentDate = $(this).find('sentdate').text();
            var content = $(this).find('content').text();
            var messageID = $(this).find('taskID').text();
            var theTask = document.createElement('a');
            theTask.href ="#";
            theTask.id = "message" + messageID;
            var senderContainer = document.createElement('span');
            senderContainer.className = 'mail-sender';
            senderContainer.appendChild(document.createTextNode(sender));
            var sentDateContainer = document.createElement('span');
            sentDateContainer.className = 'mail-subject';
            sentDateContainer.appendChild(document.createTextNode(sentDate));
            var contentContainer = document.createElement('span');
            contentContainer.className = 'mail-message-preview';
            contentContainer.appendChild(document.createTextNode(content));
            theTask.appendChild(senderContainer);
            theTask.appendChild(sentDateContainer);
            theTask.appendChild(contentContainer);
            var theTaskContainer = document.createElement('li');
            theTaskContainer.appendChild(theTask);
            $("#recentmess .mail-list").prepend(theTaskContainer);
        });
    });
}
//Get the value of the cookie
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
//Instead of sending a message directly, submit send message form will create a hidden Form with sender and date added then send it instead
function sendFakeForm(){
    var fakeForm = $("<form></form>");
    
    fakeForm.attr("method","post");
    fakeForm.attr("action","api/message/new");
    fakeForm.css("display","none");
    fakeForm.attr("enctype","multipart/form-data");
    fakeForm.attr("onsubmit","setTimeout(function () { window.location.reload(); }, 10)");
    
    $("#sendmess").children().each(function(){
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
    field1.attr('value',$.now().toString());
    
    fakeForm.append(field);
    
    $(document.body).append(fakeForm);
    fakeForm.submit();
    return false;
}