/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Util.StartUp;
import java.util.List;
import model.User;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author N5537
 */
public class UserTaskDAO {
    private Session session = StartUp.getSessionFactory().openSession();
    
    public void addUserTask(String user, Long taskid){
        session.beginTransaction();
        SQLQuery q = session.createSQLQuery("insert into user_task(worker,task) values(?,?);");
        q.setString(0, user);
        q.setLong(1, taskid);
        q.executeUpdate();
        session.getTransaction().commit();
    }
    
    public void deleteByUser(String user){
        session.beginTransaction();
        SQLQuery q = session.createSQLQuery("delete from user_task where worker=?;");
        q.setString(0, user);
        q.executeUpdate();
        session.getTransaction().commit();
    }
    
    public void deleteByTask(Long id){
        session.beginTransaction();
        SQLQuery q = session.createSQLQuery("delete from user_task where task=?;");
        q.setLong(0, id);
        q.executeUpdate();
        session.getTransaction().commit();
    }
    
    public List<String> getByTask(Long id){
        session.beginTransaction();
        SQLQuery q = session.createSQLQuery("select worker from user_task where task=?;");
        q.setLong(0, id);
        List list = q.list();
        System.out.println(list);
        session.getTransaction().commit();
        return list;
    }
}
