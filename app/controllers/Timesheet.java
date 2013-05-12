package controllers;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import play.*;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;

import views.html.*;
import models.*;

@Security.Authenticated(Secured.class)
public class Timesheet extends Controller{
	
	static Calendar startCal1 = Calendar.getInstance();
	
	public static Result time() {
		
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
		Calendar startCal = Calendar.getInstance();
		Calendar weekFirstDayCalObj = Calendar.getInstance();
		weekFirstDayCalObj.set(Calendar.DAY_OF_WEEK, weekFirstDayCalObj.getFirstDayOfWeek());	
		startCal.setTime(weekFirstDayCalObj.getTime());
    	return ok(time.render(User.findByEmail(request().username()), startCal, "Enter Time"));
    }
	
    public static Result approvedTime() {
    	return ok(time.render(User.findByEmail(request().username()), startCal1, "Approved Time"));
    }
    
    public static Result pendingTime() {
    	return ok(time.render(User.findByEmail(request().username()), startCal1, "Pending Time"));
    }
    
    public static Result rejectedTime() {
    	return ok(time.render(User.findByEmail(request().username()), startCal1, "Rejected Time"));
    }
    
    public static Result approveTime() {
    	return ok(time.render(User.findByEmail(request().username()), startCal1, "Approve Time"));
    }
    
}