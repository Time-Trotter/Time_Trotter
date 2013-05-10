package controllers;

import java.util.Date;
import java.util.List;

import play.*;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;

import views.html.*;
import models.*;

public class Application extends Controller {
  
	@Security.Authenticated(Secured.class)
    public static Result index() {
    	return ok(index.render(User.findByEmail(request().username())));
    }
    
    public static Result login() {
        return ok(
        	login.render(form(Login.class))
        );
    }
    
    public static Result logout() {
        session().clear();
        flash("success", "You've been logged out");
        return redirect(
            routes.Application.login()
        );
    }
    
    public static class Login {

        public String email;
        public String password;
        public String companyId;
        
        public String validate() {
            if (User.authenticate(email, password, companyId) == null) {
              return "Invalid user or password";
            }
            return null;
        }

    }
    
    public static Result authenticate() {
        Form<Login> loginForm = form(Login.class).bindFromRequest();
        if (loginForm.hasErrors()) {
            return badRequest(login.render(loginForm));
        } else {
            session().clear();
            session("email", loginForm.get().email);
            return redirect(
                routes.Application.index()
            );
        }
    }
    
    public static Result registration() {
    	return ok(registration.render());
    }
    
    public static Result createAccount(){
    	//create a new user in DB
    	User user = User.create(form().bindFromRequest().get("email"), form().bindFromRequest().get("password"), form().bindFromRequest().get("companyId"), form().bindFromRequest().get("firstName"), form().bindFromRequest().get("middleName"), form().bindFromRequest().get("lastName"), form().bindFromRequest().get("jobTitle"), false, 0, form().bindFromRequest().get("securityQuestion"), form().bindFromRequest().get("securityAnswer"), new Date(), new Date(), false, true);
    	//Login user after registration
    	if(User.authenticate(form().bindFromRequest().get("email"), form().bindFromRequest().get("password"), form().bindFromRequest().get("companyId")) == null){
    		//To DO: Throw error
    		return redirect(routes.Application.registration());
    	} else{
    		session().clear();
    		session("email", form().bindFromRequest().get("email"));
    		return redirect(routes.Application.index());
    	}
    }
    
    public static Result recoverPass() {
    	return ok(recover.render());
    }
    
    public static Result generatePass(){
    	String email = form().bindFromRequest().get("email");
    	User user = User.findByEmail(email);
    	if(user == null){
    		return ok("User does not Exist");
    	}else{
    		return ok("Password is '"+user.password+"'");
    	}
    }
    
    @Security.Authenticated(Secured.class)
    public static Result profile(){
    	User user = User.findByEmail(request().username());
    	return ok(profile.render(user));
    }
    
    public static Result editProfile(){
    	User user = User.findByEmail(form().bindFromRequest().get("email"));
    	user.firstName = form().bindFromRequest().get("firstName");
    	user.middleName = form().bindFromRequest().get("middleName");
    	user.lastName = form().bindFromRequest().get("lastName");
    	user.jobTitle = form().bindFromRequest().get("jobTitle");
    	user.password = form().bindFromRequest().get("password");
    	user.update();
    	return ok(profile.render(user));
    }

    public static Result company(){
    	return ok(company.render());
    }
    
    public static Result companyProfile(){
    	return ok(company.render());
    }

}
