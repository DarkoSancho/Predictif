/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;

import metier.modele.Employe;
import metier.modele.Genre;

import javax.persistence.PersistenceException;
import javax.persistence.EntityNotFoundException;
import javax.persistence.RollbackException;

/**
 *
 * @author ncardenas
 */
public class EmployeDao {

    /**
     * Creates a new employee in the database
     * 
     * @param employe The employee to persist
     * @throws PersistenceException If a database error occurs
     */
    public void create(Employe employe) throws PersistenceException {
        JpaUtil.getEntityManager().persist(employe);
    }

    public Employe archiver(Long id) {
        // prevoir impact sur les cardinalites
        Employe employe = findById(id);
        if (employe != null) {
            JpaUtil.getEntityManager().remove(employe);
        }
        return employe;
    }

    /**
     * Updates an employee in the database
     * 
     * @param employe The employee to update
     * @throws PersistenceException If a database error occurs
     * @throws RollbackException    If the update causes a transaction rollback
     */
    public void update(Employe employe) throws PersistenceException, RollbackException {
        JpaUtil.getEntityManager().merge(employe);
    }

    /**
     * Finds an employee by ID
     * 
     * @param id The ID of the employee to find
     * @return The employee, or null if not found
     * @throws PersistenceException    If a database error occurs
     * @throws EntityNotFoundException If the employee is not found
     */
    public Employe findById(Long id) throws PersistenceException, EntityNotFoundException {

        return JpaUtil.getEntityManager().find(Employe.class, id);

    }

    /**
     * Finds all employees
     * 
     * @return List of all employees
     * @throws PersistenceException If a database error occurs
     */
    public List<Employe> findAll() throws PersistenceException {
        return JpaUtil.getEntityManager().createQuery("SELECT e FROM Employe e", Employe.class)
                .getResultList();
    }

    /**
     * Finds suitable employees by genre
     * 
     * @param genre The genre to filter by
     * @return List of suitable employees
     * @throws PersistenceException If a database error occurs
     */
    public Employe findSuitable(Genre genre) throws PersistenceException {
        
        try {
            
        List<Employe> suitableEmployees = JpaUtil.getEntityManager().createQuery(
                "SELECT e FROM Employe e WHERE e.genre = :genre AND e.disponible = true ORDER BY e.nbConsultations",
                Employe.class)
                .setParameter("genre", genre)
                .getResultList();
 
            return suitableEmployees.isEmpty() ? null : suitableEmployees.get(0);
        } catch (Exception ex) {
            System.out.println("No employee found to create the consultation");
            return null;
        }
        
    }

    /**
     * Finds employees by id with consultations
     * 
     * @param id The id to search for
     * @return List of employees with the given name
     * @throws PersistenceException If a database error occurs
     */
    public Employe findByIdWithConsultations(Long id) throws PersistenceException {
        try {
            return JpaUtil.getEntityManager()
                .createQuery("SELECT e FROM Employe e LEFT JOIN FETCH e.consultations WHERE e.id = :id", Employe.class)
                .setParameter("id", id)
                .getSingleResult();
        } catch (javax.persistence.NoResultException ex) {
            return null;
        }
    }

    /**
     * Finds employees by email and password for authentication
     * 
     * @param email    The employe's email
     * @param password The employe's password
     * @return List of matching employes (should be 0 or 1)
     * @throws PersistenceException If a database error occurs
     */
    public Employe findByEmailAndPassword(String email, String password) throws PersistenceException {
        try {
            return JpaUtil.getEntityManager().createQuery(
                    "SELECT e FROM Employe e WHERE e.mail = :email AND e.motDePasse = :password",
                    Employe.class)
                    .setParameter("email", email)
                    .setParameter("password", password)
                    .getSingleResult();
        } catch (javax.persistence.NoResultException ex) {
            return null;
        }
    }
    
    
}
