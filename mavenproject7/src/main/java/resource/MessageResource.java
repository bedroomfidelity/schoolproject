/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resource;

import DAO.MessageDAO;
import DAO.UserDAO;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Calendar;
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
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import model.Message;
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

    @GET
    @Path("bysender/{username}")
    @Produces(MediaType.APPLICATION_XML)
    public List<Message> getBySender(@PathParam("username") String username) {
        UserDAO userdao = new UserDAO();
        User user = userdao.getByUsername(username).get(0);
        return user.getSentMessages();
    }

    @GET
    @Path("byreceiver/{username}")
    @Produces(MediaType.APPLICATION_XML)
    public List<Message> getByReceiver(@PathParam("username") String username) {
        UserDAO userdao = new UserDAO();
        User user = userdao.getByUsername(username).get(0);
        return user.getReceivedMessages();
    }

    @DELETE
    @Path("delete/{id}")
    public Response deleteMessage(@PathParam("id") Long id) {
        Message message = dao.getById(id).get(0);
        dao.deleteMessage(message);
        return Response.status(200).build();
    }

    @PUT
    @Path("read/{messageid}")
    public Response readMessage(@PathParam("messageid") Long id) {
        Message message = dao.getById(id).get(0);
        message.setRead(true);
        return Response.status(200).build();
    }

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Path("new")
    public Response addMessage(@FormDataParam("header") String header, @FormDataParam("sender") String sender,
            @FormDataParam("receiver") String receiver, @FormDataParam("content") String content,
            @FormDataParam("date") String date, @FormDataParam("file") InputStream file,
            @FormDataParam("file") FormDataContentDisposition fileDetail) {
        UserDAO userdao = new UserDAO();
        User send = userdao.getByUsername(sender).get(0);
        User receive = userdao.getByUsername(receiver).get(0);
        Message message = new Message(content, send, receive, null, false, new Date(Calendar.getInstance().getTimeInMillis()));
        if (file == null) {
            dao.addMessage(message);
        } else {
            String uploadedLocation = "d:/server/files/" + fileDetail.getFileName();
            writeFile(uploadedLocation, file);
            message.setFile(fileDetail.getFileName());
            dao.addMessage(message);
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
    @Path("file/{id}")
    public Response getFile(@PathParam("id") Long id){
        Message message = dao.getById(id).get(0);
        String f = message.getFile();
        File file = new File("d:/server/files/"+f);
        return Response.ok(file)
                .header("content-disposition","attachment; filename="+file.getName()).build();
    }
}
