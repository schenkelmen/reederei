package hsos.de.shared.events;

public class AuftragAngenommen {
    public Long schiffsId;
    public String schiffsName;
    public Long auftragsId;

    public AuftragAngenommen() {}

    public AuftragAngenommen(Long schiffsId, String schiffsName, Long auftragsId) {
        this.schiffsId = schiffsId;
        this.schiffsName = schiffsName;
        this.auftragsId = auftragsId;

    }
}