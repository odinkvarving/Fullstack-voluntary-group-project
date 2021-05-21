package idatt2105.frivilligprosjekt.romreservasjon.controller;


import idatt2105.frivilligprosjekt.romreservasjon.model.AuthenticationRequest;
import idatt2105.frivilligprosjekt.romreservasjon.service.AuthenticationService;
import idatt2105.frivilligprosjekt.romreservasjon.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    /**
     * Authenticates a user that tries to login and returns a token if it is valid
     *
     * @param authenticationRequest
     * @return
     * @throws Exception
     */
    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception{
        return authenticationService.createAuthenticationToken(authenticationRequest);
    }
}
