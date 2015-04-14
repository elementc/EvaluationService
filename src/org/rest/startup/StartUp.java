package org.rest.startup;

import java.io.IOException;

import com.sun.jersey.api.core.ClasspathResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.jersey.spi.container.servlet.ServletContainer;


import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.rest.security.AuthenticationFilter;
import org.rest.util.CORSFilter;

public class StartUp {

    public static void main(String[] args) throws Exception {
        int port = 48484;
        ServletHolder sh = createServletHolder();
        sh.setInitParameter("com.sun.jersey.api.json.POJOMappingFeature", "true");
        Server server = new Server(port);
        ServletContextHandler context = new ServletContextHandler(server, "/", ServletContextHandler.SESSIONS);
        context.addServlet(sh, "/evaluationsapi/*");
        server.start();
        server.join();
    }

    private static ServletHolder createServletHolder() throws IOException{
        ResourceConfig rc = getResourceConfig();
        rc.getContainerResponseFilters().add(new CORSFilter());
        rc.getContainerRequestFilters().add(new AuthenticationFilter());
        return new ServletHolder(new ServletContainer(rc));
    }

    private static ResourceConfig getResourceConfig(){
        return new ClasspathResourceConfig();
    }
}