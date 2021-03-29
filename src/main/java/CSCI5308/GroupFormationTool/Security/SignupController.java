package CSCI5308.GroupFormationTool.Security;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import CSCI5308.GroupFormationTool.IGlobalFactoryProvider;
import CSCI5308.GroupFormationTool.RuntimeSQLException;
import CSCI5308.GroupFormationTool.AccessControl.AbstractAclDependencyFactory;
import CSCI5308.GroupFormationTool.AccessControl.User;
import CSCI5308.GroupFormationTool.AccessControl.UserExistsException;
import CSCI5308.GroupFormationTool.PasswordValidation.AbstractPasswordValidatorDependencyFactory;
import CSCI5308.GroupFormationTool.PasswordValidation.IPasswordValidatorEnumerator;

@Controller
public class SignupController {
	private final String USERNAME = "username";
	private final String PASSWORD = "password";
	private final String PASSWORD_CONFIRMATION = "passwordConfirmation";
	private final String FIRST_NAME = "firstName";
	private final String LAST_NAME = "lastName";
	private final String EMAIL = "email";
	private final IPasswordValidatorEnumerator passwordValidatorEnumerator;
	private final AbstractAclDependencyFactory aclDependencyFactory;
	private final IPasswordEncryption passwordEncryption;
	private final Logger logger = LoggerFactory.getLogger(SignupController.class);

	public SignupController(IGlobalFactoryProvider provider) {
		this.aclDependencyFactory = provider.getAclDependencyFactory();
		AbstractPasswordValidatorDependencyFactory passwordValidatorDependencyFactory = provider
				.getPasswordValidatorDependencyFactory();
		passwordValidatorEnumerator = passwordValidatorDependencyFactory.getPasswordValidatorEnumerator();
		passwordEncryption = provider.getSecurityFactory().getPasswordEncryption();
	}

	@GetMapping("/signup")
	public String displaySignUp(Model model) {
		return "signup";
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public ModelAndView processSignUp(@RequestParam(name = USERNAME) String bannerID,
			@RequestParam(name = PASSWORD) String password,
			@RequestParam(name = PASSWORD_CONFIRMATION) String passwordConfirm,
			@RequestParam(name = FIRST_NAME) String firstName, @RequestParam(name = LAST_NAME) String lastName,
			@RequestParam(name = EMAIL) String email) {
		boolean success = false;
		ModelAndView m = new ModelAndView("login");
		String errorMessage = "";
		List<String> errorMessages = new ArrayList<>();
		boolean valid = User.isBannerIDValid(bannerID) && User.isEmailValid(email) && User.isFirstNameValid(firstName)
				&& User.isLastNameValid(lastName) && password.equals(passwordConfirm);
		if (valid) {
			User u = new User();
			u.setBannerID(bannerID);
			u.setPassword(password);
			u.setFirstName(firstName);
			u.setLastName(lastName);
			u.setEmail(email);
			try {
				success = u.createUser(aclDependencyFactory.getUserPersistence(), passwordValidatorEnumerator,
						passwordEncryption, null, errorMessages);
			} catch (UserExistsException existsException) {
				logger.info("User Exists for BannerNumber: " + bannerID);
				errorMessage = existsException.getMessage();
			} catch (RuntimeSQLException exception) {
				logger.error("SQL Exception: " + exception.getCause().getMessage());
				errorMessage = "Database Connection Problem. Please try after sometime.";
			}
		}
		if (success == false) {
			if (errorMessage.isEmpty()) {
				errorMessage = "Invalid data, please check your values.";
			}
			m.addObject("errorMessage", errorMessage);
			m.addObject("passwordInvalid", errorMessages);
			m.setViewName("signup.html");
		}
		return m;
	}
}