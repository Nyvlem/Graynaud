package fr.nyvlem.graynaud.mcMMO;

import com.gmail.nossr50.party.PartyManager;
import fr.nyvlem.graynaud.Graynaud;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public final class GraynaudPartyManager {

    private static String partiesFilePath =   "plugins/graynaud/parties.yml";
    private static List<GraynaudParty> parties = new ArrayList<GraynaudParty>();
    private static File partyFile = new File(partiesFilePath);

    private GraynaudPartyManager() {}

    /**
     * Retrieve a GraynaudParty by its name
     *
     * @param partyName The party name
     * @return the existing party, null otherwise
     */
    public static GraynaudParty getParty(String partyName) {
        for (GraynaudParty party : parties) {
            if (party.getName().equalsIgnoreCase(partyName)) {
                return party;
            }
        }

        return null;
    }


    /**
     * Set the spawnpoint for a party
     *
     * @param partyName The party name
     * @param spawnpoint The spawnpoint you want for the party
     */
    public static void setSpawnPoint(String partyName, Location spawnpoint) {

        if(getParty(partyName) == null){
            Graynaud.GRAYNAUD.getLogger().info("GraynaudParty " + partyName + " doesn't exist.");
            if(PartyManager.getParty(partyName) != null){
                Graynaud.GRAYNAUD.getLogger().info("Creating party... ");
                GraynaudPartyManager.createParty(partyName);
            }else{
                Graynaud.GRAYNAUD.getLogger().warning("No party corresponding in mcMMO, create one before trying to set a spawnpoint.");
                return;
            }
        }

        getParty(partyName).setSpawnpoint(spawnpoint);
        GraynaudPartyManager.saveParties();
    }

    /**
     * Get the spawnpoint from a party
     *
     * @param partyName The party name
     */
    public static Location getSpawnPoint(String partyName) {

        GraynaudParty party = getParty(partyName);

        if(party == null){
            Graynaud.GRAYNAUD.getLogger().warning("Spawnpoint for " + partyName + " doesn't exist. Set the spawnpoint before.");
            return null;
        }

        return party.getSpawnpoint();
    }

    /**
     * Create a new GraynaudParty
     *
     * @param partyName The party to add the player to
     */
    public static void createParty(String partyName) {

        if(getParty(partyName)!=null){
            Graynaud.GRAYNAUD.getLogger().warning("Name already taken.");
        }else {
            GraynaudParty party = new GraynaudParty(partyName);

            parties.add(party);
        }
        GraynaudPartyManager.saveParties();
    }

    /**
     * Load party file.
     */
    public static void loadParties() {

        if (!partyFile.exists()) {
            return;
        }

        try {
            YamlConfiguration partiesFile;
            partiesFile = YamlConfiguration.loadConfiguration(partyFile);


            for (String partyName : partiesFile.getConfigurationSection("").getKeys(false)) {
                GraynaudParty party = new GraynaudParty(partyName);

                party.setSpawnpoint(new Location(
                        Bukkit.getServer().getWorld(partiesFile.getString(partyName + ".Spawnpoint.world")),
                        partiesFile.getDouble(partyName + ".Spawnpoint.x"),
                        partiesFile.getDouble(partyName + ".Spawnpoint.y"),
                        partiesFile.getDouble(partyName + ".Spawnpoint.z"),
                        partiesFile.getLong(partyName + ".Spawnpoint.pitch"),
                        partiesFile.getLong(partyName + ".Spawnpoint.yaw")));


                parties.add(party);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Save party file.
     */
    public static void saveParties() {
        if (partyFile.exists()) {
            if (!partyFile.delete()) {
                Graynaud.GRAYNAUD.getLogger().warning("Could not delete party file. Party saving failed!");
                return;
            }
        }

        YamlConfiguration partiesFile = new YamlConfiguration();

        Graynaud.GRAYNAUD.getLogger().info("Saving Parties... (" + parties.size() + ")");
        for (GraynaudParty party : parties) {
            String partyName = party.getName();

            partiesFile.set(partyName + ".Spawnpoint.world", party.getSpawnpoint().getWorld().getName());
            partiesFile.set(partyName + ".Spawnpoint.x", party.getSpawnpoint().getX());
            partiesFile.set(partyName + ".Spawnpoint.y", party.getSpawnpoint().getY());
            partiesFile.set(partyName + ".Spawnpoint.z", party.getSpawnpoint().getZ());
            partiesFile.set(partyName + ".Spawnpoint.pitch", party.getSpawnpoint().getPitch());
            partiesFile.set(partyName + ".Spawnpoint.yaw", party.getSpawnpoint().getYaw());
        }

        try {
            partiesFile.save(partyFile);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


}
