package de.classes.fancy;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class FactionManager {

    public static final String HUMAN = "human";
    public static final String ORC = "orc";
    public static final String ELB = "elb";
    public static final String DWARF = "dwarf";
    public static final String[] FACTIONS = {ORC, HUMAN, ELB, DWARF};

    private static final String CONFIG_NAME = "sc_factions.yml";
    private File factionConfigFile;
    private FileConfiguration factionConfig;

    private Fancy plugin;

    public FactionManager(Fancy plugin) {
        this.plugin = plugin;
        createFactionConfigFile();
    }

    private void createFactionConfigFile() {
        this.factionConfigFile = new File(plugin.getDataFolder(), CONFIG_NAME);
        if (!this.factionConfigFile.exists()) {
            this.factionConfigFile.getParentFile().mkdirs();
            try {
                this.factionConfigFile.createNewFile();
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

    public void saveFactionConfig() {
        System.out.println(plugin.getDataFolder());
        try {
            this.factionConfig.save(plugin.getDataFolder() + "/" + CONFIG_NAME);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getFactionOfPlayer(Player player) {
        for (String faction : FACTIONS) {
            if (this.factionConfig.contains(faction) &&
                    this.factionConfig.getStringList(faction).stream().anyMatch(el -> el.equals(player.getName()))) {
                return faction;
            }
        }
        return null;
    }

}
