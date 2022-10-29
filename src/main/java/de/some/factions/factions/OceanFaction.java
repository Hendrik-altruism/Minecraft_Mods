package de.some.factions.factions;

import de.some.factions.Faction;
import de.some.factions.SomeFactions;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Arrays;

import static org.bukkit.Material.*;

public class OceanFaction extends AbstractFactionWithUniqueCraftingRecipes {

    public OceanFaction(SomeFactions plugin) {
        super(plugin, Faction.OCEAN);
        ShapedRecipe[] dwarfRecipes = {
                createTravelTrident(),
                createAlphaTrident()
        };
        Arrays.stream(dwarfRecipes).forEach(recipe -> {
            this.uniqueRecipes.add(recipe.getKey());
            Bukkit.addRecipe(recipe);
        });
    }

    @Override
    public void setEffectsFor(Player player) {
        player.addPotionEffect(
                new PotionEffect(PotionEffectType.DOLPHINS_GRACE, 99999, 2, false, false));
        player.addPotionEffect(
                new PotionEffect(PotionEffectType.CONDUIT_POWER, 99999, 1, false, false));
        player.addPotionEffect(
                new PotionEffect(PotionEffectType.LUCK, 99999, 2, false, false));
    }

    @Override
    public void clearEffectsFor(Player player) {
        player.removePotionEffect(PotionEffectType.DOLPHINS_GRACE);
        player.removePotionEffect(PotionEffectType.CONDUIT_POWER);
        player.removePotionEffect(PotionEffectType.LUCK);
    }

    private ShapedRecipe createAlphaTrident() {
        ItemStack item = new ItemStack(TRIDENT);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(faction.getColor() + "Thors Trident");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("§9What is dead will never die.");
        lore.add("§9Become Thor himself.");
        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);
        //item.addEnchantment(Enchantment.DAMAGE_ALL, 3);
        item.addEnchantment(Enchantment.IMPALING, 5);
        item.addEnchantment(Enchantment.LOYALTY, 3);
        item.addEnchantment(Enchantment.CHANNELING, 1);
        item.addEnchantment(Enchantment.DURABILITY, 3);
        item.addEnchantment(Enchantment.MENDING, 1);
        NamespacedKey namespacedKey = new NamespacedKey(plugin, "thor_trident");
        ShapedRecipe recipe = new ShapedRecipe(namespacedKey, item);
        recipe.shape("NQN", " S ", " S ");
        recipe.setIngredient('Q', CONDUIT);
        recipe.setIngredient('N', NETHERITE_INGOT);
        recipe.setIngredient('S', STICK);
        return recipe;
    }

    private ShapedRecipe createTravelTrident() {
        ItemStack item = new ItemStack(TRIDENT);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(faction.getColor() + "Travel Trident");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("§9Speed");
        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);
        item.addEnchantment(Enchantment.RIPTIDE, 3);
        item.addEnchantment(Enchantment.DURABILITY, 3);
        item.addEnchantment(Enchantment.MENDING, 1);
        NamespacedKey namespacedKey = new NamespacedKey(plugin, "travelling_trident");
        ShapedRecipe recipe = new ShapedRecipe(namespacedKey, item);
        recipe.shape("NQN", " S ", " S ");
        recipe.setIngredient('Q', IRON_BLOCK);
        recipe.setIngredient('N', DIAMOND);
        recipe.setIngredient('S', STICK);
        return recipe;
    }
}
