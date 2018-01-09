package p1;

import java.util.List;

public class Write {
    private List<Article> articles;
    private List<MainObjects> mainObjects;
    private String [] args;

    Write(List<Article> lista,List<MainObjects> listb,String [] arg) throws Exception {
       this.articles = lista;
       this.mainObjects = listb;
        this.args = arg;
        mode(arg);
    }

    public void mode(String [] s) throws Exception {
        if(s[1].startsWith("-t"))
            contents(s[2]);
        else if(s[1].startsWith("-s")) {
            tableOfContents(s[2]);
        }
        else{
            throw new IllegalArgumentException("Zly 2 argument");
        }
        if(!Check.isWrited())
            throw new Exception("wrong third argument");
    }

    public void tableOfContents(String s) throws IllegalArgumentException{
        if(s.charAt(1) == 'd'){
            String nr = "";
            for(int i=2;i<s.length();i++) {
                if(s.charAt(i) == ' ') break;
                nr += s.charAt(i);
            }

            for(int i = 0;i<mainObjects.size();i++) {
                if(mainObjects.get(i).getPrevObject() != null && mainObjects.get(i).getPrevObject().getName().equals(nr)) {
                    mainObjects.get(i).write();
                }
            }
        }
        else if(s.toLowerCase().charAt(1) == 'a') {
            for(int i = 0;i<mainObjects.size();i++) {
                    mainObjects.get(i).write();
            }
        }
        else{
            throw new IllegalArgumentException("Zly trzeci argument");
        }
    }

    private int i = 0;
    public void contents(String s){
        if(s.toLowerCase().charAt(1) == 'd'){
            sectionSearch(s);
        }
        else if(s.toLowerCase().charAt(1) == 'r'){
            String rozdzial;
            rozdzial = getNameOfObject(2,"",s,' ');
            searchChapter(rozdzial);
        }
        else if(s.toLowerCase().charAt(1) == 'a'){
            artileSearch(s);
        }
        else if(s.toLowerCase().charAt(1) == 'z'){
            betweenSearch(s);
        }
        else{
            throw new IllegalArgumentException("BĹ‚Ä™dne argumenty lini poleceĹ„");
        }
    }

    public void sectionSearch(String s){
        String dzial;
        String roz ;
        dzial = getNameOfObject(2,"",s,'r');
        roz = getNameOfObject(i+1,"",s,' ');
        if(roz.equals(""))
            throw new IllegalArgumentException("nie ma argumentu po r");
        for(int i=0;i<articles.size();i++) {
            if (articles.get(i).getDzial().equals(dzial) && articles.get(i).getRozdzial().equals(roz)) {
                articles.get(i).write();
            }
        }
    }

    public void artileSearch(String s){
        String artykul;
        String ustep = null;
        String podpunkt = null;
        String litera = null;

        artykul = getNameOfObject(2,".",s,'u');
        if(artykul.contains("p")) artykul = getNameOfObject(2,".",s,'p');
        if(s.toLowerCase().contains("u"))
            ustep = getNameOfObject(i+1,"",s,'p');
            if(s.toLowerCase().contains("p")){
                podpunkt = getNameOfObject(i+1,"",s,'l');
                if(s.toLowerCase().contains("l")){
                    litera = getNameOfObject(i+1,"",s,' ');
                }
            }
        if(ustep == null && podpunkt == null) {
            searchArt(artykul);
        }
        else if(artykul.equals(".4") && litera != null){
            specialArtLit(litera);
        }
        else
            for(int i = 0;i<articles.size();i++) {
                if(articles.get(i).getName().equals(artykul)) {
                    articles.get(i).search(ustep,podpunkt,litera);
                }
            }

    }

    public void betweenSearch(String s) {
        String art1;
        String art2;
        art1 = getNameOfObject(2,".",s,'-');
        art2 = getNameOfObject(i+1,".",s,' ');
        if(i != s.length())
            throw new IllegalArgumentException("BĹ‚Ä™dny trzeci argument");
        for(int i = 0;i<articles.size();i++) {
            if(articles.get(i).getName().equals(art1)) {
                startsearching(art2,i);
                break;
            }
        }
    }

    public void startsearching(String endingArt,int i) {
        for(int j = i;j<articles.size();j++) {
            articles.get(j).write();
            if(articles.get(j).getName() .equals(endingArt))
                return;
        }
    }

    public String getNameOfObject(int startingIndex,String startingString,String arg,char stoppingChar){
        String result = startingString;
        for(i=startingIndex;i<arg.length();i++){
            if(stoppingChar != ' ' && arg.toLowerCase().charAt(i) == stoppingChar) break;
            result += arg.charAt(i);
        }
        return result;
    }


    public void searchArt(String s){
        for(int x=0;x<articles.size();x++){
            if(articles.get(x).getName().equals(s))
                articles.get(x).write();
        }
    }



    public void searchChapter(String s){
        for(int i=0;i<articles.size();i++) {
            if(articles.get(i).getRozdzial().equals(s))
                articles.get(i).write();
        }
    }

    public void specialArtLit(String litera){
        if(litera.equals("a")){
            System.out.println("a) osobę fizyczną, osobę prawną, a także jednostkę organizacyjną niemającą\n" +
                    "osobowości prawnej, której ustawa przyznaje zdolność prawną,\n" +
                    "organizującą lub świadczącą usługi o charakterze użyteczności publicznej,\n" +
                    "które nie są działalnością gospodarczą w rozumieniu przepisów\n" +
                    "o swobodzie działalności gospodarczej,");
        }
        if(litera.equals("b")){
            System.out.println("b) osobę fizyczną wykonującą zawód we własnym imieniu i na własny\n" +
                    "rachunek lub prowadzącą działalność w ramach wykonywania takiego\n" +
                    "zawodu,");
        }
        if(litera.equals("c")){
            System.out.println("c) osobę fizyczną, która posiada kontrolę, w rozumieniu pkt 4, nad co najmniej\n" +
                    "jednym przedsiębiorcą, choćby nie prowadziła działalności gospodarczej\n" +
                    "w rozumieniu przepisów o swobodzie działalności gospodarczej, jeżeli\n" +
                    "podejmuje dalsze działania podlegające kontroli koncentracji, o której\n" +
                    "mowa w art. 13,");
        }
        if(litera.equals("d")){
            System.out.println("d) związek przedsiębiorców w rozumieniu pkt 2, z wyłączeniem przepisów\n" +
                    "dotyczących koncentracji;");
        }
    }

}

