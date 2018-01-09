package p1;

import java.io.IOException;

public class Projekt1 {
    /**
     *
     *
     *  TRYB to  -s  lub -t
     *
     *  Opcje Trybu odpowiednio dla:
     *     -s)  -d(nr)  (gdzie nr to numer dzialu do wypisania spisu) lub -A (wypisywanie calego spisu tresci)
     *     -t)  -A(nr)  (gdzie nr to numer jednego artykulu do wypisania) lub -z(nr1)-(nr2) (gdzie nr1 do nr2 to zakresy artykolow do wypisania)
     *          -R(nr) (gdzie nr to nr rozdzialu do wypisanie i tekst nie ma dzialow) lub -D(nr1)R(nr2) (gdzie nr1 to numer dzialu a nr2 to numer rozdzialu)
     *          -A(nr_artykulu)U(nr_ustepu)P(nr_punktu)_L(nr_litery)
     */


    public static void main(String [] args) throws IOException,IllegalArgumentException {
       try {
            new Write(new Parser(args).getArticles(),new Parser(args).getMainList(), args);
        }
        catch(Exception e){ System.out.println(e.getMessage());
        }
    }
}
