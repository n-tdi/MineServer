package world.ntdi.api.pet.pets;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import world.ntdi.api.item.builders.ItemBuilder;
import world.ntdi.api.pet.CustomPet;
import world.ntdi.api.pet.Pets;

public class CreeperPet extends CustomPet {
    public CreeperPet() {
        super(ChatColor.GREEN + "Creeper", ItemBuilder.of(Material.CREEPER_HEAD).build(), Pets.CREEPER);
    }
}
