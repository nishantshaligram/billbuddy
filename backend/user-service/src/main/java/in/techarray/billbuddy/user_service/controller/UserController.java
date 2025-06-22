package in.techarray.billbuddy.user_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.techarray.billbuddy.user_service.dto.SetUserRoleRequestDTO;
import in.techarray.billbuddy.user_service.dto.UserDto;
import in.techarray.billbuddy.user_service.service.UserService;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping( "/users" )
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserDetails(@PathVariable("id") UUID id) {
        UserDto UserDto = userService.getUserDetails(id);
        return new ResponseEntity<>(UserDto, HttpStatus.OK);
    }
    
    @PostMapping("/{id}/roles")
    public ResponseEntity<UserDto> setUserRoles(@PathVariable("id") UUID userId, @RequestBody SetUserRoleRequestDTO request){
        UserDto UserDto  = userService.setUserRole(userId, request.getRoleIds());
        return new ResponseEntity<>(UserDto, HttpStatus.OK);
    }
    
}
