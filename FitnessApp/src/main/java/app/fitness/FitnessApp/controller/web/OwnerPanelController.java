package app.fitness.FitnessApp.controller.web;

import app.fitness.FitnessApp.domain.Club;
import app.fitness.FitnessApp.domain.Coach;
import app.fitness.FitnessApp.domain.Customer;
import app.fitness.FitnessApp.domain.Owner;
import app.fitness.FitnessApp.domain.extra.PasswordForm;
import app.fitness.FitnessApp.domain.extra.ProfileForm;
import app.fitness.FitnessApp.exception.WrongOldPasswordException;
import app.fitness.FitnessApp.service.ClubManager;
import app.fitness.FitnessApp.service.UserManager;
import app.fitness.FitnessApp.validators.UniqueLoginValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.criteria.CriteriaBuilder;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class OwnerPanelController {

    private static Logger logger = LoggerFactory.getLogger(OwnerPanelController.class);

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

    @GetMapping("/owner-panel/add-club/{id}")
    public String viewEditForm(@PathVariable String id, Model model) {
        int entityID = Integer.parseInt(id);
        if(clubManager.isClubInDB(entityID)) {
            String ownerLogin = SecurityContextHolder.getContext().getAuthentication().getName();
            Club clubToEdit = clubManager.getClub(entityID);
            if(userManager.findOwnerByLogin(ownerLogin).getId() == clubToEdit.getOwner().getId()) {
                model.addAttribute("clubToAdd", clubToEdit);
            }
            else {
                return "error/403";
            }
        }
        else {
            model.addAttribute("clubToAdd", new Club());
        }
        model.addAttribute("categoryList", clubManager.getAllCategories());
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

        if(clubManager.isClubInDB(clubToAdd.getId())) {
            clubManager.updateClub(clubToAdd);
        }
        else {
            clubToAdd.setOwner(loggedOwner);
            clubManager.addClub(clubToAdd);
        }

        model.addAttribute("myClubsList", clubManager.getAllClubs(loggedOwner));
        return "owner/my-clubs";
    }

    @GetMapping("/owner-panel/delete-club/{id}")
    public ModelAndView deleteClub(@PathVariable String id) {
        int entityID = Integer.parseInt(id);
        clubManager.deleteClub(entityID);

        return new ModelAndView("redirect:/owner-panel/my-clubs");
    }

    @GetMapping("/owner-panel/add-coaches/{id}")
    public String viewCoachesForm(@PathVariable String id, Model model) {

        List<Coach> allCoaches = clubManager.getAllCoaches().collect(Collectors.toList());
        List<Coach> presentCoaches = clubManager.getAllCoachesInClub(clubManager.getClub(Integer.parseInt(id))).collect(Collectors.toList());
        allCoaches.removeAll(presentCoaches);

        model.addAttribute("availableCoaches", allCoaches);
        model.addAttribute("presentCoaches", presentCoaches);
        model.addAttribute("currentClub", clubManager.getClub(Integer.parseInt(id)));

        return "owner/add-coaches";
    }

    @GetMapping("/owner-panel/add-coaches/{clubId}/{coachId}")
    public ModelAndView addCoachToClub(@PathVariable("clubId") String clubId, @PathVariable("coachId") String coachId, Model model) {
        //authentication
        String loggedUserLogin = SecurityContextHolder.getContext().getAuthentication().getName();
        if(!loggedUserLogin.equals(clubManager.getClub(Integer.parseInt(clubId)).getOwner().getLogin()))
            return  new ModelAndView("redirect:/error");
        //end authentication
        Coach coach = userManager.findCoachById(Integer.parseInt(coachId));
        Club club = clubManager.getClub(Integer.parseInt(clubId));
        logger.info(coach.getLogin());
        logger.info(club.getName());
        clubManager.addCoachToClub(coach, club);

        return new ModelAndView("redirect:/owner-panel/add-coaches/{clubId}");
    }

    @GetMapping("/owner-panel/delete-coaches/{clubId}/{coachId}")
    public ModelAndView removeCoachFromClub(@PathVariable("clubId") String clubId, @PathVariable("coachId") String coachId, Model model) {
        //authentication
        String loggedUserLogin = SecurityContextHolder.getContext().getAuthentication().getName();
        if(!loggedUserLogin.equals(clubManager.getClub(Integer.parseInt(clubId)).getOwner().getLogin()))
            return  new ModelAndView("redirect:/error");
        //end authentication
        Coach coach = userManager.findCoachById(Integer.parseInt(coachId));
        Club club = clubManager.getClub(Integer.parseInt(clubId));
        logger.info(coach.getLogin());
        logger.info(club.getName());
        clubManager.removeCoachFromClub(coach, club);

        return new ModelAndView("redirect:/owner-panel/add-coaches/{clubId}");
    }

    @GetMapping("/owner-panel/profile")
    public String viewProfile(Model model) {

        String loggedUserLogin = SecurityContextHolder.getContext().getAuthentication().getName();
        Owner loggedOwner = userManager.findOwnerByLogin(loggedUserLogin);
        model.addAttribute("profileDetails", loggedOwner);
        return "owner/profile";
    }

    @GetMapping("owner-panel/edit-profile")
    public String viewEditProfileForm(Model model) {

        String loggedUserLogin = SecurityContextHolder.getContext().getAuthentication().getName();
        Owner loggedOwner = userManager.findOwnerByLogin(loggedUserLogin);
        model.addAttribute("profileDetails", userManager.castToProfileForm(loggedOwner));
        return "owner/edit-profile";
    }

    @PostMapping("owner-panel/profile")
    public String changeOwnerDetails(@Valid @ModelAttribute("profileDetails") ProfileForm profileForm, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            return "owner/edit-profile";
        }
        String loggedUserLogin = SecurityContextHolder.getContext().getAuthentication().getName();
        userManager.updateUserDetails(profileForm, loggedUserLogin);

        return "redirect:/owner-panel/profile";
    }

    @GetMapping("/owner-panel/change-password")
    public String changePasswordForm(Model model) {

        PasswordForm newForm = new PasswordForm();
        model.addAttribute("passwordForm", newForm);
        return "owner/change-password";
    }

    @PostMapping("/owner-panel/change-password")
    public String changeOwnerPassword(@Valid @ModelAttribute("passwordForm") PasswordForm passwordForm, BindingResult bindingResult, Model model) {

        if(bindingResult.hasErrors()) {
            return "owner/change-password";
        }
        String loggedUserLogin = SecurityContextHolder.getContext().getAuthentication().getName();
        if(userManager.isCorrectPassword(loggedUserLogin,passwordForm.getOldPassword())) {
            userManager.userChangePassword(loggedUserLogin, passwordForm.getNewPassword());
        }
        else {
            throw new WrongOldPasswordException("Hasło jest nieprawidłowe");
        }


        return "redirect:/owner-panel/profile";
    }

    @GetMapping("/owner-panel/get-image/{login}")
    @ResponseBody
    public byte[] serveFile(@PathVariable("login") String userLogin) {

        Owner loggedOwner = userManager.findOwnerByLogin(userLogin);
        ByteArrayResource file = new ByteArrayResource(loggedOwner.getProfileImage());

        return file.getByteArray();
    }

    @PostMapping("/owner-panel/change-image")
    public String addProfilePicture(@RequestParam("picture") MultipartFile multipartImage) throws IOException {

        String loggedUserLogin = SecurityContextHolder.getContext().getAuthentication().getName();
        userManager.changeProfilePicture(loggedUserLogin, multipartImage.getBytes());

        return "redirect:/owner-panel/profile";
    }

    @ExceptionHandler(WrongOldPasswordException.class)
    public String handleWrongOldPasswordException(WrongOldPasswordException exc, RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute("wrongPasswordException",
                exc.getMessage());

        return "redirect:/owner-panel/change-password";
    }

    @ModelAttribute("profileImage")
    public String getProfileImageIfSet() {
        String loggedUserLogin = SecurityContextHolder.getContext().getAuthentication().getName();
        Owner loggedOwner = userManager.findOwnerByLogin(loggedUserLogin);
        String profileImage = null;
        if(loggedOwner.getProfileImage() != null) {
            profileImage = MvcUriComponentsBuilder.fromMethodName(OwnerPanelController.class,
                    "serveFile", loggedUserLogin).build().toUri().toString();
        }
        return profileImage;
    }
}
