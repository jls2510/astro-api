package com.ping23.test.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ping23.test.dao.StarDao;
import com.ping23.test.dto.Star;
import com.ping23.test.processor.LoadStars;
import com.ping23.test.processor.StarProcessor;

@RestController
public class StarController {
    
    @Autowired StarDao starDao;
    @Autowired StarProcessor starProcessor;
    @Autowired LoadStars loadStars;
    
    @GetMapping("/")
    public String index() {
        return "Star API";
    }
    
    @GetMapping("/star")
    public List<Star> getAllStar() {
        return starDao.findAllStar();
    }
    
    @GetMapping("/star/grouped")
    public Map<Character, List<Star>> getStarsGrouped() {
        return starProcessor.getStarsGrouped();
    }
    
    @GetMapping("/star/star-names/grouped")
    public Map<Character, List<String>> getStarNamesGrouped() {
        return starProcessor.getStarNamesGrouped();
    }
    
    @GetMapping("/star/star-names/grouped/flattened")
    public List<String> getStarNamesGroupedFlattened() {
        return starProcessor.getStarNamesGroupedFlattened();
    }
    
    @GetMapping("/star/star-names")
    public List<String> starNames(@RequestParam(required = false) String prefix) {
        return starProcessor.getStarNames(prefix);
    }
    
    @GetMapping("/star/optionals")
    public List<String> optionals() {
        List<Optional<Star>> someStarOptionals = starProcessor.getSomeStarOptionals();
        
        // build a list of star names, with "not found" where appropriate
        List<String> results = someStarOptionals.stream().map(starOptional -> starOptional.map(Star::getName)).map(stringOptional -> stringOptional.orElseGet(() -> "not found")).collect(Collectors.toList());
                
        return results;
    }
    
    @GetMapping("/star/brighter-than")
    public List<Star> getAllStarBrighterThan(@RequestParam(required = false) Double magnitude) {
        return starDao.findAllStarBrighterThan(magnitude);
    }
    
    @GetMapping("/star/starting-with")
    public List<Star> findAllStarStartingWith(@RequestParam(required = false) String prefix) {
        
        return starDao.findAllStarStartingWith(prefix);
    }
    
    @GetMapping("/star/sorted")
    public List<Star> getAllStarSortedBy(@RequestParam(required = false) String sortBy) {
        return starDao.findAllStarSortedBy(sortBy);
    }
    
    @PostMapping("/star")
    public Star createStar(@RequestBody Star star) {
        return starDao.createStar(star);
    }
   
    @DeleteMapping("/star/{starId}")
    public void deleteStarById(@PathVariable(required = true) Long starId) {
        starDao.deleteStarById(starId);
    }
    
    @GetMapping("/star/load-stars")
    public List<Star> loadStars() {
        return loadStars.loadStarsIntoDB();
    }
    
}
