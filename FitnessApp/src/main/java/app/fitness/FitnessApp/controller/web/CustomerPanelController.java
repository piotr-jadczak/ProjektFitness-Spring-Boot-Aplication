package app.fitness.FitnessApp.controller.web;

import app.fitness.FitnessApp.domain.Club;
import app.fitness.FitnessApp.domain.Coach;
import app.fitness.FitnessApp.domain.Customer;
import app.fitness.FitnessApp.domain.Training;
import app.fitness.FitnessApp.domain.extra.ClubTrainingsWrapper;
import app.fitness.FitnessApp.domain.extra.PasswordForm;
import app.fitness.FitnessApp.domain.extra.ProfileForm;
import app.fitness.FitnessApp.exception.NotUniqueEmailException;
import app.fitness.FitnessApp.exception.WrongOldPasswordException;
import app.fitness.FitnessApp.repository.CustomerRepository;
import app.fitness.FitnessApp.service.ClubManager;
import app.fitness.FitnessApp.service.TrainingManager;
import app.fitness.FitnessApp.service.UserManager;
import app.fitness.FitnessApp.service.UserManagerImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class CustomerPanelController {

    private final UserManager userManager;
    private final TrainingManager trainingManager;
    private final ClubManager clubManager;

    public CustomerPanelController(@Autowired UserManager userManager,
                                   @Autowired TrainingManager trainingManager,
                                   @Autowired ClubManager clubManager) {
        this.userManager = userManager;
        this.trainingManager = trainingManager;
        this.clubManager = clubManager;
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

        model.addAttribute("allTrainings", allNotFullTrainings.stream().sorted(Comparator.comparingInt(Training::getId)).collect(Collectors.toList()));
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

        String loggedUserLogin = SecurityContextHolder.getContext().getAuthentication().getName();
        List<Training> allNotFullTrainings = trainingManager.getAllTrainings().filter(t -> !t.isTrainingFull()).collect(Collectors.toList());
        List<Training> customerTrainings = userManager.findCustomerByLogin(loggedUserLogin).getTrainings().stream().collect(Collectors.toList());

        allNotFullTrainings.removeAll(customerTrainings);
        List<List<Training>> groupedTrainings = trainingManager.groupTrainingsByClub(allNotFullTrainings).collect(Collectors.toList());
        List<ClubTrainingsWrapper> wrappedTrainings = new ArrayList<>();
        for(List<Training> trainings : groupedTrainings) {
            Club club = clubManager.getClub(trainings.get(0).getClub().getId());
            ClubTrainingsWrapper wrapper = new ClubTrainingsWrapper(club, trainings);
            wrappedTrainings.add(wrapper);
        }

        model.addAttribute("wrapTrainings", wrappedTrainings);

        return "customer/all-objects";
    }

    @GetMapping("/customer-panel/enroll/{id}")
    public String enrollOnTraining(@PathVariable("id") String id, Model model) {

        String loggedUserLogin = SecurityContextHolder.getContext().getAuthentication().getName();
        Customer loggedCustomer = userManager.findCustomerByLogin(loggedUserLogin);

        trainingManager.enrollCustomer(loggedCustomer, Integer.parseInt(id));

        return "redirect:/customer-panel/available-trainings";
    }

    @GetMapping("/customer-panel/enroll-object/{id}")
    public String enrollOnTrainingInObject(@PathVariable("id") String id, Model model) {

        String loggedUserLogin = SecurityContextHolder.getContext().getAuthentication().getName();
        Customer loggedCustomer = userManager.findCustomerByLogin(loggedUserLogin);

        trainingManager.enrollCustomer(loggedCustomer, Integer.parseInt(id));

        return "redirect:/customer-panel/all-objects";
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
        if(userManager.isEmailUnique(profileForm.getEmail(), loggedUserLogin)) {
            userManager.updateUserDetails(profileForm, loggedUserLogin);
        }

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

    @GetMapping("/customer-panel/get-image/{login}")
    @ResponseBody
    public byte[] serveFile(@PathVariable("login") String userLogin) {

        Customer loggedCustomer = userManager.findCustomerByLogin(userLogin);
        ByteArrayResource file = new ByteArrayResource(loggedCustomer.getProfileImage());

        return file.getByteArray();
    }

    @PostMapping("/customer-panel/change-image")
    public String addProfilePicture(@RequestParam("picture") MultipartFile multipartImage) throws IOException {

        String loggedUserLogin = SecurityContextHolder.getContext().getAuthentication().getName();
        userManager.changeProfilePicture(loggedUserLogin, multipartImage.getBytes());

        return "redirect:/customer-panel/profile";
    }

    @ExceptionHandler(WrongOldPasswordException.class)
    public String handleWrongOldPasswordException(WrongOldPasswordException exc, RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute("wrongPasswordException",
                exc.getMessage());

        return "redirect:/customer-panel/change-password";
    }

    @ExceptionHandler(NotUniqueEmailException.class)
    public String handleNotUniqueEmailException(NotUniqueEmailException exc, RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute("notUniqueEmailException",
                exc.getMessage());

        return "redirect:/customer-panel/edit-profile";
    }

    @ModelAttribute("profileImage")
    public String getProfileImageIfSet() {
        String loggedUserLogin = SecurityContextHolder.getContext().getAuthentication().getName();
        Customer loggedCustomer = userManager.findCustomerByLogin(loggedUserLogin);
        String profileImage = null;
        if(loggedCustomer.getProfileImage() != null) {
            profileImage = MvcUriComponentsBuilder.fromMethodName(CustomerPanelController.class,
                    "serveFile", loggedUserLogin).build().toUri().toString();
        }
        return profileImage;
    }



}
