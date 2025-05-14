package hsos.de.shared.events;

public class AuftragAngenommen {
    public Long schiffsId;
    public String schiffsName;

    public AuftragAngenommen() {}

    public AuftragAngenommen(Long schiffsId, String schiffsName) {
        this.schiffsId = schiffsId;
        this.schiffsName = schiffsName;
    }
}