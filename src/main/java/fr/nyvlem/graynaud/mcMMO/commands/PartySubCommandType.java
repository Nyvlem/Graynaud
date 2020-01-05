package fr.nyvlem.graynaud.mcMMO.commands;

public enum PartySubCommandType {
    SPAWN,SETSPAWN;

    public static PartySubCommandType getSubCommand(String commandName) {
        for (PartySubCommandType command : values()) {
            if (command.name().equalsIgnoreCase(commandName)) {
                return command;
            }
        }
        return null;
    }
}
