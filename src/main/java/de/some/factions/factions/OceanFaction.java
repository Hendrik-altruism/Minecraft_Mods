package de.some.factions.factions;

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
    public static final String FACTION_NAME = "ocean";
    public static final String FACTION_COLOR = "§7";

    protected OceanFaction(SomeFactions plugin) {
        super(plugin);
    }

    @Override
    public void resetEffects(Player player) {

    }

    @Override
    public void clearEffectsForAll() {

    }
}
