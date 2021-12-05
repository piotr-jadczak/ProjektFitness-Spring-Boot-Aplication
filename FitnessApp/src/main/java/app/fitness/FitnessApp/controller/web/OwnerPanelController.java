package app.fitness.FitnessApp.controller.web;

import app.fitness.FitnessApp.service.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OwnerPanelController {

    private final UserManager userManager;

    public OwnerPanelController(@Autowired UserManager userManager) {
        this.userManager = userManager;
    }

    @GetMapping("/owner-panel")
    public String viewOwnerPanel(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        model.addAttribute("loggedUserLogin", currentPrincipalName);
        return "owner/owner-panel";
    }
}
