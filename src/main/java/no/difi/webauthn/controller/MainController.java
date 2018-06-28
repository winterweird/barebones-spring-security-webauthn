package no.difi.webauthn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;

import com.webauthn4j.client.challenge.DefaultChallenge;
import com.webauthn4j.util.Base64UrlUtil;

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
    public String getLogin() {
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

    // this needs to be a post mapping because it's redirected from a post-to-login
    // form submission
    @PostMapping("/authenticate")
    public String authenticate(Model model) {
        // TODO: do the thing
        Challenge ch = new DefaultChallenge();
        
        System.out.println("Challenge in base64url: " +
                Base64UrlUtil.encodeToString(ch.getValue()));
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        System.out.println("principal: " + auth.getPrincipal());
        System.out.println("credentials: " + auth.getCredentials());
        model.addAttribute("_challenge", Base64UrlUtil.encodeToString(ch.getValue()));
        return "authenticate";
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
