/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import metier.modele.Consultation;

import javax.persistence.PersistenceException;
import javax.persistence.RollbackException;

/**
 *
 * @author ncardenas
 */
public class ConsultationDao {

    /**
     * Creates a new consultation in the database
     * @param consultation The consultation to persist
     * @throws PersistenceException If a database error occurs
     */
    public void create(Consultation consultation) throws PersistenceException {
        JpaUtil.getEntityManager().persist(consultation);
    }
    
    /**
     * Updates a consultation in the database
     * @param consultation The consultation to update
     * @throws PersistenceException If a database error occurs
     * @throws RollbackException If the update causes a transaction rollback
     */
    public void update(Consultation consultation) throws PersistenceException, RollbackException {
        JpaUtil.getEntityManager().merge(consultation);
    }
    
    /**
     * Deletes a consultation from the database by ID
     * @param id The ID of the consultation to delete
     * @return The deleted consultation, or null if not found
     * @throws PersistenceException If a database error occurs
     */
    public Consultation delete(Long id) throws PersistenceException {
        Consultation consultation = findById(id);
        if (consultation != null) {
            JpaUtil.getEntityManager().remove(consultation);
        }
        return consultation;
    }
    
    /**
     * Finds a consultation by ID
     * @param id The ID of the consultation to find
     * @return The consultation, or null if not found
     * @throws PersistenceException If a database error occurs
     */
    public Consultation findById(Long id) throws PersistenceException {
        return JpaUtil.getEntityManager().find(Consultation.class, id);
    }
    
    /**
     * Finds all consultations
     * @return List of all consultations
     * @throws PersistenceException If a database error occurs
     */
    public List<Consultation> findAll() throws PersistenceException {
        return JpaUtil.getEntityManager().createQuery("SELECT c FROM Consultation c", Consultation.class)
                                         .getResultList();
    }
    
    /**
     * Finds consultations by client ID
     * @param clientId The client ID to search for
     * @return List of consultations for the given client
     * @throws PersistenceException If a database error occurs
     */
    public List<Consultation> findByClientId(Long clientId) throws PersistenceException {
        return JpaUtil.getEntityManager().createQuery(
                "SELECT c FROM Consultation c WHERE c.client.id = :clientId", 
                Consultation.class)
                .setParameter("clientId", clientId)
                .getResultList();
    }
    
    /**
     * Finds consultations by employee ID
     * @param employeId The employee ID to search for
     * @return List of consultations for the given employee
     * @throws PersistenceException If a database error occurs
     */
    public List<Consultation> findByEmployeId(Long employeId) throws PersistenceException {
        return JpaUtil.getEntityManager().createQuery(
                "SELECT c FROM Consultation c WHERE c.employe.id = :employeId", 
                Consultation.class)
                .setParameter("employeId", employeId)
                .getResultList();
    }
}
