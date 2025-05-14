package hsos.de.flottenmanagement.control;

import hsos.de.auftragsmanagement.entity.Auftrag;
import hsos.de.shared.events.AuftragAngenommen;
import hsos.de.flottenmanagement.entity.Schiff;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.jboss.logging.Logger;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;


import java.util.List;

@ApplicationScoped
public class FlottenService {
    private static final Logger LOG = Logger.getLogger(FlottenService.class);

    @Inject
    EntityManager em;

    @Inject
    Event<AuftragAngenommen> auftragAngenommenEvent;

    private final Queue<Long> wartendeAuftraege = new ConcurrentLinkedQueue<>();

    @Transactional
    public Schiff schiffAnlegen(Schiff schiff) {
        LOG.info("🚢 Neues Schiff wurde angelegt: " + schiff.name);
        em.persist(schiff);
        if (!wartendeAuftraege.isEmpty()) {
            Long auftragId = wartendeAuftraege.poll();
            if (auftragId != null) {
                schiff.frei = false;
                em.merge(schiff);
                AuftragAngenommen event = new AuftragAngenommen(schiff.id, schiff.name, auftragId);
                LOG.infof("🚀 Neues Schiff '%s' übernimmt wartenden Auftrag %d", schiff.name, auftragId);
                auftragAngenommenEvent.fire(event);
            }
        }

        return schiff;
    }

    public Schiff findeSchiff(Long id) {
        return em.find(Schiff.class, id);
    }

    @Transactional
    public void auftragRein(@Observes Auftrag auftrag) {
        LOG.info("📩 Auftrag eingegangen: " + auftrag);
        List<Schiff> freieSchiffe = em.createQuery("SELECT s FROM Schiff s WHERE s.frei = true", Schiff.class)
                .getResultList();

        if (freieSchiffe.isEmpty()) {
            LOG.info("⚠️ Kein freies Schiff. Auftrag %d wird in Warteschlange gelegt.".formatted(auftrag.id));
            wartendeAuftraege.add(auftrag.id);
            return;
        }

        Schiff s = freieSchiffe.get(0);
        s.frei = false;
        em.merge(s);
        AuftragAngenommen event = new AuftragAngenommen(s.id, s.name, auftrag.id);
        LOG.infof("✅ Schiff '%s' (ID: %d) übernimmt Auftrag.", s.name, s.id);
        auftragAngenommenEvent.fire(event);
    }

}
