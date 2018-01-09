package p1;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public  class Parser {

    private  BufferedReader scanner;
    private List<MainObjects> mainObjects = new ArrayList<>(); //dzial albo rozdzial to jest
    private  List<Article> articles = new ArrayList<>();
    private  Object last;

    Parser(){}

    Parser(String [] args) throws IOException
    {
        parse(args);
    }

    public  void parse(String [] args) throws IOException
    {


        if (args.length != 3) throw new IllegalArgumentException("Nie poprawna la komend");
        for (int i = 0; i < args.length; i++)
            if (args[i].charAt(0) != '-') throw new IllegalArgumentException("Nie poprawinia komend");
        try {
            scanner = new BufferedReader(new InputStreamReader(new FileInputStream(args[0].substring(1, args[0].length())), "UTF8"));
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("Nie można odnależć określonego pliku");
        }
        makeStruct();
        if (scanner != null) {
            scanner.close();
        }
    }

    public  void makeStruct()
    {

        try {
            String line = "";
            while (!line.startsWith("Rozdział") && !line.startsWith("DZIAŁ")) //Zmienic na windows 1250
                line = scanner.readLine();
            while (scanner.ready()){
                if (toCansel(line)){
                    line = scanner.readLine();
                    continue;
                }
                addToList(line);
                line = scanner.readLine();
            }
            addToList(line);

        } catch (IOException e) {
            e.printStackTrace();
        }
        catch(IllegalArgumentException e){
            throw new IllegalArgumentException("Sprawdz kodowanie polskich znakow");
        }
        catch(NullPointerException e){
            e.getMessage();
        }

    }

    private  boolean dzial = false;
    private String nrdzialu = null;
    private String nrrozdzialu = null;
    private Type lastType = null;
    private SmallObject lastLitera = null;
    private SmallObject lastPunkt = null;
    private SmallObject lastPodpunkt = null;

    public  void addToList(String string)
    {

//        if(string.startsWith("wykonywania praw, o których mowa w ust. 1;")){
//            System.out.println(articles.get(articles.size()-1).cubes.get(1));
//        }
        if(string.startsWith("DZIA")){
            nrdzialu = getNameOfDzial(string); //zwaraca nr dzialu nr dzial II zwroci 2
            lastType = Type.Dzial;
            mainObjects.add(new MainObjects(lastType,nrdzialu));
        }
        else if(string.toLowerCase().startsWith("Rozdział".toLowerCase())) {
            nrrozdzialu = getNameOfRozdzial(string);  //to samo co wyzzej
            lastType = Type.Rozdzial;
            mainObjects.add(new MainObjects(lastType, nrrozdzialu));
            mainObjects.get(mainObjects.size() - 1).setPrevObject(findLastDzial()); //od tylu iteruje po mainObject az type bedzie rowny 'DZIAL'
            //(szuka ostatniego dzialu) lub az ccalej listy nie przejdzie wtedy zwraca null
        }
        else if(point(string) || sameLine(string)){
            if(string.startsWith("Art.")){
                setNull();
                articles.add(new Article(string,nrdzialu,nrrozdzialu));
                articles.get(articles.size()-1).removeLastConetent();
            }
            lastType = Type.Punkt;
            SmallObject object = new SmallObject(Type.Punkt,getNameOfPoint(string));
            lastPunkt = object;
            articles.get(articles.size()-1).addToSmallObject(object);
            object.addText(string);
        }
        else if(string.startsWith("Art.")){
              setNull();
              Article article = new Article(string,nrdzialu,nrrozdzialu);
              articles.add(article);
              lastType = Type.Artykul;
        }
        else if(underPoint(string)) {
            lastType = Type.Podpunkt;
            SmallObject smallObject = new SmallObject(Type.Podpunkt,getNameOfPodpunkt(string));
            lastPodpunkt = smallObject;
            if(searchToAdd(lastType) == null)
                articles.get(articles.size()-1).addToSmallObject(smallObject);
            else
                searchToAdd(lastType).addSmallObject(smallObject);
            smallObject.addText(string);
        }
        else if(letter(string)){
            lastType = Type.Litera;
            SmallObject smallObject = new SmallObject(Type.Litera, getNameOfLetter(string));
            lastLitera = smallObject;
            if(searchToAdd(lastType) == null)
                articles.get(articles.size() - 1).addToSmallObject(smallObject);
            else
                searchToAdd(lastType).addSmallObject(smallObject);
            smallObject.addText(string);
        }
        else{
            if(lastType == Type.Artykul){
                articles.get(articles.size()-1).addToText(string);
            }
            if(lastType == Type.Rozdzial || lastType == Type.Dzial){
                mainObjects.get(mainObjects.size()-1).addContent(string);
            }
            else{
                if( articles.get(articles.size()-1).searchSmallObject(lastType) != null) {
                    if (lastType == Type.Litera)
                        lastLitera.addText(string);
                    else
                        articles.get(articles.size() - 1).searchSmallObject(lastType).addText(string);
                }
            }
        }
    }

    public SmallObject searchToAdd(Type type){
        if(type == Type.Podpunkt)
            return lastPunkt;
        if(type == Type.Litera){
            if(lastPodpunkt != null)
                return lastPodpunkt;
            else
                return lastPunkt;
        }
        return null;
    }

    public String getNameOfDzial(String s){
        int i = 0;
        while(s.charAt(i) != ' ')
            i++;
        while(s.charAt(i) == ' ')
            i++;
        String result = "";
        while(i < s.length() && s.charAt(i) != ' ')
        {
            result += s.charAt(i);
            i++;
        }
        return Arab.convert(result);
    }

    public void setNull(){
        lastLitera = null;
        lastPodpunkt = null;
        lastPunkt = null;
    }

    public String getNameOfRozdzial(String s){
        return getNameOfDzial(s);
    }

    public MainObjects findLastDzial()
    {
        MainObjects result = null;
        for(int i = mainObjects.size()-1;i>=0;i--){
            if(mainObjects.get(i).getType() == Type.Dzial){
                result = mainObjects.get(i);
                break;
            }
        }
        return result;
    }


    public String getNameOfPoint(String s)
    {
        // punkt to moze byc np 2. i ma wtedy zwracac 2 jako string
        //TODO

        String result = "";
        int i = 0;
        if(s.charAt(i) == 'A')
        {
            i=5;
            while(s.charAt(i) != '.')
                i++;
            i+=2;
            while(s.charAt(i) != '.'){
                result += s.charAt(i);
                i++;
            }
        }
        else{
            while(s.charAt(i) != '.'){
                result += s.charAt(i);
                i++;
            }
        }

        return result;
    }

    // konwertuje
    // int liczbaInt = 12;
    // String liczbaString = Integer.toString(liczbaInt);

    public String getNameOfPodpunkt(String s)
    {
        String result = "";
        int i=0;
        while(s.charAt(i) != ')'){
            result += s.charAt(i);
            i++;
        }
        return result;
    }


    public String getNameOfLetter(String s)
    {
        // np 2a) i zwraca w tedy 2a
        //TODO

        int i = 0;
        String result = "";
        while(s.charAt(i) == ' ' && i < s.length()) i++;
        while(!isLetter(s.charAt(i)) && i < s.length())
        {
            result = result + s.charAt(i);
            i++;
        }
        while(isLetter(s.charAt(i)) && i < s.length())
        {
            result = result + s.charAt(i);
            i++;
        }
        return result;
    }



    public List<Article> getArticles(){
        return articles;
    }

    public  boolean toCansel(String line)
    {

        if(line == null) return false;
        if(line.length() > 7 && line.charAt(4) == '-' && line.charAt(7) == '-') return true;
        if(line.contains("Kancelaria Sejmu")) return true;  //?>??????????
        return false;
    }

    public  boolean sameLine(String string)
    {
        if(!string.startsWith("Art")) return false;
        int i = 5;
        while(i < string.length() && string.charAt(i) != '.')
            i++;
        i++;
        for(int j=0;j<4;j++){
            if(i == string.length()) return false;
            if(string.charAt(i+j) == '.') return true;
        }
        return false;
    }

    public  boolean point(String string)
    {
        if(isLetter(string.charAt(0))) return false;
        for(int i=0;i<3;i++){
            if(string.length() == i) break;
            if(string.charAt(i) == '.') return true;
        }
        return false;
    }

    public  boolean underPoint(String string)
    {
        if(isLetter(string.charAt(0))) return false;
        for(int i=0;i<3;i++){
            if(string.length() == i) break;
            if(string.charAt(i) == ')') return true;
        }
        return false;
    }

    public boolean letter(String string)
    {
        boolean contain = false;
        for(int i=0;i<5;i++){
            if(string.length() == i) break;
            if(isNumber(string.charAt(i))) return false;
            if(isLetter(string.charAt(i))) contain = true;
            if(string.charAt(i) == ')' && contain) return true;
        }
        return false;
    }

    public  boolean isLetter(char c)
    {
        if(c >= 'a' && c <= 'z') return true;
        return false;
    }

    public  List<MainObjects> getMainList() {
        return mainObjects;
    }

    public  boolean isNumber(char c)
    {
        if(c > 47 && c < 58) return true;
        return false;
    }
}
