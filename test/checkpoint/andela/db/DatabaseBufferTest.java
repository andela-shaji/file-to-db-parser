package checkpoint.andela.db;

import checkpoint.andela.parser.AttributeValuePair;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by suadahaji.
 */
public class DatabaseBufferTest {

    DatabaseBuffer databaseBuffer = new DatabaseBuffer();

    @Test
    public void testAddRow() throws Exception {
        AttributeValuePair pair = new AttributeValuePair();
        pair.setKey("TYPES");
        pair.setValue("Small-Molecule-Reactions");
        databaseBuffer.addRow(pair);
        assertEquals(databaseBuffer.getdbRecordSize(), 1);
    }

    @Test
    public void testGetdbRecordSize() throws Exception {
        AttributeValuePair pair1 = new AttributeValuePair();
        pair1.setKey("TYPES");
        pair1.setValue("Chemical-Reactions");
        databaseBuffer.addRow(pair1);
        assertEquals(databaseBuffer.getdbRecordSize(), 1);

        AttributeValuePair pair2 = new AttributeValuePair();
        pair2.setKey("CREDITS");
        pair2.setValue("|kaipa|");
        databaseBuffer.addRow(pair2);
        assertTrue(databaseBuffer.getdbRecordSize() == 2);
    }

    @Test
    public void testGetUniqueId() throws Exception {
        AttributeValuePair pair3 = new AttributeValuePair();
        pair3.setKey("TYPES");
        pair3.setValue("Small-Molecule-Reactions");
        databaseBuffer.addRow(pair3);

        AttributeValuePair pair4 = new AttributeValuePair();
        pair4.setKey("ENZYMATIC-REACTION");
        pair4.setValue("ENZRXNMT2-14");
        databaseBuffer.addRow(pair4);

        AttributeValuePair pair5 = new AttributeValuePair();
        pair5.setKey("UNIQUE-ID");
        pair5.setValue("6-PHOSPHO-BETA-GALACTOSIDASE-RXN");
        databaseBuffer.addRow(pair5);

        assertTrue(databaseBuffer.getUniqueId() == "6-PHOSPHO-BETA-GALACTOSIDASE-RXN");
    }
}