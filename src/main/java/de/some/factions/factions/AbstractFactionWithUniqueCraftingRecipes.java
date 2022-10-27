package de.some.factions.factions;

import de.some.factions.SomeFactions;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ShapedRecipe;

import java.util.ArrayList;

public abstract class AbstractFactionWithUniqueCraftingRecipes extends AbstractFaction {

    protected final ArrayList<NamespacedKey> uniqueRecipes;
    
    protected AbstractFactionWithUniqueCraftingRecipes(SomeFactions plugin, String FACTION_NAME, String FACTION_COLOR) {
        super(plugin, FACTION_NAME, FACTION_COLOR);
        uniqueRecipes = new ArrayList<>();
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPrepareItemCraftEvent(PrepareItemCraftEvent event) {
        Player player = Bukkit.getPlayer(event.getViewers().get(0).getName());
        if (!isEventForFaction(player) && event.getRecipe() instanceof ShapedRecipe) {
            ShapedRecipe recipe = (ShapedRecipe) event.getRecipe();
            if (uniqueRecipes.stream().anyMatch(key -> recipe.getKey().equals(key))) {
                event.getInventory().setResult(null);
            }
        }
    }

}
