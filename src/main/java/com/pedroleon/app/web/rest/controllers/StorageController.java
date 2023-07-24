package com.pedroleon.app.web.rest.controllers;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.pedroleon.app.config.ApplicationProperties;
import com.pedroleon.app.domain.Hero;
import com.pedroleon.app.domain.Images;
import com.pedroleon.app.service.HeroService;
import com.pedroleon.app.service.StorageService;
import com.pedroleon.app.web.rest.dto.HeroDTO;

@RestController
@RequestMapping("/api/file")
public class StorageController {

	@Autowired
	private StorageService storageService;

	@Autowired
	private HeroService heroService;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private ApplicationProperties properties;

	@PostMapping("/upload")
	public ResponseEntity<HeroDTO> uploadFile(@RequestParam(value = "file") MultipartFile file,
			@RequestParam(value = "id") Long id) {
		String fileName = storageService.uploadFile(file);
		Optional<Hero> result = heroService.findOne(id);
		if (result.isEmpty()) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}

		Hero hero = result.get();
		String fileUrl = properties.getBaseUrl().concat(fileName);
		hero.setImages(new Images(null, fileUrl, fileUrl, fileUrl, fileUrl));
		heroService.save(hero);
		return new ResponseEntity<>(modelMapper.map(hero, HeroDTO.class), HttpStatus.OK);

	}
}
