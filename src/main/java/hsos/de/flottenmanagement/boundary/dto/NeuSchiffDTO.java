package hsos.de.flottenmanagement.boundary.dto;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

public class NeuSchiffDTO {

    @Schema(example = "Evergreen")
    public String name;

    @Schema(description = "Ist das Schiff frei?", example = "true")
    public boolean frei;
}