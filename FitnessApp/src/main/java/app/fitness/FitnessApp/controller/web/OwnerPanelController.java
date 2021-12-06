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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String ownerLogin = authentication.getName();
        model.addAttribute("categoryList", clubManager.getAllCategories());
        model.addAttribute("clubToAdd", new Club(userManager.findOwnerByLogin(ownerLogin)));
        return  "owner/add-club";
    }

    @PostMapping("owner-panel/my-clubs")
    public ModelAndView addClub(@ModelAttribute("clubToAdd") Club clubToAdd, Model model) {
        clubManager.addClub(clubToAdd);

        return new ModelAndView("redirect:/owner-panel/my-clubs");
    }
}
