package ru.otus.cell;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.banknote.Banknote;
import ru.otus.banknote.BanknoteImpl;
import ru.otus.banknote.DenominationImpl;

class CashCellImplTest {

    private CashCellImpl cashCell;
    private int cellCapacity;

    @BeforeEach
    void setUp() {
        cellCapacity = 5;
        cashCell = new CashCellImpl(cellCapacity);
    }

    @Test
    @DisplayName("add banknote")
    void testAddBanknote() {
        DenominationImpl denomination = new DenominationImpl(100);
        cashCell.setDenomination(denomination);
        Banknote banknote = new BanknoteImpl(denomination);
        cashCell.addBanknote(banknote);
        assertEquals(1, cashCell.getCountBanknotes());
        assertTrue(cashCell.getAllBanknotes().contains(banknote));
    }

    @Test
    @DisplayName("remove banknote")
    void testRemoveBanknote() {
        DenominationImpl denomination = new DenominationImpl(100);
        cashCell.setDenomination(denomination);
        Banknote banknote = new BanknoteImpl(denomination);
        cashCell.addBanknote(banknote);
        Banknote removedBanknote = cashCell.removeBanknote();
        assertEquals(banknote, removedBanknote);
        assertEquals(0, cashCell.getCountBanknotes());
        assertTrue(cashCell.isFree());
    }

    @Test
    @DisplayName("is full cell")
    void testIsFull() {
        DenominationImpl denomination = new DenominationImpl(100);
        cashCell.setDenomination(denomination);
        for (int i = 0; i < cellCapacity; i++) {
            Banknote banknote = new BanknoteImpl(denomination);
            cashCell.addBanknote(banknote);
        }
        assertTrue(cashCell.isFull());
    }

    @Test
    @DisplayName("set free")
    void testSetFree() {
        DenominationImpl denomination = new DenominationImpl(100);
        cashCell.setDenomination(denomination);
        for (int i = 0; i < cellCapacity; i++) {
            Banknote banknote = new BanknoteImpl(denomination);
            cashCell.addBanknote(banknote);
        }
        cashCell.setFree();
        assertNull(cashCell.getDenomination());
        assertTrue(cashCell.isFree());
        assertEquals(0, cashCell.getCountBanknotes());
    }

    @Test
    @DisplayName("set denomination")
    void testSetDenomination() {
        DenominationImpl denomination = new DenominationImpl(100);
        cashCell.setDenomination(denomination);
        assertEquals(denomination, cashCell.getDenomination());
        assertFalse(cashCell.isFree());
    }

    @Test
    @DisplayName("get count banknotes")
    void testGetCountBanknotes() {
        DenominationImpl denomination = new DenominationImpl(100);
        cashCell.setDenomination(denomination);
        List<Banknote> banknotes = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Banknote banknote = new BanknoteImpl(denomination);
            cashCell.addBanknote(banknote);
            banknotes.add(banknote);
        }
        assertEquals(banknotes.size(), cashCell.getCountBanknotes());
    }

    @Test
    @DisplayName("get denomination")
    void testGetDenomination() {
        DenominationImpl denomination = new DenominationImpl(100);
        cashCell.setDenomination(denomination);
        assertEquals(denomination, cashCell.getDenomination());
    }

    @Test
    @DisplayName("is free")
    void testIsFree() {
        DenominationImpl denomination = new DenominationImpl(100);
        cashCell.setDenomination(denomination);
        assertFalse(cashCell.isFree());
    }

    @Test
    @DisplayName("get all banknotes")
    void testGetAllBanknotes() {
        DenominationImpl denomination = new DenominationImpl(100);
        cashCell.setDenomination(denomination);
        List<Banknote> banknotes = new ArrayList<>();
        for (int i = 0; i < cellCapacity; i++) {
            Banknote banknote = new BanknoteImpl(denomination);
            cashCell.addBanknote(banknote);
            banknotes.add(banknote);
        }
        List<Banknote> allBanknotes = cashCell.getAllBanknotes();
        assertEquals(cellCapacity, allBanknotes.size());
        assertTrue(banknotes.containsAll(allBanknotes));
    }
}
