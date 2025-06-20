package ru.otus.engine;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import ru.otus.banknote.Banknote;
import ru.otus.banknote.Denomination;
import ru.otus.cell.CashCell;
import ru.otus.property.Config;
import ru.otus.service.CashCellService;

public class CashCellEngineImpl implements CashCellEngine {

    private final Config config;

    private final List<CashCell> cells;

    private final CashCellService cashCellService;

    private List<Denomination> availableDenominations;

    public CashCellEngineImpl(CashCellService cashCellService, Config config) {
        this.cashCellService = cashCellService;
        this.config = config;
        this.cells = new ArrayList<>();
        initCells();
    }

    private void initCells() {
        cashCellService.initCells(cells, config.getCellsCount(), config.getCellsCapacity());
        availableDenominations = cashCellService.getAvailableDenomination(config.getAvailableNominal());
    }

    @Override
    public long getBalance() {
        return cashCellService.getBalance(cells);
    }

    @Override
    public Optional<List<Banknote>> putMoney(List<Banknote> banknotes) {
        return cashCellService.takeForStorage(banknotes, cells, availableDenominations);
    }

    @Override
    public Optional<List<Banknote>> getMoney(long amount) {
        return cashCellService.releaseFromStorage(amount, availableDenominations, cells);
    }
}
