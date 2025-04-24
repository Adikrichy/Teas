package org.aldousdev.teas.service.user;

import org.aldousdev.teas.models.user.Account;
import org.aldousdev.teas.models.user.Role;
import org.aldousdev.teas.repo.user.RoleRepo;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    private final RoleRepo roleRepo;

    public RoleService(RoleRepo roleRepo) {
        this.roleRepo = roleRepo;
    }

    public Role getRoleByName(String roleName){
        return roleRepo.findByName(roleName).orElseGet(
                ()->roleRepo.save(new Role(roleName))
        );
    }

    public boolean isAdmin(Account account) {
        Role adminRole = getRoleByName("ROLE_ADMIN");
        return account.getRoles().contains(adminRole);
    }

}
