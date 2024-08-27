package com.agustin.backend.usersapp.backend_usersapp.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agustin.backend.usersapp.backend_usersapp.models.entities.UserEntity;
import com.agustin.backend.usersapp.backend_usersapp.models.requests.UserRequest;
import com.agustin.backend.usersapp.backend_usersapp.repositories.UserRepository;
import com.agustin.backend.usersapp.backend_usersapp.services.UserEntityService;


@Service
public class UserEntityServiceImpl implements UserEntityService {

    @Autowired
    private UserRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<UserEntity> findAll() {
        return (List<UserEntity>) repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserEntity> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public UserEntity save(UserEntity user) {
        return repository.save(user);
    }

    @Override
    @Transactional
    public Optional<UserEntity> update(UserRequest user, Long id) {

        Optional<UserEntity> userOptional=  this.findById(id);

        if(userOptional.isPresent()){
            UserEntity userDb = userOptional.orElseThrow();
            userDb.setUsername(user.getUsername());
            userDb.setEmail(user.getEmail());
            
            return Optional.ofNullable(userDb);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public void remove(Long id) {
        repository.deleteById(id);
    }
}
