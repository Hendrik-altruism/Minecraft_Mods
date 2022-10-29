package de.some.factions;

import de.some.factions.events.ChangeFactionEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.*;

import java.util.HashMap;
import java.util.UUID;

public class FactionTabListScoreboard implements Listener {

    private final FactionManager factionManager;
    private final HashMap<UUID, Scoreboard> scoreboards;

    public FactionTabListScoreboard(FactionManager factionManager) {
        this.factionManager = factionManager;
        scoreboards = new HashMap<>();

    }

    @EventHandler (priority = EventPriority.HIGHEST)
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        scoreboards.computeIfAbsent(player.getUniqueId(), uuid -> {
            Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
            if (scoreboard.getObjective("faction") != null) {
                scoreboard.getObjective("faction").unregister();
            }
            Objective sidebarObjective = scoreboard.registerNewObjective("faction", Criteria.DUMMY, "Some");
            sidebarObjective.setDisplaySlot(DisplaySlot.SIDEBAR);
            player.setScoreboard(scoreboard);
            return scoreboard;
        });
        scoreboards.forEach((uuid, scoreboard) -> {
            Faction faction = factionManager.getFactionOfPlayer(event.getPlayer());
            Team team = scoreboard.getTeam(faction.toString());
            if (team == null) {
                team = scoreboard.registerNewTeam(faction.toString());
                team.setPrefix(faction + " | ");
                team.setColor(faction.getColor());
            }
            team.addEntry(event.getPlayer().getName());
        });
    }

    @EventHandler (priority = EventPriority.LOWEST)
    public void onLeft(PlayerQuitEvent event) {
        scoreboards.remove(event.getPlayer().getUniqueId());
        scoreboards.forEach((uuid, scoreboard) -> {
            Team team = scoreboard.getEntryTeam(event.getPlayer().getName());
            if (team != null) team.removeEntry(event.getPlayer().getName());
        });
    }

    @EventHandler
    public void onChangeFactionEvent(ChangeFactionEvent event) {
        scoreboards.forEach((uuid, scoreboard) -> {
            Team team = scoreboard.getEntryTeam(event.getPlayer().getName());
            if (team != null){
                team.removeEntry(event.getPlayer().getName());
            }
            team = scoreboard.getTeam(event.getNewFaction().toString());
            if (team == null) {
                team = scoreboard.registerNewTeam(event.getNewFaction().toString());
                team.setPrefix(event.getNewFaction() + " | ");
                team.setColor(event.getNewFaction().getColor());
            }
            team.addEntry(event.getPlayer().getName());
        });
    }
}
