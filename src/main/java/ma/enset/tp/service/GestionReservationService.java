package ma.enset.tp.service;

import ma.enset.tp.model.Equipement;
import ma.enset.tp.model.Reservation;
import ma.enset.tp.model.Salle;
import ma.enset.tp.model.Utilisateur;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.List;

public class GestionReservationService {
    private final EntityManagerFactory emf;

    public GestionReservationService(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public void enregistrerSalle(Salle salle) {
        executeInsideTransaction(em -> em.persist(salle));
    }

    public Salle trouverSalle(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Salle.class, id);
        } finally {
            em.close();
        }
    }

    public List<Salle> listerToutesLesSalles() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT s FROM Salle s", Salle.class).getResultList();
        } finally {
            em.close();
        }
    }

    public void enregistrerUtilisateur(Utilisateur user) {
        executeInsideTransaction(em -> em.persist(user));
    }

    public void enregistrerEquipement(Equipement equipement) {
        executeInsideTransaction(em -> em.persist(equipement));
    }

    public void enregistrerReservation(Reservation res) {
        executeInsideTransaction(em -> em.persist(res));
    }

    public List<Equipement> trouverEquipementsParSalle(String nomSalle) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT e FROM Equipement e JOIN e.salles s WHERE s.nom = :nomSalle", Equipement.class)
                    .setParameter("nomSalle", nomSalle)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    private void executeInsideTransaction(java.util.function.Consumer<EntityManager> action) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            action.accept(em);
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }
}
