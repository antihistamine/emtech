package models;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import play.Logger;
import play.db.ebean.Model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

public class Routine extends Model {

    public List<String> buildingBlockNames;
    public String suggestion;
    public String homework;

    public static Routine getRoutine(String feeling, int experienceLevel, int activeLevel){
        Yaml yaml = new Yaml(new Constructor(Routine.class));
        Routine routine;
        String filename = "conf/routine" + feeling + experienceLevel + activeLevel + ".yaml";
        try {
            InputStream inputStream = new FileInputStream(filename);
            routine = (Routine) yaml.load(inputStream);
        } catch (FileNotFoundException exception){
            Logger.error("Missing " + filename);
            return null;
        }
        return routine;
    }


}
