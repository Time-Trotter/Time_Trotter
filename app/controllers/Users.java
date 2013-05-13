package controllers;

import ch.qos.logback.classic.util.ContextInitializer;
import play.*;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;
import java.util.*;

import models.*;
import views.html.*;

@Security.Authenticated(Secured.class)
public class Users extends Controller {
	
	public static Result view(){
    	User user = User.findByEmail(request().username());
		List<User> userList = User.find.all();
		return ok(userView.render(user, userList));
	}
}