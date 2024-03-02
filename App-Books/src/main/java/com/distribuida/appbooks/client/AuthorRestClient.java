package com.distribuida.appbooks.client;

import com.distribuida.appbooks.dto.AuthorDto;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;

@Path("/authors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RegisterRestClient(configKey = "AuthorRestClient")
public interface AuthorRestClient {
    @GET
    public List<AuthorDto> findAll();

    @GET
    @Path("/{id}")
    public AuthorDto findById(@PathParam("id") Integer id);

}
