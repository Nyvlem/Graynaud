package fr.nyvlem.graynaud;

import com.gmail.nossr50.api.PartyAPI;
import com.gmail.nossr50.datatypes.party.Party;
import fr.nyvlem.graynaud.gamemode_inventory.GameModeInventoryListener;
import fr.nyvlem.graynaud.lavaswim.Lavaswimlistener;
import fr.nyvlem.graynaud.utils.Constants;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.stream.Collectors;

public class Graynaud extends JavaPlugin {

	private FileConfiguration config;

	@Override
	public void onEnable() {
		loadConfig();

		boolean enableGamemodeInventory = config.getBoolean(Constants.GAMEMODE_INVETORY_CONFIG_PATH, false);
		boolean enableLavaswim = config.getBoolean(Constants.LAVASWIM_CONFIG_PATH, false);
		boolean enableMcmmo = config.getBoolean(Constants.MCMMO_CONFIG_PATH, false);

		if (enableGamemodeInventory) {
			getServer().getPluginManager().registerEvents(new GameModeInventoryListener(this), this);
		}

		if (enableLavaswim) {
			getServer().getPluginManager().registerEvents(new Lavaswimlistener(), this);
		}

		if(getServer().getPluginManager().getPlugin("mcMMO") != null) {
            if (enableMcmmo) {
                getLogger().info("Parties: " + PartyAPI.getParties().stream().map(Party::getName).collect(Collectors.joining(";")));
            }
        }else{
		    config.set(Constants.MCMMO_CONFIG_PATH, false);
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
}
