package fr.nyvlem.graynaud.mcMMO.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerListener implements Listener {

    private static Plugin plugin;
    private final static Map<UUID, BukkitTask> tpQueue = new HashMap<>();

    public PlayerListener(Plugin plugin) {
        PlayerListener.plugin = plugin;
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerMove(PlayerMoveEvent event) {
        final Player player = event.getPlayer();

        BukkitTask bukkitTask = tpQueue.get(player.getUniqueId());

        if (bukkitTask != null) {
            bukkitTask.cancel();
            tpQueue.remove(player.getUniqueId());
            player.sendMessage(ChatColor.GOLD + "Teleportation canceled");
        }
    }

    public static void teleportingPlayer(int delay, Player player, Location destination) {
        tpQueue.put(player.getUniqueId(), new Teleport(player, destination).runTaskLater(plugin, 20 * delay));
    }

    private static class Teleport extends BukkitRunnable {

        Player player;
        Location destination;

        Teleport(Player player, Location destination) {
            this.player = player;
            this.destination = destination;
        }

        @Override
        public void run() {
            player.sendMessage(ChatColor.GOLD + "Teleporting to your party spawn !");
            player.teleport(destination);
            tpQueue.remove(player.getUniqueId());
        }
    }
}
