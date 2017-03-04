/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resource;

import DAO.NotiDAO;
import DAO.TaskDAO;
import DAO.UserDAO;
import DAO.UserTaskDAO;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import model.Notification;
import model.Task;
import model.User;
/**
 *
 * @author N5537
 */
@Path("task")
public class TaskResource {
    private TaskDAO dao = new TaskDAO();
    
    //ADD A NEW TASK WITH A FORM
    @POST
    @Path("add")
    public Response addTask(@FormParam("taskname") String taskname,
            @FormParam("description") String description, @FormParam("startdate") String startdate,
            @FormParam("deadline") String deadline, @FormParam("worker") String worker) throws ParseException{
        //add new task
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        Date start = format.parse(startdate);
        Date end = format.parse(deadline);
        Task task = new Task(taskname, description, start, end, false);
        dao.addTask(task);
        //add new task ralationship with user
        UserTaskDAO usertaskdao = new UserTaskDAO();
        usertaskdao.addUserTask(worker, task.getTaskID());
        //add new notification
        UserDAO userdao = new UserDAO();
        NotiDAO notidao = new NotiDAO();
        User user = userdao.getByUsername(worker).get(0);
        Notification noti = new Notification(user, task, "add");
        notidao.addNotification(noti);
        return Response.status(200).build();
    }
    
    //EDIT A TASK WITH A FORM
    @PUT
    @Path("edit")
    public Response editTask(@FormParam("taskid") Long taskid, @FormParam("taskname") String taskname,
            @FormParam("description") String description, @FormParam("startdate") Date startdate,
            @FormParam("deadline") Date deadline, @FormParam("done") boolean done){
        //get the task
        Task task = dao.getById(taskid).get(0);
        //edit task information
        task.setDeadline(deadline);
        task.setDescription(description);
        task.setDone(done);
        task.setStartdate(startdate);
        dao.editTask(task);
        //add new notification
        String user = task.getWorkers().get(0).getUsername();
        UserDAO userdao = new UserDAO();
        NotiDAO notidao = new NotiDAO();
        User u = userdao.getByUsername(user).get(0);
        Notification noti = new Notification(u, task, "edit");
        notidao.addNotification(noti);
        return Response.status(200).build();
    }
    
    //DELETE A TASK WITH A GIVEN ID
    @DELETE
    @Path("delete/{id}")
    public Response deleteTask(@PathParam("taskid") Long taskid){
        //get the task
        Task task = dao.getById(taskid).get(0);
        //get user ralated to the task
        String user = task.getWorkers().get(0).getUsername();
        //delete the task
        dao.deleteTask(task);
        //add new notification
        UserDAO userdao = new UserDAO();
        NotiDAO notidao = new NotiDAO();
        User u = userdao.getByUsername(user).get(0);
        Notification noti = new Notification(u, "task deleted");
        notidao.addNotification(noti);
        return Response.status(200).build();
    }
    
    //GET ALL THE TASKS IN THE DATABASE
    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_XML)
    public List<Task> getAllTask(){
        return dao.getAllTask();
    }
    
    //GET ALL TASKS OF AN USER WITH GIVEN USERNAME
    @GET
    @Path("{username}")
    @Produces(MediaType.APPLICATION_XML)
    public List<Task> getByUsername(@PathParam("username") String username){
        UserDAO userdao = new UserDAO();
        User user = userdao.getByUsername(username).get(0);
        return user.getTasks();
    }
    
    //GET ALL TASKS UNDONE BY A GIVEN USERNAME
    @GET
    @Path("undone/{username}")
    @Produces(MediaType.APPLICATION_XML)
    public List<Task> getByUsernameUndone(@PathParam("username") String username){
        return dao.getTaskByWorkerUndone(username);
    }
    
    //EDIT A TASK TO "DONE" STATUS
    @PUT
    @Path("done/{taskid}")
    public Response doneTask(@PathParam("taskid") Long taskid, @FormParam("done") boolean done){
        Task task = dao.getById(taskid).get(0);
        task.setDone(done);
        return Response.status(200).build();
    }
    
}
