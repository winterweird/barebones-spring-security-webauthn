package no.difi.webauthn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PostMapping;

// test
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.webauthn4j.client.challenge.Challenge;

//import no.difi.webauthn.domain.User;
//import no.difi.webauthn.authorization.ChallengeRepository;
//import no.difi.webauthn.authorization.HttpSessionChallengeRepository;

@Controller
public class MainController {
    @GetMapping("/")
    public String index() {
        return "home";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/user")
    public String user() {
        return "user";
    }

    /*
    @GetMapping
    @ResponseBody
    public Challenge getChallenge(HttpServletRequest request, HttpServletResponse response) {
        ChallengeRepository challengeRepository = new HttpSessionChallengeRepository();
        Challenge challenge = challengeRepository.generateChallenge();
        challengeRepository.saveChallenge(challenge, request, response);
        
        return challenge;
    }
    */
}
