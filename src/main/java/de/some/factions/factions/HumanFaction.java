package de.some.factions.factions;

import de.some.factions.Faction;
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
        super(plugin, Faction.HUMAN);
    }

    @Override
    public void setEffectsFor(Player player) {
        player.addPotionEffect(
                new PotionEffect(PotionEffectType.HERO_OF_THE_VILLAGE, 99999, 0, false, false));
        player.addPotionEffect(
                new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 99999, 0, false, false));
    }

    @EventHandler (priority = EventPriority.HIGHEST)
    public void onEntityMountEvent(EntityMountEvent event){
        if(event.getMount() instanceof AbstractHorse && isEventForFaction((Player) event.getEntity())){
            AbstractHorse horse = (AbstractHorse) event.getMount();
            System.out.print("Horse Ride");
            horse.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 99999, 5, false, false));
            horse.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 99999, 2, false, false));
        }
    }

    @EventHandler (priority = EventPriority.HIGHEST)
    public void onEntityDisMountEvent(EntityDismountEvent event){
        if(event.getDismounted() instanceof AbstractHorse && isEventForFaction((Player) event.getEntity())){
            AbstractHorse horse = (AbstractHorse) event.getDismounted();
            System.out.print("Horse Ride");
            horse.removePotionEffect(PotionEffectType.SPEED);
            horse.removePotionEffect(PotionEffectType.JUMP);
        }
    }

    @Override
    public void clearEffectsFor(Player player) {
        player.removePotionEffect(PotionEffectType.HERO_OF_THE_VILLAGE);
        player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
    }
}
