/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Util.StartUp;
import java.util.List;
import model.Notification;
import model.User;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author N5537
 */
public class NotiDAO {
    private Session session = StartUp.getSessionFactory().openSession();
    
    public void addNotification(Notification noti){
        session.beginTransaction();
        session.persist(noti);
        session.getTransaction().commit();
    }
    
    public List<Notification> getByUser(User user){
        session.beginTransaction();
        List list = session.createCriteria(model.Notification.class)
                .add(Restrictions.eq("user", user)).list();
        session.getTransaction().commit();
        return list;
    }
    
    public List<Notification> getByUserUnviewed(User user){
        session.beginTransaction();
        List list = session.createCriteria(model.Notification.class)
                .add(Restrictions.eq("user", user))
                .add(Restrictions.eq("viewed", false)).list();
        session.getTransaction().commit();
        return list;
    }
    
    public void view(Notification noti){
        session.beginTransaction();
        noti.setViewed(true);
        session.persist(noti);
        session.getTransaction().commit();
    }
}
