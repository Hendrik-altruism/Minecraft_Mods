package de.some.factions.factions;

import de.some.factions.FactionManager;
import de.some.factions.SomeFactions;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;
import java.util.stream.Collectors;


public class ElbFaction extends AbstractFactionWithUniqueCraftingRecipes {

    public ElbFaction(SomeFactions plugin) {
        super(plugin, FactionManager.ELB, "$2");
        List<Recipe> enchantTableRecipe = plugin.getServer().getRecipesFor(new ItemStack(Material.ENCHANTING_TABLE));
        this.uniqueRecipes.addAll(
                enchantTableRecipe.stream()
                        .filter(el -> el instanceof ShapedRecipe)
                        .map(el -> ((ShapedRecipe) el).getKey()).collect(Collectors.toList())
        );
    }

    @Override
    public void resetEffects(Player player) {
        player.setWalkSpeed(0.25f);
        player.addPotionEffect(
                new PotionEffect(PotionEffectType.JUMP, 99999, 0, false, false));
    }

    @Override
    public void clearEffectsFor(Player player) {
        player.setWalkSpeed(0.2f);
        player.removePotionEffect(PotionEffectType.JUMP);
    }

    @EventHandler (priority = EventPriority.HIGHEST)
    public void onPlayerExpChangeEvent(PlayerExpChangeEvent event) {
        event.setAmount(event.getAmount()*2);
    }

    @EventHandler (priority = EventPriority.HIGHEST)
    public void onPrepareItemEnchantEvent(PrepareItemEnchantEvent event) {
        Player player = event.getEnchanter();
        if (!isEventForFaction(player)) {
            event.setCancelled(true);
            player.sendMessage("§oYou are not worthy enough to use this holy table. Only an §2Elb§r§o my use it!");
        }
    }

}
