package CSCI5308.GroupFormationTool.AccessControl;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import CSCI5308.GroupFormationTool.PasswordValidation.IPasswordValidatorEnumerator;
import CSCI5308.GroupFormationTool.PasswordValidation.PasswordValidator;
import CSCI5308.GroupFormationTool.Security.IPasswordEncryption;

public class User {

	Logger log = LoggerFactory.getLogger(User.class);

	// This regex comes from here:
	// https://howtodoinjava.com/regex/java-regex-validate-email-address/
	private static final String EMAIL_REGEX = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";

	private long id;
	private String password;
	private String bannerID;
	private String firstName;
	private String lastName;
	private String email;

	public User() {
		setDefaults();
	}

	public User(long id, IUserPersistence persistence) {
		setDefaults();
		persistence.loadUserByID(id, this);
	}

	public void setDefaults() {
		id = -1;
		password = "";
		bannerID = "";
		firstName = "";
		lastName = "";
		email = "";
	}

	public void setID(long id) {
		this.id = id;
	}

	public long getID() {
		return id;
	}

	// These are here for the Thymeleaf / Spring binding nonsense.
	public void setId(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public void setBannerID(String bannerID) {
		this.bannerID = bannerID;
	}

	public String getBannerID() {
		return bannerID;
	}

	// Also here for Thymeleaf nonsense.
	public String getBanner() {
		return bannerID;
	}

	public void setFirstName(String name) {
		firstName = name;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setLastName(String name) {
		lastName = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public boolean isValidUser() {
		return id > -1;
	}

	public boolean createUser(IUserPersistence userDB, IPasswordValidatorEnumerator passwordEnumerator,
			IPasswordEncryption passwordEncryption, IUserNotifications notification, List<String> errorMessages) {
		log.trace("Attempting to createUser. Fetching password validators.");
		String rawPassword = password;
		boolean success = true;

		List<PasswordValidator> passwordValidators = passwordEnumerator.getActiveValidators(this);
		log.debug("Total validators fetched=" + passwordValidators.size());
		for (int i = 0; i < passwordValidators.size(); i++) {
			PasswordValidator validator = passwordValidators.get(i);
			log.debug("Attempting to Validate Password for validator=" + validator.getValidatorName());
			if (validator.isValid(rawPassword) == false) {
				log.debug("Password validation failed for validator=" + validator.getValidatorName());
				log.info("Password criteria not met!");
				errorMessages.add(validator.getValidatorName() + " - " + validator.constraint);
				success = false;
			}
		}
		if (success) {
			log.debug("Password is valid");
			success = this.createUser(userDB, passwordEncryption, notification);
		}
		log.trace("Exiting createUser method");
		return success;
	}

	public boolean createUser(IUserPersistence userDB, IPasswordEncryption passwordEncryption,
			IUserNotifications notification) {
		User existingUser = new User();
		userDB.loadUserByBannerID(bannerID, existingUser);
		if (existingUser.getId() > -1) {
			throw new UserExistsException(bannerID);
		}

		log.debug("Attempting to encrypt user password with BannerId=" + this.getBannerID());
		String rawPassword = password;
		this.password = passwordEncryption.encryptPassword(this.password);

		log.debug("Attempting to persist user with BannerId=" + this.getBannerID());
		boolean success = userDB.createUser(this);
		if (success && (null != notification)) {
			log.debug("Attempting to notify user password with BannerId=" + this.getBannerID());
			notification.sendUserLoginCredentials(this, rawPassword);
		}
		return success;
	}

	public boolean updateUser(IUserPersistence userDB) {
		return userDB.updateUser(this);
	}

	private static boolean isStringNullOrEmpty(String s) {
		if (null == s) {
			return true;
		}
		return s.isEmpty();

	}

	public static boolean isBannerIDValid(String bannerID) {

		if (isStringNullOrEmpty(bannerID)) {
			return false;
		}
		return true;
	}

	public static boolean isFirstNameValid(String name) {
		if (isStringNullOrEmpty(name)) {
			return false;
		}
		return true;
	}

	public static boolean isLastNameValid(String name) {
		if (isStringNullOrEmpty(name)) {
			return false;
		}
		return true;
	}

	public static boolean isEmailValid(String email) {
		if (isStringNullOrEmpty(email)) {
			return false;
		}

		Pattern pattern = Pattern.compile(EMAIL_REGEX);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}
}