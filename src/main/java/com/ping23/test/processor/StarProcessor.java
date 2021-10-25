package com.ping23.test.processor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.ping23.test.dao.StarDao;
import com.ping23.test.dto.Star;

@Component
public class StarProcessor {
    
    @Autowired
    private StarDao starDao;
    
    // do not instantiate
    private StarProcessor() {}
    
    /**
     * Get Star Names (UPPERCASE) with optional filter prefix
     * @param prefix
     * @return
     */
    public List<String> getStarNames(String prefix) {
        final List<Star> stars = starDao.findAllStarStartingWith(prefix);
        
        // sort in reverse alpha order
        final Comparator<String> reverseAlphaOrderComp = (a,b) -> - a.compareTo(b);
        
        // first print on console
        stars.stream().map(s -> s.getName().toUpperCase()).sorted(reverseAlphaOrderComp).forEach(System.out::println);
        
        // now build return list
        final List<String> starNames = stars.stream().map(s -> s.getName().toUpperCase()).sorted(reverseAlphaOrderComp).collect(Collectors.toList());
        
        return starNames;
    }
    
    /**
     * Get some star optionals
     * (for testing implementation will depend upon use case)
     * @return
     */
    public List<Optional<Star>> getSomeStarOptionals() {
        
        final List<Optional<Star>> someStarOptionals = new ArrayList<>();
        for(int i = 0; i < 20; ++i) {
            long starId = new Random().nextInt(1000);
            someStarOptionals.add(starDao.getStarById(starId));
        }
        
        return someStarOptionals;
    }
    
    /**
     * get star names grouped by first letter
     * @return
     */
    public Map<Character, List<String>> getStarNamesGrouped() {
        return getStarNames(null).stream().collect(Collectors.groupingBy(s -> s.charAt(0)));
    }

    /**
     * get star names grouped by first letter, flattened to single list
     * @return
     */
    public List<String> getStarNamesGroupedFlattened() {
        //return getStarNames(null).stream().collect(Collectors.groupingBy(s -> s.charAt(0)));
        Map<Character, List<Star>> starsGrouped = getStarsGrouped();
        return starsGrouped.values().stream().flatMap(Collection::stream).map(star -> star.getName()).collect(Collectors.toList());
    }

    /**
     * get Stars grouped by first letter of named
     * @return
     */
    public Map<Character, List<Star>> getStarsGrouped() {
        //return getStarNames(null).stream().collect(Collectors.groupingBy(s -> s.charAt(0)));
        return starDao.findAllStar().stream().collect(Collectors.groupingBy(star -> star.getName().charAt(0)));
    }

}
