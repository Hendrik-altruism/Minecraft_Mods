package de.some.factions.factions;

import de.some.factions.FactionManager;
import de.some.factions.SomeFactions;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class OceanFaction extends AbstractFaction{

    public OceanFaction(SomeFactions plugin) {
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
