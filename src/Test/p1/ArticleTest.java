package p1;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class ArticleTest {
    @Test
    public void getNameTest(){
        assertEquals(new Article().cutName("Art. 42."),".42");
        assertEquals(new Article().cutName("Art. 111. 1. Prezes Urzędu, ustalając wysokość nakładanej kary pieniężnej,"),".111");
    }
}
