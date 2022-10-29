package de.some.factions.events;

import de.some.factions.Faction;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class ChangeFactionEvent extends PlayerEvent {

    private static final HandlerList HANDLERS = new HandlerList();

    private final Faction newFaction;
    private final Faction oldFaction;

    public ChangeFactionEvent(Player who, Faction newFaction, Faction oldFaction) {
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

    public Faction getNewFaction() {
        return this.newFaction;
    }
    public Faction getOldFaction() {
        return this.oldFaction;
    }

}
