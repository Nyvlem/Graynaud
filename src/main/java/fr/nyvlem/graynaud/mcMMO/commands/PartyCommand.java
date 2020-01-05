package fr.nyvlem.graynaud.mcMMO.commands;


import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PartyCommand implements TabExecutor {

    private static final List<String> PARTY_SUB_COMMANDS;

    private CommandExecutor partySetSpawnCommand = new PartySetSpawnCommand();
    private CommandExecutor partySpawnCommand = new PartySpawnCommand();

    static {
        PARTY_SUB_COMMANDS = Arrays.stream(PartySubCommandType.values()).map(Enum::toString).sorted().collect(Collectors.toList());
    }

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
        switch (args.length) {
            case 1:
                return StringUtil.copyPartialMatches(args[0], PARTY_SUB_COMMANDS, new ArrayList<>(PARTY_SUB_COMMANDS.size()));
        }
        return null;
    }
}
