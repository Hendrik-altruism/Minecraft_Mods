package de.some.factions.commands;

import de.some.factions.SomeFactions;
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

import static de.some.factions.FactionManager.FACTIONS;


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
                    if (args.length >= 2 && Arrays.stream(FACTIONS).anyMatch(e -> e.equals(args[1]))) {
                        Player player = Bukkit.getPlayer(sender.getName());
                        if (plugin.getFactionManager().playerHasFaction(player)) {
                            sender.sendMessage("You already have a Faction!");
                            return true;
                        }
                        boolean res = plugin.getFactionManager().setFactionOfPlayer(player, args[1]);
                        if (res) {
                            sender.sendMessage("You are now a(n) " + args[1] + "!");
                        } else {
                            sender.sendMessage("Something went wrong..." +
                                    " Please contact an Admin if this Error persists.");
                        }
                    } else {
                        sender.sendMessage("You need to choose a Faction. You can choose from: " +
                                Arrays.stream(FACTIONS).reduce((e1, e2) -> e1 + ", " + e2).get());
                    }
                    break;
                case "get": // sc get <PlayerName>
                    if (args.length >= 2) {
                        Player player = Bukkit.getPlayer(args[1]);
                        String faction = plugin.getFactionManager().getFactionOfPlayer(player);
                        if (faction != null) {
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
                    if (args.length >= 3 && Arrays.stream(FACTIONS).anyMatch(e -> e.equals(args[2]))) {
                        Player player = Bukkit.getPlayer(args[1]);
                        if (player == null) {
                            sender.sendMessage(args[1] + " is not a known Player.");
                            return false;
                        }
                        boolean res = plugin.getFactionManager().setFactionOfPlayer(player, args[2]);
                        if (res) {
                            sender.sendMessage(args [1] + " is now a(n) " + args[2] + "!");
                        } else {
                            sender.sendMessage("Something went wrong...");
                        }
                    } else {
                        sender.sendMessage("You need to choose a PlayerName and a Faction. You can choose from: " +
                                Arrays.stream(FACTIONS).reduce((e1, e2) -> e1 + ", " + e2).get());
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
                                .collect(Collectors.toList());
                    default:
                        return list;
                }
            case 3:
                return Arrays.stream(FACTIONS)
                        .collect(Collectors.toList());
        }
        return list;
    }
}
