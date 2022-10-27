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


public class DwarfFaction extends AbstractFaction {

    public static final String FACTION_NAME = "dwarf";
    public static final String FACTION_COLOR = "§8";
    private static final Material[] ORES = {
            DIAMOND_ORE,
            IRON_ORE,
            GOLD_ORE,
            REDSTONE_ORE,
            LAPIS_ORE,
            COPPER_ORE,
            COAL_ORE,
            EMERALD_ORE,
            ANCIENT_DEBRIS,
            NETHER_QUARTZ_ORE,
            NETHER_GOLD_ORE
    };

    public DwarfFaction(SomeFactions plugin) {
        super(plugin);
        Bukkit.addRecipe(createIronPickaxeRecipe());
    }

    @Override
    public void resetEffects(Player player) {
        player.setWalkSpeed(0.15f);
        player.addPotionEffect(
                new PotionEffect(PotionEffectType.FAST_DIGGING, 99999, 1, false, false));
        player.addPotionEffect(
                new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 99999, 0, false, false));
    }

    @Override
    public void clearEffectsForAll() {

    }

    @EventHandler (priority = EventPriority.HIGHEST)
    public void onBlockBreakEvent(BlockBreakEvent event) {
        if(event.getBlock().getBlockData().getMaterial()== DIAMOND_ORE){}
    }

    @EventHandler (priority = EventPriority.HIGHEST)
    public void onBlockDropItemEvent(BlockDropItemEvent event) {
        if(isEventForFaction(event.getPlayer()) &&
                Arrays.stream(ORES).anyMatch(ore -> event.getBlockState().getBlockData().getMaterial().equals(ore))){
            event.getItems().stream().forEach(item -> {
                ItemStack stack = item.getItemStack();
                stack.setAmount(stack.getAmount() + 2);
                item.setItemStack(stack);
            });
        }
    }

    @EventHandler (priority = EventPriority.HIGHEST)
    public void onPrepareItemCraftEvent(PrepareItemCraftEvent event) {
        Player player = Bukkit.getPlayer(event.getViewers().get(0).getName());
        if (!isEventForFaction(player) && event.getRecipe() instanceof ShapedRecipe) {
           ShapedRecipe recipe = (ShapedRecipe) event.getRecipe();
            if (recipe.getKey().equals(new NamespacedKey(this.plugin, "dwarf_iron_pickaxe"))) {
               event.getInventory().setResult(null);
           }
        }
    }


    private Recipe createIronPickaxeRecipe() {
        ItemStack item = new ItemStack(IRON_PICKAXE);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(FACTION_COLOR + "Dwarfen Iron Pickaxe");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("Heavier, stronger and just better.");
        lore.add("A real Iron Pickaxe!");
        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);
        item.addEnchantment(Enchantment.DURABILITY, 2);
        item.addEnchantment(Enchantment.DIG_SPEED, 3);
        NamespacedKey namespacedKey = new NamespacedKey(plugin, "dwarf_iron_pickaxe");
        ShapedRecipe recipe = new ShapedRecipe(namespacedKey, item);
        recipe.shape("III", " S ", " S ");
        recipe.setIngredient('I', IRON_BLOCK);
        recipe.setIngredient('S', STICK);
        return recipe;
    }
}
