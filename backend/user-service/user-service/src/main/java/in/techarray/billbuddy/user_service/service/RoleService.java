package in.techarray.billbuddy.user_service.service;

import org.springframework.stereotype.Service;

import in.techarray.billbuddy.user_service.model.Role;
import in.techarray.billbuddy.user_service.repository.RoleRepository;

@Service
public class RoleService {

    private RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role createRole( String name ) {
        Role role = new Role();
        role.setRole(name);
        return roleRepository.save(role);
    }
}
