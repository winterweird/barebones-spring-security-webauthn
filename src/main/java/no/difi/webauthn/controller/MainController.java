package no.difi.webauthn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.provisioning.UserDetailsManager;

import com.webauthn4j.client.challenge.DefaultChallenge;
import com.webauthn4j.util.Base64UrlUtil;

// test
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.webauthn4j.client.challenge.Challenge;

import java.util.UUID;
import no.difi.webauthn.util.UUIDUtil;
import no.difi.webauthn.model.WebAuthnUser;

@Controller
public class MainController {
    @Autowired
    UserDetailsManager udm;
    
    @GetMapping("/")
    public String index() {
        return "home";
    }

    @GetMapping("/login")
    public String getLogin() {
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        addChallenge(model);
        
        // create a user handle
        UUID userHandle = UUID.randomUUID();
        String userHandleStr = UUIDUtil.base64UrlString(userHandle);
        
        model.addAttribute("userHandle", userHandle);
        return "register";
    }

    @RequestMapping(value="/registerUser", method=RequestMethod.POST, consumes=MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public RedirectView registerUser(@RequestBody MultiValueMap<String,String> userReg) {
        udm.createUser(WebAuthnUser.webAuthnUserBuilder()
                .username(userReg.get("username").get(0))
                .password(userReg.get("password").get(0))
                .credentialId(userReg.get("credentialId").get(0).getBytes())
                .roles("USER")
                .build());
        return new RedirectView("/login");
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
        addChallenge(model);
        
        // Test
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("principal: " + auth.getPrincipal());
        System.out.println("credentials: " + auth.getCredentials());
        
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
    private void addChallenge(Model model) {
        Challenge ch = new DefaultChallenge();
        String chAsBase64UrlString = Base64UrlUtil.encodeToString(ch.getValue());
        
        // Debug output
        System.out.println("GENERATED CHALLENGE: " + chAsBase64UrlString);
        
        model.addAttribute("_challenge", chAsBase64UrlString);
    }
}
