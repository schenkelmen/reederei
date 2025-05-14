package hsos.de.flottenmanagement.events;

public class AuftragEingegangen {
    public String beschreibung;
    public String eingangsdatum;

    public AuftragEingegangen() {}

    public AuftragEingegangen(String beschreibung, String eingangsdatum) {
        this.beschreibung = beschreibung;
        this.eingangsdatum = eingangsdatum;
    }
}
