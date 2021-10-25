package com.ping23.test.dao;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.ping23.test.dto.Star;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class StarDaoTest {
    
    @Autowired StarDao starDao;
    
    private static Star testStar;

    @BeforeEach
    void setUp() throws Exception {}

    @AfterEach
    void tearDown() throws Exception {}

    @Test
    @Order(1)
    void testFindAll() {
        final List<Star> allStars = starDao.findAllStar();
        assertNotNull(allStars);
        assertTrue(allStars.size() > 0);
    }
    
    @Test
    @Order(2)
    void testCreateStar() {
        final Star newStar = new Star();
        newStar.setName("Test Star").setConstellation("Test Constellation").setVMag(15.3);
        testStar = starDao.createStar(newStar);
        
        assertNotNull(testStar);
        assertNotNull(testStar.getId());
        //System.out.println("testStar.id: " + testStar.getId());
        
    }
    
    @Test
    @Order(3)
    void testGetStarById() {
        assertNotNull(testStar);
        assertTrue(starDao.getStarById(testStar.getId()).isPresent());
        
    }
    
    @Test
    @Order(4)
    void testDeleteStarById() {
        
        starDao.deleteStarById(testStar.getId());
        
        assertFalse(starDao.getStarById(testStar.getId()).isPresent());
    }

}
