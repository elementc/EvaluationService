package org.rest.util;

import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerResponse;
import com.sun.jersey.spi.container.ContainerResponseFilter;

public class CORSFilter implements ContainerResponseFilter {

    @Override
    public ContainerResponse filter(ContainerRequest containerRequest, ContainerResponse containerResponse) {
        containerResponse.getHttpHeaders().add("Access-Control-Allow-Origin", containerRequest.getHeaderValue("Origin"));
        containerResponse.getHttpHeaders().add("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
        containerResponse.getHttpHeaders().add("Access-Control-Allow-Credentials", "true");
        containerResponse.getHttpHeaders().add("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With, Authorization");
        return containerResponse;
    }
}