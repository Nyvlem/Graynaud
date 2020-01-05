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
import sun.applet.Main;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerListener implements Listener {

    private static Plugin plugin;
    public PlayerListener(Plugin plugin)
    {
        this.plugin = plugin;
    }

    private final static Map<UUID, BukkitTask> tpQueue = new HashMap<UUID, BukkitTask>();

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e){
        final Player p = e.getPlayer();
        if(tpQueue.containsKey(p.getUniqueId())) {
            tpQueue.get(p.getUniqueId()).cancel();
            tpQueue.remove(p.getUniqueId());
            p.sendMessage(ChatColor.GOLD + "Teleportation canceled");
        }
    }

    public static void teleportingPlayer(int delay, Player player, Location destination){
        tpQueue.put(player.getUniqueId(), new Teleport(delay,player,destination).runTaskTimer(plugin, 0, 20));
    }

    private static class Teleport extends BukkitRunnable {
        int delay;
        Player player;
        Location destination;
        Teleport(int delay, Player player, Location destination) {
            this.delay = delay;
            this.player = player;
            this.destination = destination;
        }

        @Override
        public void run() {
            if(delay > 0) {
                delay--;
            } else {
                player.teleport(destination);
                tpQueue.remove(player.getUniqueId());
                this.cancel();
            }
        }
    }
}