package ma.enset.tp.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "t_equipements")
public class Equipement implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_equipement")
    private Long id;

    @Column(name = "code_materiel", nullable = false, unique = true, length = 50)
    private String codeMateriel;

    @Column(name = "designation_materiel", nullable = false, length = 120)
    private String designation;

    @Column(name = "etat_marche", length = 50)
    private String etatFonctionnel;

    @ManyToMany(mappedBy = "listeEquipements")
    private Set<Salle> sallesAffectees = new HashSet<>();

    public Equipement() {
    }

    public Equipement(String codeMateriel, String designation, String etatFonctionnel) {
        this.codeMateriel = codeMateriel;
        this.designation = designation;
        this.etatFonctionnel = etatFonctionnel;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeMateriel() {
        return codeMateriel;
    }

    public void setCodeMateriel(String codeMateriel) {
        this.codeMateriel = codeMateriel;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getEtatFonctionnel() {
        return etatFonctionnel;
    }

    public void setEtatFonctionnel(String etatFonctionnel) {
        this.etatFonctionnel = etatFonctionnel;
    }

    public Set<Salle> getSallesAffectees() {
        return sallesAffectees;
    }

    public void setSallesAffectees(Set<Salle> sallesAffectees) {
        this.sallesAffectees = sallesAffectees;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Equipement)) return false;
        Equipement that = (Equipement) o;
        return Objects.equals(codeMateriel, that.codeMateriel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codeMateriel);
    }

    @Override
    public String toString() {
        return "Equipement{" +
                "id=" + id +
                ", codeMateriel='" + codeMateriel + '\'' +
                ", designation='" + designation + '\'' +
                ", etatFonctionnel='" + etatFonctionnel + '\'' +
                '}';
    }
}
