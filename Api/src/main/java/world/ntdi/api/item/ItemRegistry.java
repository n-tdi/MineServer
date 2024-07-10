package world.ntdi.api.item;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import world.ntdi.api.collections.RegistryTemplate;

public class ItemRegistry extends RegistryTemplate<String, ItemStack> {

    private static ItemRegistry instance;

    private ItemRegistry() {
        super(new ItemStack(Material.AIR));
    }

    public static ItemRegistry getInstance() {
        if (instance == null) {
            instance = new ItemRegistry();
        }
        return instance;
    }

}
