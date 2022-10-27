package de.some.factions;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FactionManager {

    public static final String HUMAN = "human";
    public static final String ORC = "orc";
    public static final String ELB = "elb";
    public static final String DWARF = "dwarf";
    public static final String OCEAN = "ocean";
    public static final String[] FACTIONS = {ORC, HUMAN, ELB, DWARF, OCEAN};

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

    public String getFactionOfPlayer(Player player) {
        String name = player.getName();
        if (this.factionConfig.contains(name)) {
            String faction = this.factionConfig.getString(name);
            return Arrays.asList(FACTIONS).contains(faction) ? faction : null;
        }
        return null;
    }

    public boolean setFactionOfPlayer(Player player, String faction) {
        if (!Arrays.asList(FACTIONS).contains(faction)) {
            // TODO throw Exception
            return false;
        }
        String name = player.getName();
        factionConfig.set(name, faction);
        return saveFactionConfig();
    }

    public boolean playerHasFaction(Player player) {
        return getFactionOfPlayer(player) != null;
    }

}
