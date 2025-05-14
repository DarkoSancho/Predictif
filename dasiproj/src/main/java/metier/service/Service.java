
package metier.service;

import com.google.maps.model.LatLng;
import dao.ClientDao;
import dao.ConsultationDao;
import dao.EmployeDao;
import dao.JpaUtil;
import dao.MediumDao;

import java.io.IOException;

import java.util.HashMap;
import java.util.LinkedHashMap;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.persistence.PersistenceException;

import metier.modele.Client;
import metier.modele.Employe;
import metier.modele.Medium;
import metier.modele.Prediction;
import metier.modele.Consultation;
import util.AstroNetApi;
import util.Message;

import util.GeoNetApi;

/**
 *
 * @author ncardenas
 */
public class Service {

    private final ClientDao clientDao;
    private final EmployeDao employeDao;
    private final MediumDao mediumDao;
    private final ConsultationDao consultationDao;

    public Service() {
        this.clientDao = new ClientDao();
        this.employeDao = new EmployeDao();
        this.mediumDao = new MediumDao();
        this.consultationDao = new ConsultationDao();
    }

    /**
     * Authenticates a client using email and password.
     * 
     * @param mail The email address of the client
     * @param mdp  The password of the client
     * @return The authenticated Client object if successful, null otherwise
     */
    public Client authentifierClient(String mail, String mdp) {
        Client client = null;
        try {
            JpaUtil.creerContextePersistance();
            client = clientDao.findByEmailAndPassword(mail, mdp);
        } catch (PersistenceException ex) {
            System.out.println(ex);
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return client;
    }

    /**
     * Authenticates an employee using email and password.
     * 
     * @param mail The email address of the employee
     * @param mdp  The password of the employee
     * @return The authenticated Employe object if successful, null otherwise
     */
    public Employe authentifierEmploye(String mail, String mdp) {
        Employe employe = null;
        try {
            JpaUtil.creerContextePersistance();
            employe = employeDao.findByEmailAndPassword(mail, mdp);
        } catch (PersistenceException ex) {
            System.out.println(ex);
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return employe;
    }

    /**
     * Registers a new client in the system, initializes their astral profile,
     * and sends a confirmation email.
     * 
     * @param client The Client object to be registered
     * @return true if registration was successful, false otherwise
     */
    public boolean inscrireClient(Client client) {
        boolean success = false;
        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();

            AstroNetApi astroApi = new AstroNetApi();
            List<String> profil = astroApi.getProfil(client.getPrenom(), client.getDateNaissance());
            client.initProfilAstral(profil);

            clientDao.create(client);
            JpaUtil.validerTransaction();

            success = true;
            Message.envoyerMail("admin@mail.com", client.getMail(), "Congrats", "compte bon");
        } catch (Exception ex) {
            JpaUtil.annulerTransaction();
            Message.envoyerMail("admin@mail.com", client.getMail(), "Erreur", "compte pas bon");
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return success;
    }

    /**
     * Registers a new employee in the system and sends a confirmation email.
     * 
     * @param employe The Employe object to be registered
     * @return true if registration was successful, false otherwise
     */
    public boolean inscrireEmploye(Employe employe) {
        boolean success = false;
        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();

            employeDao.create(employe);
            JpaUtil.validerTransaction();

            success = true;
            Message.envoyerMail("admin@mail.com", employe.getMail(), "Congrats", "compte bon");
        } catch (Exception ex) {
            JpaUtil.annulerTransaction();
            Message.envoyerMail("admin@mail.com", employe.getMail(), "Erreur de creation", "compte pas bon");
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return success;
    }

    /**
     * Registers a new medium in the system.
     * 
     * @param medium The Medium object to be registered
     * @return true if registration was successful, false otherwise
     */
    public boolean inscrireMedium(Medium medium) {
        boolean success = false;
        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();

            mediumDao.create(medium);
            JpaUtil.validerTransaction();

            success = true;
        } catch (Exception ex) {
            System.out.println("Error creating medium: " + ex.getMessage());
            JpaUtil.annulerTransaction();
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return success;
    }

    /**
     * Creates a consultation for a client with a specific medium.
     * Finds an appropriate employee based on the medium's gender preference
     * and assigns them to the consultation.
     * 
     * @param client The client requesting the consultation
     * @param medium The medium requested for the consultation
     * @return true if consultation creation was successful, false otherwise
     */
    public boolean creerConsultation(Client client, Medium medium) {
        boolean success = false;
        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();

            Consultation consultation;
            Employe selectedEmp = employeDao.findSuitable(medium.getGenre());

            if (selectedEmp != null) {
                consultation = new Consultation(client, medium, selectedEmp);

                selectedEmp.setDisponible(false);
                selectedEmp.incrementerNbConsultations();
                selectedEmp.appendConsultation(consultation);
                client.appendConsultation(consultation);

                employeDao.update(selectedEmp);
                clientDao.update(client);
            } else {
                System.out.println("no employee found for consultation");
                consultation = new Consultation(client, medium);
                consultation.setEtat(Consultation.Etat.annulee);
            }

            consultationDao.create(consultation);
            JpaUtil.validerTransaction();
            success = true;
        } catch (Exception ex) {
            ex.printStackTrace();
            JpaUtil.annulerTransaction();
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return success;
    }

    /**
     * Finds an employee and fetches their consultations
     *
     * @param id Employee ID
     * @return Employe object with consultations
     */
    public Employe findEmployeWithConsultations(Long id) {
        Employe employe = null;
        try {
            JpaUtil.creerContextePersistance();
            employe = employeDao.findByIdWithConsultations(id);
        } catch (PersistenceException ex) {
            System.out.println(ex);
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return employe;
    }

    /**
     * Accepts a consultation request, updates its status to 'enCours',
     * and notifies the client via email.
     * 
     * @param consultation The consultation to be accepted
     * @return true if the operation was successful, false otherwise
     */
    public boolean accepterConsultation(Consultation consultation) {
        boolean success = false;
        consultation.setEtat(Consultation.Etat.enCours);
        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();

            consultationDao.update(consultation);
            JpaUtil.validerTransaction();

            success = true;
            Message.envoyerMail("admin@mail.com", consultation.getClient().getMail(),
                    "RDV!!!", "telephoner a " + consultation.getEmploye().getNoTelPro());
        } catch (Exception ex) {
            System.out.println("Could not change consultation status");
            ex.printStackTrace();
            JpaUtil.annulerTransaction();
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return success;
    }

    /*
     * adds the employee's comment, * @param consultation The consultation to be
     * terminated
     * 
     * @param commentaire The employee's comment about the consultation
     * 
     * @return true if the operation was successful, false otherwise
     */
    public boolean terminerConsultation(Consultation consultation, String commentaire) {
        boolean success = false;
        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();

            consultation.setEtat(Consultation.Etat.effectuee);
            consultation.setCommentaire_employe(commentaire);
            Employe emp = consultation.getEmploye();
            emp.setDisponible(true);

            employeDao.update(emp);
            consultationDao.update(consultation);
            JpaUtil.validerTransaction();

            success = true;
        } catch (Exception ex) {
            System.out.println("Could not change consultation status");
            ex.printStackTrace();
            JpaUtil.annulerTransaction();
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return success;
    }

    /**
     * Generates a prediction for a client based on their astral profile and
     * specified levels for love, health and work.
     * 
     * @param client  The client for whom to generate the prediction
     * @param amour   Love prediction level (1-4)
     * @param sante   Health prediction level (1-4)
     * @param travail Work prediction level (1-4)
     * @return A Prediction object containing the generated predictions, or null if
     *         error occurs
     */
    public Prediction genererPrediction(Client client, int amour, int sante, int travail) {
        Prediction result = null;
        try {
            AstroNetApi astroApi = new AstroNetApi();
            String couleur = client.getCouleur();
            String animal = client.getAnimal();
            List<String> predictions = astroApi.getPredictions(couleur, animal, amour, sante, travail);
            result = Prediction.fromApiResults(predictions);
        } catch (IOException ex) {
            System.err.println("Error getting predictions: " + ex.getMessage());
            ex.printStackTrace();
        }
        return result;
    }

    /**
     * Retrieves a list of all clients in the system.
     * 
     * @return List of all Client objects, or null if an error occurs
     */
    public List<Client> listerClients() {
        List<Client> res = null;
        try {
            JpaUtil.creerContextePersistance();
            res = clientDao.findAll();
        } catch (PersistenceException ex) {
            System.out.println(ex);
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return res;
    }

    /**
     * Retrieves a list of all employees in the system.
     * 
     * @return List of all Employe objects, or null if an error occurs
     */
    public List<Employe> listerEmployes() {
        List<Employe> res = null;
        try {
            JpaUtil.creerContextePersistance();
            res = employeDao.findAll();
        } catch (PersistenceException ex) {
            System.out.println(ex);
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return res;
    }

    /**
     * Retrieves a list of all mediums in the system.
     * 
     * @return List of all Medium objects, or null if an error occurs
     */
    public List<Medium> listerMediums() {
        List<Medium> res = null;
        try {
            JpaUtil.creerContextePersistance();
            res = mediumDao.findAll();
        } catch (PersistenceException ex) {
            System.out.println(ex);
        } finally {
            JpaUtil.fermerContextePersistance();
        }

        return res;
    }

    /**
     * Retrieves a list of all consultations in the system.
     * 
     * @return List of all Consultation objects, or null if an error occurs
     */
    public List<Consultation> listerConsultations() {
        List<Consultation> res = null;
        try {
            JpaUtil.creerContextePersistance();
            res = consultationDao.findAll();
        } catch (PersistenceException ex) {
            System.out.println(ex);
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return res;
    }

    /**
     * Retrieves the consultation history for a specific client.
     * 
     * @param idClient The ID of the client
     * @return List of Consultation objects for the specified client, or null if an
     *         error occurs
     */
    public List<Consultation> historiqueConsultations(Long idClient) {
        List<Consultation> res = null;
        try {
            JpaUtil.creerContextePersistance();
            res = consultationDao.findByClientId(idClient);
        } catch (PersistenceException ex) {
            System.out.println(ex);
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return res;
    }

    /**
     * Finds an employee by their ID.
     * 
     * @param id The ID of the employee to find
     * @return The found Employe object, or null if not found or an error occurs
     */
    public Employe findEmploye(Long id) {
        Employe res = null;
        try {
            JpaUtil.creerContextePersistance();
            res = employeDao.findById(id);
        } catch (PersistenceException ex) {
            System.out.println(ex);
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return res;
    }

    /**
     * Finds a medium by their ID.
     * 
     * @param id The ID of the medium to find
     * @return The found Medium object, or null if not found or an error occurs
     */
    public Medium findMedium(Long id) {
        Medium res = null;
        try {
            JpaUtil.creerContextePersistance();
            res = mediumDao.findById(id);
        } catch (PersistenceException ex) {
            System.out.println(ex);
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return res;
    }

    /**
     * Retrieves the geographic coordinates (latitude and longitude) for a client's postal address.
     * This method attempts to geocode the client's address using the GeoNetApi service.
     * 
     * @param client The client whose geographic location is to be determined
     * @return A LatLng object containing the latitude and longitude coordinates of the client's address,
     *         or null if the geocoding fails or an exception occurs
     */
    public LatLng getClientLocalisation(Client client) {
        try {
            return GeoNetApi.getLatLng(client.getAdressePostale());
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * Retrieves the number of consultations conducted for each medium.
     * 
     * @return A Map with Medium IDs as keys and consultation counts as values, or
     *         null if an error occurs
     */
    public Map<Long, Integer> getConsultationsPerMedium() {
        HashMap<Long, Integer> resultMap = new HashMap<>();
        try {
            JpaUtil.creerContextePersistance();
            for (Medium m : mediumDao.findAll()) {
                resultMap.put(m.getId(), mediumDao.findAllConsultations(m).size());
            }
        } catch (PersistenceException ex) {
            ex.printStackTrace();
            resultMap = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return resultMap;
    }

    /**
     * Returns the top 5 most requested mediums based on their consultation counts.
     * Results are sorted in descending order by number of consultations.
     * 
     * @return A Map with the top 5 Medium IDs as keys and consultation counts as
     *         values, or null if an error occurs
     */
    public Map<Long, Integer> getTop5SelectedMedium() {
        Map<Long, Integer> top5Mediums;
        try {
            Map<Long, Integer> consultationsPerMedium = getConsultationsPerMedium();
            top5Mediums = consultationsPerMedium.entrySet()
                    .stream()
                    .sorted(Map.Entry.<Long, Integer>comparingByValue().reversed()) // Tri décroissant
                    .limit(5) // On garde que les 5 premiers
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            Map.Entry::getValue,
                            (e1, e2) -> e1,
                            LinkedHashMap::new));
        } catch (Exception ex) {
            top5Mediums = null;
        }
        return top5Mediums;
    }
}
