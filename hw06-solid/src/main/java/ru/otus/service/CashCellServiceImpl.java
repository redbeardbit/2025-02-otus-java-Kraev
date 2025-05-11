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
                .mapToLong(cashCell -> cashCell.getDenomination().ammount())
                .sum();
    }

    @Override
    public Optional<List<Banknote>> takeForStorage(
            List<Banknote> banknotes, List<CashCell> cells, List<Denomination> availableDenominations) {
        List<Banknote> notAvailableBanknotes = new ArrayList<>();
        for (Banknote banknote : banknotes) {
            if (!checkAvailableBanknote(banknote, availableDenominations)
                    || !tryToPutBanknote(banknote, cells, false)) {
                notAvailableBanknotes.add(banknote);
            }
        }
        return Optional.ofNullable(notAvailableBanknotes);
    }

    private boolean tryToPutBanknote(Banknote banknote, List<CashCell> cells, boolean isLastChance) {
        if (getCellAndPutBanknote(banknote, cells)) {
            return true;
        } else if (!isLastChance && getNewCellForPutBanknote(banknote, cells)) {
            return tryToPutBanknote(banknote, cells, true);
        }
        return false;
    }

    @Override
    public Optional<List<Banknote>> releaseFromStorage(
            long amount, List<Denomination> availableDenominations, List<CashCell> cells) {
        List<Banknote> banknotes = new ArrayList<>();
        availableDenominations.sort(
                Comparator.comparingInt(Denomination::ammount).reversed());
        for (Denomination denomination : availableDenominations) {
            while (amount >= denomination.ammount()) {
                Optional<Banknote> banknote = getBanknotebyDenomination(denomination, cells);
                if (banknote.isEmpty()) {
                    break;
                }
                amount -= banknote.get().getDenomination().ammount();
                banknotes.add(banknote.get());
            }
        }
        return Optional.ofNullable(banknotes);
    }

    private Optional<Banknote> getBanknotebyDenomination(Denomination denomination, List<CashCell> cells) {
        Banknote banknote = null;
        for (CashCell cell : cells) {
            if (!cell.isFree() && cell.getDenomination().equals(denomination)) {
                banknote = cell.removeBanknote();
                break;
            }
        }
        return Optional.ofNullable(banknote);
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
                .filter(cashCell -> !cashCell.isFree())
                .filter(cashCell -> !cashCell.isFull())
                .filter(cashCell -> cashCell.getDenomination().equals(denomination))
                .findFirst();
    }

    @Override
    public List<Denomination> getAvailableDenomination(int[] availableNominal) {
        return Arrays.stream(availableNominal).mapToObj(DenominationImpl::new).collect(Collectors.toList());
    }
}
