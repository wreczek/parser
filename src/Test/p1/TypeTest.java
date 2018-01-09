package p1;

import org.junit.Test;
import static junit.framework.TestCase.assertEquals;

public class TypeTest {
    @Test
    public void nextTest(){
        assertEquals(Type.Podpunkt,Type.Punkt.next());
        assertEquals(Type.Dzial,Type.Litera.next());
        assertEquals(Type.Rozdzial,Type.Dzial.next());
    }
    @Test
    public void prevTest(){
        assertEquals(Type.Podpunkt,Type.Litera.prev());
        assertEquals(Type.Dzial,Type.Dzial.prev());
        assertEquals(Type.Artykul,Type.Punkt.prev());
    }
}
