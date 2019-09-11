package fr.nyvlem.graynaud;

import fr.nyvlem.graynaud.lavaswim.Lavaswimlistener;
import org.bukkit.plugin.java.JavaPlugin;

public class Graynaud extends JavaPlugin {

	@Override
	public void onEnable() {
		getLogger().info("Graynaud ready !");
		getServer().getPluginManager().registerEvents(new Lavaswimlistener(), this);
	}
	
	@Override
	public void onDisable() {
		getLogger().info("Graynaud successfully unloaded.");
	}
}
