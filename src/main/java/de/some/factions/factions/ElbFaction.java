package de.some.factions.factions;

import de.some.factions.Faction;
import de.some.factions.SomeFactions;
import org.bukkit.Material;
import org.bukkit.entity.AbstractHorse;
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
import org.spigotmc.event.entity.EntityDismountEvent;
import org.spigotmc.event.entity.EntityMountEvent;

import java.util.List;
import java.util.stream.Collectors;


public class ElbFaction extends AbstractFactionWithUniqueCraftingRecipes {

    public ElbFaction(SomeFactions plugin) {
        super(plugin, Faction.ELB);
        List<Recipe> enchantTableRecipe = plugin.getServer().getRecipesFor(new ItemStack(Material.ENCHANTING_TABLE));
        this.uniqueRecipes.addAll(
                enchantTableRecipe.stream()
                        .filter(el -> el instanceof ShapedRecipe)
                        .map(el -> ((ShapedRecipe) el).getKey()).collect(Collectors.toList())
        );
    }

    @Override
    public void setEffectsFor(Player player) {
        player.setWalkSpeed(0.27f);
        player.addPotionEffect(
                new PotionEffect(PotionEffectType.JUMP, DURATION, 0, false, false));
    }

    @Override
    public void clearEffectsFor(Player player) {
        player.setWalkSpeed(0.2f);
        player.removePotionEffect(PotionEffectType.JUMP);
    }

    @EventHandler (priority = EventPriority.HIGHEST)
    public void onPlayerExpChangeEvent(PlayerExpChangeEvent event) {
        if (isEventForFaction(event.getPlayer())) {
            event.setAmount(event.getAmount()*2);
        }
    }

    @EventHandler (priority = EventPriority.HIGHEST)
    public void onPrepareItemEnchantEvent(PrepareItemEnchantEvent event) {
        Player player = event.getEnchanter();
        if (!isEventForFaction(player)) {
            event.setCancelled(true);
            player.sendMessage("§oYou are not worthy enough to use this holy table. Only an §2Elb§r§o my use it!");
        }
    }

    @EventHandler (priority = EventPriority.HIGHEST)
    public void onEntityMountEvent(EntityMountEvent event){
        if(event.getMount() instanceof AbstractHorse && isEventForFaction((Player) event.getEntity())){
            AbstractHorse horse = (AbstractHorse) event.getMount();
            horse.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 99999, 3, false, false));
            horse.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 99999, 1, false, false));
        }
    }

    @EventHandler (priority = EventPriority.HIGHEST)
    public void onEntityDisMountEvent(EntityDismountEvent event){
        if(event.getDismounted() instanceof AbstractHorse && isEventForFaction((Player) event.getEntity())){
            AbstractHorse horse = (AbstractHorse) event.getDismounted();
            horse.removePotionEffect(PotionEffectType.SPEED);
            horse.removePotionEffect(PotionEffectType.JUMP);
        }
    }
}
