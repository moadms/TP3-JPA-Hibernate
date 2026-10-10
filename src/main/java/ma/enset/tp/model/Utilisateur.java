package ma.enset.tp.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "t_utilisateurs")
public class Utilisateur implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_utilisateur")
    private Long id;

    @Column(name = "identifiant_unique", unique = true, nullable = false, length = 60)
    private String matricule;

    @Column(name = "nom_prenom", nullable = false, length = 120)
    private String nomComplet;

    @Column(name = "courriel_pro", unique = true, nullable = false, length = 150)
    private String emailPro;

    @Enumerated(EnumType.STRING)
    @Column(name = "role_utilisateur", length = 35)
    private RoleCompte role;

    @OneToMany(mappedBy = "auteurDemande", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reservation> historiqueReservations = new ArrayList<>();

    public Utilisateur() {
    }

    public Utilisateur(String matricule, String nomComplet, String emailPro, RoleCompte role) {
        this.matricule = matricule;
        this.nomComplet = nomComplet;
        this.emailPro = emailPro;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getNomComplet() {
        return nomComplet;
    }

    public void setNomComplet(String nomComplet) {
        this.nomComplet = nomComplet;
    }

    public String getEmailPro() {
        return emailPro;
    }

    public void setEmailPro(String emailPro) {
        this.emailPro = emailPro;
    }

    public RoleCompte getRole() {
        return role;
    }

    public void setRole(RoleCompte role) {
        this.role = role;
    }

    public List<Reservation> getHistoriqueReservations() {
        return historiqueReservations;
    }

    public void setHistoriqueReservations(List<Reservation> historiqueReservations) {
        this.historiqueReservations = historiqueReservations;
    }

    @Override
    public String toString() {
        return "Utilisateur{" +
                "id=" + id +
                ", matricule='" + matricule + '\'' +
                ", nomComplet='" + nomComplet + '\'' +
                ", emailPro='" + emailPro + '\'' +
                ", role=" + role +
                '}';
    }
}
