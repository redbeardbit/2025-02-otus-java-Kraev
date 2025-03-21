package ru.otus.banknote;

import com.google.common.base.Preconditions;

public class BanknoteImpl implements Banknote {

    private final Denomination denomination;

    public BanknoteImpl(Denomination denomination) {
        Preconditions.checkArgument(denomination.ammount() > 0, "ammount must be positive");
        this.denomination = denomination;
    }

    @Override
    public Denomination getDenomination() {
        return denomination;
    }
}
