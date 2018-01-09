package p1;

import java.util.ArrayList;
import java.util.List;

import static p1.Arab.isLetter;
import static p1.Arab.isNumber;
import static p1.Parser.*;

public class SmallObject {// to bedzie punkt podpunkt lub litera
    private List<String> text = new ArrayList<>();
    private String name;
    List<SmallObject> cubes = new ArrayList<>();  //zrobic private
    private Type type;
    private boolean blockWrite = false;

    SmallObject(){
    }

    SmallObject(Type type, String name) {
        this.type = type;
        this.name = name;
    }

    public SmallObject lastObject(Type typeToStop) {
        if (cubes.size() == 0 || typeToStop == type) return this;
        if (cubes.get(cubes.size() - 1).lastObject(typeToStop) == null) return this;
        return cubes.get(cubes.size() - 1).lastObject(typeToStop);
    }

    public void addSmallObject(SmallObject object) {
        cubes.add(object);
    }

    public SmallObject searchSmallObject(Type type) {
        if (this.type == type)
            return this;
        return cubes.get(cubes.size() - 1).searchSmallObject(type);
    }

    public void addText(String s) {
        text.add(s);
    }

    public void write() {
        Check.set();
        if(!blockWrite) {
            for (int i = 0; i < text.size(); i++) {
                System.out.println(text.get(i));
            }
            for (int i = 0; i < cubes.size(); i++) {
                cubes.get(i).write();
            }
        }
        blockWrite = true;
    }

    public boolean search(String punkt, String podpunkt, String litera) {
        if (type == Type.Punkt && punkt != null && punkt.equals(name)) {
            return searchHelper2018(1,punkt, podpunkt, litera);
        } else if (type == Type.Podpunkt && podpunkt != null &&  podpunkt.equals(name)) {
            return searchHelper2018(2,punkt, podpunkt, litera);
        } else if (type == Type.Litera && litera != null && litera.equals(name)){
            write();
            return true;
        }
        return false;
    }

    public boolean searchHelper2018(int x,String punkt, String podpunkt, String litera) {
        if (cubes.size() == 0) {
            write();
            return true;
        } else {
            if(x == 1 && podpunkt == null)
                write();
            else{
                    for (int i = 0; i < cubes.size(); i++)
                        cubes.get(i).search(punkt, podpunkt, litera);
                }
            if(x == 2 && litera == null)
                write();
            else {
                for(int i=0;i<cubes.size();i++)
                    cubes.get(i).search(punkt, podpunkt, litera);
            }
        }
        return true;
    }

    public String getName() {
        return name;
    }

    public String test(){
        return text.get(0);
    }
}