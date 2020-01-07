package fr.nyvlem.graynaud.utils;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class Utils {

    private Utils() {
    }

    public static File getDataFileForPlayer(JavaPlugin plugin, Player player) {
        return new File(plugin.getDataFolder() + File.separator + Constants.DATA_FOLDER + File.separator + player.getUniqueId() + ".yml");
    }

    public static String getInventoryContentPath(GameMode gameMode) {
        return "inventory." + gameMode.name() + ".content";
    }
}
