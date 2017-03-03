/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Util.StartUp;
import java.util.List;
import model.Comment;
import model.Task;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author N5537
 */
public class CommentDAO {
    private Session session = StartUp.getSessionFactory().openSession();
    
    public void addComment(Comment comment){
        session.beginTransaction();
        session.persist(comment);
        session.getTransaction().commit();   
    }
    
    public void editComment(Comment comment){
        session.beginTransaction();
        session.merge(comment);
        session.getTransaction().commit();
    }
    
    public void deleteComment(Comment comment){
        session.beginTransaction();
        session.delete(comment);
        session.getTransaction().commit();
    }

    public List<Comment> getById(Long id) {
        session.beginTransaction();
        List list = session.createCriteria(model.Comment.class).add(Restrictions.eq("commentID", id)).list();
        session.getTransaction().commit();
        return list;
    }
    
}
