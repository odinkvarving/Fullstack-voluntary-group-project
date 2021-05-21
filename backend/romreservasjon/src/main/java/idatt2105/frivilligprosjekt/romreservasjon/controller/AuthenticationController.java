package idatt2105.frivilligprosjekt.romreservasjon.controller;


import idatt2105.frivilligprosjekt.romreservasjon.model.AuthenticationRequest;
import idatt2105.frivilligprosjekt.romreservasjon.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    /**
     * PostMapping for sending an AuthenticationRequest
     * Authenticates a user that tries to log in
     *
     * @param authenticationRequest requests the body of an AuthenticationRequest
     * @return an AuthenticationToken if the request is valid
     * @throws Exception the exception that is thrown
     */
    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception{
        return authenticationService.createAuthenticationToken(authenticationRequest);
    }
}
