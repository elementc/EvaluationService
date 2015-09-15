package org;

import java.io.IOException;

import com.sun.jersey.api.core.ClasspathResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.jersey.spi.container.servlet.ServletContainer;


import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.rest.security.AuthenticationFilter;
import org.rest.util.CORSFilter;

public class StartUp {

    public static void main(String[] args) throws Exception {
        int port = 48484;

        if(args.length > 0){
            port = Integer.parseInt(args[0]);
        }

        StartUp startUp = new StartUp();
        ServletHolder sh = startUp.buildRestServletHolder();
        sh.setInitParameter("com.sun.jersey.api.json.POJOMappingFeature", "true");
        Server server = new Server(port);
        ServletContextHandler context = new ServletContextHandler(server, "/", ServletContextHandler.SESSIONS);
        context.addServlet(sh, "/evaluationsapi/*");
        context.addServlet(startUp.buildUIServletHolder(), "/evaluationsui/*");
        context.addServlet(startUp.buildAdminServletHolder(), "/evaluationsadmin/*");
        server.start();
        server.join();
    }

    private ServletHolder buildRestServletHolder() throws IOException{
        ResourceConfig rc = getResourceConfig();
        rc.getContainerResponseFilters().add(new CORSFilter());
        rc.getContainerRequestFilters().add(new AuthenticationFilter());
        return new ServletHolder(new ServletContainer(rc));
    }

    private ServletHolder buildUIServletHolder(){
        DefaultServlet defaultServlet = new DefaultServlet();
        ServletHolder sh = new ServletHolder("EvaluationsUI", defaultServlet);
        //below is a trick to allow loading static resources from the jar file
        String resourceDir = this.getClass().getClassLoader().getResource("org/ui").toExternalForm();
        sh.setInitParameter("resourceBase", resourceDir);
        sh.setInitParameter("pathInfoOnly", "true");
        return sh;
    }

    private ServletHolder buildAdminServletHolder(){
        DefaultServlet defaultServlet = new DefaultServlet();
        ServletHolder sh = new ServletHolder("EvaluationsAdmin", defaultServlet);
        //below is a trick to allow loading static resources from the jar file
        String resourceDir = this.getClass().getClassLoader().getResource("org/admin").toExternalForm();
        sh.setInitParameter("resourceBase", resourceDir);
        sh.setInitParameter("pathInfoOnly", "true");
        return sh;
    }

    private static ResourceConfig getResourceConfig(){
        return new ClasspathResourceConfig();
    }
}