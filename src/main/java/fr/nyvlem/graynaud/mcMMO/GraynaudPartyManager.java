package fr.nyvlem.graynaud.mcMMO;

import com.gmail.nossr50.datatypes.party.Party;
import com.gmail.nossr50.party.PartyManager;
import fr.nyvlem.graynaud.Graynaud;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.stream.Collectors;

public final class GraynaudPartyManager {

    private static File partiesFile = new File(Graynaud.getDataDirectory() + "parties.yml");
    private static Map<String, GraynaudParty> parties = new HashMap<>();

    private GraynaudPartyManager() {
    }

    /**
     * Retrieve a GraynaudParty by its name
     *
     * @param partyName The party name
     *
     * @return the existing party, null otherwise
     */
    public static GraynaudParty getParty(String partyName) {
        return parties.getOrDefault(partyName, null);
    }

    /**
     * Set the spawnpoint for a party
     *
     * @param partyName  The party name
     * @param spawnpoint The spawnpoint you want for the party
     */
    public static Location setSpawnPoint(String partyName, Location spawnpoint) {
        if (getParty(partyName) == null) {
            Graynaud.GRAYNAUD.getLogger().info("GraynaudParty " + partyName + " doesn't exist.");
            return null;
        }

        getParty(partyName).setSpawnPoint(spawnpoint);
        saveParties();

        return spawnpoint;
    }

    /**
     * Get the spawnpoint from a party
     *
     * @param partyName The party name
     */
    public static Location getSpawnPoint(String partyName) {
        GraynaudParty party = getParty(partyName);

        if (party == null) {
            Graynaud.GRAYNAUD.getLogger()
                             .warning("Spawnpoint for " + partyName + " doesn't exist. Set the spawnpoint before.");
            return null;
        }

        return party.getSpawnPoint();
    }

    /**
     * Create a new GraynaudParty
     *
     * @param party The McMMOParty to create
     */
    public static void createParty(Party party) {
        GraynaudParty grParty = new GraynaudParty(party);
        if (parties.putIfAbsent(grParty.getParty().getName(), grParty) != null) {
            Graynaud.GRAYNAUD.getLogger().warning("Trying to create a party with an already taken name: " + party);
        }

        saveParties();
    }

    /**
     * Remove a new GraynaudParty
     *
     * @param party The McMMOParty to remove
     */
    public static void removeParty(Party party) {
        if (parties.remove(party.getName()) == null) {
            Graynaud.GRAYNAUD.getLogger().warning("Trying to remove a party that does not exists: " + party);
        }

        saveParties();
    }

    /**
     * Load party file.
     */
    public static void loadParties() {
        if (!partiesFile.exists()) {
            return;
        }

        try {
            YamlConfiguration partiesFile;
            partiesFile = YamlConfiguration.loadConfiguration(GraynaudPartyManager.partiesFile);

            ConfigurationSection topSection = partiesFile.getConfigurationSection("");

            if (topSection != null) {
                parties = topSection.getKeys(false)
                                    .stream()
                                    .filter(partyName -> PartyManager.getParty(partyName) != null)
                                    .map(partyName -> new GraynaudParty(PartyManager.getParty(partyName), topSection.getConfigurationSection(partyName))).collect(Collectors.toMap(grParty -> grParty.getParty().getName(), Function.identity()));
            }
        } catch (Exception e) {
            Graynaud.GRAYNAUD.getLogger().severe("Loading parties failed.");
            Graynaud.GRAYNAUD.getLogger().log(Level.SEVERE, e.getMessage(), e);
        }
    }

    /**
     * Save party file.
     */
    public static void saveParties() {
        if (partiesFile.exists()) {
            if (!partiesFile.delete()) {
                Graynaud.GRAYNAUD.getLogger().warning("Could not delete party file. Party saving failed!");
                return;
            }
        }

        Graynaud.GRAYNAUD.getLogger().info("Saving Parties... (" + parties.size() + ")");
        YamlConfiguration partiesFile = new YamlConfiguration();
        parties.forEach((partyName, party) -> party.toYaml(partiesFile.createSection(partyName)));

        try {
            partiesFile.save(GraynaudPartyManager.partiesFile);
            Graynaud.GRAYNAUD.getLogger().info("Saving Parties succeeded (" + parties.size() + ")");
        } catch (Exception e) {
            Graynaud.GRAYNAUD.getLogger().warning("Saving parties failed.");
        }
    }
}
