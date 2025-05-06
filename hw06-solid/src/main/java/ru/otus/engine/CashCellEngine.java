package ru.otus.engine;

import java.util.List;
import java.util.Optional;
import ru.otus.banknote.Banknote;

public interface CashCellEngine {

    long getBalance();

    Optional<List<Banknote>> putMoney(List<Banknote> banknotes);

    Optional<List<Banknote>> getMoney(long amount);
}
