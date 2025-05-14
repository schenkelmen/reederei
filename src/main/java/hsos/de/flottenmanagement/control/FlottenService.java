package hsos.de.flottenmanagement.control;

import hsos.de.flottenmanagement.entity.Schiff;
import hsos.de.shared.events.AuftragAngenommen;
import hsos.de.shared.events.AuftragEingegangen;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class FlottenService {

    @Inject
    EntityManager em;

    @Inject
    Event<AuftragAngenommen> auftragAngenommenEvent;

    @Transactional
    public Schiff schiffAnlegen(Schiff schiff) {
        em.persist(schiff);
        return schiff;
    }

    public Schiff findeSchiff(Long id) {
        return em.find(Schiff.class, id);
    }

    @Transactional
    public void auftragRein(@Observes AuftragEingegangen auftrag) {
        List<Schiff> freieSchiffe = em.createQuery("SELECT s FROM Schiff s WHERE s.frei = true", Schiff.class)
                .getResultList();

        freieSchiffe.stream().findFirst().ifPresent(s -> {
            s.frei = false;
            em.merge(s);

            AuftragAngenommen event = new AuftragAngenommen(s.id, s.name);
            auftragAngenommenEvent.fire(event);
        });
    }
}
