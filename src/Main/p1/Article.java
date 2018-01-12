package p1;

import java.util.ArrayList;
import java.util.List;



public class Article
{
    private List<String> text = new ArrayList<>();
    private List<SmallObject> cubes = new ArrayList<>();  //zrobic private
    private String name;
    private Type type;
    private String dzial;
    private String rozdzial;

    Article(){}

    Article (String string,String dzial,String rozdzial)
    {
        this.dzial = dzial;
        this.rozdzial = rozdzial;
        type = Type.Artykul;
        name = cutName(string);
        text.add(getContent(string));
    }

    public void removeLastConetent(){
        text.remove(text.size()-1);
    }

    public String getDzial() {
        return dzial;
    }

    public String getRozdzial() {
        return rozdzial;
    }

    public String getContent(String string){
        // wycina tekst ale tylko podczas tworzenia obiektu
        return string;
    }

    public String cutName(String string){

        String result = ".";
        for(int i = 5;i<string.length();i++){
            if(string.charAt(i) == '.') break;
            result += string.charAt(i);
        }
        return result;
    }

    public void addToText(String s){
        text.add(s);
    }

    public void addToSmallObject(SmallObject object){
        cubes.add(object);
    }

    public SmallObject searchSmallObject(Type type){
        if(cubes.size() == 0 || this.type == type) return null;
        return cubes.get(cubes.size()-1).searchSmallObject(type);
    }

    public void write(){
        Check.set();
          for(int i=0;i<text.size();i++)
            System.out.println(text.get(i));
        for(int i = 0;i<cubes.size();i++)
            cubes.get(i).write();
    }

    public String getName() {
        return name;
    }

    public void search(String punkt,String podpunkt,String litera){
        for(int i = 0;i<cubes.size();i++)
            cubes.get(i).search(punkt,podpunkt,litera);
    }
}
