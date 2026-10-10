package ma.enset.tp.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "t_salles")
public class Salle implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_salle")
    private Long id;

    @Column(name = "code_salle", unique = true, nullable = false, length = 30)
    private String codeSalle;

    @Column(name = "nom_salle", nullable = false, length = 100)
    private String nomSalle;

    @Column(name = "capacite_personnes")
    private Integer capacite;

    @Column(name = "localisation_etage")
    private String etage;

    @OneToMany(mappedBy = "salleConcernee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reservation> planningReservations = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "t_salle_equipement_assoc",
            joinColumns = @JoinColumn(name = "fk_salle"),
            inverseJoinColumns = @JoinColumn(name = "fk_equipement")
    )
    private Set<Equipement> listeEquipements = new HashSet<>();

    public Salle() {
    }

    public Salle(String codeSalle, String nomSalle, Integer capacite, String etage) {
        this.codeSalle = codeSalle;
        this.nomSalle = nomSalle;
        this.capacite = capacite;
        this.etage = etage;
    }

    public void ajouterEquipement(Equipement equipement) {
        this.listeEquipements.add(equipement);
        equipement.getSallesAffectees().add(this);
    }

    public void retirerEquipement(Equipement equipement) {
        this.listeEquipements.remove(equipement);
        equipement.getSallesAffectees().remove(this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeSalle() {
        return codeSalle;
    }

    public void setCodeSalle(String codeSalle) {
        this.codeSalle = codeSalle;
    }

    public String getNomSalle() {
        return nomSalle;
    }

    public void setNomSalle(String nomSalle) {
        this.nomSalle = nomSalle;
    }

    public Integer getCapacite() {
        return capacite;
    }

    public void setCapacite(Integer capacite) {
        this.capacite = capacite;
    }

    public String getEtage() {
        return etage;
    }

    public void setEtage(String etage) {
        this.etage = etage;
    }

    public List<Reservation> getPlanningReservations() {
        return planningReservations;
    }

    public void setPlanningReservations(List<Reservation> planningReservations) {
        this.planningReservations = planningReservations;
    }

    public Set<Equipement> getListeEquipements() {
        return listeEquipements;
    }

    public void setListeEquipements(Set<Equipement> listeEquipements) {
        this.listeEquipements = listeEquipements;
    }

    @Override
    public String toString() {
        return "Salle{" +
                "id=" + id +
                ", codeSalle='" + codeSalle + '\'' +
                ", nomSalle='" + nomSalle + '\'' +
                ", capacite=" + capacite +
                ", etage='" + etage + '\'' +
                '}';
    }
}
