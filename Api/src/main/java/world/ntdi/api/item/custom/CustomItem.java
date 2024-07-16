package world.ntdi.api.item.custom;

import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import world.ntdi.api.item.builders.ItemBuilder;

@Getter
public abstract class CustomItem {
    private final int m_id;
    private final String m_title;
    private final String[] m_description;
    private final Material m_material;

    protected CustomItem(int p_id, String p_title, String[] p_description, Material p_material) {
        m_id = p_id;
        m_title = p_title;
        m_description = p_description;
        m_material = p_material;
    }

    public ItemStack getItemStack() {
        final ItemBuilder item = new ItemBuilder().material(m_material).amount(1).lore(m_description).name(m_title);

        final ItemStack itemStack = item.build();
        ItemMeta meta = itemStack.getItemMeta();

        meta.getPersistentDataContainer().set(CustomItemRegister.NAMESPACED_KEY, PersistentDataType.INTEGER, m_id);

        itemStack.setItemMeta(meta);

        return itemStack;
    }

    public abstract void onLeftClick(final Player p_player, final PlayerInteractEvent p_playerInteractEvent);
    public abstract void onRightClick(final Player p_player, final PlayerInteractEvent p_playerInteractEvent);


}
