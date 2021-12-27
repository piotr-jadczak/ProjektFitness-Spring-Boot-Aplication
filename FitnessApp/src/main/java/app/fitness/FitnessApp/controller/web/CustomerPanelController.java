package app.fitness.FitnessApp.controller.web;

import app.fitness.FitnessApp.domain.Coach;
import app.fitness.FitnessApp.domain.Customer;
import app.fitness.FitnessApp.domain.Training;
import app.fitness.FitnessApp.domain.extra.PasswordForm;
import app.fitness.FitnessApp.domain.extra.ProfileForm;
import app.fitness.FitnessApp.exception.WrongOldPasswordException;
import app.fitness.FitnessApp.repository.CustomerRepository;
import app.fitness.FitnessApp.service.TrainingManager;
import app.fitness.FitnessApp.service.UserManager;
import app.fitness.FitnessApp.service.UserManagerImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class CustomerPanelController {

    private final UserManager userManager;
    private final TrainingManager trainingManager;

    public CustomerPanelController(@Autowired UserManager userManager,
                                   @Autowired TrainingManager trainingManager) {
        this.userManager = userManager;
        this.trainingManager = trainingManager;
    }

    @GetMapping("/customer-panel")
    public String viewCustomerPanel(Model model) {
        String loggedUserLogin = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("loggedUserLogin", loggedUserLogin);
        return "customer/customer-panel";
    }

    @GetMapping("/customer-panel/available-trainings")
    public String viewAvailableTrainings(Model model) {

        String loggedUserLogin = SecurityContextHolder.getContext().getAuthentication().getName();
        List<Training> allNotFullTrainings = trainingManager.getAllTrainings().filter(t -> !t.isTrainingFull()).collect(Collectors.toList());
        List<Training> customerTrainings = userManager.findCustomerByLogin(loggedUserLogin).getTrainings().stream().collect(Collectors.toList());

        allNotFullTrainings.removeAll(customerTrainings);

        model.addAttribute("allTrainings", allNotFullTrainings);
        return "customer/available-trainings";
    }

    @GetMapping("/customer-panel/my-trainings")
    public String viewMyTrainings(Model model) {

        String loggedUserLogin = SecurityContextHolder.getContext().getAuthentication().getName();
        Customer loggedCustomer = userManager.findCustomerByLogin(loggedUserLogin);
        model.addAttribute("myTrainings", new ArrayList<>(loggedCustomer.getTrainings()));
        return "customer/my-trainings";
    }

    @GetMapping("/customer-panel/all-objects")
    public String viewAllObjects(Model model) {

        return "customer/all-objects";
    }

    @GetMapping("/customer-panel/enroll/{id}")
    public String enrollOnTraining(@PathVariable("id") String id, Model model) {

        String loggedUserLogin = SecurityContextHolder.getContext().getAuthentication().getName();
        Customer loggedCustomer = userManager.findCustomerByLogin(loggedUserLogin);

        trainingManager.enrollCustomer(loggedCustomer, Integer.parseInt(id));

        return "redirect:/customer-panel/available-trainings";
    }

    @GetMapping("/customer-panel/resign/{id}")
    public String resignFromTraining(@PathVariable("id") String id, Model model) {

        String loggedUserLogin = SecurityContextHolder.getContext().getAuthentication().getName();
        Customer loggedCustomer = userManager.findCustomerByLogin(loggedUserLogin);

        trainingManager.resignCustomer(loggedCustomer, Integer.parseInt(id));

        return "redirect:/customer-panel/my-trainings";
    }

    @GetMapping("/customer-panel/profile")
    public String viewProfile(Model model) {

        String loggedUserLogin = SecurityContextHolder.getContext().getAuthentication().getName();
        Customer loggedCustomer = userManager.findCustomerByLogin(loggedUserLogin);
        model.addAttribute("profileDetails", loggedCustomer);
        return "customer/profile";
    }

    @GetMapping("customer-panel/edit-profile")
    public String viewEditProfileForm(Model model) {

        String loggedUserLogin = SecurityContextHolder.getContext().getAuthentication().getName();
        Customer loggedCustomer = userManager.findCustomerByLogin(loggedUserLogin);
        model.addAttribute("profileDetails", userManager.castToProfileForm(loggedCustomer));
        return "customer/edit-profile";
    }

    @PostMapping("customer-panel/profile")
    public String changeCustomerDetails(@Valid @ModelAttribute("profileDetails") ProfileForm profileForm, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            return "customer/edit-profile";
        }
        String loggedUserLogin = SecurityContextHolder.getContext().getAuthentication().getName();
        userManager.updateUserDetails(profileForm, loggedUserLogin);

        return "redirect:/customer-panel/profile";
    }

    @GetMapping("/customer-panel/change-password")
    public String changePasswordForm(Model model) {

        PasswordForm newForm = new PasswordForm();
        model.addAttribute("passwordForm", newForm);
        return "customer/change-password";
    }

    @PostMapping("/customer-panel/change-password")
    public String changeCustomerPassword(@Valid @ModelAttribute("passwordForm") PasswordForm passwordForm, BindingResult bindingResult, Model model) {

        if(bindingResult.hasErrors()) {
            return "customer/change-password";
        }
        String loggedUserLogin = SecurityContextHolder.getContext().getAuthentication().getName();
        if(userManager.isCorrectPassword(loggedUserLogin,passwordForm.getOldPassword())) {
            userManager.userChangePassword(loggedUserLogin, passwordForm.getNewPassword());
        }
        else {
            throw new WrongOldPasswordException("Hasło jest nieprawidłowe");
        }


        return "redirect:/customer-panel/profile";
    }

    @ExceptionHandler(WrongOldPasswordException.class)
    public String handleWrongOldPasswordException(WrongOldPasswordException exc, RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute("wrongPasswordException",
                exc.getMessage());

        return "redirect:/customer-panel/change-password";
    }

}
