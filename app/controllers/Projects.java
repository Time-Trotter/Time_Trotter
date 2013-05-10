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
public class Projects extends Controller {
	
	public static Result view(){
		User user = User.findByEmail(request().username());
		List<Project> project = Project.findInvolvingByCompany(user.companyId);
		return ok(projectView.render(user, project));
	}

    public static Result add(){
        User user = User.findByEmail(request().username());
        Project newProject = Project.create(user.companyId, "test Project", new Date(), new Date(), true, true, true, user.email);
        return ok(projectView.render(user, Project.find.all()));
    }
    
    
    
    public static Result javascriptRoutes() {
        response().setContentType("text/javascript");
        return ok(
            Routes.javascriptRouter("jsRoutes",
                controllers.routes.javascript.Projects.add())
        );
    }
}

