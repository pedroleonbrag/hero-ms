package com.pedroleon.app.service;

import com.pedroleon.app.domain.Hero;
import com.pedroleon.app.web.rest.filters.HeroFilter;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Product}.
 */
public interface HeroService {
    /**
     * Save a product.
     *
     * @param product the entity to save.
     * @return the persisted entity.
     */
    Hero save(Hero hero);

    /**
     * Updates a product.
     *
     * @param product the entity to update.
     * @return the persisted entity.
     */
    Hero update(Hero hero);

    /**
     * Partially updates a product.
     *
     * @param product the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Hero> partialUpdate(Hero hero);

    /**
     * Get all the products.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Hero> findAll(Pageable pageable);

    /**
     * Get the "id" product.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Hero> findOne(Long id);

    /**
     * Delete the "id" product.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    void saveAll(List<Hero> heroes);

    List<Hero> findAll();

    List<Hero> filter(List<Hero> list, HeroFilter filter);
}
