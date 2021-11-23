package app.fitness.FitnessApp.controller.web;

import app.fitness.FitnessApp.domain.login.BaseUserLogin;
import app.fitness.FitnessApp.repository.CustomerRepository;
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
public class WebAppController {

    private final UserManagerImp userManagerImp;

    public WebAppController(@Autowired UserManagerImp userManagerImp) { this.userManagerImp = userManagerImp; }

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("")
    public String viewHomePage() {
        return "index";
    }

    @GetMapping("/register")
    public String viewRegistrationForm(Model model) {
        model.addAttribute("user", new BaseUserLogin());

        return "signup_form";
    }

    @PostMapping("/process_register")
    public String processRegister(BaseUserLogin user) {
        userManagerImp.addUser(user);
        return "register_success";
    }

    @GetMapping("/login")
    public String viewLoginPage() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || authentication instanceof AnonymousAuthenticationToken)
            return "login";

        return "redirect:/";
    }

    @GetMapping("/customer_panel")
    public String viewCustomerPanel(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        model.addAttribute("logged_customer", currentPrincipalName);
        return "customer_panel";
    }
}
