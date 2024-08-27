package com.agustin.backend.usersapp.backend_usersapp.repositories;

import org.springframework.data.repository.CrudRepository;
import java.util.List;
import com.agustin.backend.usersapp.backend_usersapp.models.entities.RoleEntity;

public interface RoleRepository extends CrudRepository<RoleEntity,Long>{

    List<RoleEntity> findRoleEntitiesByRoleEnumIn(List<String> roleNames);

}
