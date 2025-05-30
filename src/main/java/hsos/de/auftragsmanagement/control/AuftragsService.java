package hsos.de.auftragsmanagement.control;

import hsos.de.auftragsmanagement.entity.Auftrag;
import hsos.de.flottenmanagement.control.FlottenService;
import hsos.de.flottenmanagement.entity.Schiff;
import hsos.de.shared.events.AuftragAngenommen;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.jboss.logging.Logger;

@ApplicationScoped
public class AuftragsService {
    private static final Logger LOG = Logger.getLogger(AuftragsService.class);

    @Inject
    EntityManager em;

    @Inject
    Event<Auftrag> auftragEvent;

    @Transactional
    public Auftrag auftragErstellen(Auftrag auftrag){
        em.persist(auftrag);
        auftragEvent.fire(auftrag);
        return auftrag;
    }

    public Auftrag getAuftrag(Long id){
        return em.find(Auftrag.class, id);
    }

    @Transactional
    public Auftrag auftragAktualisieren(Long id, Auftrag updated) {
        Auftrag existing = em.find(Auftrag.class, id);
        if (existing == null) {
            throw new IllegalArgumentException("Auftrag mit ID " + id + " nicht gefunden.");
        }

        existing.setBeschreibung(updated.getBeschreibung());
        existing.setEingangsdatum(updated.getEingangsdatum());

        auftragEvent.fire(existing);
        return existing;
    }

    @Transactional
    public void auftragAngenommen(@Observes AuftragAngenommen event) {
        LOG.info("📩 Auftrag angenommen: für Auftrag:" + event.auftragsId + "; für Schiff: " + event.schiffsId);
        Auftrag auftrag = em.find(Auftrag.class, event.auftragsId);
        if (auftrag == null) {
            return;
        }

        auftrag.setUrl("http://localhost:8081/schiffe/" + event.schiffsId);
    }

    @Transactional
    public void auftragLoeschen(Long id) {
        Auftrag auftrag = em.find(Auftrag.class, id);
        if (auftrag != null) {
            em.remove(auftrag);
            auftragEvent.fire(auftrag);
        }
    }
}
