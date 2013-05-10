package controllers;

import java.util.Date;

import play.*;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;

import views.html.*;
import models.*;

public class Timesheet extends Controller{

	@Security.Authenticated(Secured.class)
    public static Result time() {
    	return ok(time.render(User.findByEmail(request().username())));
    }
}
