package ru.otus.cell;

import java.util.List;
import ru.otus.banknote.Banknote;
import ru.otus.banknote.Denomination;

public interface CashCell {

    void addBanknote(Banknote banknote);

    Banknote removeBanknote();

    int getCountBanknotes();

    Denomination getDenomination();

    void setDenomination(Denomination denomination);

    boolean isFull();

    void setFree();

    boolean isFree();

    List<Banknote> getAllBanknotes();
}
