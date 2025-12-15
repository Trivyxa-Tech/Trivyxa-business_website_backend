package com.trivyxa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
public ResponseEntity<?> sendContact(@RequestBody ContactRequest req) {
    try {
        emailService.sendContactMail(req);
        return ResponseEntity.ok("Success");
    } catch (Exception e) {
        e.printStackTrace(); // shows error in Railway logs
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Email sending failed");
    }
}

}
