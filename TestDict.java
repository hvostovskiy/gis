import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestDict {

    @Test
    public void testDict() {
        Dict<ComplexKey<Integer, String>, String> dict = new Dict<>();
        for (int i = 0; i < 50; i++) {
            dict.put(new ComplexKey<>(i, "elem"+i), "value"+i);
        }
        ComplexKey<Integer, String> key2 = new ComplexKey<>(2, "elem2");
        dict.put(key2, "value22");
        ComplexKey<Integer, String> key5 = new ComplexKey<>(3810, "qwe3810qwe3810");
        dict.put(key5, "value5");

        assertNotNull(dict);
        assertEquals(51, dict.getSize());
        assertEquals("value22", dict.get(key2));
        assertNotEquals("value2", dict.get(key2));
        assertEquals("value22", dict.getByID(key2.getId()));
        assertEquals("value22", dict.getByName(key2.getName()));
        assertNotEquals("value3810", dict.getByName(key2));

        dict.remove(key5);
        assertEquals(50, dict.getSize());
        dict.remove(key5);
        assertEquals(50, dict.getSize());

        System.out.println(dict);
        for (String entry : dict) {
            System.out.println(entry);
        }
    }
}
