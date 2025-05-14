package hsos.de.flottenmanagement.entity;
import jakarta.persistence.*;

@Entity
public class Schiff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String name;

    public boolean frei = true;
}
