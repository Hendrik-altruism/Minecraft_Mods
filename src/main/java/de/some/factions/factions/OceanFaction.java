package de.some.factions.factions;

import de.some.factions.FactionManager;
import de.some.factions.SomeFactions;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Arrays;

import static org.bukkit.Material.*;


public class OceanFaction extends AbstractFaction{

    protected OceanFaction(SomeFactions plugin) {
        super(plugin, FactionManager.OCEAN, "$9");
    }

    @Override
    public void resetEffects(Player player) {
        player.addPotionEffect(
                new PotionEffect(PotionEffectType.DOLPHINS_GRACE, 99999, 0, false, false));
        player.addPotionEffect(
                new PotionEffect(PotionEffectType.CONDUIT_POWER, 99999, 1, false, false));
        player.addPotionEffect(
                new PotionEffect(PotionEffectType.LUCK, 99999, 0, false, false));
    }

    @Override
    public void clearEffectsForAll() {

    }
}
