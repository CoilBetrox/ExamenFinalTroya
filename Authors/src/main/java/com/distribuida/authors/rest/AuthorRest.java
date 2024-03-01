package com.distribuida.authors.rest;

import com.distribuida.authors.db.Author;
import com.distribuida.authors.repo.AuthorRepository;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;

import java.util.List;

@Path("/authors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Transactional
@RequestScoped
public class AuthorRest {

    @Inject
    AuthorRepository rep;

    //books GET
    @GET
    @Operation(
            summary = "Busca todos los autores",
            description = "no recibe parametros")
    public List<Author> findAll() {
        return rep.findAll();
    }

    @GET
    @Path("/{id}")
    @Operation(
            summary = "Busca autores por id",
            description = "recibe como parametro Integer"
    )
    public Response findById(@PathParam("id") Integer id) {
        var book = rep.findById(id);
        if (book == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(book).build();
    }

    @POST
    @Operation(
            summary = "Crea un nuevo autor",
            description = "recibe como pametro un objeto libro"
    )
    public Response create(Author p) {
        rep.create(p);

        return Response.status(Response.Status.CREATED.getStatusCode(), "author created").build();
    }

    @PUT
    @Path("/{id}")
    @Operation(
            summary = "Actualiza un autor por ID",
            description = "recibe como parametro Integer para Id y objeto author"
    )
    public Response update(@PathParam("id") Integer id, Author authorObj) {
        Author author = rep.findById(id);
        author.setFirstName(authorObj.getFirstName());
        author.setLastName(authorObj.getLastName());

        //rep.persistAndFlush(author);

        return Response.ok().build();
    }

    //books/{id} DELETE
    @DELETE
    @Path("/{id}")
    @Operation(
            summary = "Elimina author por ID",
            description = "recibe Integer para ID"
    )
    public Response delete(@PathParam("id") Integer id) {
        rep.delete(id);

        return Response.ok( )
                .build();
    }
}
