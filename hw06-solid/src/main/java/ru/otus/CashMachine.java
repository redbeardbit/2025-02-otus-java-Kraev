package ru.otus;

import ru.otus.engine.CashCellEngine;

public class CashMachine {

    private final CashCellEngine cashCellEngine;

    public CashMachine(CashCellEngine cashCellEngine) {
        this.cashCellEngine = cashCellEngine;
    }

    public void run() {
        cashCellEngine.getBalance();
    }
}
