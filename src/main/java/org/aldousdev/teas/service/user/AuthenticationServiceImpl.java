package org.aldousdev.teas.service.user;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletResponse;
import org.aldousdev.teas.dto.request.SigninRequest;
import org.aldousdev.teas.dto.request.SignupRequest;
import org.aldousdev.teas.dto.response.SigninResponse;
import org.aldousdev.teas.models.user.Account;
import org.aldousdev.teas.models.user.Role;
import org.aldousdev.teas.repo.user.AccountRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;


@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationServiceImpl.class);

    private final AccountRepo accountRepo;
    private final RoleService roleService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    public AuthenticationServiceImpl(AccountRepo accountRepo, RoleService roleService, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.accountRepo = accountRepo;
        this.roleService = roleService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }


    @Override
    public Long signup(SignupRequest signupRequest) {
        logger.info("Signing up a new account with {}",signupRequest.getEmail());

        if(accountRepo.existsByEmail(signupRequest.getEmail())){
            logger.error("Email already in use");
            throw new EntityExistsException("Email already exists");
        }

        Account account = new Account();
        account.setEmail(signupRequest.getEmail());
        account.setNickname(signupRequest.getNickname());
        account.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        logger.info("Assign Role_USER");
        Role role = roleService.getRoleByName("ROLE_USER");

        account.setRoles(Collections.singleton(role));
        logger.info("Saving new account");
        accountRepo.save(account);
        logger.info("Saved new account");

        return account.getId();
    }



    @Override
    public SigninResponse signin(SigninRequest signinRequest, HttpServletResponse response) throws AuthenticationException {
        logger.info("Signing in {}", signinRequest.getEmail());

        Authentication authenticationRequest = UsernamePasswordAuthenticationToken.unauthenticated(signinRequest.getEmail(), signinRequest.getPassword());
        Authentication authenticationResponse = this.authenticationManager.authenticate(authenticationRequest);
        logger.info("Email and Password authenticated");
//        If the credentials are correct, Spring Security creates an Authentication object (e.g., UsernamePasswordAuthenticationToken for form login or JwtAuthenticationToken for JWT-based login) and places it in the SecurityContext.
//        The authenticated Authentication object contains the user’s details, roles, and other information.
//            The Authentication contains:
//
//            principal: Identifies the user. When authenticating with a username/password this is often an instance of UserDetails.
//            System.out.println("principal: " + authenticationResponse.getPrincipal());
//            credentials: Often a password. In many cases, this is cleared after the user is authenticated, to ensure that it is not leaked.
//            System.out.println("cred: " + authenticationResponse.getCredentials());
//            authorities: The GrantedAuthority instances are high-level permissions the user is granted. Two examples are roles and scopes.
//            System.out.println("auth: " + authenticationResponse.getAuthorities());

//            principal: org.springframework.security.core.userdetails.User [Username=springuser@gg.com, Password=[PROTECTED], Enabled=true, AccountNonExpired=true, CredentialsNonExpired=true, AccountNonLocked=true, Granted Authorities=[ROLE_USER]]
//            cred: null
//            auth: [ROLE_USER]


        logger.info("Saving authenticated account to springsecurity");
         SecurityContextHolder.getContext().setAuthentication(authenticationResponse);

        logger.info("generate JWT token and Saving into cookie");

        jwtService.generateToken(signinRequest.getEmail(), response);

        UserDetails userDetails = (UserDetails) authenticationResponse.getPrincipal();
        String email = userDetails.getUsername();
        Account account = accountRepo.findByEmail(email).orElseThrow(()->new EntityNotFoundException("email not found"));


        SigninResponse signinResponse = new SigninResponse();
        signinResponse.setId(account.getId());
        signinResponse.setEmail(account.getEmail());
        signinResponse.setNickname(account.getNickname());

        Role adminRole = roleService.getRoleByName("ROLE_ADMIN");
        signinResponse.setIsAdmin(account.getRoles().contains(adminRole));
        logger.info("Return authenticated account in SigninResponse DTO");
        return signinResponse;


    }
    public void logoutUser(HttpServletResponse response){
        jwtService.removeTokenFromCookie(response);
    }



}
