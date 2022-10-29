package de.some.factions.commands;

import de.some.factions.Faction;
import de.some.factions.FactionManager;
import de.some.factions.SomeFactions;
import de.some.factions.events.ChangeFactionEvent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static de.some.factions.Faction.FACTIONS;
import static de.some.factions.Faction.NO_FACTION;


public class FactionCommand implements CommandExecutor, TabCompleter {

    private final SomeFactions plugin;

    public FactionCommand(SomeFactions plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label,
                             String[] args) {
        if (args.length == 0) {
            sender.sendMessage("You can try the following Actions: join, get");
            return false;
        } else {
            switch (args[0]) {
                case "join": // sc join <class>
                    if (args.length >= 2 &&
                            Arrays.stream(FACTIONS).map(Faction::toString).anyMatch(e -> e.equals(args[1]))) {
                        Player player = Bukkit.getPlayer(sender.getName());
                        Faction newFaction = Faction.fromString(args[1]);
                        if (plugin.getFactionManager().playerHasFaction(player)) {
                            sender.sendMessage("You already have a Faction!");
                            return true;
                        }
                        Faction oldFaction = plugin.getFactionManager().getFactionOfPlayer(player);
                        boolean res = plugin.getFactionManager().setFactionOfPlayer(player, newFaction);
                        Bukkit.getPluginManager().callEvent(new ChangeFactionEvent(player, newFaction, oldFaction));
                        if (res) {
                            sender.sendMessage("You are now a(n) " + args[1] + "!");
                        } else {
                            sender.sendMessage("Something went wrong..." +
                                    " Please contact an Admin if this Error persists.");
                        }
                    } else {
                        sender.sendMessage("You need to choose a Faction. You can choose from: " +
                                Arrays.stream(FACTIONS).map(Faction::toString).reduce((e1, e2) -> e1 + ", " + e2).get());
                    }
                    break;
                case "get": // sc get <PlayerName>
                    if (args.length >= 2) {
                        Player player = Bukkit.getPlayer(args[1]);
                        Faction faction = plugin.getFactionManager().getFactionOfPlayer(player);
                        if (faction != NO_FACTION) {
                            sender.sendMessage(player.getName()+ " is an " + faction);
                        } else {
                            sender.sendMessage(player.getName() + " has no Faction... :(");
                        }
                    } else {
                        sender.sendMessage("You need to specify a Players name.");
                    }
                    break;
                case "set": // faction set <PlayerName> <Faction>
                    if (args.length >= 2 &&
                            !Bukkit.getOperators().stream().anyMatch(el -> el.getName().equals(sender.getName()))) {
                        sender.sendMessage("You have no permission to execute this Command.");
                        return true;
                    }
                    if (args.length >= 3 &&
                            Arrays.stream(FACTIONS).map(Faction::toString).anyMatch(e -> e.equals(args[2]))) {
                        Player player = Bukkit.getPlayer(args[1]);
                        if (player == null) {
                            sender.sendMessage(args[1] + " is not a known Player.");
                            return false;
                        }
                        Faction oldFaction = plugin.getFactionManager().getFactionOfPlayer(player);
                        Faction newFaction = Faction.fromString(args[2]);
                        boolean res = plugin.getFactionManager().setFactionOfPlayer(player, newFaction);
                        Bukkit.getPluginManager().callEvent(new ChangeFactionEvent(player, newFaction, oldFaction));
                        if (res) {
                            sender.sendMessage(args [1] + " is now a(n) " + args[2] + "!");
                        } else {
                            sender.sendMessage("Something went wrong...");
                        }
                    } else {
                        sender.sendMessage("You need to choose a PlayerName and a Faction. You can choose from: " +
                                Arrays.stream(FACTIONS).map(Faction::toString).reduce((e1, e2) -> e1 + ", " + e2).get());
                    }
                    break;
                default:
                    sender.sendMessage("Invalid Command!");
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label,
                                      String[] args) {
        ArrayList<String> list = new ArrayList<>();
        switch (args.length) {
            case 1:
                list.add("join");
                list.add("get");
                System.out.println(Bukkit.getOperators());
                if (Bukkit.getOperators().stream().anyMatch(el -> el.getName().equals(sender.getName()))) {
                    list.add("set");
                }
                break;
            case 2:
                switch (args[0]) {
                    case "set":
                    case "get":
                        return Bukkit.getOnlinePlayers().stream()
                                .map(Player::getName)
                                .collect(Collectors.toList());
                    case "join":
                        return Arrays.stream(FACTIONS)
                                .map(Faction::toString)
                                .collect(Collectors.toList());
                    default:
                        return list;
                }
            case 3:
                return Arrays.stream(FACTIONS)
                        .map(Faction::toString)
                        .collect(Collectors.toList());
        }
        return list;
    }
}
