package com.pedroleon.app.service.impl;

import com.pedroleon.app.domain.Hero;
import com.pedroleon.app.repository.HeroRepository;
import com.pedroleon.app.service.HeroService;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class HeroServiceImpl implements HeroService {

    //private final Logger log = LoggerFactory.getLogger(HeroServiceImpl.class);

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
    public Optional<Hero> partialUpdate(Hero hero) {
        // TODO Auto-generated method stub
        return heroRepository
            .findById(hero.getId())
            .map(existingHero -> {
                if (hero.getName() != null) {
                    existingHero.setName(hero.getName());
                }
                if (hero.getSlug() != null) {
                    existingHero.setSlug(hero.getSlug());
                }
                return existingHero;
            })
            .map(heroRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Hero> findAll(Pageable pageable) {
        return heroRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
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
    public List<Hero> findAll() {
        return heroRepository.findAll();
    }
}
