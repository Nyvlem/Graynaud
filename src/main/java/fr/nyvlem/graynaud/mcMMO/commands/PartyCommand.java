package fr.nyvlem.graynaud.mcMMO.commands;


import com.google.common.collect.ImmutableList;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PartyCommand implements TabExecutor {

    private static final List<String> PARTY_SUBCOMMANDS;

    private CommandExecutor partysetspawnCommand      = new PartysetspawnCommand();
    private CommandExecutor partySpawnCommand         = new PartySpawnCommand();

    static {
        ArrayList<String> subcommands = new ArrayList<String>();

        for (PartySubCommandType subcommand : PartySubCommandType.values()) {
            subcommands.add(subcommand.toString());
        }

        Collections.sort(subcommands);
        PARTY_SUBCOMMANDS = ImmutableList.copyOf(subcommands);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player;
        if (!(sender instanceof Player)) {
            return false;
        } else {
            player = ((Player) sender).getPlayer();
        }

        PartySubCommandType subcommand = PartySubCommandType.getSubCommand(args[0]);

        switch (subcommand) {
            case SPAWN:
                return partySpawnCommand.onCommand(sender, command, label, args);
            case SETSPAWN:
                return partysetspawnCommand.onCommand(sender, command, label, args);
            default:
                break;
        }


        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        switch (args.length) {
            case 1:
                return StringUtil.copyPartialMatches(args[0], PARTY_SUBCOMMANDS, new ArrayList<String>(PARTY_SUBCOMMANDS.size()));
        }
        return null;
    }
}
