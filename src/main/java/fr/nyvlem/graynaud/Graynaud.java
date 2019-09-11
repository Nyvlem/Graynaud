package fr.nyvlem.graynaud;

import fr.nyvlem.graynaud.lavaswim.Lavaswimlistener;
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
		getServer().getPluginManager().registerEvents(new Lavaswimlistener(), this);
	}

	private void loadConfig() {
		File file = new File(getDataFolder(), Constants.CONFIG_FILE);

		if (!file.exists()) {
			saveResource(Constants.CONFIG_FILE, false);
		}

		config = YamlConfiguration.loadConfiguration(file);
	}
}
