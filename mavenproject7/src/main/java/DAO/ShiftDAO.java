/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Util.StartUp;
import java.util.List;
import model.Shift;
import model.User;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author N5537
 */
public class ShiftDAO {
    private Session session = StartUp.getSessionFactory().openSession();
    
    public List<Shift> getAllShift(){
        session.beginTransaction();
        List list = session.createCriteria(model.Shift.class).list();
        session.getTransaction().commit();
        return list;
    }
    
    public void addShift(Shift shift){
        session.beginTransaction();
        session.persist(shift);
        session.getTransaction().commit();   
    }
    
    public void editShift(Shift shift){
        session.beginTransaction();
        session.merge(shift);
        session.getTransaction().commit();
    }
    
    public void deleteShift(Shift shift){
        session.beginTransaction();
        session.delete(shift);
        session.getTransaction().commit();
    }
    
    public List<Shift> getByName(String name){
        UserDAO userdao = new UserDAO();
        User user = userdao.getByUsername(name).get(0);
        session.beginTransaction();
        List list = session.createCriteria(model.Shift.class).add(Restrictions.eq("employee", user)).list();
        session.getTransaction().commit();
        return list;
    }
    
    public List<Shift> getById(Long id){
        session.beginTransaction();
        List list = session.createCriteria(model.Shift.class).add(Restrictions.eq("id", id)).list();
        session.getTransaction().commit();
        return list;
    }
}
