package checkpoint.andela.parser;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by suadahaji.
 */
public class AttributeValuePairTest {
    AttributeValuePair pair = new AttributeValuePair();

    @Before
    public void setUp() throws Exception {
        String string1 = "UNIQUE-ID - ALKAPHOSPHA-RXN";
        String string2 = "TYPES - Small-Molecule-Reactions";
        String string3 = "ATOM-MAPPINGS - (:NO-HYDROGEN-ENCODING (2 6 0 3 1 4 5) (((WATER 0 0) (|Orthophosphoric-Monoesters| 1 6)) ((|Alcohols| 0 1) (|Pi| 2 6))))";
        String string4 = "CREDITS - SRI";
    }

    @Test
    public void testSetKey() throws Exception {

        assertEquals(pair.getKey(), null);
        pair.setKey("TYPES");
        assertEquals(pair.getKey(), "TYPES");
    }

    @Test
    public void testGetValue() throws Exception {
        assertEquals(pair.getValue(), null);
        pair.setValue("SRI");
        assertEquals(pair.getValue(), "SRI");
    }
}