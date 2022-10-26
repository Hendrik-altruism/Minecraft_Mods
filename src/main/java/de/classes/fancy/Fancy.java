package de.classes.fancy;

import de.classes.fancy.commands.FactionCommand;
import de.classes.fancy.commands.TestCommand;
import de.classes.fancy.factions.AbstractFaction;
import de.classes.fancy.factions.DwarfFaction;
import org.bukkit.plugin.java.JavaPlugin;


public final class Fancy extends JavaPlugin {

    private FactionManager factionManager;
    private AbstractFaction[] factions;

    @Override
    public void onEnable() {
        this.factionManager = new FactionManager(this);
        // this.factions = new AbstractFaction[]{ new DwarfFaction(this.factionManager, this) };
        getServer().getPluginManager().registerEvents(new DwarfFaction(this), this);

        getCommand("test").setExecutor(new TestCommand(this));
        getCommand("faction").setExecutor(new FactionCommand(this));
    }

    @Override
    public void onDisable() {
        for (AbstractFaction faction : factions) {
            faction.clearEffectsForAll();
        }
    }

    public FactionManager getFactionManager() {
        return factionManager;
    }
}
