package com.ping23.astro.dto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;
import lombok.experimental.Accessors;

@Entity
@Data
@Accessors(chain = true)
public class Star {
    
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    
    private String catalog;
    
    private String catalogId;
    
    private String constellation;
    
    private String constellationId;
    
    private Double vMag;
    
    private String binaryClass;
    
    private String hip;
    
    private Double ra;
    
    private Double dec;

}
