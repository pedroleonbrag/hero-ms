package com.pedroleon.app.web.rest.controllers;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pedroleon.app.domain.Hero;
import com.pedroleon.app.service.HeroService;
import com.pedroleon.app.web.rest.dto.HeroDTO;
import com.pedroleon.app.web.rest.errors.BadRequestAlertException;
import com.pedroleon.app.web.rest.filters.HeroFilter;

@RestController
@RequestMapping("/api")
public class HeroController {

    private final Logger log = LoggerFactory.getLogger(HeroController.class);

    private HeroService heroService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public HeroController(HeroService heroService) {
        this.heroService = heroService;
    }

    @PostMapping(path = "/hero/find")
    public List<HeroDTO> findAll(@RequestBody(required = false) HeroFilter filter) {
        List<Hero> list = heroService.findAll();
        list = heroService.filter(list, filter);
        return list.stream().map(h -> modelMapper.map(h, HeroDTO.class)).collect(Collectors.toList());
    }

    @GetMapping(path = "/hero/{id}")
    public HeroDTO get(@PathVariable Long id) {
        Optional<Hero> result = heroService.findOne(id);
        if (result.isEmpty()) {
            return null;
        }
        return modelMapper.map(result.get(), HeroDTO.class);
    }

    @PostMapping("/hero")
    public ResponseEntity<HeroDTO> create(@RequestBody Hero hero) throws URISyntaxException {
        log.debug("REST request to save Hero : {}", hero);
        if (hero.getId() != null) {
            throw new BadRequestAlertException("A new hero cannot already have an ID", "Hero", "idexists");
        }
        Hero result = heroService.save(hero);
        return ResponseEntity.status(HttpStatus.OK).body(modelMapper.map(result, HeroDTO.class));
    }
}
