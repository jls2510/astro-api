package com.ping23.test.dao;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import com.ping23.test.dto.Star;

public interface StarRepo extends CrudRepository<Star, Long> {
    
    Optional<Star> findByName(String name);
    
}
