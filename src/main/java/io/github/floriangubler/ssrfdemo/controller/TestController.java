package io.github.floriangubler.ssrfdemo.controller;

import io.github.floriangubler.ssrfdemo.security.SSRFRemoteRessource;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/api/data")
public class TestController {
    private List<SSRFRemoteRessource> allowedURLs = new ArrayList<>();

    public List<SSRFRemoteRessource> getAllowedURLs() {
        return allowedURLs;
    }

    @PostConstruct
    private void fillallowedURLS(){
        SSRFRemoteRessource ressource = new SSRFRemoteRessource();
        ressource.setAllowedHost("localhost");
        ressource.setAllowedPorts(List.of(8080));
        ressource.setAllowedProtocols(List.of("http", "https"));
        ressource.setAllowedPaths(List.of("/api/end/good"));
        ressource.setAllowNoPort(false);
        this.getAllowedURLs().add(ressource);

        SSRFRemoteRessource ressource2 = new SSRFRemoteRessource();
        ressource2.setAllowedHost("google.com");
        ressource2.setAllowedPorts(List.of(80, 443));
        ressource2.setAllowedProtocols(List.of("http", "https"));
        ressource2.setAllowedPaths(List.of(""));
        ressource2.setAllowNoPort(true);
        this.getAllowedURLs().add(ressource2);
    }

    @GetMapping("/wrong")
    public ResponseEntity<String> wrong(@RequestParam String location) {
        try{
            URL url = new URL(location);
            URLConnection connection = url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String body = reader.lines().collect(Collectors.joining());

            return ResponseEntity.ok("Unsecured ressource: " + body);
        } catch(Exception e){
            System.out.println(e.getClass().getCanonicalName());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/good")
    public ResponseEntity<String> good(@RequestParam String location) {
        try{
            URL url = new URL(location);

            //Check Remote Ressource
            for(SSRFRemoteRessource verifier : this.getAllowedURLs()){
                if(verifier.checkURL(url)){
                    //Call API
                    URLConnection connection = url.openConnection();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String body = reader.lines().collect(Collectors.joining());
                    return ResponseEntity.ok("Secured ressource: " + body);
                }
            }

            //Ressource not valid
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Forbidden remote ressource");
        } catch(Exception e){
            System.out.println(e.getClass().getCanonicalName());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
