package fr.nyvlem.graynaud;

import fr.nyvlem.graynaud.gamemode_inventory.GameModeInventoryListener;
import fr.nyvlem.graynaud.utils.Constants;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class Graynaud extends JavaPlugin {

	private FileConfiguration config;

	@Override
	public void onEnable() {
		loadConfig();

		boolean enableGamemodeInventory = config.getBoolean(Constants.GAMEMODE_INVETORY_CONFIG_PATH, false);

		if (enableGamemodeInventory) {
			getServer().getPluginManager().registerEvents(new GameModeInventoryListener(this), this);
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
