package fr.nyvlem.graynaud.mcMMO.commands;

public enum PartySubCommandType {
    SPAWN, SETSPAWN;

    public static PartySubCommandType getSubCommand(String command) {
        try {
            return PartySubCommandType.valueOf(command.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
