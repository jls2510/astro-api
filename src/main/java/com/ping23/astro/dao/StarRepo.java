package com.ping23.astro.dao;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

import com.ping23.astro.dto.Star;

public interface StarRepo extends CrudRepository<Star, Long> {
    
    Optional<Star> findByName(String name);
    
}
