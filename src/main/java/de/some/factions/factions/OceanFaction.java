package de.some.factions.factions;

import de.some.factions.FactionManager;
import de.some.factions.SomeFactions;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class OceanFaction extends AbstractFactionWithUniqueCraftingRecipes {

    public OceanFaction(SomeFactions plugin) {
        super(plugin, FactionManager.OCEAN, "$9");
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
                new PotionEffect(PotionEffectType.DOLPHINS_GRACE, 99999, 0, false, false));
        player.addPotionEffect(
                new PotionEffect(PotionEffectType.CONDUIT_POWER, 99999, 1, false, false));
        player.addPotionEffect(
                new PotionEffect(PotionEffectType.LUCK, 99999, 0, false, false));
    }

    @Override
    public void clearEffectsFor(Player player) {
        player.removePotionEffect(PotionEffectType.DOLPHINS_GRACE);
        player.removePotionEffect(PotionEffectType.CONDUIT_POWER);
        player.removePotionEffect(PotionEffectType.LUCK);
    }
}
