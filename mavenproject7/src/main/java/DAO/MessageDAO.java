/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Util.StartUp;
import java.util.List;
import model.Message;
import model.User;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author N5537
 */
public class MessageDAO {
    private Session session = StartUp.getSessionFactory().openSession();
    
    public List<Message> getAllMessage(){
        session.beginTransaction();
        List list = session.createCriteria(model.Shift.class).list();
        session.getTransaction().commit();
        return list;
    }
    
    public void addMessage(Message message){
        session.beginTransaction();
        session.persist(message);
        session.getTransaction().commit();   
    }
    
    public void editMessage(Message message){
        session.beginTransaction();
        session.merge(message);
        session.getTransaction().commit();
    }
    
    public void deleteMessage(Message message){
        session.beginTransaction();
        session.delete(message);
        session.getTransaction().commit();
    }
    
    public List<Message> getBySender(String sender){
        UserDAO userdao = new UserDAO();
        User user = userdao.getByUsername(sender).get(0);
        session.beginTransaction();
        List list = session.createCriteria(model.Shift.class).add(Restrictions.eq("sender", user)).list();
        session.getTransaction().commit();
        return list;
    }
    
    public List<Message> getByReceiver(String receiver){
        UserDAO userdao = new UserDAO();
        User user = userdao.getByUsername(receiver).get(0);
        session.beginTransaction();
        List list = session.createCriteria(model.Shift.class).add(Restrictions.eq("sender", user)).list();
        session.getTransaction().commit();
        return list;
    }
    
    public List<Message> getById(Long id){
        session.beginTransaction();
        List list = session.createCriteria(model.Message.class).add(Restrictions.eq("messageID", id)).list();
        session.getTransaction().commit();
        return list;
    }
    
    public List<Message> getByReceiverUnread(String receiver){
        session.beginTransaction();
        List list = session.createCriteria(model.Shift.class).add(Restrictions.eq("receiver", receiver))
                .add(Restrictions.eq("read", false)).list();
        session.getTransaction().commit();
        return list;
    }
}
