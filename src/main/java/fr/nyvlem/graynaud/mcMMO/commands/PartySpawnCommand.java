package fr.nyvlem.graynaud.mcMMO.commands;

import com.gmail.nossr50.api.PartyAPI;
import com.gmail.nossr50.party.PartyManager;
import fr.nyvlem.graynaud.Graynaud;
import fr.nyvlem.graynaud.mcMMO.GraynaudPartyManager;
import fr.nyvlem.graynaud.mcMMO.listeners.PlayerListener;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PartySpawnCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            String partyName = PartyAPI.getPartyName(player);
            if (!GraynaudPartyManager.getParty(partyName).hasUnlockedPartySpawn()) {
                player.sendMessage(ChatColor.GOLD + "Your party need to be level " + Graynaud.getSpawnLevel() +
                                   " to unlock party spawn and your party is level " +
                                   PartyManager.getParty(player).getLevel());
                return false;
            }

            Location destination = GraynaudPartyManager.getSpawnPoint(partyName);

            if (destination == null) {
                player.sendMessage(ChatColor.GOLD + "Spawnpoint not found.");
            } else {
                if (Graynaud.getTeleportDelay() > 0) {
                    player.sendMessage(ChatColor.GOLD + "Teleporting in (" + Graynaud.getTeleportDelay() +
                                       ") seconds. Move to cancel.");
                }

                PlayerListener.teleportingPlayer(Graynaud.getTeleportDelay(), player, destination);
                return true;
            }
        }

        return false;
    }
}
