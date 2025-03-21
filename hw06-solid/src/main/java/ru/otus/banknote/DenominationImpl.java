package ru.otus.banknote;

public record DenominationImpl(int ammount) implements Denomination {

    @Override
    public boolean equals(Object o) {
        if (o instanceof DenominationImpl) {
            return ((DenominationImpl) o).ammount() == this.ammount;
        } else {
            return false;
        }
    }
}
