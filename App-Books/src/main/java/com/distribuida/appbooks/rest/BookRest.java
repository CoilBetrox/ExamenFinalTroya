package com.distribuida.appbooks.rest;

import com.distribuida.appbooks.client.AuthorRestClient;
import com.distribuida.appbooks.db.Book;
import com.distribuida.appbooks.dto.BookDto;
import com.distribuida.appbooks.repo.BookRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;
import java.util.stream.Collectors;

@Path("/books")
@Produces(jakarta.ws.rs.core.MediaType.APPLICATION_JSON)
@Consumes(jakarta.ws.rs.core.MediaType.APPLICATION_JSON)
@ApplicationScoped
@Transactional
public class BookRest {

    @Inject
    BookRepository repo;

    @Inject
    @RestClient
    AuthorRestClient authorClient;

    @GET
    @Operation( summary = "Busca todos libros",
            description = "no recibe parametros")
    public List<BookDto> findAll() {
        return repo.findAll().stream()
                .map(book->{
                    var author = authorClient.findById(book.getAuthorId());

                    var dto = BookDto.from(book);

                    String aname = String.format("%s %s",
                            author.getLastName(), author.getFirstName());

                    dto.setAuthorName( aname );

                    return dto;
                })
                .collect(Collectors.toList());
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Busca un libro por ID",
            description = "recibe como parametro Integer")
    public Response findById(@PathParam("id")Integer id) {
        var book = repo.findById(id);

        if(book == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(book).build();
    }

    @POST
    @Operation(summary = "Crea un nuevo libro",
            description = "recibe como parametro un objeto libro")
    public Response create(Book obj) {
        obj.setId(null);

        repo.create(obj);

        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "actualia un libro por ID",
            description = "recibe como parametro Integer para ID y objeto Libro")
    public Response update(@PathParam("id")Integer id, Book obj) {

        Book b = repo.findById(id);

        b.setIsbn(obj.getIsbn());
        b.setTitle(obj.getTitle());
        b.setPrice(obj.getPrice());
        b.setAuthorId(obj.getAuthorId());

        return Response.ok()
                .build();
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Elimina un libro por ID",
            description = "recibe como parametro un Integer para id")
    public Response delete(@PathParam("id")Integer id) {
        repo.findById(id);
        return Response.ok()
                .build();
    }



}
