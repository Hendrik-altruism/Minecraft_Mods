package de.some.factions.factions;

import de.some.factions.FactionManager;
import de.some.factions.SomeFactions;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public abstract class AbstractFaction implements Listener {

    public final String FACTION_NAME;
    public final String FACTION_COLOR;
    protected final FactionManager factionManager;
    protected final SomeFactions plugin;

    protected AbstractFaction (SomeFactions plugin, String FACTION_NAME, String FACTION_COLOR) {
        this.factionManager = plugin.getFactionManager();
        this.plugin = plugin;
        this.FACTION_NAME = FACTION_NAME;
        this.FACTION_COLOR = FACTION_COLOR;
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
        if (isEventForFaction(event.getPlayer()) && event.getItem().isSimilar(new ItemStack(Material.MILK_BUCKET))) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    filterEvents(event.getPlayer());
                }
            }.runTaskLater(plugin, 1);
        }
    }

    protected boolean isEventForFaction(Player player) {
        String faction = this.factionManager.getFactionOfPlayer(player);
        return faction != null && faction.equals(FACTION_NAME);
    }
    protected void filterEvents(Player player) {
        if (this.isEventForFaction(player)) {
            this.resetEffects(player);
        }
    }

    public abstract void resetEffects(Player player);
    public abstract void clearEffectsForAll();

}
