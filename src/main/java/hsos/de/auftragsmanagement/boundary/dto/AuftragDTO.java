package hsos.de.auftragsmanagement.boundary.dto;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.time.LocalDate;

public class AuftragDTO {
    @Schema(example = "Mast gebrochen")
    public String beschreibung;

    @Schema(description = "Eingangsdatum des Auftrags", example = "2025-03-12")
    public LocalDate eingangsdatum;

    @Schema(example = "/schiffe/titanic")
    public String url;
}
