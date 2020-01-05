package fr.nyvlem.graynaud.mcMMO;

import com.gmail.nossr50.datatypes.party.Party;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;


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

    public void setUnlockedPartySpawn(boolean state) {
        this.unlockedPartySpawn = state;
    }

    public boolean isUnlockedPartySpawn() {
        return unlockedPartySpawn;
    }

    public void toYaml(ConfigurationSection config) {
        config.set(this.getName() + ".Spawnpoint.world", this.getSpawnpoint().getWorld().getName());
        config.set(this.getName() + ".Spawnpoint.x", this.getSpawnpoint().getX());
        config.set(this.getName() + ".Spawnpoint.y", this.getSpawnpoint().getY());
        config.set(this.getName() + ".Spawnpoint.z", this.getSpawnpoint().getZ());
        config.set(this.getName() + ".Spawnpoint.pitch", this.getSpawnpoint().getPitch());
        config.set(this.getName() + ".Spawnpoint.yaw", this.getSpawnpoint().getYaw());
    }

}
