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
@RequestMapping(value="/aes-encrypt", produces="application/json;charset=UTF-8")
public class AESEncryptController {

  private final static String AESKey = properties.getProperty("AES_KEY");

  @GetMapping("/")
  @ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED, reason="This endpoint accepts only POST requests.")
  protected void doGet() {
  }

    /**
     * 
     */
  @PostMapping("/")
  protected ResponseEntity<String> doPost(@RequestBody MessageDTO messageDto) {

        String message = messageDto.getMessage();

        if (message == null || message.isEmpty()) {
            return new ResponseEntity<String>("No message supplied.", HttpStatus.NOT_ACCEPTABLE);
        }

        String encryptedMessage = "";
        try {
            encryptedMessage = AESEncryptDecrypt.encryptToString(message, AESKey);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>("Could not complete request.", HttpStatus.BAD_REQUEST);
        }

        StringBuilder responseBuilder = new StringBuilder();
        
        responseBuilder.append("original message: " + message).append("\n");
        responseBuilder.append("encrypted message: " + encryptedMessage).append("\n");

        return new ResponseEntity<String>(responseBuilder.toString(), HttpStatus.OK);
    }

}
