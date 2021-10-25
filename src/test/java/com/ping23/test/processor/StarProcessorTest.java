package com.ping23.test.processor;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class StarProcessorTest {
    
    @Autowired
    private StarProcessor starProcessor;

    @BeforeEach
    void setUp() throws Exception {
        // empty
    }

    @AfterEach
    void tearDown() throws Exception {
        // empty
    }

    @Test
    void testStarNames() {
        // get star names with no prefix
        final List<String> starNamesNoPrefix = starProcessor.getStarNames(null);
        assertNotNull(starNamesNoPrefix);
        
        // assert that there are MORE THAN 1 starting character
        final Map<Character, List<String>> starNamesNoPrefixMap = starNamesNoPrefix.stream().collect(Collectors.groupingBy(s -> s.charAt(0)));
        assertTrue(starNamesNoPrefixMap.keySet().size() > 1);
        
        // now get star names WITH prefix
        final List<String> starNamesWithPrefix = starProcessor.getStarNames("C");
        assertNotNull(starNamesWithPrefix);
        
        // assert that there are ZERO OR ONE starting characters
        final Map<Character, List<String>> starNamesWithPrefixMap = starNamesWithPrefix.stream().collect(Collectors.groupingBy(s -> s.charAt(0)));
        assertTrue(starNamesWithPrefixMap.keySet().size() <= 1);
    }

}
