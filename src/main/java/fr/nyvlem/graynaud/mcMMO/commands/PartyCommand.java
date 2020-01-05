package fr.nyvlem.graynaud.mcMMO.commands;


import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.List;

public class PartyCommand implements TabExecutor {

    private CommandExecutor partySetSpawnCommand = new PartySetSpawnCommand();
    private CommandExecutor partySpawnCommand = new PartySpawnCommand();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            return false;
        }

        PartySubCommandType subCommand = PartySubCommandType.valueOf(args[0].toUpperCase());

        switch (subCommand) {
            case SPAWN:
                return partySpawnCommand.onCommand(sender, command, label, args);
            case SETSPAWN:
                return partySetSpawnCommand.onCommand(sender, command, label, args);
            default:
                break;
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return null;
    }
}
