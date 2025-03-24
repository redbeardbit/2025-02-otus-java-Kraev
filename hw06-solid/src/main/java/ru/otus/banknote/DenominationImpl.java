package ru.otus.banknote;

public record DenominationImpl(int ammount) implements Denomination {

    @Override
    public boolean equals(Object o) {
        if (o instanceof DenominationImpl(int ammount)) {
            return ammount == this.ammount;
        } else {
            return false;
        }
    }
}
