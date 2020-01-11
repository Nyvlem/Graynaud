package fr.nyvlem.graynaud.mcMMO.listeners;

import com.gmail.nossr50.events.party.McMMOPartyCreatedEvent;
import com.gmail.nossr50.events.party.McMMOPartyDisbandedEvent;
import fr.nyvlem.graynaud.Graynaud;
import fr.nyvlem.graynaud.mcMMO.GraynaudPartyManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class McMMOEventListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onMcMMOPartyCreatedEvent(McMMOPartyCreatedEvent event) {
        GraynaudPartyManager.createParty(event.getParty());
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onMcMMOPartyDisbandedEvent(McMMOPartyDisbandedEvent event) {
        GraynaudPartyManager.removeParty(event.getParty());
    }
}
