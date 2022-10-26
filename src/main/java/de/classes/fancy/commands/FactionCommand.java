package de.classes.fancy.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.File;

public class FactionCommand implements CommandExecutor {

    private Plugin plugin;

    public FactionCommand(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("You can try the following Actions: join, get");
            return false;
        } else {
            switch (args[0]) {
                case "join": // sc join <class>
                    Player player = Bukkit.getPlayer(sender.getName());
                    sender.sendMessage("You have joined " + args[1] + "!");
                    break;
                case "get": // sc get <PlayerName>
                    Player getPlayer = Bukkit.getPlayer(args[1]);
                default:
                    sender.sendMessage("Invalid Command!");
            }
        }
        return true;
    }
}
