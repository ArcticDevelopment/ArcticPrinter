package dev.kyro.arcticprinter.enums;

public enum PrinterEndReason {

    ENEMY_NEARBY("Enemy nearby; disabling printer"),
    OUT_OF_MONEY("Out of money; disabling printer"),
    COMMAND_DISABLE("Exited printer"),
    LEFT_FACTION_CLAIM("No longer in faction claim");

    String message;

    PrinterEndReason(String message) {

        this.message = message;
    }

    public String getMessage() {

        return message;
    }
}