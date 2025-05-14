package hsos.de.auftragsmanagement.control;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

@ApplicationScoped
public class AuftragsService {

    @Inject
    EntityManager em;

}
