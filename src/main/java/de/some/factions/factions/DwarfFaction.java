package de.some.factions.factions;

import de.some.factions.FactionManager;
import de.some.factions.SomeFactions;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.AbstractHorse;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.vehicle.VehicleCreateEvent;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Arrays;

import static org.bukkit.Material.*;

public class DwarfFaction extends AbstractFactionWithUniqueCraftingRecipes {

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

    private static final Material[] VEHICLES = {
            MINECART,
            FURNACE_MINECART,
            CHEST_MINECART,
            HOPPER_MINECART,
            TNT_MINECART
    };

    public DwarfFaction(SomeFactions plugin) {
        super(plugin, FactionManager.DWARF, "§8");
        ShapedRecipe[] dwarfRecipes = {createIronPickaxeRecipe(), createWoodenPickaxeRecipe()};
        Arrays.stream(dwarfRecipes).forEach(recipe -> {
            this.uniqueRecipes.add(recipe.getKey());
            Bukkit.addRecipe(recipe);
        });
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
        if(event.getBlock().getBlockData().getMaterial() == DIAMOND_ORE){}
    }

    @EventHandler (priority = EventPriority.HIGHEST)
    public void onBlockDropItemEvent(BlockDropItemEvent event) {
        if(isEventForFaction(event.getPlayer()) &&
                Arrays.stream(ORES).anyMatch(ore -> event.getBlockState().getBlockData().getMaterial().equals(ore))){
            event.getItems().stream()
                    .filter(item -> !item.getItemStack().getType().isBlock())
                    .forEach(item -> {
                        ItemStack stack = item.getItemStack();
                        stack.setAmount(stack.getAmount() + 2);
                        item.setItemStack(stack);
                    });
        }
    }

    @EventHandler (priority = EventPriority.HIGHEST)
    public void onPlayerInteractAtEntityEvent(PlayerInteractAtEntityEvent event){
        System.out.print("Horse");
        if(event.getRightClicked() instanceof Horse){
            Horse horse = (Horse) event.getRightClicked();
            System.out.print("Horse Ride");
            horse.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 99999, 1, false, false));
        }
    }

    @EventHandler (priority = EventPriority.HIGHEST)
    public void onVehicleEnterEvent(VehicleEnterEvent event){
        System.out.print("Horse Ride");
        if(event.getVehicle() instanceof AbstractHorse){
            AbstractHorse horse = (AbstractHorse) event.getVehicle();
            System.out.print("Horse Ride");
            horse.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 99999, 1, false, false));
        }
    }

    @EventHandler (priority = EventPriority.HIGHEST)
    public void onVehicleCreateEvent(VehicleCreateEvent event) {
        if (event.getVehicle() instanceof Minecart) {
            Minecart minecart = (Minecart) event.getVehicle();
            System.out.println("Speed changed from: "+minecart.getMaxSpeed());
            minecart.setMaxSpeed(.8);
        }
    }
    private ShapedRecipe createWoodenPickaxeRecipe() {
        ItemStack item = new ItemStack(WOODEN_PICKAXE);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(FACTION_COLOR + "Dwarfen Wooden Pickaxe");
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
        itemMeta.setDisplayName(FACTION_COLOR + "Dwarfen Iron Pickaxe");
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


}
