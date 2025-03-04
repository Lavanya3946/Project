package com.xworkz.moduleapp.repo;

import com.xworkz.moduleapp.entity.ModuleEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@Repository
public class ModuleRepoImpl implements ModuleRepo {
    private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("lavanya");
    EntityManager entityManager = entityManagerFactory.createEntityManager();

    @Override
    public boolean signUpSave(ModuleEntity moduleEntity) {
        try {
            if (moduleEntity != null) {
                entityManager.getTransaction().begin();
                entityManager.persist(moduleEntity);
                entityManager.getTransaction().commit();
                return true;
            }
            return false;
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
        }
        return false;
    }
}
