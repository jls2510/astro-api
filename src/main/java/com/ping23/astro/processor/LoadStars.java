package com.ping23.astro.processor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.ping23.astro.dao.StarDao;
import com.ping23.astro.dto.Star;

@Component
public class LoadStars {
    
    @Autowired
    private StarDao starDao;
    
    private static final String STARS_FILENAME = "iau_star_names.csv";
    
    // TODO create constants for line indexes
    private static final Function<String[], Star> starMapper = sa -> {
        return new Star()
                .setName(sa[0])
                .setCatalog(sa[1])
                .setCatalogId(sa[2])
                .setConstellationId(sa[3])
                .setConstellation(sa[4])
                .setBinaryClass(sa[5])
                .setVMag(safeParseDouble(sa[7]))
                .setHip(sa[8])
                .setRa(safeParseDouble(sa[10]))
                .setDec(safeParseDouble(sa[11]));
        };
    
    public List<Star> loadStarsIntoDB() {
        
        List<Star> createdStars = new ArrayList<>();
        
        InputStream is = null;
        try {
            is = new ClassPathResource(STARS_FILENAME).getInputStream();
        }
        catch (IOException e1) {
            e1.printStackTrace();
        }
        
        try(BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            // read lines from file, parse into Star objects
            final List<Star> starsFromFile = br.lines().map(line -> line.split(",")).map(starMapper).collect(Collectors.toList());
            
            // note that we read and create all Star objects before inserting into db - failure to read into objects will fail before db is changed
            
            // create Stars in db, return created Stars
            createdStars = starsFromFile.stream().map(star -> starDao.createStar(star)).collect(Collectors.toList());
            
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        
        return createdStars;
        
    }
    
    /**
     * Return a Double, or null
     * @param value
     * @return
     */
    private static Double safeParseDouble(String value) {
        Double returnValue = null;
        try {
            returnValue = Double.parseDouble(value);
        } catch(Exception e) {
            // fall through
        }
        
        return returnValue;
    }

}
