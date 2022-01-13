package com.ping23.crypt.controller;

import static com.ping23.crypt.util.AppProperties.properties;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ping23.crypt.dto.MessageDTO;
import com.ping23.crypt.processor.AESEncryptDecrypt;

@RestController
@RequestMapping(value="/aes-decrypt", produces="application/json;charset=UTF-8")
public class AESDecryptController {

    private final static String AESKey = properties.getProperty("AES_KEY");

    @GetMapping("/")
    @ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED, reason="This endpoint accepts only POST requests.")
    protected void doGet() {
    }

    @PostMapping("/")
    protected ResponseEntity<String> doPost(@RequestBody MessageDTO messageDto) {

        String encryptedMessage = messageDto.getMessage();
        
        if (encryptedMessage == null || encryptedMessage.isEmpty()) {
            return new ResponseEntity<String>("No encrypted message supplied.", HttpStatus.NOT_ACCEPTABLE);
        }

        String decryptedMessage = "";
        try {
            decryptedMessage =
                    AESEncryptDecrypt.decryptFromString(encryptedMessage, AESKey);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>("Could not complete request.", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        StringBuilder responseBuilder = new StringBuilder();
        
        responseBuilder.append("encrypted message: " + encryptedMessage).append("\n");
        responseBuilder.append("decrypted message: " + decryptedMessage).append("\n");
        
        return new ResponseEntity<String>(responseBuilder.toString(), HttpStatus.OK);
    }

}
