package ua.glumaks.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.glumaks.dto.MailParams;
import ua.glumaks.service.MailService;

@RestController
@RequestMapping("/mail")
@RequiredArgsConstructor
public class MainController {

    private final MailService mailService;

    @PostMapping
    @RequestMapping("/activate")
    ResponseEntity<?> sendActivationEmail(@RequestBody MailParams mailParams) {
        mailService.send(mailParams);
        return ResponseEntity.ok().build();
    }
}
