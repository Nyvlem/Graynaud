package fr.nyvlem.graynaud.lavaswim;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class Lavaswimlistener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    void onPlayerMove(PlayerMoveEvent e){
        Player player = e.getPlayer();
        Material m = player.getLocation().getBlock().getType();
        if(m == Material.LAVA){
            //Joueur dans la lave
            if(player.isSprinting()){
                player.setGliding(true);
                player.setVelocity(player.getLocation().getDirection().multiply(0.32));
            }
        }
    }
}