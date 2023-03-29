package ua.glumaks.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.glumaks.service.AppUserService;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsersController {

    private final AppUserService userService;


    @GetMapping("/activate")
    ResponseEntity<?> activate(@RequestParam String id) {
        return userService.activate(id)?
                ResponseEntity.ok().build():
                ResponseEntity.internalServerError().build();
    }

}
