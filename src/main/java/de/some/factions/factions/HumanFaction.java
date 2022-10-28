package de.some.factions.factions;

import de.some.factions.FactionManager;
import de.some.factions.SomeFactions;
import org.bukkit.entity.AbstractHorse;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.spigotmc.event.entity.EntityDismountEvent;
import org.spigotmc.event.entity.EntityMountEvent;


public class HumanFaction extends AbstractFaction{


    public HumanFaction(SomeFactions plugin) {
        super(plugin, FactionManager.HUMAN, "§6");
    }

    @Override
    public void setEffectsFor(Player player) {
        player.addPotionEffect(
                new PotionEffect(PotionEffectType.HERO_OF_THE_VILLAGE, 99999, 0, false, false));
        player.addPotionEffect(
                new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 99999, 0, false, false));
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerInteractEntityEvent(PlayerInteractEntityEvent event){
        System.out.print("Horse");
        if(event.getRightClicked() instanceof AbstractHorse){
            AbstractHorse horse = (AbstractHorse) event.getRightClicked();
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

    @Override
    public void clearEffectsForAll() {

    }
}
