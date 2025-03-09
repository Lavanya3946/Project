package com.xworkz.moduleapp.repo;

import com.xworkz.moduleapp.entity.ModuleEntity;

public interface ModuleRepo {
    boolean signUpSave(ModuleEntity moduleEntity);

    ModuleEntity findByEmail(String email);

    boolean updateUser(ModuleEntity moduleEntity);


    void unlockUserAccount(String email);
}
