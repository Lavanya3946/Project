package com.xworkz.moduleapp.repo;

import com.xworkz.moduleapp.entity.ModuleEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    @Override
    public ModuleEntity findByEmail(String email) {
        try {
            Query query = entityManager.createNamedQuery("getByEmail", ModuleEntity.class);
            query.setParameter("email", email);
            ModuleEntity moduleEntity = (ModuleEntity) query.getSingleResult();


            if (moduleEntity.isAccountLocked() && moduleEntity.getLockTime() != null) {
                LocalDateTime unlockTime = moduleEntity.getLockTime().plusHours(24);
                if (LocalDateTime.now().isAfter(unlockTime)) {
                    unlockUserAccount(email);
                    moduleEntity.setAccountLocked(false);
                    moduleEntity.setFailedAttempts(0);
                    moduleEntity.setLockTime(null);
                }
            }

            System.out.println("repo: " + moduleEntity);
            return moduleEntity;

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    @Override
    public boolean updateUser(ModuleEntity moduleEntity) {
        boolean isUpdate = false;
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(moduleEntity);
        entityManager.getTransaction().commit();
        entityManager.close();
        isUpdate = true;
        return isUpdate;
    }

//
    @Override
    public void unlockUserAccount(String email) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Query query = entityManager.createQuery("UPDATE ModuleEntity m SET m.accountLocked = false, m.failedAttempts = 0, m.lockTime = NULL WHERE m.email = :email");
            query.setParameter("email", email);
            query.executeUpdate();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            System.out.println("Error unlocking account: " + e.getMessage());
        } finally {
            entityManager.close();
        }
    }
}
