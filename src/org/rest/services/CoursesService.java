package org.rest.services;

import org.orm.entities.Course;
import org.orm.services.CourseOperations;
import org.rest.services.dtos.CourseDTO;
import org.rest.util.DTOToEntity;
import org.rest.util.EntityToDTO;
import org.rest.util.URIConstants;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Path(URIConstants.KEY_COURSES)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CoursesService {

    private CourseOperations courseOperations;

    @Context
    private SecurityContext securityContext;

    public CoursesService(){
        courseOperations = new CourseOperations();
    }

    @GET
    public List<CourseDTO> getCourses() throws Exception{
        if(securityContext == null || !securityContext.isUserInRole("ADMIN")){
            unauthorized();
        }
        List<Course> courses = courseOperations.getAllCourses();
        if(courses == null){
            return null;
        }
        List<CourseDTO> courseDTOs = null;

        if(courses != null){
            courseDTOs = new ArrayList<CourseDTO>();
            for (Course course : courses){
                courseDTOs.add(EntityToDTO.getCourseDTO(course));
            }
        }
        return courseDTOs;
    }

    @Path("{courseID}")
    @GET
    public CourseDTO getCourse(@PathParam("courseID") Integer courseID) throws Exception{
        if(securityContext == null || !securityContext.isUserInRole("ADMIN")){
            unauthorized();
        }
        return EntityToDTO.getCourseDTO(courseOperations.getCourse(courseID));
    }


    @Path("{courseID}")
    @DELETE
    public void deleteCourse(@PathParam("courseID") Integer courseID) throws Exception{
        if(securityContext == null || !securityContext.isUserInRole("ADMIN")){
            unauthorized();
        }
        courseOperations.deleteCourse(courseID);
    }

    @PUT
    public void updateCourse(CourseDTO course) throws Exception{
        if(securityContext == null || !securityContext.isUserInRole("ADMIN")){
            unauthorized();
        }
        courseOperations.addOrUpdateCourse(DTOToEntity.getCourseEntity(course));
    }

    @POST
    public void addCourse(CourseDTO course) throws Exception{
        if(securityContext == null || !securityContext.isUserInRole("ADMIN")){
            unauthorized();
        }
        course.setCreated_on(new Timestamp(System.currentTimeMillis()));
        courseOperations.addOrUpdateCourse(DTOToEntity.getCourseEntity(course));
    }

    private void unauthorized(){
        Response.ResponseBuilder builder = Response.status(Response.Status.FORBIDDEN).entity("{\"error\":\"Only admins can access this service\"}");
        throw new WebApplicationException(builder.build());
    }
}
