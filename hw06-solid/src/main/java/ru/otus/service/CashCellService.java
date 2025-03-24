package ru.otus.service;

import java.util.List;
import java.util.Optional;
import ru.otus.banknote.Banknote;
import ru.otus.banknote.Denomination;
import ru.otus.cell.CashCell;

public interface CashCellService {

    Optional<List<Banknote>> releaseFromStorage(
            long amount, List<Denomination> availableDenominations, List<CashCell> cells);

    List<Denomination> getAvailableDenomination(int[] availableNominal);

    void initCells(List<CashCell> cells, int cellsCount, int cellsCapacity);

    Long getBalance(List<CashCell> cells);

    Optional<List<Banknote>> takeForStorage(
            List<Banknote> banknotes, List<CashCell> cells, List<Denomination> availableDenominations);
}
