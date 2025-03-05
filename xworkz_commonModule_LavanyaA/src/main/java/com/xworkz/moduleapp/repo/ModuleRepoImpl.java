package com.xworkz.moduleapp.repo;

import com.xworkz.moduleapp.entity.ModuleEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.*;

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
        try{
           Query query= entityManager.createNamedQuery("getByEmail",ModuleEntity.class);
            System.out.println("email"+ModuleEntity.class);
           query.setParameter("email",email);
           ModuleEntity moduleEntity=(ModuleEntity) query.getSingleResult();
           return moduleEntity;

        }catch (Exception e){
            System.out.println("error: "+e.getMessage());
            return null;
        }

    }
}
