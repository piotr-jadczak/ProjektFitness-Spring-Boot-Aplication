package app.fitness.FitnessApp.domain;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class BaseUser {

	@GeneratedValue
	@Id
	private int id;

	@Column(nullable = false, unique = true, length = 32)
	private String login;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false, length = 64)
	private String email;

	@Column(nullable = false, length = 64)
	private String firstName;

	@Column(nullable = false, length = 64)
	private String lastName;

	@Column(nullable = true, length = 64)
	private Date dob;

	@Column(nullable = true, length = 64)
	private String phoneNumber;

	public BaseUser() {
	}

	public BaseUser(String login, String password, String email, String firstName, String lastName, Date dob,
			String phoneNumber) {
		super();
		this.login = login;
		this.password = password;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dob = dob;
		this.phoneNumber = phoneNumber;
	}

	public BaseUser(BaseUser baseUser) {
		super();
		this.login = baseUser.getLogin();
		this.password = baseUser.getPassword();
		this.email = baseUser.getEmail();
		this.firstName = baseUser.getFirstName();
		this.lastName = baseUser.getLastName();
		this.dob = baseUser.getDob();
		this.phoneNumber = baseUser.getPhoneNumber();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Override
	public String toString() {
		return "BaseUser [login=" + login + ", password=" + password + ", email=" + email + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", dob=" + dob + ", phoneNumber=" + phoneNumber + "]";
	}

}
