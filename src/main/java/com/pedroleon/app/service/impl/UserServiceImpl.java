package com.pedroleon.app.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pedroleon.app.domain.ERole;
import com.pedroleon.app.domain.Role;
import com.pedroleon.app.domain.Team;
import com.pedroleon.app.domain.UserEntity;
import com.pedroleon.app.repository.RoleRepository;
import com.pedroleon.app.repository.UserRepository;
import com.pedroleon.app.service.UserService;
import com.pedroleon.app.web.rest.dto.UserDTO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final JpaContext jpaContext;

	public UserServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository,
			RoleRepository roleRepository, JpaContext jpaContext) {
		this.passwordEncoder = passwordEncoder;
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.jpaContext = jpaContext;
	}

	@Override
	public UserEntity save(UserEntity user) {
		return this.userRepository.save(user);
	}

	@Override
	public UserEntity findByUsername(String username) {
		EntityManager entityManager = jpaContext.getEntityManagerByManagedType(UserEntity.class);
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<UserEntity> query = cb.createQuery(UserEntity.class);
		Root<UserEntity> root = query.from(UserEntity.class);
		root.fetch("roles", JoinType.INNER);
		root.fetch("team", JoinType.INNER);
		query.select(root);
		Predicate usernameEquals = cb.equal(root.get("username"), username);
		query.where(usernameEquals);
		TypedQuery<UserEntity> typedQuery = entityManager.createQuery(query);
		List<UserEntity> list = typedQuery.getResultList();
		UserEntity user = list.size() == 0 ? null : list.get(0);
		System.out.println(user != null ? user.getTeam().getHeroes().size() : "");
		return user;
	}

	@Override
	public UserEntity registerUser(UserDTO user) {

		UserEntity newUser = new UserEntity();
		newUser.setUsername(user.getUsername());
		newUser.setPassword(passwordEncoder.encode(user.getPassword()));
		Role role = roleRepository.findByName(ERole.USER.getValue());
		newUser.setRoles(new ArrayList<>(Arrays.asList(role)));
		newUser.setTeam(new Team());
		return userRepository.save(newUser);

	}

}
