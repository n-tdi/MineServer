package world.ntdi.api.pet;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor
public class CustomPet implements Pet {
    @Getter
    private final String m_name;
    private final ItemStack m_petItem;

    @Override
    public ItemStack getPetItem() {
        return m_petItem;
    }
}
