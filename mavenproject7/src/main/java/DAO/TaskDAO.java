/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Util.StartUp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.Task;
import model.User;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author N5537
 */
public class TaskDAO {
    private Session session = StartUp.getSessionFactory().openSession();
    
    public List<Task> getAllTask(){
        session.beginTransaction();
        List list = session.createCriteria(model.Task.class).list();
        session.getTransaction().commit();
        return list;
    }
    
    public void addTask(Task task){
        session.beginTransaction();
        session.persist(task);
        session.getTransaction().commit();
    }
    
    public void editTask(Task task){
        session.beginTransaction();
        session.persist(task);
        session.getTransaction().commit();
    }
    
    public void deleteTask(Task task){
        session.beginTransaction();
        session.delete(task);
        session.getTransaction().commit();
    }
    
    public List<Task> getByDone(boolean done){
        session.beginTransaction();
        List list = session.createCriteria(model.Task.class).add(Restrictions.eq("done", done)).list();
        session.getTransaction().commit();
        return list;
    }
    
    public List<Task> getById(Long id){
        session.beginTransaction();
        List list = session.createCriteria(model.Task.class).add(Restrictions.eq("taskID", id)).list();
        session.getTransaction().commit();
        return list;
    }
    
    public List<Task> getTaskFuture(Date time){
        session.beginTransaction();
        List list = session.createCriteria(model.Task.class).add(Restrictions.ge("startdate", time)).list();
        session.getTransaction().commit();
        return list;
    }
    
    public List<Task> getTaskPast(Date time){
        session.beginTransaction();
        List list = session.createCriteria(model.Task.class).add(Restrictions.lt("deadline", time)).list();
        session.getTransaction().commit();
        return list;
    }
    
    public List<Task> getTaskCurrent(Date time){
        session.beginTransaction();
        List list = session.createCriteria(model.Task.class).add(Restrictions.ge("deadline", time))
                .add(Restrictions.lt("startdate", time)).list();
        
        session.getTransaction().commit();
        return list;
    }
    
    public List<Task> getTaskByWorkerUndone(String worker){
        UserDAO userdao = new UserDAO();
        User user = userdao.getByUsername(worker).get(0);
        List<Task> task = user.getTasks();
        List<Task> tasks = new ArrayList<>();
        for (Task t:task){
            if(t.isDone()==false){
                tasks.add(t);
            }
        }
        return tasks;
    }
    
    public List<Task> getTaskByWorkerUnread(String worker){
        UserDAO userdao = new UserDAO();
        User user = userdao.getByUsername(worker).get(0);
        List<Task> task = user.getTasks();
        List<Task> tasks = new ArrayList<>();
        for (Task t:task){
            if(t.isNoticed()==false){
                tasks.add(t);
            }
        }
        return tasks;
    }
}
