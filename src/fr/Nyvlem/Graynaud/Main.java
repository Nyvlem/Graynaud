package fr.Nyvlem.Graynaud;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	@Override
	public void onEnable() {
		System.out.println("Plugin lancé chef ! Graynaud paré a l'attaque !");
	}
	
	@Override
	public void onDisable() {
		System.out.println("Ok on remballe.");
	}
}
