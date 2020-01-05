package fr.nyvlem.graynaud;

import com.gmail.nossr50.api.PartyAPI;
import com.gmail.nossr50.datatypes.party.Party;
import fr.nyvlem.graynaud.gamemode_inventory.GameModeInventoryListener;
import fr.nyvlem.graynaud.lavaswim.Lavaswimlistener;
import fr.nyvlem.graynaud.mcMMO.commands.PartyCommand;
import fr.nyvlem.graynaud.mcMMO.GraynaudPartyManager;
import fr.nyvlem.graynaud.mcMMO.listeners.PartyPlayerDeathListener;
import fr.nyvlem.graynaud.mcMMO.listeners.PlayerListener;
import fr.nyvlem.graynaud.utils.Constants;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.stream.Collectors;

public class Graynaud extends JavaPlugin {

    public static Graynaud GRAYNAUD;
    private FileConfiguration config;

    private static int setSpawnLevel;
    private static int teleportDelay;

    public static int getSpawnLevel() {
        return setSpawnLevel;
    }

    public static int getTeleportDelay() {
        return teleportDelay;
    }

    public static void setSetSpawnLevel(int spawnLevel) {
        Graynaud.setSpawnLevel = spawnLevel;
    }

    public static void setTeleportDelay(int teleportDelay) {
        Graynaud.teleportDelay = teleportDelay;
    }

    @Override
    public void onEnable() {
        loadConfig();

        GRAYNAUD = this;

        boolean enableGamemodeInventory = config.getBoolean(Constants.GAMEMODE_INVETORY_CONFIG_PATH, false);
        boolean enableLavaswim = config.getBoolean(Constants.LAVASWIM_CONFIG_PATH, false);
        boolean enableMcmmo = config.getBoolean(Constants.MCMMO_CONFIG_PATH, false);
        boolean enableSpawnToPartySpawn = config.getBoolean(Constants.PARTY_RESPAWN_PATH, false);
        setSetSpawnLevel(config.getInt(Constants.PARTY_SETSPAWN_LEVEL_PATH, 50));
        setTeleportDelay(config.getInt(Constants.PARTY_TELEPORT_DELAY_PATH, 3));


        if (enableGamemodeInventory) {
            getServer().getPluginManager().registerEvents(new GameModeInventoryListener(this), this);
        }

        if (enableLavaswim) {
            getServer().getPluginManager().registerEvents(new Lavaswimlistener(), this);
        }

        if (getServer().getPluginManager().getPlugin("mcMMO") != null) {
            if (enableMcmmo) {
                getServer().getPluginManager().registerEvents(new PlayerListener(this), this);

                GraynaudPartyManager.loadParties();

                getLogger().info("Parties: " + PartyAPI.getParties().stream().map(Party::getName).collect(Collectors.joining(";")));

                if (setSpawnLevel != -1) {
                    getCommand("grparty").setExecutor(new PartyCommand());
                }

                if (enableSpawnToPartySpawn) {
                    getServer().getPluginManager().registerEvents(new PartyPlayerDeathListener(), this);
                }
            }
        } else {
            enableMcmmo = false;
        }

        getLogger().info("Graynaud enabled !");
    }

    private void loadConfig() {
        File file = new File(getDataFolder(), Constants.CONFIG_FILE);

        if (!file.exists()) {
            saveResource(Constants.CONFIG_FILE, false);
        }

        config = YamlConfiguration.loadConfiguration(file);
    }

    @Override
    public void onDisable() {
        GraynaudPartyManager.saveParties();
    }
}
