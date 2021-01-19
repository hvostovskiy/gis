import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestDict {
    @Test
    public void testDict() {
        Dict<ComplexKey<Integer, String>, String> dict = new Dict<>();
        dict.put(new ComplexKey<>(1, "elem1"), "value1");
        ComplexKey<Integer, String> key2 = new ComplexKey<>(2, "elem2");
        dict.put(key2, "value2");
        dict.put(new ComplexKey<>(3, "elem3"), "value3");
        dict.put(new ComplexKey<>(4, "elem4"), "value4");
        dict.put(new ComplexKey<>(3810, "qwe3810qwe3810qwe3810qwe3810"), "value5");

        assertNotNull(dict);
        assertEquals(5, dict.getSize());
        assertEquals("value2", dict.get(key2));

    }
}
