/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Util.StartUp;
import java.util.List;
import model.User;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author N5537
 */
public class UserDAO {
    private Session session = StartUp.getSessionFactory().openSession();
    
    public List<User> getAllUser(){
        session.beginTransaction();
        List list = session.createCriteria(model.User.class).list();
        session.getTransaction().commit();
        return list;
    }
    
    public void addUser(User user){
        session.beginTransaction();
        session.persist(user);
        session.getTransaction().commit();
    }
    
    public void editUser(User user){
        session.beginTransaction();
        session.persist(user);
        session.getTransaction().commit();
    }
    
    public void deleteUser(User user){
        session.beginTransaction();
        session.delete(user);
        session.getTransaction().commit();
    }
    
    public List<User> getByUsername(String username){
        session.beginTransaction();
        List list = session.createCriteria(model.User.class).add(Restrictions.eq("username", username)).list();
        session.getTransaction().commit();
        return list;
    }
    
    public List<User> getByFirstname(String firstname){
        session.beginTransaction();
        List list = session.createCriteria(model.User.class).add(Restrictions.eq("firstname", firstname)).list();
        session.getTransaction().commit();
        return list;
    }
    
    public List<User> getByLastname(String lastname){
        session.beginTransaction();
        List list = session.createCriteria(model.User.class).add(Restrictions.eq("lastname", lastname)).list();
        session.getTransaction().commit();
        return list;
    }
    
    public List<User> getByTitle(String title){
        session.beginTransaction();
        List list = session.createCriteria(model.User.class).add(Restrictions.eq("title", title)).list();
        session.getTransaction().commit();
        return list;
    }

    public List<User> getById(Long id) {
        session.beginTransaction();
        List list = session.createCriteria(model.User.class).add(Restrictions.eq("userID", id)).list();
        session.getTransaction().commit();
        return list;
    }

    
}
