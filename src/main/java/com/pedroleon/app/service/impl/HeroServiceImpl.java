package com.pedroleon.app.service.impl;

import com.pedroleon.app.domain.Hero;
import com.pedroleon.app.repository.HeroRepository;
import com.pedroleon.app.service.HeroService;
import com.pedroleon.app.web.rest.filters.HeroFilter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class HeroServiceImpl implements HeroService {

    // private final Logger log = LoggerFactory.getLogger(HeroServiceImpl.class);

    private final HeroRepository heroRepository;

    public HeroServiceImpl(HeroRepository heroRepository) {
        this.heroRepository = heroRepository;
    }

    @Override
    public Hero save(Hero hero) {
        return heroRepository.save(hero);
    }

    @Override
    public Hero update(Hero hero) {
        return heroRepository.save(hero);
    }

    @Override
    public Page<Hero> findAll(Pageable pageable) {
        return heroRepository.findAll(pageable);
    }

    @Override
    public Optional<Hero> findOne(Long id) {
        return heroRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        heroRepository.deleteById(id);
    }

    @Override
    public void saveAll(List<Hero> heroes) {
        heroRepository.saveAll(heroes);
    }

    @Override
    @Cacheable("findAll")
    public List<Hero> findAll() {
        return heroRepository.findAll();
    }

    @Override
    public Optional<Hero> partialUpdate(Hero hero) {
        return Optional.empty();
    }

    public List<Hero> filter(List<Hero> heroes, HeroFilter filter) {
        if (filter == null) return heroes;
        if (filter.getName() != null && !"".equals(filter.getName().trim())) {
            heroes =
                heroes
                    .stream()
                    .filter(hero -> hero.getName().toLowerCase().contains(filter.getName().toLowerCase()))
                    .collect(Collectors.toList());
        }
        if (filter.getRace() != null && !"".equals(filter.getRace().trim())) {
            heroes = heroes.stream().filter(hero -> hero.getAppearance().getRace().contains(filter.getRace())).collect(Collectors.toList());
        }
        if (filter.getAlignment() != null) {
            heroes =
                heroes
                    .stream()
                    .filter(hero -> hero.getBiography().getAlignment().equals(filter.getAlignment()))
                    .collect(Collectors.toList());
        }
        return heroes;
    }
}
