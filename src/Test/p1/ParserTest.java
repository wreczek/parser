package p1;

import org.junit.Test;

import java.lang.reflect.Parameter;

import static junit.framework.TestCase.assertEquals;

public class ParserTest {
    @Test
    public void getNameOfPointTest(){
        assertEquals(new Parser().getNameOfPoint("6. W przypadku uchylenia bądź zmiany prawomocnej decyzji, których"),"6");
        assertEquals(new Parser().getNameOfPoint("Art. 113. 1. Prezes Urzędu może na wniosek przedsiębiorcy, osoby"),"1");
        assertEquals(new Parser().getNameOfPoint("Art. 113c. 1. W przypadku gdy przedsiębiorca, który zawarł porozumienie,"),"1");
    }

    @Test
    public void getNameOfPodpunktTest(){
        assertEquals(new Parser().getNameOfPodpunkt("1) pierwszy wniosek – obniża wysokość kary pieniężnej nakładanej na tego"),"1");
        assertEquals(new Parser().getNameOfPodpunkt("5) czasu trwania porozumienia;"),"5");
    }

    @Test
    public void getNameOfLetterTest(){
        assertEquals(new Parser().getNameOfLetter("d) umyślność naruszenia;"),"d");
        assertEquals(new Parser().getNameOfLetter("a) działanie pod przymusem,"),"a");
        assertEquals(new Parser().getNameOfLetter("f) działanie pod przymusem – w przypadku naruszenia zakazu porozumień"),"f");
    }

    @Test
    public void sameLineTest(){
        assertEquals(new Parser().sameLine("Art. 109. (uchylony)"),false);
        assertEquals(new Parser().sameLine("Art. 111. 1. Prezes Urzędu, ustalając wysokość nakładanej kary pieniężnej,"),true);
        assertEquals(new Parser().underPoint("1) umów, w szczególności licencji, a także innych niż umowy praktyk"),true);
    }
}
