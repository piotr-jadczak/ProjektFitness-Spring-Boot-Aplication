package app.fitness.FitnessApp.controller.web;

import app.fitness.FitnessApp.domain.Coach;
import app.fitness.FitnessApp.domain.Training;
import app.fitness.FitnessApp.exception.CoachNotInAnyClubException;
import app.fitness.FitnessApp.service.TrainingManager;
import app.fitness.FitnessApp.service.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @GetMapping("coach-panel/add-training")
    public String viewTrainingForm(Model model) {

        String loggedUserLogin = SecurityContextHolder.getContext().getAuthentication().getName();
        Coach loggedCoach = userManager.findCoachByLogin(loggedUserLogin);
        //check if coach has any club so he can add trainings to it
        if(loggedCoach.getClubs() == null || loggedCoach.getClubs().size() <= 0)
            throw new CoachNotInAnyClubException("Nie możesz dodać treningu ponieważ nie jesteś w żadnym obiekcie sportowym");

        model.addAttribute("trainingToAdd", new Training());
        model.addAttribute("trainingCategories", trainingManager.getAllTrainingCategories().collect(Collectors.toList()));
        model.addAttribute("availableClubs", loggedCoach.getClubs());

        return "/coach/add-training";
    }

    @PostMapping("coach-panel/my-trainings")
    public String addTraining(@ModelAttribute("trainingToAdd") Training trainingToAdd, BindingResult bindingResult, Model model) {

        String loggedUserLogin = SecurityContextHolder.getContext().getAuthentication().getName();
        Coach loggedCoach = userManager.findCoachByLogin(loggedUserLogin);
        trainingToAdd.setCoach(loggedCoach);
        trainingManager.addTraining(trainingToAdd);

        model.addAttribute("coachTrainings", trainingManager.getAllTrainings(loggedCoach));
        return "/coach/my-trainings";
    }

    @ExceptionHandler(CoachNotInAnyClubException.class)
    public String handleCoachNotInAnyClubException(CoachNotInAnyClubException exc, RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute("exceptionMessage",
                exc.getMessage());

        return "redirect:/coach-panel/my-trainings";
    }
}
