/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resource;

import DAO.CommentDAO;
import DAO.NotiDAO;
import DAO.TaskDAO;
import DAO.UserDAO;
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
import model.Comment;
import model.Notification;
import model.Task;
import model.User;

/**
 *
 * @author N5537
 */
@Path("comment")
public class CommentResource {
    private CommentDAO dao = new CommentDAO();
    
    //GET ALL COMMENTS IN A TASK WITH THE TASK'S GIVEN ID
    @GET
    @Path("{taskid}")
    @Produces(MediaType.APPLICATION_XML)
    public List<Comment> getByTask(@PathParam("taskid") Long id){
        TaskDAO taskdao = new TaskDAO();
        List<Task> tasks = taskdao.getById(id);
        return tasks.get(0).getComments();
    }
    
    //ADD NEW COMMENT WITH A FORM
    @POST
    @Path("add")
    public Response addComment(@FormParam("commenter") String commenter,
            @FormParam("taskid") Long taskid, @FormParam("content") String content){
        UserDAO userdao = new UserDAO();
        TaskDAO taskdao = new TaskDAO();
        User user = userdao.getByUsername(commenter).get(0);
        Task task = taskdao.getById(taskid).get(0);
        Comment comment = new Comment(content, user, task);
        dao.addComment(comment);
        //add new notification
        User u = task.getWorkers().get(0);
        NotiDAO notidao = new NotiDAO();
        Notification noti = new Notification(u, comment , "add");
        notidao.addNotification(noti);
        return Response.status(200).build();
    }
    
    //EDIT A COMMENT WITH A FORM
    @PUT
    @Path("edit")
    public Response editComment(@FormParam("commentid") Long id,
            @FormParam("user") String user, @FormParam("content") String content,
            @FormParam("taskid") Long taskid){
        UserDAO userdao = new UserDAO();
        TaskDAO taskdao = new TaskDAO();
        Comment comment = dao.getById(id).get(0);
        User commenter = userdao.getByUsername(user).get(0);
        Task task = taskdao.getById(taskid).get(0);
        comment.setCommenter(commenter);
        comment.setOnTask(task);
        comment.setComment(content);
        dao.editComment(comment);
        return Response.status(200).build();
    }
    
    //DELETE A COMMENT WITH A GIVEN ID
    @DELETE
    @Path("delete/{id}")
    public Response deleteComment(@PathParam("id") Long id){
        Comment comment = dao.getById(id).get(0);
        dao.deleteComment(comment);
        return Response.status(200).build();
    }
}
