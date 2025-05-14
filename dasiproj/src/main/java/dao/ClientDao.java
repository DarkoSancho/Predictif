/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import metier.modele.Client;

import javax.persistence.PersistenceException;
import javax.persistence.RollbackException;

/**
 *
 * @author ncardenas
 */
public class ClientDao {

    /**
     * Creates a new client in the database
     * @param client The client to persist
     * @throws PersistenceException If a database error occurs
     */
    public void create(Client client) throws PersistenceException {
        JpaUtil.getEntityManager().persist(client);
    }
    
    /**
     * Deletes a client from the database by ID
     * @param id The ID of the client to delete
     * @return The deleted client, or null if not found
     * @throws PersistenceException If a database error occurs
     */
    public Client delete(Long id) throws PersistenceException {
        Client client = findById(id);
        if (client != null) {
            JpaUtil.getEntityManager().remove(client);
        }
        return client;
    }
    
    /**
     * Updates a client in the database
     * @param client The client to update
     * @throws PersistenceException If a database error occurs
     * @throws RollbackException If the update causes a transaction rollback
     */
    public void update(Client client) throws PersistenceException, RollbackException {
        JpaUtil.getEntityManager().merge(client);
    }
        
    /**
     * Finds a client by ID
     * @param id The ID of the client to find
     * @return The client, or null if not found
     * @throws PersistenceException If a database error occurs
     */
    public Client findById(Long id) throws PersistenceException {
        return JpaUtil.getEntityManager().find(Client.class, id);
    }
    
    /**
     * Finds all clients
     * @return List of all clients
     * @throws PersistenceException If a database error occurs
     */
    public List<Client> findAll() throws PersistenceException {
        return JpaUtil.getEntityManager().createQuery("SELECT c FROM Client c", Client.class)
                                         .getResultList();
    }
    
    /**
     * Finds clients by email and password for authentication
     * @param email The client's email
     * @param password The client's password
     * @return List of matching clients (should be 0 or 1)
     * @throws PersistenceException If a database error occurs
     */
    public Client findByEmailAndPassword(String email, String password) throws PersistenceException {
        return JpaUtil.getEntityManager().createQuery(
                "SELECT c FROM Client c WHERE c.mail = :email AND c.motDePasse = :password", 
                Client.class)
                .setParameter("email", email)
                .setParameter("password", password)
                .getSingleResult();
    }
}
