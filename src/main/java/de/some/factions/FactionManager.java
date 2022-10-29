package de.some.factions;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class FactionManager {

    private static final String CONFIG_NAME = "factions.yml";
    private FileConfiguration factionConfig;

    private final SomeFactions plugin;

    public FactionManager(SomeFactions plugin) {
        this.plugin = plugin;
        createFactionConfigFile();
    }

    private void createFactionConfigFile() {
        File factionConfigFile = new File(plugin.getDataFolder(), CONFIG_NAME);
        if (!factionConfigFile.exists()) {
            factionConfigFile.getParentFile().mkdirs();
            try {
                factionConfigFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // load ConfigFile
        this.factionConfig = new YamlConfiguration();
        try {
            factionConfig.load(factionConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public FileConfiguration getFactionConfig() {
        return this.factionConfig;
    }

    public boolean saveFactionConfig() {
        try {
            this.factionConfig.save(plugin.getDataFolder() + "/" + CONFIG_NAME);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public Faction getFactionOfPlayer(Player player) {
        String name = player.getName();
        if (this.factionConfig.contains(name)) {
            return Faction.fromString(this.factionConfig.getString(name));
        }
        return null;
    }

    public boolean setFactionOfPlayer(Player player, Faction faction) {
        String name = player.getName();
        factionConfig.set(name, faction.toString());
        return saveFactionConfig();
    }

    public boolean playerHasFaction(Player player) {
        return getFactionOfPlayer(player) != null;
    }

}
