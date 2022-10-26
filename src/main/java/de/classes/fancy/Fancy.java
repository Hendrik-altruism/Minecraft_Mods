package de.classes.fancy;

import de.classes.fancy.commands.FactionCommand;
import de.classes.fancy.commands.TestCommand;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;


public final class Fancy extends JavaPlugin {

    private static final String CONFIG_NAME = "sc_factions.yml";

    private File factionConfigFile;
    private FileConfiguration factionConfig;

    private void createFactionConfigFile() {
        this.factionConfigFile = new File(getDataFolder(), CONFIG_NAME);
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
        System.out.println(getDataFolder());
        try {
            this.factionConfig.save(getDataFolder() + "/" + CONFIG_NAME);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onEnable() {
        createFactionConfigFile();

        // Plugin startup logic
        getCommand("test").setExecutor(new TestCommand(this));
        getCommand("faction").setExecutor(new FactionCommand(this));

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
