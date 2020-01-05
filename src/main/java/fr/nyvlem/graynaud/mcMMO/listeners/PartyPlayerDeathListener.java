package fr.nyvlem.graynaud.mcMMO.listeners;

import com.gmail.nossr50.api.PartyAPI;
import fr.nyvlem.graynaud.mcMMO.GraynaudPartyManager;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PartyPlayerDeathListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    void onPlayerRespawn(PlayerRespawnEvent event){
        Player player = event.getPlayer();
        if(PartyAPI.inParty(player)){
            String partyName = PartyAPI.getPartyName(player);
            Location destination = GraynaudPartyManager.getSpawnPoint(partyName);
            if(destination == null){
                return;
            }else{
                event.setRespawnLocation(destination);
            }
        }
    }
}
