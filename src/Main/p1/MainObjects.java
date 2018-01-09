package p1;

import java.util.ArrayList;
import java.util.List;

public class MainObjects {
    private List<String> content = new ArrayList<>();  //tytuly rozdzialow lub dzialow
    private Type type;
    private String name;
    private MainObjects prevObject = null;    // jesli rozdzial ma jakis dzial nad soba tj np rozdzial 2 jest w dziale 1
    //to dla obiektu type = rozdzial i name = 2 to prevObject = 1 a jesli prevOBject = null to znaczy ze to jest dzial
    // albo rozdzial nie ma nad soba  dzialow

    MainObjects(Type type,String name)
    {
        this.type = type;
        this.name = name;
    }

    public void setPrevObject(MainObjects prev){
        this.prevObject = prev;
    }

    public void addContent(String s){
        content.add(s);
    }

    public Type getType() {
        return type;
    }

    public void write()
    {
        Check.set();
        System.out.println(type + "  " + name);
        for(int i=0;i<content.size();i++){
            System.out.println("   " + content.get(i));
        }
    }

    public String getName() {
        return name;
    }

    public MainObjects getPrevObject() {
        return prevObject;
    }
}
