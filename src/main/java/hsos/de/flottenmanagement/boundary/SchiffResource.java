package hsos.de.flottenmanagement.boundary;

import hsos.de.flottenmanagement.control.FlottenService;
import hsos.de.flottenmanagement.entity.Schiff;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/schiffe")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SchiffResource {

    @Inject
    FlottenService service;

    @POST
    public Response neuesSchiff(Schiff schiff) {
        return Response.ok(service.schiffAnlegen(schiff)).build();
    }

    @GET
    @Path("/{id}")
    public Response findeSchiff(@PathParam("id") Long id) {
        Schiff s = service.findeSchiff(id);
        if (s == null) return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok(s).build();
    }
}
