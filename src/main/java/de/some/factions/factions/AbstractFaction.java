package de.some.factions.factions;

import de.some.factions.Faction;
import de.some.factions.FactionManager;
import de.some.factions.SomeFactions;
import de.some.factions.events.ChangeFactionEvent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;

public abstract class AbstractFaction implements Listener {

    protected final int DURATION = 1200;
    public final Faction faction;
    protected final FactionManager factionManager;
    protected final SomeFactions plugin;

    protected AbstractFaction (SomeFactions plugin, Faction faction) {
        this.factionManager = plugin.getFactionManager();
        this.plugin = plugin;
        this.faction = faction;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        this.filterEvents(player);
        if (this.isEventForFaction(player) &&
                !Objects.requireNonNull(event.getJoinMessage()).contains(this.faction.toString())) {
            event.setJoinMessage(this.faction.getColor() + this.faction.toString() + " | " + event.getJoinMessage());
        }
    }

    @EventHandler
    public void onAsyncPlayerChatEvent(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (this.isEventForFaction(player)) {
            player.setDisplayName(this.faction.getColor() + player.getDisplayName() + ChatColor.WHITE);
        }
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

    // since Milk can remove effects we need to refresh them -> now handled onEntityPotionEffectEvent
    /*
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
    }*/

    @EventHandler
    public void onEntityPotionEffectEvent(EntityPotionEffectEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof Player && isEventForFaction((Player) entity)) {
            if (event.getAction().equals(EntityPotionEffectEvent.Action.REMOVED)) {
                this.setEffectsFor((Player) entity);
            } else if (event.getAction().equals(EntityPotionEffectEvent.Action.CLEARED)) {
                // if cleared the effect needs to be updated slightly later
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        setEffectsFor((Player) entity);
                    }
                }.runTaskLater(plugin, 1);
            }
        }
    }

    @EventHandler
    public void onChangeFactionEvent(ChangeFactionEvent event) {
        if (this.faction.equals(event.getOldFaction())) {
            this.clearEffectsFor(event.getPlayer());
        }
        if (this.faction.equals(event.getNewFaction())) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    filterEvents(event.getPlayer());
                }
            }.runTaskLater(plugin, 1);
        }
    }

    protected boolean isEventForFaction(Player player) {
        Faction playerFaction = this.factionManager.getFactionOfPlayer(player);
        return faction.equals(playerFaction);
    }
    protected void filterEvents(Player player) {
        if (this.isEventForFaction(player)) {
            this.setEffectsFor(player);
        }
    }

    public abstract void setEffectsFor(Player player);
    public abstract void clearEffectsFor(Player player);

}
