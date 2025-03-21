package ru.otus.cell;

import com.google.common.base.Preconditions;
import java.util.ArrayList;
import java.util.List;
import ru.otus.banknote.Banknote;
import ru.otus.banknote.Denomination;

public class CashCellImpl implements CashCell {

    public static final String CELL_IS_ALREADY_INITIALIZED = "cell is already initialized";
    private List<Banknote> cashKeeper;

    private final int cellCapacity;

    private Denomination denomination;

    private boolean isFreeCell;

    public CashCellImpl(int cellCapacity) {
        this.cashKeeper = new ArrayList<>();
        this.cellCapacity = cellCapacity;
        this.isFreeCell = true;
    }

    @Override
    public void addBanknote(Banknote banknote) {
        Preconditions.checkArgument(
                !banknote.getDenomination().equals(denomination),
                "you can't add this banknote, denomination is not correct");
        Preconditions.checkArgument(cashKeeper.size() >= cellCapacity, "you can't add this banknote, cell is full");

        if (banknote.getDenomination().equals(denomination)) {
            cashKeeper.add(banknote);
        }
    }

    @Override
    public void removeBanknote() {
        Preconditions.checkArgument(cashKeeper.size() <= 0, "no more banknotes");

        cashKeeper.removeLast();

        if (cashKeeper.size() == 0) {
            setFree();
        }
    }

    @Override
    public boolean isFull() {
        if (cashKeeper.size() == cellCapacity) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void setFree() {
        cashKeeper.clear();
        denomination = null;
        isFreeCell = true;
    }

    @Override
    public void setDenomination(Denomination denomination) {
        Preconditions.checkState(!isFreeCell, CELL_IS_ALREADY_INITIALIZED);
        Preconditions.checkState(!cashKeeper.isEmpty(), CELL_IS_ALREADY_INITIALIZED);
        Preconditions.checkState(denomination != null, CELL_IS_ALREADY_INITIALIZED);

        this.denomination = denomination;
        isFreeCell = false;
    }

    @Override
    public int getCountBanknotes() {
        return cashKeeper.size();
    }

    @Override
    public Denomination getDenomination() {
        return denomination;
    }

    @Override
    public boolean isFree() {
        if (isFreeCell) {
            return true;
        } else {
            return false;
        }
    }
}
