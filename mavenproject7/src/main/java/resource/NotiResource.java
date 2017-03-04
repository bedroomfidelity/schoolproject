/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resource;

import DAO.NotiDAO;
import DAO.UserDAO;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import model.Notification;
import model.User;

/**
 *
 * @author N5537
 */
@Path("noti")
public class NotiResource {
    private NotiDAO dao = new NotiDAO();
    
    //GET ALL NOTIFICATIONS OF AN USER
    @GET
    @Path("{username}")
    @Produces(MediaType.APPLICATION_XML)
    public List<Notification> getNotiByUser(@PathParam("username") String username){
        UserDAO userdao = new UserDAO();
        User user = userdao.getByUsername(username).get(0);
        return dao.getByUser(user);
    } 
    
    //GET ALL NOTIFICATIONS THAT AN USER HASNT VIEWED 
    @GET
    @Path("unviewed/{username}")
    @Produces(MediaType.APPLICATION_XML)
    public List<Notification> getNotiUnviewed(@PathParam("username") String username){
        UserDAO userdao = new UserDAO();
        User user = userdao.getByUsername(username).get(0);
        return dao.getByUserUnviewed(user);
    }
    
    //VIEW ALL UNVIEWED NOTIFICATION
    @PUT
    @Path("view/{username}")
    public void view(@PathParam("username") String username){
        UserDAO userdao = new UserDAO();
        User user = userdao.getByUsername(username).get(0);
        //view the notis
        List<Notification> list = dao.getByUserUnviewed(user);
        for (Notification noti:list){
            dao.view(noti);
        }
    }
}
