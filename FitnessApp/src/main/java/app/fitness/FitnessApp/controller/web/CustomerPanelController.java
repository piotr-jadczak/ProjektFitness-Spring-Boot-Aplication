package app.fitness.FitnessApp.controller.web;

import app.fitness.FitnessApp.repository.CustomerRepository;
import app.fitness.FitnessApp.service.UserManager;
import app.fitness.FitnessApp.service.UserManagerImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CustomerPanelController {

    private final UserManager userManager;

    public CustomerPanelController(@Autowired UserManager userManager) {
        this.userManager = userManager;
    }

    @GetMapping("/customer-panel")
    public String viewCustomerPanel(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        model.addAttribute("loggedUserLogin", currentPrincipalName);
        return "customer/customer-panel";
    }
}
