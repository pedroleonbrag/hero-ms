package com.pedroleon.app.web.rest.controllers;

import com.pedroleon.app.config.ApplicationProperties;
import com.pedroleon.app.domain.Hero;
import com.pedroleon.app.domain.Images;
import com.pedroleon.app.service.HeroService;
import com.pedroleon.app.service.StorageService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/file")
public class StorageController {

    @Autowired
    private StorageService service;

    @Autowired
    private HeroService heroService;

    @Autowired
    private ApplicationProperties properties;

    @PostMapping("/upload")
    public ResponseEntity<Hero> uploadFile(@RequestParam(value = "file") MultipartFile file, @RequestParam(value = "id") Long id) {
        String fileName = service.uploadFile(file);
        Optional<Hero> result = heroService.findOne(id);
        if (result.isPresent()) {
            Hero hero = result.get();
            String fileUrl = properties.getBaseUrl().concat(fileName);
            hero.setImages(new Images(null, fileUrl, fileUrl, fileUrl, fileUrl));
            heroService.save(hero);
            return new ResponseEntity<>(hero, HttpStatus.OK);
        }

        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable String fileName) {
        byte[] data = service.downloadFile(fileName);
        ByteArrayResource resource = new ByteArrayResource(data);
        return ResponseEntity
            .ok()
            .contentLength(data.length)
            .header("Content-type", "application/octet-stream")
            .header("Content-disposition", "attachment; filename=\"" + fileName + "\"")
            .body(resource);
    }

    @DeleteMapping("/delete/{fileName}")
    public ResponseEntity<String> deleteFile(@PathVariable String fileName) {
        return new ResponseEntity<>(service.deleteFile(fileName), HttpStatus.OK);
    }
}
