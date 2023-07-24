package com.pedroleon.app.web.rest.controllers;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pedroleon.app.domain.Hero;
import com.pedroleon.app.domain.Team;
import com.pedroleon.app.domain.UserEntity;
import com.pedroleon.app.service.UserService;
import com.pedroleon.app.web.rest.dto.TeamDTO;

@RestController
@RequestMapping("/api")
public class TeamController {

	private final Logger log = LoggerFactory.getLogger(TeamController.class);

	private static final int MAX_HERO_QUANTITY = 6;

	private UserService userService;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	public TeamController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping(path = "/team")
	public TeamDTO getTeam() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserEntity us = this.userService.findByUsername(authentication.getName());
		System.out.println(us.getTeam().getHeroes().size());
		return modelMapper.map(us.getTeam(), TeamDTO.class);
	}

	@PostMapping(path = "/deleteFromTeam")
	public TeamDTO deleteFromTeam(@RequestBody Hero hero) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserEntity user = this.userService.findByUsername(authentication.getName());
		Team team = user.getTeam();
		team.getHeroes().remove(hero);
		userService.save(user);
		return modelMapper.map(user.getTeam(), TeamDTO.class);
	}

	@PostMapping(path = "/addToTeam")
	public ResponseEntity<String> addToTeam(@RequestBody Hero hero) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserEntity user = this.userService.findByUsername(authentication.getName());

		if (user.getTeam().getHeroes().size() >= MAX_HERO_QUANTITY) {
			return new ResponseEntity<>("Not allowed to add more than " + MAX_HERO_QUANTITY + " heroes",
					HttpStatus.BAD_REQUEST);
		}
		user.getTeam().getHeroes().add(hero);

		try {
			userService.save(user);
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<>("Hero already in the team", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(null, HttpStatus.OK);
	}
}
