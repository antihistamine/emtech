package controllers;

import models.BuildingBlock;
import models.Feeling;
import models.Routine;
import models.User;
import org.joda.time.DateTime;
import play.Logger;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

import java.util.List;

public class Application extends Controller {

	static Form<User> userForm = form(User.class);
    static Form<Feeling> feelingForm = form(Feeling.class);

 	public static Result index() {
    	return ok(index.render());
  	}

  	public static Result register() {
		return ok(invite.render(userForm));
	}

	public static Result processRegistration() {
		Form<User> filledForm = userForm.bindFromRequest();
		if(filledForm.hasErrors()) {
		    return badRequest(
		      invite.render(filledForm)
		    );
		} else {
			try {
				User.create(filledForm.get());
				session("connected",filledForm.get().email);
				return ok("Thanks");
			} catch(Exception e) {
				return ok("User already Exists");
			}
		}
	}

	public static Result processLogin() {
		Form<User> filledForm = userForm.bindFromRequest();
		if(filledForm.hasErrors()) {
			return badRequest(
				invite.render(filledForm)
			);
		} else {
			User login = User.getUser(filledForm.get().email);

			if (login == null) {
				return ok("User doesn't exist");
			} else {
				session("connected",filledForm.get().email);
				return redirect(routes.Application.map());
			}
		}
	}

	public static Result map() {
		User user = User.getUser(session("connected"));
        Feeling lastFeeling = Feeling.getLastFeeling(user);
		return ok(map.render(user,lastFeeling));
	}

    public static Result howFeeling() {
        return ok(howFeeling.render(feelingForm));
    }

    public static Result processHowFeeling() {
        Form<Feeling> filledForm = feelingForm.bindFromRequest();
        if(filledForm.hasErrors()) {
            return badRequest(
                    howFeeling.render(filledForm)
            );
        } else {
            User user = User.getUser(session("connected"));
          //  user.feelings.add(filledForm.get());
            session("feeling",filledForm.get().feeling);

            filledForm.get().user = user;
            filledForm.get().checkInDateTime = DateTime.now();
            filledForm.get().save();

            Routine todayRoutine = Routine.getRoutine(filledForm.get().feeling,user.skillLevel,user.activityLevel);

            List<BuildingBlock> buildingBlockList = BuildingBlock.getRoutine(todayRoutine.buildingBlockNames);
            Logger.info("Loaded Routines" + todayRoutine.buildingBlockNames + " " + Integer.toString(buildingBlockList.size()) + " BuildingBlocks");

            return ok(routine.render(todayRoutine,buildingBlockList));
//            return ok(routine.render(program));
        }
    }
}
