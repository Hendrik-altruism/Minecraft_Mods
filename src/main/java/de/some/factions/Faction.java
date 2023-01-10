package de.some.factions;

import org.bukkit.ChatColor;

public enum Faction {
    NO_FACTION ("No Faction", ChatColor.GRAY),
    DWARF ("Dwarf", ChatColor.DARK_GRAY),
    ELB  ( "Elb", ChatColor.GREEN),
    HUMAN ("Human", ChatColor.GOLD),
    OCEAN ("Oceanic", ChatColor.BLUE);

    public static final Faction[] FACTIONS = {DWARF, ELB, HUMAN, OCEAN};
    private final String name;
    private final ChatColor color;

    Faction(String name, ChatColor color) {
        this.name = name;
        this.color = color;
    }

    public boolean equalsName(String otherName) {
        return name.equals(otherName);
    }
    public String toString() {
        return this.name;
    }
    public ChatColor getColor() {
        return this.color;
    }

    public static Faction fromString(String faction) {
        for (Faction f:FACTIONS) {
            if (f.toString().equals(faction)) return f;
        }
        return NO_FACTION;
    }
}
