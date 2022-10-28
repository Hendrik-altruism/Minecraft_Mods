package de.some.factions.factions;

import de.some.factions.FactionManager;
import de.some.factions.SomeFactions;
import org.bukkit.entity.AbstractHorse;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;


public class HumanFaction extends AbstractFaction{


    public HumanFaction(SomeFactions plugin) {
        super(plugin, FactionManager.HUMAN, "§6");
    }

    @Override
    public void resetEffects(Player player) {
        player.addPotionEffect(
                new PotionEffect(PotionEffectType.HERO_OF_THE_VILLAGE, 99999, 0, false, false));
        player.addPotionEffect(
                new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 99999, 0, false, false));
    }

    @Override
    public void clearEffectsForAll() {

    }
}
