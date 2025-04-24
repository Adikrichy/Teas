package org.aldousdev.teas.controller.user;

import org.aldousdev.teas.dto.request.PasswordRequestDto;
import org.aldousdev.teas.dto.response.SigninResponse;
import org.aldousdev.teas.models.user.Account;
import org.aldousdev.teas.models.user.Role;
import org.aldousdev.teas.repo.user.RoleRepo;
import org.aldousdev.teas.service.user.AuthorizationService;
import org.aldousdev.teas.service.user.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/resource")
public class AuthorizationController {

    private final AuthorizationService authorizationService;
    private final RoleRepo roleRepo;
    private final RoleService roleService;
//    private final RoleRepo roleRepo;

    public AuthorizationController(AuthorizationService authorizationService, RoleRepo roleRepo, RoleService roleService) {

        this.authorizationService = authorizationService;
        this.roleRepo = roleRepo;
        this.roleService = roleService;
    }


    @GetMapping("/user")
    public ResponseEntity<?> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        try {
            System.out.println(userDetails.getUsername());
            Account account = authorizationService.findByEmail(userDetails.getUsername());

            SigninResponse signinResponse = new SigninResponse();
            signinResponse.setId(account.getId());
            signinResponse.setEmail(account.getEmail());
            signinResponse.setNickname(account.getNickname());

            Role adminRole = roleService.getRoleByName("ROLE_ADMIN");

            signinResponse.setIsAdmin(account.getRoles().contains(adminRole));
            return ResponseEntity.ok(signinResponse);
        } catch (UsernameNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PatchMapping("/user")
    public ResponseEntity<SigninResponse> updateUser(@RequestBody Map<String, Object> userRequestDto, @AuthenticationPrincipal UserDetails userDetails) {
        System.out.println("update user info");
        Account account = authorizationService.findByEmail(userDetails.getUsername());

        return ResponseEntity.ok(authorizationService.updateUser(userRequestDto, account));
    }

    @PatchMapping("/updatePassword")
    public ResponseEntity<String> updatePassword(@RequestBody PasswordRequestDto passwordRequestDto,
                                                 @AuthenticationPrincipal UserDetails userDetails) {
        System.out.println("update user password");
        Account account = authorizationService.findByEmail(userDetails.getUsername());
        try {
            authorizationService.updatePassword(passwordRequestDto, account);
            return new ResponseEntity<>(HttpStatus.OK);

        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }

    }


    @GetMapping("/accounts")
    public ResponseEntity<List<SigninResponse>> getAccounts() {
        return new ResponseEntity<>(authorizationService.getAllAccounts(), HttpStatus.OK);

    }
    @PatchMapping("/accounts/{id}")
    public ResponseEntity<SigninResponse> toggleAdminRole(@PathVariable Long id) {
        return new ResponseEntity<>(authorizationService.toggleAdminRole(id), HttpStatus.OK);

    }

    @DeleteMapping("/accounts/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        Account account = authorizationService.findByEmail(userDetails.getUsername());
        authorizationService.deleteAccount(id, account);
        return ResponseEntity.noContent().build();

    }
}

