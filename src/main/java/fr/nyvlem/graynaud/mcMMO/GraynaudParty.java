package fr.nyvlem.graynaud.mcMMO;

import com.gmail.nossr50.datatypes.party.Party;
import fr.nyvlem.graynaud.Graynaud;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;

public class GraynaudParty {

    private static final String spawnPointSectionPath = "spawnpoint";

    private Party party;

    private Location spawnPoint;

    public GraynaudParty(Party party) {
        this.party = party;
    }

    public GraynaudParty(Party party, ConfigurationSection partySection) {
        this(party);

        ConfigurationSection spawnPointSection = partySection.getConfigurationSection(spawnPointSectionPath);

        if (spawnPointSection != null && spawnPointSection.getString("world") != null) {
            this.spawnPoint = new Location(
                    Bukkit.getServer().getWorld(spawnPointSection.getString("world")),
                    spawnPointSection.getDouble("x"),
                    spawnPointSection.getDouble("y"),
                    spawnPointSection.getDouble("z"),
                    spawnPointSection.getLong("pitch"),
                    spawnPointSection.getLong("yaw")
            );
        }
    }

    public Party getParty() {
        return party;
    }

    public void setSpawnPoint(Location spawnPoint) {
        this.spawnPoint = spawnPoint;
    }

    public Location getSpawnPoint() {
        return spawnPoint;
    }

    public boolean hasUnlockedPartySpawn() {
        return this.party.getLevel() >= Graynaud.getSpawnLevel();
    }

    public void toYaml(ConfigurationSection partySection) {
        if (this.getSpawnPoint() != null && this.spawnPoint.getWorld() != null) {
            ConfigurationSection spawnPointSection = partySection.createSection(spawnPointSectionPath);
            spawnPointSection.set("world", this.spawnPoint.getWorld().getName());
            spawnPointSection.set("x", this.spawnPoint.getX());
            spawnPointSection.set("y", this.spawnPoint.getY());
            spawnPointSection.set("z", this.spawnPoint.getZ());
            spawnPointSection.set("pitch", this.spawnPoint.getPitch());
            spawnPointSection.set("yaw", this.spawnPoint.getYaw());
        }
    }
}
