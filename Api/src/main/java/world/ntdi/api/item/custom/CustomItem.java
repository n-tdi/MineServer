package world.ntdi.api.item.custom;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import world.ntdi.api.item.builders.ItemBuilder;

@Getter
public abstract class CustomItem implements Listener {
    private final int m_id;
    private final String m_title;
    private final String[] m_description;
    private final Material m_material;
    private final NamespacedKey m_namespacedKey;

    protected CustomItem(int p_id, String p_title, String[] p_description, Material p_material) {
        m_id = p_id;
        m_title = p_title;
        m_description = p_description;
        m_material = p_material;
        m_namespacedKey = new NamespacedKey("tazkaboom", m_id + "hehe");

        Bukkit.getServer().getPluginManager().registerEvents(this, Bukkit.getPluginManager().getPlugin("Api"));
    }

    public ItemStack getItemStack() {
        final ItemBuilder item = new ItemBuilder().material(m_material).amount(1).lore(m_description).name(m_title);

        final ItemStack itemStack = item.build();
        ItemMeta meta = itemStack.getItemMeta();

        meta.getPersistentDataContainer().set(m_namespacedKey, PersistentDataType.INTEGER, m_id);

        itemStack.setItemMeta(meta);

        return itemStack;
    }

    public boolean isCustomItem(final ItemStack p_itemStack) {
        final ItemMeta meta = p_itemStack.getItemMeta();
        final PersistentDataContainer container = meta.getPersistentDataContainer();

        if (container.has(m_namespacedKey, PersistentDataType.INTEGER)) {
            return container.get(m_namespacedKey, PersistentDataType.INTEGER) == m_id;
        }
        return false;
    }

    @EventHandler
    public void onInteract(final PlayerInteractEvent p_playerInteractEvent) {
        final ItemStack itemStack = p_playerInteractEvent.getItem();
        if (itemStack == null || itemStack.getType() != m_material) {
            return;
        }


        if (!isCustomItem(itemStack)) {
            return;
        }

        p_playerInteractEvent.setCancelled(true);

        if (p_playerInteractEvent.getAction() == Action.LEFT_CLICK_AIR || p_playerInteractEvent.getAction() == Action.LEFT_CLICK_BLOCK) {
            final Player player = p_playerInteractEvent.getPlayer();
            onLeftClick(player, p_playerInteractEvent);
        } else if (p_playerInteractEvent.getAction() == Action.RIGHT_CLICK_AIR || p_playerInteractEvent.getAction() == Action.RIGHT_CLICK_BLOCK) {
            final Player player = p_playerInteractEvent.getPlayer();
            onRightClick(player, p_playerInteractEvent);
        }
    }

    public abstract void onLeftClick(final Player p_player, final PlayerInteractEvent p_playerInteractEvent);
    public abstract void onRightClick(final Player p_player, final PlayerInteractEvent p_playerInteractEvent);


}
