package ru.otus.engine;

import java.util.List;
import ru.otus.banknote.Banknote;

public interface CashCellEngine {

    long getBalance();

    void putMoney(List<Banknote> banknotes);

    List<Banknote> getMoney(Long amount);
}
