package models;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import play.Logger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class BuildingBlock {
    int buildingBlockId;

    public String name;
    public String description;

    public String videoURI;
    public List<Concept> concepts;

    public static List<BuildingBlock> getRoutine(List<String> blockNames)  {

        Yaml yaml = new Yaml(new Constructor(BuildingBlock.class));
        ArrayList<BuildingBlock> buildingBlocks;
        buildingBlocks = new ArrayList<BuildingBlock>();

        for (int i=0;i<blockNames.size();i++) {
            String filename = "conf/block" + blockNames.get(i) + ".yaml";
            try {
                InputStream inputStream = new FileInputStream(filename);
                buildingBlocks.add((BuildingBlock) yaml.load(inputStream));
            } catch (FileNotFoundException exception){
                Logger.error("Missing " + filename);
            }
        }
        return buildingBlocks;

    }

}





