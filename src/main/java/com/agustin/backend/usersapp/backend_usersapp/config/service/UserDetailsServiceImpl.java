package com.agustin.backend.usersapp.backend_usersapp.config.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.agustin.backend.usersapp.backend_usersapp.config.utils.JwtUtils;
import com.agustin.backend.usersapp.backend_usersapp.controllers.dto.AuthCreateRoleRequest;
import com.agustin.backend.usersapp.backend_usersapp.controllers.dto.AuthCreateUserRequest;
import com.agustin.backend.usersapp.backend_usersapp.controllers.dto.AuthLoginRequest;
import com.agustin.backend.usersapp.backend_usersapp.controllers.dto.AuthResponse;
import com.agustin.backend.usersapp.backend_usersapp.models.entities.RoleEntity;
import com.agustin.backend.usersapp.backend_usersapp.models.entities.UserEntity;
import com.agustin.backend.usersapp.backend_usersapp.repositories.RoleRepository;
import com.agustin.backend.usersapp.backend_usersapp.repositories.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

    @Autowired
    private PasswordEncoder passwordEncoder; 
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity userEntity = userRepository.findUserEntityByUsername(username)
        .orElseThrow(()-> new UsernameNotFoundException("El ususario " + username + " no existe"));
        

        List<SimpleGrantedAuthority> authoritiesList = new ArrayList<>();

        userEntity.getRoles()
        .forEach(role -> authoritiesList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRoleEnum().name()))));

        userEntity.getRoles().stream()
        .flatMap(role -> role.getPermissionList().stream())
        .forEach(permission-> authoritiesList.add(new SimpleGrantedAuthority(permission.getName())));

        return new User(userEntity.getUsername(),
        userEntity.getPassword(),
        userEntity.isEnable(),
        userEntity.isAccountNoExpired(),
        userEntity.isCredentialNoExpired(),
        userEntity.isAccounLocked(),
        authoritiesList);

    }

    public AuthResponse loginUser(AuthLoginRequest authLoginRequest){
        String username = authLoginRequest.username();
        String password = authLoginRequest.password();

        Authentication authentication = this.authenticate(username,password);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accesToken = jwtUtils.createToken(authentication);

        AuthResponse authResponse = new AuthResponse(username, "Usuario logeado con exito!", accesToken, true);
        return authResponse;
    }

    public Authentication authenticate (String username, String password){
        UserDetails userDetails = this.loadUserByUsername(username);

        if (userDetails == null) {
            throw new BadCredentialsException("Usuario o contraseña invalidos");
        }

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("La contraseña es incorrecta");
        }

        return new UsernamePasswordAuthenticationToken(username, userDetails.getPassword() ,userDetails.getAuthorities());
    }

    public AuthResponse createUser(AuthCreateUserRequest authCreateUserRequest){

        String username = authCreateUserRequest.username();
        String password = authCreateUserRequest.password();
        List<String> roleRequest = authCreateUserRequest.roleRequest().rolesListName();

        Set<RoleEntity> roleEntitySet = roleRepository.findRoleEntitiesByRoleEnumIn(roleRequest).stream().collect(Collectors.toSet());

        if (roleEntitySet.isEmpty()) {
            throw new IllegalArgumentException("Los roles especificados no existen");
        }

        UserEntity userEntity = UserEntity.builder()
        .username(username)
        .password(passwordEncoder.encode(password))
        .email(authCreateUserRequest.email())
        .roles(roleEntitySet)
        .isEnable(true)
        .accounLocked(true)
        .accountNoExpired(true)
        .credentialNoExpired(true)
        .build();

        UserEntity userCreated = userRepository.save(userEntity);

        List <SimpleGrantedAuthority> authorityList = new ArrayList<>();

        userCreated.getRoles().forEach(role -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRoleEnum().name()))));

        Authentication authentication = new UsernamePasswordAuthenticationToken(userCreated.getUsername(),userCreated.getPassword(),authorityList);

        String accessToken = jwtUtils.createToken(authentication);

        AuthResponse authResponse = new AuthResponse(userCreated.getUsername(),"Usuario creado", accessToken, true);
        return authResponse;
    }
}
