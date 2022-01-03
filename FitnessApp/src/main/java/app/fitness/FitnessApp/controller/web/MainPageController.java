package app.fitness.FitnessApp.controller.web;

import app.fitness.FitnessApp.domain.BaseUser;
import app.fitness.FitnessApp.domain.Coach;
import app.fitness.FitnessApp.login.UserForm;
import app.fitness.FitnessApp.service.UserManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.validation.Valid;

@Controller
public class MainPageController {

    private static Logger logger = LoggerFactory.getLogger(MainPageController.class);

    private final UserManager userManager;

    public MainPageController(@Autowired UserManager userManager) {
        this.userManager = userManager;
    }

    @GetMapping("")
    public String viewHomePage(Model model) {

        String userRole = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        String loggedUserLogin = SecurityContextHolder.getContext().getAuthentication().getName();
        String profileImage = null;
        if(!SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser")) {
            BaseUser loggedUser = null;
            if(userRole.equals("[ROLE_OWNER]")) {
                loggedUser = userManager.findOwnerByLogin(loggedUserLogin);
                if (loggedUser.getProfileImage() != null) {
                    profileImage = MvcUriComponentsBuilder.fromMethodName(OwnerPanelController.class,
                            "serveFile", loggedUserLogin).build().toUri().toString();
                }
            }

            if(userRole.equals("[ROLE_COACH]")) {
                loggedUser = userManager.findCoachByLogin(loggedUserLogin);
                if(loggedUser.getProfileImage() != null) {
                    profileImage = MvcUriComponentsBuilder.fromMethodName(CoachPanelController.class,
                            "serveFile", loggedUserLogin).build().toUri().toString();
                }
            }
            if(userRole.equals("[ROLE_CUSTOMER]")) {
                loggedUser = userManager.findCustomerByLogin(loggedUserLogin);
                if(loggedUser.getProfileImage() != null) {
                    profileImage = MvcUriComponentsBuilder.fromMethodName(CustomerPanelController.class,
                            "serveFile", loggedUserLogin).build().toUri().toString();
                }
            }
        }

        model.addAttribute("userRole", userRole);
        model.addAttribute("profileImage", profileImage);

        return "main/index";
    }

    @GetMapping("/register")
    public String viewRegistrationForm(Model model) {
        model.addAttribute("user", new UserForm());

        return "main/register";
    }

    @PostMapping("/process_register")
    public String processRegister(@Valid @ModelAttribute("user") UserForm user, BindingResult bindingResult) {
        logger.info("Rejestracja poczatek");

        if(bindingResult.hasErrors()) {
            logger.info("Rejestracja bledy");
            return "main/register";
        }
        userManager.addUser(user);

        logger.info("Rejestracja udana");
        return "main/register_success";
    }

    @GetMapping("/login")
    public String viewLoginPage() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || authentication instanceof AnonymousAuthenticationToken)
            return "main/login";

        return "redirect:/success_login_redirect";
    }

    @GetMapping("/success_login_redirect")
    public String redirectToPanelPage() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String role = userManager.getAuthorityName(authentication);
        switch (role) {
            case "ROLE_CUSTOMER" :
                return "redirect:/customer-panel";
            case "ROLE_COACH" :
                return "redirect:/coach-panel";
            case "ROLE_OWNER" :
                return "redirect:/owner-panel";
        }

        return "redirect:/error_login";
    }

    @GetMapping("/logout")
    public String logoutUser() {
        SecurityContextHolder.clearContext();
        return "main/index";
    }

    @GetMapping("errors/error403")
    public String view403Error() {
        return "errors/error403";
    }

//    @ModelAttribute("profileImage")
//    public String getProfileImageIfSet() {
//
//        String profileImage = null;
//        if(!SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser")) {
//            BaseUser loggedUser = null;
//            String loggedUserLogin = SecurityContextHolder.getContext().getAuthentication().getName();
//            String userRole = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
//            logger.info(userRole);
//            if(userRole.equals("[ROLE_OWNER]")) {
//                loggedUser = userManager.findOwnerByLogin(loggedUserLogin);
//                if (loggedUser.getProfileImage() != null) {
//                    profileImage = MvcUriComponentsBuilder.fromMethodName(OwnerPanelController.class,
//                            "serveFile", loggedUserLogin).build().toUri().toString();
//                }
//                return profileImage;
//            }
//
//            if(userRole.equals("[ROLE_COACH]")) {
//                loggedUser = userManager.findCoachByLogin(loggedUserLogin);
//                if(loggedUser.getProfileImage() != null) {
//                    profileImage = MvcUriComponentsBuilder.fromMethodName(CoachPanelController.class,
//                            "serveFile", loggedUserLogin).build().toUri().toString();
//                }
//                return profileImage;
//            }
//            if(userRole.equals("[ROLE_CUSTOMER]")) {
//                    loggedUser = userManager.findCustomerByLogin(loggedUserLogin);
//                    if(loggedUser.getProfileImage() != null) {
//                        profileImage = MvcUriComponentsBuilder.fromMethodName(CustomerPanelController.class,
//                                "serveFile", loggedUserLogin).build().toUri().toString();
//                    }
//                return profileImage;
//            }
//        }
//
//        return null;
//    }
//
//    @ModelAttribute("userRole")
//    public String getUserRole() {
//        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
//    }

}
