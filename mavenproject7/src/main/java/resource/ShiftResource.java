/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resource;

import DAO.ShiftDAO;
import DAO.UserDAO;
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
import model.Shift;
import model.User;

/**
 *
 * @author N5537
 */
@Path("shift")
public class ShiftResource {
    private ShiftDAO dao = new ShiftDAO();
    
    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_XML)
    public List<Shift> getAllShift(){
        return dao.getAllShift();
    }
    
    @GET
    @Path("getbyname/{name}")
    @Produces(MediaType.APPLICATION_XML)
    public List<Shift> getByName(@PathParam("name") String name){
        return dao.getByName(name);
    }
    
    @POST
    @Path("add")
    public Response addShift(@FormParam("employee") String employee,
            @FormParam("starttime") Date starttime, @FormParam("endtime") Date endtime){
        UserDAO userdao = new UserDAO();
        User user = userdao.getByUsername(employee).get(0);
        Shift shift = new Shift(user, starttime, endtime);
        dao.addShift(shift);
        return Response.status(200).build();
    }
    
    @PUT
    @Path("edit")
    public Response editShift(@FormParam("id") Long id,
            @FormParam("employee") String employee, @FormParam("starttime") Date starttime,
            @FormParam("endtime") Date endtime){
        UserDAO userdao = new UserDAO();
        Shift shift = dao.getById(id).get(0);
        shift.setEmployee(userdao.getByUsername(employee).get(0));
        shift.setEndtime(endtime);
        shift.setStarttime(starttime);
        dao.editShift(shift);
        return Response.status(200).build();
    }
    
    @DELETE
    @Path("delete/{id}")
    public Response deleteShift(@PathParam("id") Long id){
        Shift shift = dao.getById(id).get(0);
        dao.deleteShift(shift);
        return Response.status(200).build();
    }
}
