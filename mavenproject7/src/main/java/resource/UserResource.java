/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resource;

import DAO.UserDAO;
import DAO.UserTaskDAO;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.List;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import model.User;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

/**
 *
 * @author N5537
 */
@Path("user")
public class UserResource {

    @Context
    private ServletContext context;
    
    private UserDAO dao = new UserDAO();
    
    //ADD NEW USER WITH A MULTIPART FORM
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Path("signup")
    public Response signUp(@FormDataParam("username") String username, @FormDataParam("password") String password,
            @FormDataParam("title") String title, @FormDataParam("email") String email,
            @FormDataParam("firstname") String firstname, @FormDataParam("lastname") String lastname,
            @FormDataParam("address") String address, @FormDataParam("phonenumber") String phonenumber,
            @FormDataParam("image") InputStream image, @FormDataParam("image") FormDataContentDisposition imageDetail) {
        if (dao.getByUsername(username).isEmpty()) {
            //String uploadedLocation = "d:/server/images/" + imageDetail.getFileName();
            String uploadedLocation = context.getRealPath("/pic/") + imageDetail.getFileName();
            writeFile(uploadedLocation, image);
            User user = new User(username, password, title, email,
                    phonenumber, firstname, lastname, address, imageDetail.getFileName());
            dao.addUser(user);
            return Response.status(200).build();
        } else {
            return Response.noContent().build();
        }
    }
    
    //DELETE AN USER WITH GIVEN USERNAME
    @DELETE
    @Path("delete/{username}")
    public Response deleteUser(@PathParam("username") String username) {
        //get user with matching username
        User user = dao.getByUsername(username).get(0);
        //delete entry in user_task
        UserTaskDAO usertaskdao = new UserTaskDAO();
        usertaskdao.deleteByUser(username);
        //delete user
        dao.deleteUser(user);
        return Response.status(200).build();
    }
    
    //GET ALL USERS IN DATABASE
    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_XML)
    public List<User> getAllUser() {
        List<User> list = dao.getAllUser();
        System.out.println(list);
        return list;
    }
    
    //EDIT AN USER INFORMATION WITH A MULTIPART FORM
    @POST
    @Path("edit")
    public void editUser(@FormDataParam("username") String username, @FormDataParam("password") String password,
            @FormDataParam("email") String email, @FormDataParam("phonenumber") String phoneNumber,
            @FormDataParam("firstname") String firstname, @FormDataParam("lastname") String lastname,
            @FormDataParam("address") String address,
            @FormDataParam("image") InputStream image, @FormDataParam("image") FormDataContentDisposition imageDetail) {
        User user = dao.getByUsername(username).get(0);
        user.setAddress(address);
        //user.setUsername(newUsername);
        //user.setTitle(title);
        user.setPhoneNumber(phoneNumber);
        user.setPassword(password);
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setEmail(email);
        if (image!=null){
            File file = new File("d:/server/images/"+user.getImage());         
                if (file.delete()){
                writeFile("d:/server/images/"+imageDetail.getFileName(), image);
                user.setImage(imageDetail.getFileName());
                }
        }
        dao.editUser(user);
        //return Response.status(200).build();
        }
    
    //GET USER BY USERNAME
    @GET
    @Path("getbyname/{username}")
    @Produces(MediaType.APPLICATION_XML)
    public List<User> getByUsername(@PathParam("username") String username) {
        return dao.getByUsername(username);
    }
    
    //LOGIN 
    @POST
    @Path("login")
    @Produces(MediaType.APPLICATION_XML)
    public Response login(@FormParam("username") String username, @FormParam("password") String password) throws URISyntaxException {
        if (!dao.getByUsername(username).isEmpty()) {
            User user = dao.getByUsername(username).get(0);
            if (user.getPassword().equals(password)) {
                java.net.URI location = new java.net.URI("../Home.html?user=" + user.getUsername());
                return Response.temporaryRedirect(location).build();
            }
        }
        return null;
    }

    //WRITE THE FILE TO THE SERVER
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
    
    //THE THE IMAGE FILE FROM THE SERVER WITH GIVEN USERNAME
    @GET
    @Path("avatar/{username}")
    public Response getAvatar(@PathParam("username") String username){
        User user = dao.getByUsername(username).get(0);
        String image = user.getImage();
        File file = new File(context.getRealPath("images/")+image);
        return Response.ok(file)
                .header("content-disposition","attachment; filename="+file.getName()).build();
    }
}
