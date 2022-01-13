package com.ping23.crypt.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ping23.crypt.dto.MessageDTO;
import com.ping23.crypt.processor.RSAEncryptDecrypt;

@RestController
@RequestMapping(value="/rsa-decrypt", produces="application/json;charset=UTF-8")
public class RSADecryptController {

    public final static String privateKeyFilename = "/encryption/rsa-private-key.pem";

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
                    RSAEncryptDecrypt.decryptTextWithPemFile(encryptedMessage, privateKeyFilename);
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
