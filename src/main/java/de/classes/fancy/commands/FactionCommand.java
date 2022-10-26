package de.classes.fancy.commands;

import de.classes.fancy.Fancy;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class FactionCommand implements CommandExecutor, TabCompleter {

    private final Fancy plugin;
    private static final String HUMAN = "human";
    private static final String ORC = "orc";
    private static final String ELB = "elb";
    private static final String DWARF = "dwarf";
    private static final String[] FACTIONS = {ORC, HUMAN, ELB, DWARF};

    public FactionCommand(Fancy plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
                             String[] args) {
        if (args.length == 0) {
            sender.sendMessage("You can try the following Actions: join, get");
            return false;
        } else {
            switch (args[0]) {
                case "join": // sc join <class>
                    if (args.length >= 2 && Arrays.stream(FACTIONS).anyMatch(e -> e.equals(args[1]))) {
                        Player player = Bukkit.getPlayer(sender.getName());
                        if (this.getFactionOfPlayer(player) != null) {
                            sender.sendMessage("You already have a Faction!");
                            return true;
                        }
                        FileConfiguration fc = plugin.getFactionConfig();
                        if (!fc.contains(args[1])) {
                            fc.set(args[1], new ArrayList<String>());
                        }
                        List<String> factionMembers = fc.getStringList(args[1]);
                        factionMembers.add(player.getName());
                        fc.set(args[1], factionMembers);
                        plugin.saveFactionConfig();
                        sender.sendMessage("You have joined " + args[1] + "!");
                    } else {
                        sender.sendMessage("You need to choose a Faction. You can choose from: " +
                                Arrays.stream(FACTIONS).reduce((e1, e2) -> e1 + ", " + e2).get());
                    }
                    break;
                case "get": // sc get <PlayerName>
                    Player player = Bukkit.getPlayer(args[1]);
                    String faction = this.getFactionOfPlayer(player);
                    if (faction != null) {
                        sender.sendMessage(player.getName()+ " is an " + faction);
                    } else {
                        sender.sendMessage(player.getName() + " has no Faction... :(");
                    }
                    break;
                default:
                    sender.sendMessage("Invalid Command!");
            }
        }
        return true;
    }


    private String getFactionOfPlayer(Player player) {
        FileConfiguration fc = plugin.getFactionConfig();
        for (String faction : FACTIONS) {
            if (fc.contains(faction) &&
                    fc.getStringList(faction).stream().anyMatch(el -> el.equals(player.getName()))) {
                return faction;
            }
        }
        return null;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
                                      String[] args) {
        ArrayList<String> list = new ArrayList<>();
        if (args.length == 1) {
            list.add("join");
            list.add("get");
        } else if (args.length == 2) {
            switch (args[0]) {
                case "get":
                    return Bukkit.getOnlinePlayers().stream()
                            .map(Player::getName)
                            .collect(Collectors.toList());
                case "join":
                    return Arrays.stream(FACTIONS)
                            .collect(Collectors.toList());
                default:
                    return list;
            }
        }
        return list;
    }
}
