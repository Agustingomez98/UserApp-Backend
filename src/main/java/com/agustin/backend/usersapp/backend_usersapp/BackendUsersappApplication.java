package com.agustin.backend.usersapp.backend_usersapp;

import java.util.List;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.agustin.backend.usersapp.backend_usersapp.models.entities.PermissionEntity;
import com.agustin.backend.usersapp.backend_usersapp.models.entities.RoleEntity;
import com.agustin.backend.usersapp.backend_usersapp.models.entities.UserEntity;
import com.agustin.backend.usersapp.backend_usersapp.models.enums.RoleEnum;
import com.agustin.backend.usersapp.backend_usersapp.repositories.UserRepository;

@SpringBootApplication
public class BackendUsersappApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendUsersappApplication.class, args);
	}

	@Bean
	CommandLineRunner init(UserRepository userRepository) {
		return args -> {
			/* Creacion de Permisos */
			PermissionEntity createPermission = PermissionEntity.builder()
					.name("CREATE")
					.build();

			PermissionEntity readPermission = PermissionEntity.builder()
					.name("READ")
					.build();

			PermissionEntity updatePermission = PermissionEntity.builder()
					.name("UPDATE")
					.build();

			PermissionEntity refactorPermission = PermissionEntity.builder()
					.name("REFACTOR")
					.build();

			PermissionEntity deletePermission = PermissionEntity.builder()
					.name("DELET")
					.build();

			/* Creacion de Roles */
			RoleEntity roleAdmin = RoleEntity.builder()
					.roleEnum(RoleEnum.ADMIN)
					.permissionList(Set.of(createPermission, readPermission, updatePermission, deletePermission))
					.build();

			RoleEntity roleUser = RoleEntity.builder()
					.roleEnum(RoleEnum.USER)
					.permissionList(Set.of(createPermission, readPermission))
					.build();

			RoleEntity roleInvited= RoleEntity.builder()
					.roleEnum(RoleEnum.INVITED)
					.permissionList(Set.of(readPermission))
					.build();

			RoleEntity roleDeveloper = RoleEntity.builder()
					.roleEnum(RoleEnum.DEVELOPER)
					.permissionList(Set.of(createPermission, readPermission, updatePermission, deletePermission))
					.build();

					/*Creacion de Usuarios */

			UserEntity userAgustin = UserEntity.builder()
			.username("Agustin")
			.password("$2a$10$E1AsbOMNoT1G3Dk.qoHyRudUt1AF7WtCWGBFNgNMHQW6q9R4oGPWK")
			.email("agustin@email.com")
			.isEnable(true)
			.accountNoExpired(true)
			.accounLocked(true)
			.credentialNoExpired(true)
			.roles(Set.of(roleAdmin))
			.build();

			UserEntity userFranco = UserEntity.builder()
			.username("Franco")
			.password("$2a$10$E1AsbOMNoT1G3Dk.qoHyRudUt1AF7WtCWGBFNgNMHQW6q9R4oGPWK")
			.email("franco@email.com")
			.isEnable(true)
			.accountNoExpired(true)
			.accounLocked(true)
			.credentialNoExpired(true)
			.roles(Set.of(roleUser))
			.build();

			UserEntity userGomez = UserEntity.builder()
			.username("Gomez")
			.password("$2a$10$E1AsbOMNoT1G3Dk.qoHyRudUt1AF7WtCWGBFNgNMHQW6q9R4oGPWK")
			.email("gomez@email.com")
			.isEnable(true)
			.accountNoExpired(true)
			.accounLocked(true)
			.credentialNoExpired(true)
			.roles(Set.of(roleInvited))
			.build();

			UserEntity userRoma = UserEntity.builder()
			.username("Roma")
			.password("$2a$10$E1AsbOMNoT1G3Dk.qoHyRudUt1AF7WtCWGBFNgNMHQW6q9R4oGPWK")
			.email("roma@email.com")
			.isEnable(true)
			.accountNoExpired(true)
			.accounLocked(true)
			.credentialNoExpired(true)
			.roles(Set.of(roleDeveloper))
			.build();

			userRepository.saveAll(List.of(userAgustin,userFranco,userGomez,userRoma));
		};

	}

}
