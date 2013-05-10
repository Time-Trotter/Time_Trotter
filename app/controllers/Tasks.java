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
public class Tasks extends Controller {
	
	public static Result view(){
		User user = User.findByEmail(request().username());
//		List<Project> project = Project.findInvolvingByCompany(user.companyId);
		return ok(taskView.render(user, Task.find.all()));
	}

    public static Result add(){
        User user = User.findByEmail(request().username());
        List<Project> project = Project.findInvolvingByCompany(user.companyId);
        Task task = new Task();
        task.title = "New Task";
        task.assignedTo = user;
        task.project = project.get(0);
        task.save();
        return ok(taskView.render(user, Task.find.all()));
    }
}

