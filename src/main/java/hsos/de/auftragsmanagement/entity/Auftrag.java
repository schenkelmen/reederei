package hsos.de.auftragsmanagement.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class Auftrag{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String beschreibung;
    public LocalDate eingangsdatum;
    public String url;

    public Long getId() {
        return id;
    }

    public String getAuftragsbeschreibung() {
        return beschreibung;
    }

    public void setAuftragsbeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public LocalDate getDatum() {
        return eingangsdatum;
    }

    public void setDatum(LocalDate datum) {
        this.eingangsdatum = datum;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
