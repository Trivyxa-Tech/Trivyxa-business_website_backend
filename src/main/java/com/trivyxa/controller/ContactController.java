package com.trivyxa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.trivyxa.dto.ContactRequest;
import com.trivyxa.service.EmailService;

@RestController
@RequestMapping("/api/contact")
@CrossOrigin(origins = {
    "https://trivyxa-businesswebsite-production-5766.up.railway.app"
})
public class ContactController {

    @Autowired
    private EmailService emailService;

    @PostMapping
    public ResponseEntity<String> sendContact(@RequestBody ContactRequest req) {
        try {
            emailService.sendContactMail(req);
            return ResponseEntity.ok("Success");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Email sending failed");
        }
    }
}
