package ru.otus.cell;

import ru.otus.banknote.Banknote;
import ru.otus.banknote.Denomination;

public interface CashCell {

    void addBanknote(Banknote banknote);

    void removeBanknote();

    int getCountBanknotes();

    Denomination getDenomination();

    void setDenomination(Denomination denomination);

    boolean isFull();

    void setFree();

    boolean isFree();
}
