package hsos.de.auftragsmanagement.boundary;

import hsos.de.auftragsmanagement.boundary.dto.AuftragDTO;
import hsos.de.auftragsmanagement.control.AuftragsService;
import hsos.de.auftragsmanagement.entity.Auftrag;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/auftraege")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuftragResource {

    @Inject
    AuftragsService service;

    @POST
    public Response erstelleAuftrag(AuftragDTO dto) {
        Auftrag auftrag = new Auftrag();

        auftrag.beschreibung = dto.beschreibung;
        auftrag.eingangsdatum = dto.eingangsdatum;
        auftrag.url = dto.url;

        Auftrag created = service.auftragErstellen(auftrag);
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    @GET
    @Path("/{id}")
    public Response holAuftragNachId(@PathParam("id") Long id) {
        Auftrag auftrag = service.getAuftrag(id);
        if (auftrag == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(auftrag).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateAuftrag(@PathParam("id") Long id, Auftrag updated) {
        try {
            Auftrag auftrag = service.auftragAktualisieren(id, updated);
            return Response.ok(auftrag).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response loescheAuftrag(@PathParam("id") Long id) {
        service.auftragLoeschen(id);
        return Response.noContent().build();
    }
}
