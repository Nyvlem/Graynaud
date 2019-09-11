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
            //Joueur dans la lave
            if(player.isSprinting()){
                player.setGliding(true);
                //0.32 est la valeur limite pour que le gliding ne bug pas lors d'une nage dans trop peu de lave
                player.setVelocity(player.getLocation().getDirection().multiply(0.32));
            }
        }
    }
}