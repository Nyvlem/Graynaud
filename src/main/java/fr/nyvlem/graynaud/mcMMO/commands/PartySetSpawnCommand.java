package fr.nyvlem.graynaud.mcMMO.commands;

import com.gmail.nossr50.api.PartyAPI;
import com.gmail.nossr50.party.PartyManager;
import fr.nyvlem.graynaud.Graynaud;
import fr.nyvlem.graynaud.mcMMO.GraynaudPartyManager;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PartySetSpawnCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            String partyName = PartyAPI.getPartyName(player);
            if (partyName != null) {
                if (player.getName().equals(PartyAPI.getPartyLeader(partyName))) {
                    if (!GraynaudPartyManager.getParty(partyName).hasUnlockedPartySpawn()) {
                        player.sendMessage(ChatColor.GOLD + "Your party need to be level " + Graynaud.getSpawnLevel() +
                                           " to unlock party spawn and your party is level " +
                                           PartyManager.getParty(player).getLevel());
                        return false;
                    }

                    Location spawnPoint = GraynaudPartyManager.setSpawnPoint(partyName, player.getLocation());

                    if (spawnPoint == null) {
                        player.sendMessage(ChatColor.GOLD + "You must create or join a party first");
                        return false;
                    }

                    player.sendMessage(
                            ChatColor.GOLD + "New spawn for your party set to : " + spawnPoint.getBlockX() +
                            " / " + spawnPoint.getBlockY() + " / " + spawnPoint.getBlockZ());

                    return true;
                } else {
                    player.sendMessage(ChatColor.GOLD + "You are not the leader of your party");
                }
            } else {
                player.sendMessage(ChatColor.GOLD + "You must create or join a party first");
            }
        }
        return false;
    }
}
