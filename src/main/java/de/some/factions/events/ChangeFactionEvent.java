package de.some.factions.events;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class ChangeFactionEvent extends PlayerEvent {

    private static final HandlerList HANDLERS = new HandlerList();

    private String newFaction;
    private String oldFaction;

    public ChangeFactionEvent(Player who, String newFaction, String oldFaction) {
        super(who);
        this.newFaction = newFaction;
        this.oldFaction = oldFaction;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public String getNewFaction() {
        return this.newFaction;
    }
    public String getOldFaction() {
        return this.oldFaction;
    }

}
