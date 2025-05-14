/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

/**
 *
 * @author ncardenas
 */
public class JpaUtilOLD {

    public static final String PERSISTENCE_UNIT_NAME = "notdb";
    private static EntityManagerFactory entityManagerFactory = null;
    private static EntityManager entityManager = null;

    public static void createEntityManager() {
        entityManager = entityManagerFactory.createEntityManager();
    }

    public static EntityManager getEntityManager() {
        return entityManager;
    }

    public static void closeEntityManager() {
        entityManager.close();
    }

    public static void openTransaction() {
        entityManager.getTransaction().begin();

    }

    public static void closeTransaction() {
        try {
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    public static EntityTransaction getTransaction() {
        return entityManager.getTransaction();
    }

    public void persist(Object object) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        try {
            em.persist(object);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

}
