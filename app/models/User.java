package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

@Entity
public class User extends Model {

	@Id
    int userId;

	public String email;
	public String password;
	public int activityLevel;
	public int skillLevel;
/*
    @OneToMany
    private List<models.Feeling> feelings;
 */

	public static Finder<String,User> find = new Finder(
		String.class, User.class
	);

    public static User getUser(String email) {
        List<User> usercheck = User.find.where()
                .startsWith("email",email)
                .findList();
        if (usercheck.size() > 0) {
            return usercheck.get(0);
        }
        else
            return null;
    }

	public static void create(User user) throws Exception {
		User usercheck = User.getUser(user.email);

		if (usercheck != null) {
			throw new Exception();
		}
		user.activityLevel=1;
		user.skillLevel=1;
/*
        Feeling f = new Feeling();
        f.feeling = "Good";
        f.checkInDateTime = DateTime.now();

        user.feelings.add(f);
*/
        user.save();
	}
/**
    public List<Feeling> getFeelings() {
        return feelings;
    }

    public void setFeelings(List<Feeling> feelings) {
        this.feelings = feelings;
    }

    public User(int user_id, String email, String password, int activityLevel, int skillLevel, List<Feeling> feelings) {
        this.user_id = user_id;
        this.email = email;
        this.password = password;
        this.activityLevel = activityLevel;
        this.skillLevel = skillLevel;
        this.feelings = feelings;
    }
        */
    /*
        public static User authenticate(String email, String password) {
            return find("byEmailAndPassword", email, password).first();
        }

    */
}
