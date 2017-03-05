/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resource;

import DAO.MessageDAO;
import DAO.NotiDAO;
import DAO.UserDAO;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import model.Message;
import model.Notification;
import model.User;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

/**
 *
 * @author N5537
 */
@Path("message")
public class MessageResource {

    private MessageDAO dao = new MessageDAO();
    
    //GET ALL MESSAGE SENT BY AN USER
    @GET
    @Path("bysender/{username}")
    @Produces(MediaType.APPLICATION_XML)
    public List<Message> getBySender(@PathParam("username") String username) {
        UserDAO userdao = new UserDAO();
        User user = userdao.getByUsername(username).get(0);
        return user.getSentMessages();
    }
    
    //GET ALL MESSAGE SENT TO AN USER
    @GET
    @Path("byreceiver/{username}")
    @Produces(MediaType.APPLICATION_XML)
    public List<Message> getByReceiver(@PathParam("username") String username) {
        UserDAO userdao = new UserDAO();
        User user = userdao.getByUsername(username).get(0);
        return user.getReceivedMessages();
    }
    
    //DELETE A MESSAGE WITH GIVEN ID
    @DELETE
    @Path("delete/{id}")
    public Response deleteMessage(@PathParam("id") Long id) {
        Message message = dao.getById(id).get(0);
        dao.deleteMessage(message);
        return Response.status(200).build();
    }
    
    //ADD NEW MESSAGE WITH A MULTIPART FORM 
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Path("new")
    public Response addMessage(@FormDataParam("sender") String sender,
            @FormDataParam("receiver") String receiver, @FormDataParam("content") String content,
            @FormDataParam("date") String date, @FormDataParam("file") InputStream file,
            @FormDataParam("file") FormDataContentDisposition fileDetail) {
        UserDAO userdao = new UserDAO();
        User send = userdao.getByUsername(sender).get(0);
        User receive = userdao.getByUsername(receiver).get(0);
        Message message = new Message(content, send, receive, new Date(Calendar.getInstance().getTimeInMillis()));
        if (file == null) {
            dao.addMessage(message);
        } else {
            String uploadedLocation = "d:/server/files/" + fileDetail.getFileName();
            writeFile(uploadedLocation, file);
            message.setFile(fileDetail.getFileName());
            dao.addMessage(message);
        }
        //add new notification 
        NotiDAO notidao = new NotiDAO();
        Notification noti = new Notification(receive, message , "add");
        notidao.addNotification(noti);
        return Response.status(200).build();
    }
    
    //WRITE THE FILE (IF INCLUDED IN THE FORM) TO THE SERVER
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
    
    //GET THE FILE (IF INCLUDED) IN A MESSAGE WITH GIVEN MESSAGE ID 
    @GET
    @Path("file/{id}")
    public Response getFile(@PathParam("id") Long id){
        Message message = dao.getById(id).get(0);
        String f = message.getFile();
        File file = new File("d:/server/files/"+f);
        return Response.ok(file)
                .header("content-disposition","attachment; filename="+file.getName()).build();
    }
}
