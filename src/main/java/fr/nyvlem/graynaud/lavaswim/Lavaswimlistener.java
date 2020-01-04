package fr.nyvlem.graynaud.lavaswim;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class Lavaswimlistener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    void onPlayerMove(PlayerMoveEvent event){
        Player player = event.getPlayer();
        Material material = player.getLocation().getBlock().getType();
        if(material == Material.LAVA){
            //Player in lava
            if(player.isSprinting()){
                player.setGliding(true);
                //0.32 is limit value before gliding animation bug in a small amount of lava
                player.setVelocity(player.getLocation().getDirection().multiply(0.32));
            }
        }
    }
}