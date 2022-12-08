package io.github.floriangubler.ssrfdemo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/end")
public class EndController {

    @GetMapping("/bad")
    public ResponseEntity<String> bad() {
        return ResponseEntity.ok("This endpoint is unsecure");
    }

    @GetMapping("/good")
    public ResponseEntity<String> good() {
        return ResponseEntity.ok("This endpoint is secure");
    }
}
