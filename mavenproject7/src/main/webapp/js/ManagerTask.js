/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function(){
   username = getCookie("uname");
   websitebuild();
});
function websitebuild(){
    showTaskList();
}
function showTaskList(){
    username = getCookie("uname");
    console.log(username);
    $.get("api/task/undone/",function(data){
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
            theTask.appendChild(taskNameAndTimeContainer);
            theTask.appendChild(taskDescription);
            $("#tasklist .task-card .task-content .list-group").append(theTask);
        });
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