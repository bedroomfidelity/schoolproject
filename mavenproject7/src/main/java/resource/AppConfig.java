/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resource;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.ws.rs.core.Application;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

/**
 *
 * @author peter
 */
@javax.ws.rs.ApplicationPath("api")
public class AppConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        //ResourceConfig resourceconfig = new ResourceConfig(MultiPartFeature.class);
        //resourceconfig.register(MultiPartFeature.class);
        return resources;
    }


    @Override
    public Map<String, Object> getProperties() {
        Map<String, Object> props = new HashMap<>();
        props.put("jersey.config.server.provider.classnames", 
            "org.glassfish.jersey.media.multipart.MultiPartFeature");
        return props;
    }

    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(resource.CommentResource.class);
        resources.add(resource.MessageResource.class);
        resources.add(resource.ShiftResource.class);
        resources.add(resource.TaskResource.class);
        resources.add(resource.UserResource.class);
    }
        
    
}
