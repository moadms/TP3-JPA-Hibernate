package ma.enset.tp;

import ma.enset.tp.model.Equipement;
import ma.enset.tp.model.RoleCompte;
import ma.enset.tp.model.Salle;
import ma.enset.tp.model.Utilisateur;
import ma.enset.tp.service.GestionReservationService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GestionReservationServiceTest {
    private static EntityManagerFactory emf;
    private static GestionReservationService service;

    @BeforeAll
    public static void setUp() {
        emf = Persistence.createEntityManagerFactory("tp3_gestion_salles");
        service = new GestionReservationService(emf);
    }

    @AfterAll
    public static void tearDown() {
        if (emf != null && emf.isOpen()) emf.close();
    }

    @Test
    public void testEnregistrerSalleEtEquipements() {
        Salle salle = new Salle("SALLE-B204", "Salle B204", 45, "Etage 2");
        service.enregistrerSalle(salle);
        assertNotNull(salle.getId());

        Equipement eq = new Equipement("EQ-4K-01", "Videoprojecteur 4K Laser", "OPERATIONNEL");
        service.enregistrerEquipement(eq);
        assertNotNull(eq.getId());

        List<Salle> salles = service.listerToutesLesSalles();
        assertFalse(salles.isEmpty());
    }

    @Test
    public void testEnregistrerUtilisateur() {
        Utilisateur user = new Utilisateur("MAT-101", "Moussaoui Moad", "moussaoui9881@gmail.com", RoleCompte.MEMBRE_EQUIPE);
        service.enregistrerUtilisateur(user);
        assertNotNull(user.getId());
    }
}
