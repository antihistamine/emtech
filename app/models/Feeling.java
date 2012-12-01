package models;

import org.joda.time.DateTime;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Feeling extends Model {

    @Id
    int feelingId;

    @Temporal(TemporalType.TIMESTAMP)
    public DateTime checkInDateTime;
    public String feeling;

    @ManyToOne
    public User user;

    public static Finder<String,Feeling> find = new Finder(
            String.class, Feeling.class
    );

    public static Feeling getLastFeeling(User user) {
        List<Feeling> feelingList = Feeling.find.where()
                .eq("User_user_ID",user.userId)
                .orderBy("Check_In_Date_Time DESC")
                .findList();

        System.out.println("Got " + Integer.toString(feelingList.size()) + " records. " );

        if (feelingList.size() > 0){
            return feelingList.get(0);

        } else {
            return null;
        }
    }

/*
    @ManyToOne
    public User user;
 */
    /*


    public Feeling(String feeling)  {
        this.checkInDateTime = DateTime.now();
        this.feeling = feeling;
    }

    public Feeling(String f, DateTime d)   {
        this.checkInDateTime = d;
        this.feeling = f;
    }

    public DateTime getCheckInDateTime() {
        return checkInDateTime;
    }

    public void setCheckInDateTime(DateTime checkInDateTime) {
        this.checkInDateTime = checkInDateTime;
    }

    public String getFeeling() {
        return feeling;
    }

    public void setFeeling(String feeling) {
        this.feeling = feeling;
    }

    */
}
