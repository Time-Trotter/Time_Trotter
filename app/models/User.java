package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;
import com.avaje.ebean.*;
 
@Entity
public class User extends Model {

	@Id
	@Constraints.Required
    @Formats.NonEmpty
    public int id;
	@Constraints.Required
    @Formats.NonEmpty
	public String	email;
    public String	password;
    public String	companyId;
    public String	firstName;
    public String	middleName;
    public String	lastName;
    public String	jobTitle;
    public Boolean	resetFlag;
    public Integer	failedLoginAttempt;
    public String	securityQuestion;
    public String	securityAnswer;
    public Date		createDate;
    public Date		modifiedDate;
    public Boolean	passwordReset;
    public Boolean	status;
    
    public User(String email, String password, String companyId, String firstName, String middleName, String lastName, String jobTitle, boolean resetFlag, int failedLoginAttempt, String securityQuestion, String securityAnswer, Date createDate, Date modifiedDate, boolean passwordReset, boolean status) {
        this.email = email;
        this.password = password;
        this.companyId = companyId;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.jobTitle = jobTitle;
        this.resetFlag = resetFlag;
        this.failedLoginAttempt = failedLoginAttempt;
        this.securityQuestion = securityQuestion;
        this.securityAnswer = securityAnswer;
        this.createDate = createDate;
        this.modifiedDate = modifiedDate;
        this.passwordReset = passwordReset;
        this.status = status;
      }
    
    public static Finder<String,User> find = new Finder<String,User>(
            String.class, User.class
        );
    
    public static User authenticate(String email, String password, String companyId) {
        return find.where().eq("email", email)
            .eq("password", password).eq("company_id", companyId).findUnique();
    }
    
    /**
     * Retrieve a User from email.
     */
    public static User findByEmail(String email) {
        return find.where().eq("email", email).findUnique();
    }
    
    //Create a new User
    public static User create(String email, String password, String companyId, String firstName, String middleName, String lastName, String jobTitle, Boolean resetFlag, int failedLoginAttempt, String securityQuestion, String securityAnswer, Date createDate, Date modifiedDate, boolean passwordReset, boolean status) {
    	User user = new User(email, password, companyId, firstName, middleName, lastName, jobTitle, resetFlag, failedLoginAttempt, securityQuestion, securityAnswer, createDate, modifiedDate, passwordReset, status);
        user.save();
        return user;
    }
}
