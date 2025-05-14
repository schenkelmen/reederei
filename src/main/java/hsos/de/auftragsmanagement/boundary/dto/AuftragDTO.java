package hsos.de.auftragsmanagement.boundary.dto;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.Date;

public class AuftragDTO {
    @Schema(example = "Mast gebrochen")
    public String auftragsbeschreibeibung;

    @Schema(description = "Eingangsdatum des Auftrags", example = "12.03.2025")
    public Date datum;

    @Schema(example = "/schiffe/titanic")
    public String url;
}
