package app.fitness.FitnessApp.controller.web;

import app.fitness.FitnessApp.service.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CoachPanelController {

    private final UserManager userManager;

    public CoachPanelController(@Autowired UserManager userManager) {
        this.userManager = userManager;
    }

    @GetMapping("/coach-panel")
    public String viewCoachPanel(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        model.addAttribute("loggedUserLogin", currentPrincipalName);
        return "/coach/coach-panel";
    }
}
