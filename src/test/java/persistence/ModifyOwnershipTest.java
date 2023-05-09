package persistence;

import entity.Own;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.testng.AssertJUnit.assertEquals;

class ModifyOwnershipTest {
    private TrekDao<Own> ownDao;
    private ModifyOwnership modifyOwnershipUnderTest;

    @BeforeEach
    void setUp() throws Exception {
        ownDao = new TrekDao<>(Own.class);
        modifyOwnershipUnderTest = new ModifyOwnership();
    }

    @Test
    void testAddNewSeasonsToCollection() {
        // Setup
        ArrayList<Own> allOwns = (ArrayList<Own>) ownDao.getAll();

        // Run the test
        int preTest = allOwns.size();
        modifyOwnershipUnderTest.addNewSeasonsToCollection(new ArrayList<>(List.of("1", "2", "3")), "1");
        ArrayList<Own> allPlusNewOwns = (ArrayList<Own>) ownDao.getAll();
        int postTest = allPlusNewOwns.size();

        // Verify the results
        assertNotEquals(preTest, postTest);
        assertEquals(3, postTest - preTest);
    }

    @Test
    void testRemoveOwnedSeasonsFromCollection() {
        // Setup
        ArrayList<Own> allOwns = (ArrayList<Own>) ownDao.getAll();

        // Run the test
        int preTest = allOwns.size();
        modifyOwnershipUnderTest.removeOwnedSeasonsFromCollection(new ArrayList<>(List.of("1", "2", "3")), "1");
        ArrayList<Own> allMinusNewOwns = (ArrayList<Own>) ownDao.getAll();
        int postTest = allMinusNewOwns.size();

        // Verify the results
        assertNotEquals(preTest, postTest);
        assertEquals(3, preTest - postTest);
    }
}