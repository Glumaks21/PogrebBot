package ua.glumaks.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.glumaks.dto.MailParams;
import ua.glumaks.service.MailService;

@RestController
@RequestMapping(
        consumes = "application/json",
        produces = "application/json"
)
@RequiredArgsConstructor
@CrossOrigin("localhost:8003")
public class MailController {

    private final MailService mailService;


    @PostMapping
    @RequestMapping("/send")
    ResponseEntity<String> send(@RequestBody MailParams mailParams) {
        mailService.send(mailParams);
        return ResponseEntity.ok().build();
    }

}
