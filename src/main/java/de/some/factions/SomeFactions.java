package de.some.factions;

import de.some.factions.commands.FactionCommand;
import de.some.factions.commands.TestCommand;
import de.some.factions.factions.DwarfFaction;
import org.bukkit.plugin.java.JavaPlugin;


public final class SomeFactions extends JavaPlugin {

    private FactionManager factionManager;

    @Override
    public void onEnable() {
        this.factionManager = new FactionManager(this);
        getServer().getPluginManager().registerEvents(new DwarfFaction(this), this);

        getCommand("test").setExecutor(new TestCommand(this));
        getCommand("faction").setExecutor(new FactionCommand(this));
    }

    @Override
    public void onDisable() { }

    public FactionManager getFactionManager() {
        return factionManager;
    }
}
