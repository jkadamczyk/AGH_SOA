package com.jadamczyk.books.RestServices;

import com.jadamczyk.books.DAO.AuthorDAO;
import com.jadamczyk.books.Entities.Author;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/author")
public class AuthorService {
    private AuthorDAO authorDAO = new AuthorDAO();

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getBooks() {
        List<Author> authors = authorDAO.findAll();

        return Response.ok().entity(authors).build();
    }

    @DELETE
    @Produces({MediaType.APPLICATION_JSON})
    public Response deleteBook(@QueryParam("id") Integer id) {
        try {
            Author toDelete = authorDAO.findById(id);
            authorDAO.delete(toDelete);
            return Response
                    .ok()
                    .entity(toDelete)
                    .build();
        } catch (Exception e) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        }
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response insertBook(Author payload) {
        try {
            Author newAuthor = new Author();

            newAuthor.setName(payload.getName());
            newAuthor.setSurname(payload.getSurname());
            authorDAO.insert(newAuthor);

            return Response.ok().entity(newAuthor).build();
        } catch (Exception e) {
            return Response.serverError().build();
        }
    }

    @PUT
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response updateBook(Author payload) {
        try {
            if (payload.getId() == null) return Response.status(Response.Status.BAD_REQUEST).build();

            Author authorToUpdate = authorDAO.findById(payload.getId());

            if (payload.getName() != null) authorToUpdate.setName(payload.getName());
            if (payload.getSurname() != null) authorToUpdate.setSurname(payload.getSurname());

            authorDAO.update(authorToUpdate);

            return Response.ok().entity(authorToUpdate).build();
        } catch (Exception e) {
            return Response.serverError().build();
        }
    }
}