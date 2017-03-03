/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resource;

import DAO.MessageDAO;
import DAO.TaskDAO;
import DAO.UserDAO;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
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
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import model.User;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

/**
 *
 * @author N5537
 */
@Path("user")
public class UserResource {

    private UserDAO dao = new UserDAO();

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Path("signup")
    public Response signUp(@FormDataParam("username") String username, @FormDataParam("password") String password,
            @FormDataParam("title") String title, @FormDataParam("email") String email,
            @FormDataParam("firstname") String firstname, @FormDataParam("lastname") String lastname,
            @FormDataParam("address") String address, @FormDataParam("phonenumber") String phonenumber,
            @FormDataParam("image") InputStream image, @FormDataParam("image") FormDataContentDisposition imageDetail) {
        if (dao.getByUsername(username).isEmpty()) {
            String uploadedLocation = "d:/server/images/" + imageDetail.getFileName();
            writeFile(uploadedLocation, image);
            User user = new User(username, password, title, email,
                    phonenumber, firstname, lastname, address, false, imageDetail.getFileName());
            dao.addUser(user);
            return Response.status(200).build();
        } else {
            return Response.noContent().build();
        }
    }

    @DELETE
    @Path("delete/{username}")
    public Response deleteUser(@PathParam("username") String username) {
        User user = dao.getByUsername(username).get(0);
        dao.deleteUser(user);
        return Response.status(200).build();
    }

    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_XML)
    public List<User> getAllUser() {
        List<User> list = dao.getAllUser();
        System.out.println(list);
        return list;
    }

    @PUT
    @Path("edit")
    public Response editUser(@FormDataParam("username") String username, @FormDataParam("password") String password,
            @FormDataParam("title") String title, @FormDataParam("email") String email,
            @FormDataParam("firstname") String firstname, @FormDataParam("lastname") String lastname,
            @FormDataParam("address") String address, @FormDataParam("newUsername") String newUsername,
            @FormDataParam("image") InputStream image, @FormDataParam("image") FormDataContentDisposition imageDetail) {
        User user = dao.getByUsername(username).get(0);
        user.setAddress(address);
        user.setUsername(newUsername);
        user.setTitle(title);
        user.setPhoneNumber(lastname);
        user.setPassword(password);
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setEmail(email);
        File file = new File("d:/server/images/"+user.getImage());
        if (file.delete()){
            writeFile("d:/server/images/"+imageDetail.getFileName(), image);
            user.setImage(imageDetail.getFileName());
            dao.editUser(user);
        }
        return Response.status(200).build();
    }

    @GET
    @Path("getbyname/{username}")
    @Produces(MediaType.APPLICATION_XML)
    public List<User> getByUsername(@PathParam("username") String username) {
        return dao.getByUsername(username);
    }

    @POST
    @Path("login")
    @Produces(MediaType.APPLICATION_XML)
    public User login(@PathParam("username") String username, @PathParam("password") String password) {
        if (!dao.getByUsername(username).isEmpty()) {
            User user = dao.getByUsername(username).get(0);
            if (user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    @PUT
    @Path("check/{username}")
    public Response checkNoti(@PathParam("username") String username) {
        User user = dao.getByUsername(username).get(0);
        user.setNotification(true);
        dao.editUser(user);
        return Response.status(200).build();
    }

    @PUT
    @Path("updatenoti/{username}")
    public Response updateNoti(@PathParam("username") String username) {
        TaskDAO taskdao = new TaskDAO();
        MessageDAO messagedao = new MessageDAO();
        User user = dao.getByUsername(username).get(0);
        if (taskdao.getTaskByWorkerUnread(username).isEmpty()
                && messagedao.getByReceiverUnread(username).isEmpty()) {
            user.setNotification(false);
        } else {
            user.setNotification(true);
        }
        return Response.status(200).build();
    }
    
    private void writeFile(String uploadedLocation, InputStream uploadedInput) {
        try {
            OutputStream out = new FileOutputStream(new File(uploadedLocation));
            int read = 0;
            byte[] bytes = new byte[1024];

            out = new FileOutputStream(new File(uploadedLocation));
            while ((read = uploadedInput.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @GET
    @Path("avatar/{username}")
    public Response getAvatar(@PathParam("username") String username){
        User user = dao.getByUsername(username).get(0);
        String image = user.getImage();
        File file = new File("d:/server/images/"+image);
        return Response.ok(file)
                .header("content-disposition","attachment; filename="+file.getName()).build();
    }
}
