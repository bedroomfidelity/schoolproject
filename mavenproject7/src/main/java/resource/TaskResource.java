/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resource;

import DAO.TaskDAO;
import DAO.UserDAO;
import DAO.UserTaskDAO;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.ws.rs.Consumes;
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
import model.Task;
import model.User;
import org.glassfish.jersey.media.multipart.FormDataParam;
/**
 *
 * @author N5537
 */
@Path("task")
public class TaskResource {
    private TaskDAO dao = new TaskDAO();
    
    @POST
    @Path("add")
    public Response addTask(@FormParam("taskname") String taskname,
            @FormParam("description") String description, @FormParam("startdate") String startdate,
            @FormParam("deadline") String deadline, @FormParam("worker") String worker) throws ParseException{
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        Date start = format.parse(startdate);
        Date end = format.parse(deadline);
        Task task = new Task(taskname, description, start, end, false);
        dao.addTask(task);
        UserTaskDAO usertaskdao = new UserTaskDAO();
        usertaskdao.addUserTask(worker, task.getTaskID());
        return Response.status(200).build();
    }
    
    @PUT
    @Path("edit")
    public Response editTask(@FormParam("taskid") Long taskid, @FormParam("taskname") String taskname,
            @FormParam("description") String description, @FormParam("startdate") Date startdate,
            @FormParam("deadline") Date deadline, @FormParam("done") boolean done){
        Task task = dao.getById(taskid).get(0);
        task.setDeadline(deadline);
        task.setDescription(description);
        task.setDone(done);
        task.setStartdate(startdate);
        task.setNoticed(false);
        dao.editTask(task);
        return Response.status(200).build();
    }
    
    @DELETE
    @Path("delete/{id}")
    public Response deleteShift(@PathParam("taskid") Long taskid){
        Task task = dao.getById(taskid).get(0);
        dao.deleteTask(task);
        return Response.status(200).build();
    }
    
    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_XML)
    public List<Task> getAllTask(){
        return dao.getAllTask();
    }
    
    @GET
    @Path("{username}")
    @Produces(MediaType.APPLICATION_XML)
    public List<Task> getByUsername(@PathParam("username") String username){
        UserDAO userdao = new UserDAO();
        User user = userdao.getByUsername(username).get(0);
        return user.getTasks();
    }
    
    @GET
    @Path("undone/{username}")
    @Produces(MediaType.APPLICATION_XML)
    public List<Task> getByUsernameUndone(@PathParam("username") String username){
        return dao.getTaskByWorkerUndone(username);
    }
    
    @PUT
    @Path("done/{taskid}")
    public Response doneTask(@PathParam("taskid") Long taskid, @FormParam("done") boolean done){
        Task task = dao.getById(taskid).get(0);
        task.setDone(done);
        return Response.status(200).build();
    }
    
    @PUT
    @Path("read/{taskid}")
    public Response readTask(@PathParam("taskid") Long taskid){
        Task task = dao.getById(taskid).get(0);
        task.setNoticed(true);
        return Response.status(200).build();
    }
}
