/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.methotels.rest;


import com.mycompany.methotels.entities.Soba;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;



/**
 *
 * @author Zeljko
 */
@Path("/sobeservis")
public interface SobeWebServis {
    
    @GET
    @Produces({"application/json"})
    public List<Soba> getAll();
    
    @GET
    @Path("{id}")
    @Produces({"application/json"})
    public Soba getSoba(@PathParam("id") Integer id);
    
    @POST
    @CommitAfter
    @Produces({"application/json"})
    @Consumes({"application/json"})
    public Response post(Soba soba);
}
