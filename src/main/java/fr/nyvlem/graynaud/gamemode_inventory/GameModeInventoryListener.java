package fr.nyvlem.graynaud.gamemode_inventory;

import fr.nyvlem.graynaud.utils.Utils;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class GameModeInventoryListener implements Listener {

    private JavaPlugin plugin;

    public GameModeInventoryListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onGamemodeChange(PlayerGameModeChangeEvent event) {
        Player player = event.getPlayer();
        File playerDataFile = Utils.getDataFileForPlayer(plugin, player);

        try {
            YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(playerDataFile);
            yamlConfiguration.set(Utils.getInventoryContentPath(player.getGameMode()), player.getInventory().getContents());
            yamlConfiguration.save(playerDataFile);

            Object content = yamlConfiguration.get(Utils.getInventoryContentPath(event.getNewGameMode()));

            if (content instanceof ArrayList<?> && !((ArrayList<?>) content).isEmpty() && ((ArrayList<?>) content).stream().anyMatch(Objects::nonNull)) {
                ((ArrayList<?>) content).stream().filter(Objects::nonNull).findFirst().ifPresent(o -> {
                    if (o instanceof ItemStack) {
                        ArrayList<ItemStack> itemStacks = ((ArrayList<ItemStack>) content);
                        player.getInventory().setContents(itemStacks.toArray(new ItemStack[]{}));
                    }
                });
            } else {
                player.getInventory().clear();
            }
        } catch (IOException e) {
            plugin.getLogger().severe("Can't right in data to file: " + playerDataFile.getName() + " for reason: " + e.getMessage() + ". Abort inventory change !");
        }
    }
}
