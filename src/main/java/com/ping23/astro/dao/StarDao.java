package com.ping23.astro.dao;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ping23.astro.dto.Star;

@Component
public class StarDao {
    
    @Autowired StarRepo starRepo;
    
    
    /**
     * Retrieve all stars in the table
     * @return
     */
    public List<Star> findAllStar() {
        return StreamSupport.stream(starRepo.findAll().spliterator(), false).collect(Collectors.toList());
    }
    
    /**
     * Retrieve all stars in the table
     * @return
     */
    public List<Star> findAllStarSortedBy(String sortBy) {
        Comparator<Star> comp;
        if (sortBy.equals("magnitude")) {
            comp = (s1, s2) -> {
                if (s1.getVMag() == null && s2.getVMag() == null) { return 0; }
                else if (s1.getVMag() == null) { return -1; }
                else if (s2.getVMag() == null) { return 1; }
                
                return s1.getVMag().compareTo(s2.getVMag());
            };
        }
        else {
            return findAllStar();
        }
        
        return StreamSupport.stream(starRepo.findAll().spliterator(), false).sorted(comp).collect(Collectors.toList());
    }
    
    /**
     * Find all stars in the table brighter than the given magnitude
     * @param magnitude
     * @return
     */
    public List<Star> findAllStarBrighterThan(Double magnitude) {
        if (magnitude == null) {
            return findAllStar();
        }
        
        return StreamSupport.stream(starRepo.findAll().spliterator(), false).filter(a -> a.getVMag() != null && a.getVMag() < magnitude).collect(Collectors.toList());
    }
    
    /**
     * Create a new star in the table
     * @param star
     * @return
     */
    public Star createStar(Star star) {
        return starRepo.save(star);
    }
    
    /**
     * Delete the Star corresponding to the given id from the table
     * @param id
     */
    public void deleteStarById(Long id) {
        starRepo.deleteById(id);
    }
    
    /**
     * Return the Star corresponding to the given id
     * @param id
     * @return
     */
    public Optional<Star> getStarById(Long id) {
        return starRepo.findById(id);
    }
    
    /**
     * Return a list of stars starting with the given prefix
     * @param prefix
     * @return
     */
    public List<Star> findAllStarStartingWith(String prefix) {
        if (prefix == null) {
            return findAllStar();
        }
        
        return StreamSupport.stream(starRepo.findAll().spliterator(), false).filter(a -> a.getName().toLowerCase().startsWith(prefix.toLowerCase())).collect(Collectors.toList());
    }
 
}
