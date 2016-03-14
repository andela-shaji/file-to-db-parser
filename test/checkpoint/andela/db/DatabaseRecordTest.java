package checkpoint.andela.db;

import checkpoint.andela.parser.AttributeValuePair;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by suadahaji.
 */
public class DatabaseRecordTest {

    DatabaseRecord databaseRecord = new DatabaseRecord();

    @Test
    public void testAddColumn() throws Exception {
        AttributeValuePair pair = new AttributeValuePair();
        pair.setKey("TYPES");
        pair.setValue("Small-Molecule-Reactions");
        databaseRecord.addColumn(pair);
        assertEquals(databaseRecord.getDbRecordSize(), 1);
    }

    @Test
    public void testGetdbRecordSize() throws Exception {
        AttributeValuePair pair1 = new AttributeValuePair();
        pair1.setKey("TYPES");
        pair1.setValue("Chemical-Reactions");
        databaseRecord.addColumn(pair1);
        assertEquals(databaseRecord.getDbRecordSize(), 1);

        AttributeValuePair pair2 = new AttributeValuePair();
        pair2.setKey("CREDITS");
        pair2.setValue("|kaipa|");
        databaseRecord.addColumn(pair2);
        assertTrue(databaseRecord.getDbRecordSize() == 2);
    }

    @Test
    public void testGetUniqueId() throws Exception {
        AttributeValuePair pair3 = new AttributeValuePair();
        pair3.setKey("TYPES");
        pair3.setValue("Small-Molecule-Reactions");
        databaseRecord.addColumn(pair3);

        AttributeValuePair pair4 = new AttributeValuePair();
        pair4.setKey("ENZYMATIC-REACTION");
        pair4.setValue("ENZRXNMT2-14");
        databaseRecord.addColumn(pair4);

        AttributeValuePair pair5 = new AttributeValuePair();
        pair5.setKey("UNIQUE-ID");
        pair5.setValue("6-PHOSPHO-BETA-GALACTOSIDASE-RXN");
        databaseRecord.addColumn(pair5);

        assertTrue(databaseRecord.getUniqueId() == "6-PHOSPHO-BETA-GALACTOSIDASE-RXN");
    }
}