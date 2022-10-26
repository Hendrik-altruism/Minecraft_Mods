package de.classes.fancy.factions;

import de.classes.fancy.FactionManager;
import de.classes.fancy.Fancy;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public abstract class AbstractFaction implements Listener {

    private final String FACTION_NAME;
    private final FactionManager factionManager;
    private final Fancy plugin;

    public AbstractFaction (String factionName, Fancy plugin) {
        this.FACTION_NAME = factionName;
        this.factionManager = plugin.getFactionManager();
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        this.filterEvents(event.getPlayer());
    }

    @EventHandler (priority = EventPriority.HIGHEST)
    public void onPlayerRespawnEvent(PlayerRespawnEvent event) {
        new BukkitRunnable() {
            @Override
            public void run() {
                filterEvents(event.getPlayer());
            }
        }.runTaskLater(plugin, 1);
    }

    // since Milk can remove effects we need to refresh them
    @EventHandler (priority = EventPriority.HIGHEST) // needs to be called after effects got removed
    public void onPlayerItemConsumeEvent(PlayerItemConsumeEvent event) {
        if (event.getItem().isSimilar(new ItemStack(Material.MILK_BUCKET))) {
            this.filterEvents(event.getPlayer());
        }
    }

    protected boolean isEventForFaction(Player player) {
        return this.factionManager.getFactionOfPlayer(player).equals(FACTION_NAME);
    }
    protected void filterEvents(Player player) {
        if (this.isEventForFaction(player)) {
            this.resetEffects(player);
        }
    }

    public abstract void resetEffects(Player player);
    public abstract void clearEffectsForAll();

}