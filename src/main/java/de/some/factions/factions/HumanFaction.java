package de.some.factions.factions;

import de.some.factions.Faction;
import de.some.factions.SomeFactions;
import org.bukkit.Material;
import org.bukkit.entity.AbstractHorse;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.event.vehicle.VehicleCreateEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.spigotmc.event.entity.EntityDismountEvent;
import org.spigotmc.event.entity.EntityMountEvent;

import java.util.Arrays;
import java.util.Random;

import static org.bukkit.Material.*;


public class HumanFaction extends AbstractFaction{

    private static final Material[] CROPS = {
            CARROTS,
            POTATOES,
            WHEAT,
            BEETROOT,
            SUGAR_CANE
    };

    public HumanFaction(SomeFactions plugin) {
        super(plugin, Faction.HUMAN);
    }

    @Override
    public void setEffectsFor(Player player) {
        player.addPotionEffect(
                new PotionEffect(PotionEffectType.HERO_OF_THE_VILLAGE, DURATION, 0, false, false));
        player.addPotionEffect(
                new PotionEffect(PotionEffectType.INCREASE_DAMAGE, DURATION, 0, false, false));
    }

    @EventHandler (priority = EventPriority.HIGHEST)
    public void onEntityMountEvent(EntityMountEvent event){
        if(event.getMount() instanceof AbstractHorse && isEventForFaction((Player) event.getEntity())){
            AbstractHorse horse = (AbstractHorse) event.getMount();
            horse.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 99999, 5, false, false));
            horse.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 99999, 2, false, false));
        }
    }

    @EventHandler (priority = EventPriority.HIGHEST)
    public void onEntityDisMountEvent(EntityDismountEvent event){
        if(event.getDismounted() instanceof AbstractHorse && isEventForFaction((Player) event.getEntity())){
            AbstractHorse horse = (AbstractHorse) event.getDismounted();
            horse.removePotionEffect(PotionEffectType.SPEED);
            horse.removePotionEffect(PotionEffectType.JUMP);
        }
    }

    @EventHandler (priority = EventPriority.HIGHEST)
    public void onBlockDropItemEvent(BlockDropItemEvent event) {
        if (isEventForFaction(event.getPlayer()) && event.getBlockState().getBlockData() instanceof Ageable) {
            Ageable block = (Ageable) event.getBlockState().getBlockData();
            if(Arrays.stream(CROPS).anyMatch(crop -> block.getMaterial().equals(crop))
                   && block.getAge() == block.getMaximumAge()) {
                event.getItems().forEach(item -> {
                    ItemStack stack = item.getItemStack();
                    Random random = new Random();
                    stack.setAmount(stack.getAmount() + 1 + random.nextInt(2));
                    item.setItemStack(stack);
                });
            }
        }
    }

    @EventHandler (priority = EventPriority.HIGHEST)
    public void onVehicleCreateEvent(VehicleCreateEvent event) {
        if (event.getVehicle() instanceof Minecart) {
            Minecart minecart = (Minecart) event.getVehicle();
            minecart.setMaxSpeed(.8);
        }
    }

    @Override
    public void clearEffectsFor(Player player) {
        player.removePotionEffect(PotionEffectType.HERO_OF_THE_VILLAGE);
        player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
    }
}
