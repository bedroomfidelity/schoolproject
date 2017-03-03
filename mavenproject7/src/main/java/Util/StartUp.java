/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import org.hibernate.SessionFactory;
import org.hibernate.SessionFactoryObserver;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.boot.registry.internal.StandardServiceRegistryImpl;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.tool.hbm2ddl.SchemaExport;

/**
 *
 * @author N5537
 */
@WebListener
public class StartUp implements ServletContextListener {
    
    private static SessionFactory sessionFactory;
            
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Configuration config = new Configuration();
        config.addAnnotatedClass(model.User.class)
              .addAnnotatedClass(model.Task.class)
              .addAnnotatedClass(model.Message.class)
              .addAnnotatedClass(model.Comment.class)
              .addAnnotatedClass(model.Shift.class);
        config.configure();
        new SchemaExport(config).drop(false,false);
            
        StandardServiceRegistryBuilder serviceRegistryBuilder = 
                new StandardServiceRegistryBuilder();
        serviceRegistryBuilder.applySettings(config.getProperties());

        final ServiceRegistry serviceRegistry = serviceRegistryBuilder.build();
        
        config.setSessionFactoryObserver(new SessionFactoryObserver() {
            @Override
            public void sessionFactoryCreated(SessionFactory factory) {}
            @Override
            public void sessionFactoryClosed(SessionFactory factory) {
                System.out.println("sessionFactoryClosed()");
                ((StandardServiceRegistryImpl) serviceRegistry).destroy();
            }
        });

        sessionFactory = config.buildSessionFactory(serviceRegistry);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        
    }
    
    public static SessionFactory getSessionFactory(){
        return sessionFactory;
    }
    
}
