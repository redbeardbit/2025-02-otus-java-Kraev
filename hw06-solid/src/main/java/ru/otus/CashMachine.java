package ru.otus;

import ru.otus.engine.CashCellEngine;

public class CashMachine {
    // remove this when implement run()
    @SuppressWarnings("S1068")
    private final CashCellEngine cashCellEngine;

    public CashMachine(CashCellEngine cashCellEngine) {
        this.cashCellEngine = cashCellEngine;
    }

    @SuppressWarnings("java:S1135")
    public void run() {
        // TODO something intresting here
    }
}
