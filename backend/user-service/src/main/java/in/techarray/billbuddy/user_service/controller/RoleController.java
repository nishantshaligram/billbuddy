package in.techarray.billbuddy.user_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.techarray.billbuddy.user_service.dto.CreateRoleRequestDTO;
import in.techarray.billbuddy.user_service.model.Role;
import in.techarray.billbuddy.user_service.service.RoleService;

@RestController
@RequestMapping("/roles")
public class RoleController {

    private RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    public ResponseEntity<Role> createRole(@RequestBody CreateRoleRequestDTO request){
        Role role = roleService.createRole( request.getName() );
        return new ResponseEntity<>(role, HttpStatus.OK);
    }
}
