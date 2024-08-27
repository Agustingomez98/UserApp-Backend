package com.agustin.backend.usersapp.backend_usersapp.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import com.agustin.backend.usersapp.backend_usersapp.models.entities.UserEntity;


public interface UserRepository extends CrudRepository<UserEntity,Long> {

    Optional<UserEntity> findUserEntityByUsername(String username);
}
