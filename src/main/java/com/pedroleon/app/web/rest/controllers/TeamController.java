package com.pedroleon.app.web.rest.controllers;

import com.pedroleon.app.domain.Hero;
import com.pedroleon.app.domain.Team;
import com.pedroleon.app.domain.UserEntity;
import com.pedroleon.app.service.UserService;
import com.pedroleon.app.web.rest.dto.TeamDTO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TeamController {

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
        Optional<UserEntity> result = this.userService.findByUsername(authentication.getName());

        if (result.isEmpty() || result.get().getTeam() == null) {
            return null;
        }
        return modelMapper.map(result.get().getTeam(), TeamDTO.class);
    }

    @PostMapping(path = "/deleteFromTeam")
    public TeamDTO deleteFromTeam(@RequestBody Hero hero) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<UserEntity> result = this.userService.findByUsername(authentication.getName());

        if (result.isEmpty() || result.get().getTeam() == null) {
            return null;
        }

        UserEntity user = result.get();
        Team team = user.getTeam();
        team.getHeroes().remove(hero);
        userService.save(user);

        return modelMapper.map(user.getTeam(), TeamDTO.class);
    }

    @PostMapping(path = "/addToTeam")
    public TeamDTO addToTeam(@RequestBody Hero hero) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<UserEntity> result = this.userService.findByUsername(authentication.getName());

        if (result.isEmpty()) {
            return null;
        }

        UserEntity user = result.get();
        if (user.getTeam() == null) {
            user.setTeam(new Team(null, null, new ArrayList<>(Arrays.asList(hero))));
        } else {
            user.getTeam().getHeroes().add(hero);
        }
        userService.save(user);

        return modelMapper.map(user.getTeam(), TeamDTO.class);
    }
}
