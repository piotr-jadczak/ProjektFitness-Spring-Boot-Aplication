package app.fitness.FitnessApp.controller.web;

import app.fitness.FitnessApp.domain.Coach;
import app.fitness.FitnessApp.domain.Training;
import app.fitness.FitnessApp.domain.extra.PasswordForm;
import app.fitness.FitnessApp.domain.extra.ProfileForm;
import app.fitness.FitnessApp.exception.CoachNotInAnyClubException;
import app.fitness.FitnessApp.exception.WrongOldPasswordException;
import app.fitness.FitnessApp.service.TrainingManager;
import app.fitness.FitnessApp.service.UserManager;
import app.fitness.FitnessApp.service.UserManagerImp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.stream.Collectors;

@Controller
public class CoachPanelController {

    private final UserManager userManager;
    private final TrainingManager trainingManager;

    public CoachPanelController(@Autowired UserManager userManager,
                                @Autowired TrainingManager trainingManager) {
        this.userManager = userManager;
        this.trainingManager = trainingManager;
    }

    private static Logger logger = LoggerFactory.getLogger(CoachPanelController.class);

    @GetMapping("/coach-panel")
    public String viewCoachPanel(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        model.addAttribute("loggedUserLogin", currentPrincipalName);
        return "/coach/coach-panel";
    }

    @GetMapping("/coach-panel/all-trainings")
    public String viewAllTrainings(Model model) {

        model.addAttribute("allTrainings", trainingManager.getAllTrainings().collect(Collectors.toList()));
        return "/coach/all-trainings";
    }

    @GetMapping("/coach-panel/my-trainings")
    public String viewMyTrainings(Model model) {

        String loggedUserLogin = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("coachTrainings", trainingManager.getAllTrainings(userManager.findCoachByLogin(loggedUserLogin)));

        return "/coach/my-trainings";
    }

    @GetMapping("/coach-panel/add-training/{id}")
    public String viewTrainingForm(@PathVariable("id") String id, Model model) {

        String loggedUserLogin = SecurityContextHolder.getContext().getAuthentication().getName();
        Coach loggedCoach = userManager.findCoachByLogin(loggedUserLogin);
        //check if coach has any club so he can add trainings to it
        if(loggedCoach.getClubs() == null || loggedCoach.getClubs().size() <= 0)
            throw new CoachNotInAnyClubException("Nie możesz dodać treningu ponieważ nie jesteś w żadnym obiekcie sportowym");

        int entityID = Integer.parseInt(id);
        if(trainingManager.isTrainingInDB(entityID)) {
            Training trainingToEdit = trainingManager.getTraining(entityID);
            if(userManager.findCoachByLogin(loggedUserLogin).getId() == trainingToEdit.getCoach().getId()) {
                model.addAttribute("trainingToAdd", trainingToEdit);
            }
            else {
                return "errors/error403";
            }
        }
        else {
            model.addAttribute("trainingToAdd", new Training());
        }

        model.addAttribute("trainingCategories", trainingManager.getAllTrainingCategories().collect(Collectors.toList()));
        model.addAttribute("availableClubs", loggedCoach.getClubs());

        return "/coach/add-training";
    }

    @PostMapping("/coach-panel/my-trainings")
    public String addTraining(@ModelAttribute("trainingToAdd") Training trainingToAdd, BindingResult bindingResult, Model model) {

        String loggedUserLogin = SecurityContextHolder.getContext().getAuthentication().getName();
        Coach loggedCoach = userManager.findCoachByLogin(loggedUserLogin);

        if(bindingResult.hasErrors()) {
            model.addAttribute("trainingCategories", trainingManager.getAllTrainingCategories().collect(Collectors.toList()));
            model.addAttribute("availableClubs", loggedCoach.getClubs());
            return "/coach/add-training";
        }
        if(trainingManager.isTrainingInDB(trainingToAdd.getId())) {
            trainingManager.updateTraining(trainingToAdd);
        }
        else {
            trainingToAdd.setCoach(loggedCoach);
            trainingManager.addTraining(trainingToAdd);
        }

        model.addAttribute("coachTrainings", trainingManager.getAllTrainings(loggedCoach));
        return "/coach/my-trainings";
    }

    @GetMapping("/coach-panel/delete-training/{id}")
    public ModelAndView deleteTraining(@PathVariable String id) {
        int entityID = Integer.parseInt(id);
        trainingManager.deleteTraining(entityID);

        return new ModelAndView("redirect:/coach-panel/my-trainings");
    }

    @GetMapping("/coach-panel/profile")
    public String viewProfile(Model model) {

        String loggedUserLogin = SecurityContextHolder.getContext().getAuthentication().getName();
        Coach loggedCoach = userManager.findCoachByLogin(loggedUserLogin);
        model.addAttribute("profileDetails", loggedCoach);
        return "coach/profile";
    }

    @GetMapping("coach-panel/edit-profile")
    public String viewEditProfileForm(Model model) {

        String loggedUserLogin = SecurityContextHolder.getContext().getAuthentication().getName();
        Coach loggedCoach = userManager.findCoachByLogin(loggedUserLogin);
        model.addAttribute("profileDetails", userManager.castToProfileForm(loggedCoach));
        return "coach/edit-profile";
    }

    @PostMapping("coach-panel/profile")
    public String changeCoachDetails(@Valid @ModelAttribute("profileDetails") ProfileForm profileForm, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            return "coach/edit-profile";
        }
        String loggedUserLogin = SecurityContextHolder.getContext().getAuthentication().getName();
        userManager.updateUserDetails(profileForm, loggedUserLogin);

        return "redirect:/coach-panel/profile";
    }

    @GetMapping("/coach-panel/change-password")
    public String changePasswordForm(Model model) {

        PasswordForm newForm = new PasswordForm();
        model.addAttribute("passwordForm", newForm);
        return "coach/change-password";
    }

    @PostMapping("/coach-panel/change-password")
    public String changeCoachPassword(@Valid @ModelAttribute("passwordForm") PasswordForm passwordForm, BindingResult bindingResult, Model model) {

        if(bindingResult.hasErrors()) {
            return "coach/change-password";
        }
        String loggedUserLogin = SecurityContextHolder.getContext().getAuthentication().getName();
        if(userManager.isCorrectPassword(loggedUserLogin,passwordForm.getOldPassword())) {
            userManager.userChangePassword(loggedUserLogin, passwordForm.getNewPassword());
        }
        else {
            throw new WrongOldPasswordException("Hasło jest nieprawidłowe");
        }


        return "redirect:/coach-panel/profile";
    }

    @ExceptionHandler(CoachNotInAnyClubException.class)
    public String handleCoachNotInAnyClubException(CoachNotInAnyClubException exc, RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute("exceptionMessage",
                exc.getMessage());

        return "redirect:/coach-panel/my-trainings";
    }

    @ExceptionHandler(WrongOldPasswordException.class)
    public String handleWrongOldPasswordException(WrongOldPasswordException exc, RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute("wrongPasswordException",
                exc.getMessage());

        return "redirect:/coach-panel/change-password";
    }
}
