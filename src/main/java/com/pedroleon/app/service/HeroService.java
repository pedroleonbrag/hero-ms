package com.pedroleon.app.service;

import com.pedroleon.app.domain.Hero;
import com.pedroleon.app.web.rest.filters.HeroFilter;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HeroService {
	Hero save(Hero hero);

	Hero update(Hero hero);

	Optional<Hero> partialUpdate(Hero hero);

	Page<Hero> findAll(Pageable pageable);

	Optional<Hero> findOne(Long id);

	void delete(Long id);

	void saveAll(List<Hero> heroes);

	List<Hero> findAll();

	List<Hero> filter(List<Hero> list, HeroFilter filter);
}
