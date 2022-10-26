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

    @Override
    public void onEnable() {
        this.factionConfigFile = new File(getDataFolder(), CONFIG_NAME);
        if (!this.factionConfigFile.exists()) {
            this.factionConfigFile.getParentFile().mkdirs();
            saveResource(CONFIG_NAME, false);
            this.factionConfigFile = new File(getDataFolder(), CONFIG_NAME);
        }
        this.factionConfig = new YamlConfiguration();
        try {
            factionConfig.load(factionConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }

        // Plugin startup logic
        getCommand("test").setExecutor(new TestCommand(this));
        getCommand("faction").setExecutor(new FactionCommand(this));

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
