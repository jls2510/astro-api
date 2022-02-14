package com.ping23.crypt.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ping23.crypt.dto.MessageDTO;

@RestController
@RequestMapping(value="/bcrypt-hash", produces="application/json;charset=UTF-8")
public class BCryptHashController {

	@GetMapping("/")
    @ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED, reason="This endpoint accepts only POST requests.")
    protected void doGet() {
    }

    /**
     * 
     */
    @PostMapping("/")
    protected ResponseEntity<String> doPost(@RequestHeader Map<String, String> headers, @RequestBody MessageDTO messageDto) {

        String cleartextPassword = messageDto.getMessage();

        if (cleartextPassword == null || cleartextPassword.isEmpty()) {
        	return new ResponseEntity<String>("No cleartextPassword supplied.", HttpStatus.BAD_REQUEST);
        }

        String hashedPassword = "";
        try {
            hashedPassword = BCrypt.hashpw(cleartextPassword, BCrypt.gensalt());
        } catch (Exception e) {
            return new ResponseEntity<String>("Could not complete request.", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        StringBuilder responseBuilder = new StringBuilder();
        
        responseBuilder.append("cleartextPassword: " + cleartextPassword).append("\n");
        responseBuilder.append("hashedPassword: " + hashedPassword).append("\n");
        
        return new ResponseEntity<String>(responseBuilder.toString(), HttpStatus.OK);
    }

}
