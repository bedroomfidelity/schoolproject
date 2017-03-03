/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Util.StartUp;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

/**
 *
 * @author N5537
 */
public class UserTaskDAO {
    private Session session = StartUp.getSessionFactory().openSession();
    
    public void addUserTask(String user, Long taskid){
        session.beginTransaction();
        SQLQuery q = session.createSQLQuery("insert into user_task(worker,task) values(?,?);");
        q.setString(1, user);
        q.setLong(2, taskid);
        q.executeUpdate();
        session.getTransaction().commit();
        
    }
}
