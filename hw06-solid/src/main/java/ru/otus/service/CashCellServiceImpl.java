package ru.otus.service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import ru.otus.banknote.Banknote;
import ru.otus.banknote.Denomination;
import ru.otus.banknote.DenominationImpl;
import ru.otus.cell.CashCell;
import ru.otus.cell.CashCellImpl;

public class CashCellServiceImpl implements CashCellService {

    @Override
    public void initCells(List<CashCell> cells, int cellsCount, int cellsCapacity) {
        cells.addAll(IntStream.range(0, cellsCount)
                .mapToObj(i -> new CashCellImpl(cellsCapacity))
                .toList());
    }

    @Override
    public Long getBalance(List<CashCell> cells) {
        return cells.stream()
                .filter(cashCell -> !cashCell.isFree())
                .mapToLong(cashCell -> cashCell.getCountBanknotes()
                        + cashCell.getDenomination().ammount())
                .sum();
    }

    @Override
    public Optional<List<Banknote>> takeForStorage(
            List<Banknote> banknotes, List<CashCell> cells, List<Denomination> availableDenominations) {
        List<Banknote> notAvailableBanknotes = new ArrayList<>();
        for (Banknote banknote : banknotes) {
            if (!checkAvailableBanknote(banknote, availableDenominations)) {
                notAvailableBanknotes.add(banknote);
                continue;
            }
            if (getCellAndPutBanknote(banknote, cells)) {
                continue;
            }
            if (!getNewCellForPutBanknote(banknote, cells)) {
                notAvailableBanknotes.add(banknote);
                continue;
            }
            if (getCellAndPutBanknote(banknote, cells)) {
                continue;
            }
            notAvailableBanknotes.add(banknote);
        }
        return Optional.of(notAvailableBanknotes);
    }

    @Override
    public List<Banknote> releaseFromStorage(Long amount) {
        return List.of();
    }

    private Optional<Map<Denomination, Integer>> getDenominationsAndCountBanknotes(List<CashCell> cells) {
        if (cells.isEmpty()) {
            return Optional.empty();
        } else {
            Map<Denomination, Integer> denominations = new HashMap<>();
            for (CashCell cell : cells) {
                if (denominations.containsKey(cell.getDenomination())) {
                    //todo
                    var t = denominations.get(cell.getDenomination());
                }
                denominations.put(cell.getDenomination(), cell.getCountBanknotes());
            }
            return Optional.of(denominations);
        }
    }

    private boolean canAmmountBeTaken(Long amount, List<CashCell> cells) {
        Optional<Map<Denomination, Integer>> banknotes = getDenominationsAndCountBanknotes(cells);
        if (banknotes.isEmpty()) {
            throw new IllegalStateException("Недостаточно банкнот в хранилище");
        }
        return true;
    }

    private boolean checkAvailableBanknote(Banknote banknote, List<Denomination> availableDenominations) {
        return availableDenominations.contains(banknote.getDenomination());
    }

    private boolean getCellAndPutBanknote(Banknote banknote, List<CashCell> cells) {
        Optional<CashCell> cell = getCellbyDenomination(banknote.getDenomination(), cells);
        if (cell.isEmpty()) {
            return false;
        }
        cell.get().addBanknote(banknote);
        return true;
    }

    private Optional<CashCell> getFreeCell(List<CashCell> cells) {
        return cells.stream().filter(CashCell::isFree).findFirst();
    }

    private boolean getNewCellForPutBanknote(Banknote banknote, List<CashCell> cells) {
        Optional<CashCell> cell = getFreeCell(cells);
        boolean present = cell.isPresent();
        if (present) {
            cell.get().setDenomination(banknote.getDenomination());
        }
        return present;
    }

    private Optional<CashCell> getCellbyDenomination(Denomination denomination, List<CashCell> cells) {
        return cells.stream()
                .filter(cashCell -> cashCell.getDenomination().equals(denomination))
                .filter(cashCell -> !cashCell.isFull())
                .findFirst();
    }

    @Override
    public List<Denomination> getAvailableDenomination(int[] availableNominal) {
        return Arrays.stream(availableNominal).mapToObj(DenominationImpl::new).collect(Collectors.toList());
    }
}
