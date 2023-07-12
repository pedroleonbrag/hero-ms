package com.pedroleon.app.web.rest.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pedroleon.app.domain.Appearance;
import com.pedroleon.app.domain.Biography;
import com.pedroleon.app.domain.Hero;
import com.pedroleon.app.domain.Images;
import com.pedroleon.app.domain.Powerstats;
import com.pedroleon.app.domain.Work;
import com.pedroleon.app.service.HeroService;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing {@link com.pedroleon.services.domain.Product}.
 */
@RestController
@RequestMapping("/api/hero")
public class HeroResource {

    private HeroService heroService;

    @Autowired
    public HeroResource(HeroService heroService) {
        this.heroService = heroService;
    }

    @GetMapping(headers = "Accept=application/json")
    public List<Hero> findAll() {
        return heroService.findAll();
    }

    @GetMapping(path = "/{id}", headers = "Accept=application/json")
    public Hero get(@PathVariable Long id) {
        Optional<Hero> result = heroService.findOne(id);
        if (result.isEmpty()) {
            return null;
        }
        return result.get();
    }

    @GetMapping("create")
    public String create() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // Read the JSON file
            InputStream inputStream = HeroResource.class.getResourceAsStream("/heros.json");
            JsonNode rootNode = objectMapper.readTree(inputStream);

            // Iterate over the JSON objects
            if (rootNode.isArray()) {
                List<Hero> heroes = new ArrayList<>();
                for (JsonNode jsonNode : rootNode) {
                    // Process each JSON object
                    Long id = jsonNode.get("id").asLong();
                    String name = jsonNode.get("name").asText();
                    String slug = jsonNode.get("slug").asText();

                    JsonNode powerstatsNode = jsonNode.get("powerstats");
                    Integer intelligence = powerstatsNode.get("intelligence").asInt();
                    Integer strength = powerstatsNode.get("strength").asInt();
                    Integer speed = powerstatsNode.get("speed").asInt();
                    Integer durability = powerstatsNode.get("durability").asInt();
                    Integer power = powerstatsNode.get("power").asInt();
                    Integer combat = powerstatsNode.get("combat").asInt();

                    JsonNode appearenceNode = jsonNode.get("appearance");
                    String gender = appearenceNode.get("gender").asText();
                    String race = appearenceNode.get("race").asText();

                    String height = appearenceNode.get("height").get(1).asText();
                    String weight = appearenceNode.get("weight").get(1).asText();

                    String eyeColor = appearenceNode.get("eyeColor").asText();
                    String hairColor = appearenceNode.get("hairColor").asText();

                    JsonNode biographyNode = jsonNode.get("biography");
                    String fullName = biographyNode.get("fullName").asText();
                    String alterEgos = biographyNode.get("alterEgos").asText();
                    String aliases = biographyNode.get("aliases").get(0).asText();
                    String placeOfBirth = biographyNode.get("placeOfBirth").asText();
                    String firstAppearance = biographyNode.get("firstAppearance").asText();
                    String publisher = biographyNode.get("publisher").asText();
                    String alignment = biographyNode.get("alignment").asText();

                    JsonNode workNode = jsonNode.get("work");
                    String occupation = workNode.get("occupation").asText();
                    String base = workNode.get("base").asText();

                    JsonNode imagesNode = jsonNode.get("images");
                    String xs = imagesNode.get("xs").asText();
                    String sm = imagesNode.get("sm").asText();
                    String md = imagesNode.get("md").asText();
                    String lg = imagesNode.get("lg").asText();

                    Powerstats ps = new Powerstats(null, intelligence, strength, speed, durability, power, combat);
                    Appearance ap = new Appearance(null, gender, race, height, weight, eyeColor, hairColor);
                    Biography bio = new Biography(null, fullName, alterEgos, aliases, placeOfBirth, firstAppearance, publisher, alignment);
                    Work work = new Work(null, occupation, base);
                    Images img = new Images(null, xs, sm, md, lg);

                    Hero hero = new Hero(null, name, slug, ps, ap, bio, work, img);
                    System.out.println(hero);

                    //this.heroRepository.save(hero);
                    heroes.add(hero);
                }

                this.heroService.saveAll(heroes);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "Done";
    }
}
