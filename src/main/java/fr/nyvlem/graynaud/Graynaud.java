package fr.nyvlem.graynaud;

import org.bukkit.plugin.java.JavaPlugin;

public class Graynaud extends JavaPlugin {

	@Override
	public void onEnable() {
		getLogger().info("Plugin lancé chef ! graynaud paré a l'attaque !");
	}
	
	@Override
	public void onDisable() {
		getLogger().info("Ok on remballe.");
	}
}
