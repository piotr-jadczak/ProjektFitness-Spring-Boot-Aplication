package app.fitness.FitnessApp.controller.web;

import app.fitness.FitnessApp.domain.Club;
import app.fitness.FitnessApp.domain.Owner;
import app.fitness.FitnessApp.service.ClubManager;
import app.fitness.FitnessApp.service.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class OwnerPanelController {

    private final UserManager userManager;
    private final ClubManager clubManager;


    public OwnerPanelController(@Autowired UserManager userManager,
                                @Autowired ClubManager clubManager) {
        this.userManager = userManager;
        this.clubManager = clubManager;
    }

    @GetMapping("/owner-panel")
    public String viewOwnerPanel(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String ownerLogin = authentication.getName();
        model.addAttribute("loggedUserName", userManager.findOwnerByLogin(ownerLogin).fullName());
        return "owner/owner-panel";
    }

    @GetMapping("/owner-panel/all-clubs")
    public String viewAllObjects(Model model) {
        model.addAttribute("categoryList", clubManager.getAllCategories());
        model.addAttribute("clubList", clubManager.getAllClubs());

        return "owner/all-clubs";
    }

    @GetMapping("/owner-panel/my-clubs")
    public String viewMyObjects(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String ownerLogin = authentication.getName();
        Owner loggedOwner = userManager.findOwnerByLogin(ownerLogin);
        model.addAttribute("myClubsList", clubManager.getAllClubs(loggedOwner));

        return "owner/my-clubs";
    }

    @GetMapping("/owner-panel/add-club")
    public String viewEditForm(Model model) {
        model.addAttribute("categoryList", clubManager.getAllCategories());
        model.addAttribute("clubToAdd", new Club());
        return  "owner/add-club";
    }

    @PostMapping("/owner-panel/my-clubs")
    public String addClub(@ModelAttribute("clubToAdd") @Valid Club clubToAdd, BindingResult bindingResult, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String ownerLogin = authentication.getName();
        Owner loggedOwner = userManager.findOwnerByLogin(ownerLogin);

        if(bindingResult.hasErrors()) {
            model.addAttribute("categoryList", clubManager.getAllCategories());
            return "owner/add-club";
        }

        clubToAdd.setOwner(loggedOwner);
        clubManager.addClub(clubToAdd);
        model.addAttribute("myClubsList", clubManager.getAllClubs(loggedOwner));
        return "owner/my-clubs";
    }
}
