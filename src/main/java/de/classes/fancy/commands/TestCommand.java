package de.classes.fancy.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import sun.tools.jconsole.JConsole;

public class TestCommand implements  CommandExecutor {

    private Plugin plugin;

    public TestCommand(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        /*
        if (args.length > 0) {
            switch (args[0]) {
                case "join": // sc join <class>
                    Player player = Bukkit.getPlayer(sender.getName());
                    sender.sendMessage("You have joined " + args[1] + "!");
                    break;
                case "get":
                    Player getPlayer = Bukkit.getPlayer(args[1]);
                default:
                    sender.sendMessage("Invalid Command!");
            }
        }*/

        Player player = Bukkit.getPlayer(sender.getName());
        player.setWalkSpeed(Float.parseFloat(args[0]));
        sender.sendMessage("Speed changed to: "+args[0]);
        // System.out.println(args.length);
        // sc get <PlayerName>
        return false;
    }
}
