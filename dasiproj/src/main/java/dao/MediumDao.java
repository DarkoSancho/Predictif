/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;

import metier.modele.Medium;
import metier.modele.Genre;

import javax.persistence.PersistenceException;
import javax.persistence.RollbackException;
import metier.modele.Consultation;

/**
 *
 * @author ncardenas
 */
public class MediumDao {

    /**
     * Creates a new medium in the database
     *
     * @param medium The medium to persist
     * @throws PersistenceException If a database error occurs
     */
    public void create(Medium medium) throws PersistenceException {
        JpaUtil.getEntityManager().persist(medium);
    }

    /**
     * Updates a medium in the database
     *
     * @param medium The medium to update
     * @throws PersistenceException If a database error occurs
     * @throws RollbackException If the update causes a transaction rollback
     */
    public void update(Medium medium) throws PersistenceException, RollbackException {
        JpaUtil.getEntityManager().merge(medium);
    }

    /**
     * Deletes a medium from the database by ID
     *
     * @param id The ID of the medium to delete
     * @return The deleted medium, or null if not found
     * @throws PersistenceException If a database error occurs
     */
    public Medium delete(Long id) throws PersistenceException {
        Medium medium = findById(id);
        if (medium != null) {
            JpaUtil.getEntityManager().remove(medium);
        }
        return medium;
    }

    /**
     * Finds a medium by ID
     *
     * @param id The ID of the medium to find
     * @return The medium, or null if not found
     * @throws PersistenceException If a database error occurs
     */
    public Medium findById(Long id) throws PersistenceException {
        return JpaUtil.getEntityManager().find(Medium.class, id);
    }

    /**
     * Finds all mediums
     *
     * @return List of all mediums
     * @throws PersistenceException If a database error occurs
     */
    public List<Medium> findAll() throws PersistenceException {
        return JpaUtil.getEntityManager().createQuery("SELECT m FROM Medium m", Medium.class)
                .getResultList();
    }

    /**
     * Finds mediums by genre
     *
     * @param genre The genre to filter by
     * @return List of mediums with the specified genre
     * @throws PersistenceException If a database error occurs
     */
    public List<Medium> findByGenre(Genre genre) throws PersistenceException {
        return JpaUtil.getEntityManager().createQuery(
                "SELECT m FROM Medium m WHERE m.genre = :genre",
                Medium.class)
                .setParameter("genre", genre)
                .getResultList();
    }

    public List<Consultation> findAllConsultations(Medium medium) {
        return JpaUtil.getEntityManager().createQuery("SELECT cons FROM Consultation cons WHERE cons.medium = :m AND cons.etat = :etat",
                Consultation.class).setParameter("m", medium).setParameter("etat", Consultation.Etat.effectuee).getResultList();
    }

}
