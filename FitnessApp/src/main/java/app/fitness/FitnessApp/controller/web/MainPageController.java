package app.fitness.FitnessApp.controller.web;

import app.fitness.FitnessApp.login.BaseUserLogin;
import app.fitness.FitnessApp.repository.CustomerRepository;
import app.fitness.FitnessApp.service.UserManager;
import app.fitness.FitnessApp.service.UserManagerImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MainPageController {

    private final UserManager userManager;

    public MainPageController(@Autowired UserManager userManager) { this.userManager = userManager; }

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("")
    public String viewHomePage() {
        return "index";
    }

    @GetMapping("/register")
    public String viewRegistrationForm(Model model) {
        model.addAttribute("user", new BaseUserLogin());

        return "register";
    }

    @PostMapping("/process_register")
    public String processRegister(BaseUserLogin user) {
        userManager.addUser(user);
        return "register_success";
    }

    @GetMapping("/login")
    public String viewLoginPage() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || authentication instanceof AnonymousAuthenticationToken)
            return "login";

        return "redirect:/success_login_redirect";
    }

    @GetMapping("/success_login_redirect")
    public String redirectToPanelPage() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String role = userManager.getAuthorityName(authentication);
        switch (role) {
            case "ROLE_CUSTOMER" :
                return "redirect:/customer_panel";
            case "ROLE_COACH" :
                return "redirect:/coach_panel";
            case "ROLE_OWNER" :
                return "redirect:/owner_panel";
        }

        return "redirect:/error_login";
    }

    @GetMapping("/logout")
    public String logoutUser() {
        SecurityContextHolder.clearContext();
        return "index";
    }

}
