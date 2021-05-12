package idatt2105.frivilligprosjekt.romreservasjon.controller;


import idatt2105.frivilligprosjekt.romreservasjon.model.AuthenticationRequest;
import idatt2105.frivilligprosjekt.romreservasjon.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception{
        return authenticationService.createAuthenticationToken(authenticationRequest);
    }
}
