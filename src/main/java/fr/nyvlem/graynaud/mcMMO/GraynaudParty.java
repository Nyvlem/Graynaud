package fr.nyvlem.graynaud.mcMMO;

import com.gmail.nossr50.datatypes.party.Party;
import org.bukkit.Location;


public class GraynaudParty extends Party {

    private Location spawnpoint;
    private boolean unlockedPartySpawn = false;

    public GraynaudParty(String name) {
        super(name);
    }

    public void setSpawnpoint(Location spawnpoint) {
        this.spawnpoint = spawnpoint;
    }

    public Location getSpawnpoint() {
        return spawnpoint;
    }

    public void setunlockedPartySpawn(boolean STATE){
        unlockedPartySpawn = STATE;
    }

    public boolean isUnlockedPartySpawn() {
        return unlockedPartySpawn;
    }

}
