package ru.otus.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.banknote.Banknote;
import ru.otus.banknote.BanknoteImpl;
import ru.otus.banknote.Denomination;
import ru.otus.banknote.DenominationImpl;
import ru.otus.cell.CashCell;

class CashCellServiceImplTest {

    private CashCellServiceImpl cashCellService;
    private List<CashCell> cells;
    private List<Denomination> availableDenominations;

    @BeforeEach
    void setUp() {
        cashCellService = new CashCellServiceImpl();
        cells = new ArrayList<>();
        availableDenominations = Arrays.asList(
                new DenominationImpl(100),
                new DenominationImpl(200),
                new DenominationImpl(500),
                new DenominationImpl(1000),
                new DenominationImpl(5000));
    }

    @Test
    void initCells() {
        cashCellService.initCells(cells, 3, 10);
        assertEquals(3, cells.size());
        for (CashCell cell : cells) {
            assertTrue(cell.isFree());
        }
    }

    @Test
    void getBalance() {
        List<Banknote> banknotes = Arrays.asList(
                new BanknoteImpl(new DenominationImpl(100)),
                new BanknoteImpl(new DenominationImpl(500)),
                new BanknoteImpl(new DenominationImpl(1000)));

        cashCellService.initCells(cells, 3, 10);
        cashCellService.takeForStorage(banknotes, cells, availableDenominations);
        assertEquals(1600, cashCellService.getBalance(cells));
    }

    @Test
    void takeForStorage() {
        cashCellService.initCells(cells, 3, 10);
        List<Banknote> banknotes = Arrays.asList(
                new BanknoteImpl(new DenominationImpl(100)),
                new BanknoteImpl(new DenominationImpl(500)),
                new BanknoteImpl(new DenominationImpl(1000)),
                new BanknoteImpl(new DenominationImpl(3000)));
        Optional<List<Banknote>> notAvailableBanknotes =
                cashCellService.takeForStorage(banknotes, cells, availableDenominations);
        assertTrue(notAvailableBanknotes.isPresent());
        assertEquals(1, notAvailableBanknotes.get().size());
        assertEquals(3000, notAvailableBanknotes.get().get(0).getDenomination().ammount());
    }

    @Test
    void takeForStorage_withoutFreeCells() {

        List<Banknote> cash = Arrays.asList(
                new BanknoteImpl(new DenominationImpl(100)),
                new BanknoteImpl(new DenominationImpl(200)),
                new BanknoteImpl(new DenominationImpl(500)),
                new BanknoteImpl(new DenominationImpl(1000)),
                new BanknoteImpl(new DenominationImpl(5000)),
                new BanknoteImpl(new DenominationImpl(100)),
                new BanknoteImpl(new DenominationImpl(100)));

        cashCellService.initCells(cells, 5, 1);

        Optional<List<Banknote>> notAvailableBanknotes =
                cashCellService.takeForStorage(cash, cells, availableDenominations);
        assertTrue(notAvailableBanknotes.isPresent());
        assertEquals(2, notAvailableBanknotes.get().size());
        assertEquals(
                200,
                notAvailableBanknotes.get().stream()
                        .mapToInt(b -> b.getDenomination().ammount())
                        .sum());
    }

    @Test
    void releaseFromStorage() {
        List<Banknote> cash = Arrays.asList(
                new BanknoteImpl(new DenominationImpl(100)),
                new BanknoteImpl(new DenominationImpl(100)),
                new BanknoteImpl(new DenominationImpl(100)),
                new BanknoteImpl(new DenominationImpl(100)),
                new BanknoteImpl(new DenominationImpl(100)),
                new BanknoteImpl(new DenominationImpl(500)),
                new BanknoteImpl(new DenominationImpl(500)),
                new BanknoteImpl(new DenominationImpl(500)),
                new BanknoteImpl(new DenominationImpl(500)),
                new BanknoteImpl(new DenominationImpl(1000)),
                new BanknoteImpl(new DenominationImpl(1000)),
                new BanknoteImpl(new DenominationImpl(1000)),
                new BanknoteImpl(new DenominationImpl(1000)));

        cashCellService.initCells(cells, 30, 2);
        cashCellService.takeForStorage(cash, cells, availableDenominations);

        Optional<List<Banknote>> banknotes = cashCellService.releaseFromStorage(1600, availableDenominations, cells);
        assertTrue(banknotes.isPresent());
        assertEquals(3, banknotes.get().size());
        assertEquals(1000, banknotes.get().get(0).getDenomination().ammount());
        assertEquals(500, banknotes.get().get(1).getDenomination().ammount());
        assertEquals(100, banknotes.get().get(2).getDenomination().ammount());
    }

    @Test
    void releaseFromStorage_notAllAmmount() {
        List<Banknote> cash = Arrays.asList(
                new BanknoteImpl(new DenominationImpl(100)),
                new BanknoteImpl(new DenominationImpl(100)),
                new BanknoteImpl(new DenominationImpl(100)),
                new BanknoteImpl(new DenominationImpl(100)),
                new BanknoteImpl(new DenominationImpl(100)),
                new BanknoteImpl(new DenominationImpl(100)),
                new BanknoteImpl(new DenominationImpl(100)),
                new BanknoteImpl(new DenominationImpl(100)),
                new BanknoteImpl(new DenominationImpl(100)),
                new BanknoteImpl(new DenominationImpl(100)),
                new BanknoteImpl(new DenominationImpl(500)));

        cashCellService.initCells(cells, 30, 2);
        cashCellService.takeForStorage(cash, cells, availableDenominations);

        Optional<List<Banknote>> banknotes = cashCellService.releaseFromStorage(1600, availableDenominations, cells);
        assertTrue(banknotes.isPresent());
        assertEquals(
                1500,
                banknotes.get().stream()
                        .mapToInt(b -> b.getDenomination().ammount())
                        .sum());
    }

    @Test
    void getAvailableDenomination() {
        int[] availableNominal = {100, 500, 1000};
        List<Denomination> denominations = cashCellService.getAvailableDenomination(availableNominal);
        assertEquals(3, denominations.size());
        assertEquals(100, denominations.get(0).ammount());
        assertEquals(500, denominations.get(1).ammount());
        assertEquals(1000, denominations.get(2).ammount());
    }
}
