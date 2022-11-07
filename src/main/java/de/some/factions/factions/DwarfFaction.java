package de.some.factions.factions;

import de.some.factions.FactionManager;
import de.some.factions.SomeFactions;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.event.vehicle.VehicleCreateEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Arrays;

import static de.some.factions.Faction.DWARF;
import static org.bukkit.Material.*;

public class DwarfFaction extends AbstractFactionWithUniqueCraftingRecipes {

    private static final Material[] ORES = {
            DIAMOND_ORE,
            DEEPSLATE_DIAMOND_ORE,
            IRON_ORE,
            DEEPSLATE_IRON_ORE,
            GOLD_ORE,
            DEEPSLATE_GOLD_ORE,
            REDSTONE_ORE,
            DEEPSLATE_REDSTONE_ORE,
            LAPIS_ORE,
            DEEPSLATE_LAPIS_ORE,
            COPPER_ORE,
            DEEPSLATE_COPPER_ORE,
            COAL_ORE,
            DEEPSLATE_COAL_ORE,
            EMERALD_ORE,
            DEEPSLATE_EMERALD_ORE,
            ANCIENT_DEBRIS,
            NETHER_QUARTZ_ORE,
            NETHER_GOLD_ORE
    };

    private static final Material[] VEHICLES = {
            MINECART,
            FURNACE_MINECART,
            CHEST_MINECART,
            HOPPER_MINECART,
            TNT_MINECART
    };

    public DwarfFaction(SomeFactions plugin) {
        super(plugin, DWARF);
        ShapedRecipe[] dwarfRecipes = {
                createIronPickaxeRecipe(),
                createWoodenPickaxeRecipe(),
                createGoldPickaxe(),
                createDiamondPickaxe(),
                createIronCombatAxe(),
                createDiamondCombatAxe()
            };
        Arrays.stream(dwarfRecipes).forEach(recipe -> {
            this.uniqueRecipes.add(recipe.getKey());
            Bukkit.addRecipe(recipe);
        });
    }

    @Override
    public void setEffectsFor(Player player) {
        player.setWalkSpeed(0.15f);
        player.addPotionEffect(
                new PotionEffect(PotionEffectType.FAST_DIGGING, DURATION, 1, false, false));
        player.addPotionEffect(
                new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, DURATION, 0, false, false));
    }

    @Override
    public void clearEffectsFor(Player player) {
        player.setWalkSpeed(0.2f);
        player.removePotionEffect(PotionEffectType.FAST_DIGGING);
        player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
    }

    @EventHandler (priority = EventPriority.HIGHEST)
    public void onBlockDropItemEvent(BlockDropItemEvent event) {
        if(isEventForFaction(event.getPlayer()) &&
                Arrays.stream(ORES).anyMatch(ore -> event.getBlockState().getBlockData().getMaterial().equals(ore))){
            event.getItems().stream()
                    .filter(item -> !item.getItemStack().getType().isBlock()
                            || item.getItemStack().getType() == ANCIENT_DEBRIS)
                    .forEach(item -> {
                        if(item.getItemStack().getType() == ANCIENT_DEBRIS){
                            item.setItemStack(new ItemStack(NETHERITE_SCRAP, 2));
                        } else {
                            ItemStack stack = item.getItemStack();
                            stack.setAmount(stack.getAmount() + 2);
                            item.setItemStack(stack);
                        }
                    });
        }
    }

    @EventHandler (priority = EventPriority.HIGHEST)
    public void onVehicleCreateEvent(VehicleCreateEvent event) {
        if (event.getVehicle() instanceof Minecart) {
            Minecart minecart = (Minecart) event.getVehicle();
            minecart.setMaxSpeed(.8);
        }
    }

    private ShapedRecipe createWoodenPickaxeRecipe() {
        ItemStack item = new ItemStack(WOODEN_PICKAXE);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(faction.getColor() + "Dwarfen Wooden Pickaxe");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("§7Heavier, stronger and just better.");
        lore.add("§7This is good enough for now.");
        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);
        item.addEnchantment(Enchantment.DIG_SPEED, 2);
        NamespacedKey namespacedKey = new NamespacedKey(plugin, "dwarf_wood_pickaxe");
        ShapedRecipe recipe = new ShapedRecipe(namespacedKey, item);
        recipe.shape("WWW", " S ", " S ");
        recipe.setIngredient('W', OAK_LOG);
        recipe.setIngredient('S', STICK);
        return recipe;
    }
    private ShapedRecipe createIronPickaxeRecipe() {
        ItemStack item = new ItemStack(IRON_PICKAXE);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(faction.getColor() + "Dwarfen Iron Pickaxe");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("§7Heavier, stronger and just better.");
        lore.add("§7A real Iron Pickaxe!");
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
    private ShapedRecipe createGoldPickaxe() {
        ItemStack item = new ItemStack(GOLDEN_PICKAXE);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(faction.getColor() + "Dwarfen Gold Pickaxe");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("§7Heavier, luckier and just better.");
        lore.add("§7A really fancy Pickaxe!");
        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);
        item.addEnchantment(Enchantment.SILK_TOUCH, 1);
        item.addEnchantment(Enchantment.DURABILITY, 3);
        item.addEnchantment(Enchantment.DIG_SPEED, 2);
        NamespacedKey namespacedKey = new NamespacedKey(plugin, "dwarf_gold_pickaxe");
        ShapedRecipe recipe = new ShapedRecipe(namespacedKey, item);
        recipe.shape("GGG", " S ", " S ");
        recipe.setIngredient('G', GOLD_BLOCK);
        recipe.setIngredient('S', STICK);
        return recipe;
    }
    private ShapedRecipe createDiamondPickaxe() {
        ItemStack item = new ItemStack(DIAMOND_PICKAXE);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(faction.getColor() + "Dwarfen Diamond Pickaxe");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("§7Heavier, Stronger and just better.");
        lore.add("§7A really awesome Pickaxe!");
        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);
        item.addEnchantment(Enchantment.LOOT_BONUS_BLOCKS, 2);
        item.addEnchantment(Enchantment.MENDING, 1);
        item.addEnchantment(Enchantment.DIG_SPEED, 4);
        item.addEnchantment(Enchantment.DURABILITY, 2);
        NamespacedKey namespacedKey = new NamespacedKey(plugin, "dwarf_diamond_pickaxe");
        ShapedRecipe recipe = new ShapedRecipe(namespacedKey, item);
        recipe.shape("DDD", " S ", " S ");
        recipe.setIngredient('D', DIAMOND_BLOCK);
        recipe.setIngredient('S', STICK);
        return recipe;
    }

    private ShapedRecipe createIronCombatAxe() {
        ItemStack item = new ItemStack(IRON_AXE);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(faction.getColor() + "Dwarfen Iron Axe");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("§7Heavier, stronger and just better.");
        lore.add("§7The beloved Weapon of most dwarfs!");
        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);
        item.addEnchantment(Enchantment.DURABILITY, 2);
        item.addEnchantment(Enchantment.DAMAGE_ALL, 3);
        item.addUnsafeEnchantment(Enchantment.LOOT_BONUS_MOBS, 2);
        // item.addEnchantment(Enchantment.KNOCKBACK, 1);
        NamespacedKey namespacedKey = new NamespacedKey(plugin, "dwarf_iron_axe");
        ShapedRecipe recipe = new ShapedRecipe(namespacedKey, item);
        recipe.shape(" II", " SI", " S ");
        recipe.setIngredient('I', IRON_BLOCK);
        recipe.setIngredient('S', STICK);
        return recipe;
    }
    private ShapedRecipe createDiamondCombatAxe() {
        ItemStack item = new ItemStack(DIAMOND_AXE);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(faction.getColor() + "Dwarfen Diamond Axe");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("§7Heavier, stronger and just better.");
        lore.add("§7It is said that a dwarf with an Axe");
        lore.add("§7 is better than 100 Elbs!");
        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);
        item.addEnchantment(Enchantment.DURABILITY, 2);
        item.addEnchantment(Enchantment.DAMAGE_ALL, 4);
        // item.addEnchantment(Enchantment.LOOT_BONUS_MOBS, 2);
        item.addUnsafeEnchantment(Enchantment.KNOCKBACK, 2);
        item.addEnchantment(Enchantment.MENDING, 1);
        item.addUnsafeEnchantment(Enchantment.FIRE_ASPECT, 1);
        NamespacedKey namespacedKey = new NamespacedKey(plugin, "dwarf_diamond_axe");
        ShapedRecipe recipe = new ShapedRecipe(namespacedKey, item);
        recipe.shape(" DD", " SD", " S ");
        recipe.setIngredient('D', DIAMOND_BLOCK);
        recipe.setIngredient('S', STICK);
        return recipe;
    }
}
