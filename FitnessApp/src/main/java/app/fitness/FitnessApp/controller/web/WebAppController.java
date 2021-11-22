package app.fitness.FitnessApp.controller.web;

import app.fitness.FitnessApp.domain.BaseUserLogin;
import app.fitness.FitnessApp.repository.CustomerRepository;
import app.fitness.FitnessApp.service.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class WebAppController {

    private final UserManager userManager;

    public WebAppController(@Autowired UserManager userManager) { this.userManager = userManager; }

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
        userManager.addUser(user);
        return "register_success";
    }
}
