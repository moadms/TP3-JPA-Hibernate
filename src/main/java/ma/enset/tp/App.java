package ma.enset.tp;

import ma.enset.tp.model.*;
import org.h2.tools.Server;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class App {

    public static void main(String[] args) {
        System.out.println("==========================================================");
        System.out.println(">>> DEMARRAGE DU PROJET TP3 - JPA & HIBERNATE AVANCE <<<");
        System.out.println("==========================================================");

        Server h2ConsoleServer = null;
        try {
            // Lancement du serveur Web H2 pour consultation visuelle
            h2ConsoleServer = Server.createWebServer("-web", "-webAllowOthers", "-webPort", "8083").start();
            System.out.println("✓ Console H2 accessible sur : http://localhost:8083");
            System.out.println("  JDBC URL : jdbc:h2:mem:tp3db");
            System.out.println("  User     : sa (mot de passe vide)");
        } catch (SQLException e) {
            System.err.println("Avertissement H2 Console: " + e.getMessage());
        }

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("tp3_gestion_salles");
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            System.out.println("\n--- [1] CREATION DES UTILISATEURS ---");
            Utilisateur user1 = new Utilisateur("EMP-001", "Moad Moussaoui", "moussaoui9881@gmail.com", RoleCompte.ADMINISTRATEUR);
            Utilisateur user2 = new Utilisateur("EMP-002", "Sarah Alami", "s.alami@entreprise.ma", RoleCompte.MEMBRE_EQUIPE);
            em.persist(user1);
            em.persist(user2);
            System.out.println("Utilisateurs persistés : " + user1.getNomComplet() + ", " + user2.getNomComplet());

            System.out.println("\n--- [2] CREATION DES EQUIPEMENTS ---");
            Equipement eqVideo = new Equipement("EQ-VID-4K", "Vidéoprojecteur Laser 4K", "EXCELLENT");
            Equipement eqVisio = new Equipement("EQ-VIS-POLY", "Système Visioconférence Polycom", "OPERATIONNEL");
            Equipement eqTableau = new Equipement("EQ-TAB-INT", "Écran Tactile Interactif 75 pouces", "OPERATIONNEL");
            em.persist(eqVideo);
            em.persist(eqVisio);
            em.persist(eqTableau);
            System.out.println("Équipements enregistrés avec succès.");

            System.out.println("\n--- [3] CREATION DES SALLES ET ASSOCIATION DES EQUIPEMENTS ---");
            Salle salleAtlas = new Salle("SALLE-A1", "Amphithéâtre Atlas", 120, "Rez-de-chaussée");
            salleAtlas.ajouterEquipement(eqVideo);
            salleAtlas.ajouterEquipement(eqVisio);

            Salle salleToubkal = new Salle("SALLE-B2", "Salle de Réunion Toubkal", 18, "2ème Étage");
            salleToubkal.ajouterEquipement(eqTableau);
            salleToubkal.ajouterEquipement(eqVisio);

            em.persist(salleAtlas);
            em.persist(salleToubkal);
            System.out.println("Salles créées et associées aux équipements.");

            System.out.println("\n--- [4] CREATION D'UNE RESERVATION (RELATION MULTI-ENTITES) ---");
            Reservation resa1 = new Reservation(
                    LocalDateTime.now().plusDays(1).withHour(9).withMinute(0),
                    LocalDateTime.now().plusDays(1).withHour(11).withMinute(30),
                    "Présentation Sprint Architecture & JPA",
                    StatutDemande.VALIDEE,
                    user1,
                    salleToubkal
            );
            em.persist(resa1);

            em.getTransaction().commit();
            System.out.println("✓ Transaction validée avec succès !");

            // Requêtes de vérification
            System.out.println("\n--- [5] CONSULTATION DES DONNEES PERSISTEES VIA JPQL ---");
            List<Reservation> reservations = em.createQuery(
                    "SELECT r FROM Reservation r JOIN FETCH r.auteurDemande JOIN FETCH r.salleConcernee",
                    Reservation.class
            ).getResultList();

            for (Reservation r : reservations) {
                System.out.println("Réservation ID: " + r.getId() +
                        " | Motif: " + r.getObjetReunion() +
                        " | Demandeur: " + r.getAuteurDemande().getNomComplet() +
                        " | Salle: " + r.getSalleConcernee().getNomSalle() +
                        " | Statut: " + r.getStatut());
            }

            System.out.println("\n==========================================================");
            System.out.println(">>> TEST TP3 TERMINE AVEC SUCCES <<<");
            System.out.println("==========================================================");

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
            if (h2ConsoleServer != null) {
                h2ConsoleServer.stop();
            }
        }
    }
}
