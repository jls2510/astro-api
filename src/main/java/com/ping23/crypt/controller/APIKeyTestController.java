package com.ping23.crypt.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ping23.crypt.util.Authenticate;

@RestController
@RequestMapping(value="/api-key-test", produces="application/json;charset=UTF-8")
public class APIKeyTestController {

	@GetMapping("/")
    @ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED, reason="This endpoint accepts only POST requests.")
    protected void doGet() {
    }

    /**
     * 
     */
    @PostMapping("/")
    protected ResponseEntity<String> doPost(@RequestHeader Map<String, String> headers) {

        String apiKey = headers.get("x-api-key");
        if (!Authenticate.authenticate(apiKey)) {
        	return new ResponseEntity<String>("Incorrect or missing API Key provided. Access denied.", HttpStatus.UNAUTHORIZED);
        }

        StringBuilder responseBuilder = new StringBuilder();
        
        responseBuilder.append("API Key successfully authenticated.").append("\n");
        responseBuilder.append("header: x-api-key: " + apiKey).append("\n");
    
        return new ResponseEntity<String>(responseBuilder.toString(), HttpStatus.OK);
}

}
