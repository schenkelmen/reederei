package hsos.de.auftragsmanagement.control;

import hsos.de.auftragsmanagement.entity.Auftrag;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class AuftragsService {

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
        existing.setUrl(updated.getUrl());

        auftragEvent.fire(existing);
        return existing;
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
