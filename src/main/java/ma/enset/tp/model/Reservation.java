package ma.enset.tp.model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "t_reservations")
public class Reservation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reservation")
    private Long id;

    @Column(name = "debut_creneau", nullable = false)
    private LocalDateTime debutReservation;

    @Column(name = "fin_creneau", nullable = false)
    private LocalDateTime finReservation;

    @Column(name = "objet_session", length = 200)
    private String objetReunion;

    @Enumerated(EnumType.STRING)
    @Column(name = "etat_demande", length = 30)
    private StatutDemande statut;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "fk_demandeur", nullable = false)
    private Utilisateur auteurDemande;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "fk_salle_reservee", nullable = false)
    private Salle salleConcernee;

    public Reservation() {
    }

    public Reservation(LocalDateTime debutReservation, LocalDateTime finReservation, String objetReunion,
                       StatutDemande statut, Utilisateur auteurDemande, Salle salleConcernee) {
        this.debutReservation = debutReservation;
        this.finReservation = finReservation;
        this.objetReunion = objetReunion;
        this.statut = statut;
        this.auteurDemande = auteurDemande;
        this.salleConcernee = salleConcernee;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDebutReservation() {
        return debutReservation;
    }

    public void setDebutReservation(LocalDateTime debutReservation) {
        this.debutReservation = debutReservation;
    }

    public LocalDateTime getFinReservation() {
        return finReservation;
    }

    public void setFinReservation(LocalDateTime finReservation) {
        this.finReservation = finReservation;
    }

    public String getObjetReunion() {
        return objetReunion;
    }

    public void setObjetReunion(String objetReunion) {
        this.objetReunion = objetReunion;
    }

    public StatutDemande getStatut() {
        return statut;
    }

    public void setStatut(StatutDemande statut) {
        this.statut = statut;
    }

    public Utilisateur getAuteurDemande() {
        return auteurDemande;
    }

    public void setAuteurDemande(Utilisateur auteurDemande) {
        this.auteurDemande = auteurDemande;
    }

    public Salle getSalleConcernee() {
        return salleConcernee;
    }

    public void setSalleConcernee(Salle salleConcernee) {
        this.salleConcernee = salleConcernee;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", debutReservation=" + debutReservation +
                ", finReservation=" + finReservation +
                ", objetReunion='" + objetReunion + '\'' +
                ", statut=" + statut +
                '}';
    }
}
