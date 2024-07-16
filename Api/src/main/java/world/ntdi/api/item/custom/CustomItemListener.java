package world.ntdi.api.item.custom;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class CustomItemListener implements Listener {
    @EventHandler
    public void onInteract(final PlayerInteractEvent p_playerInteractEvent) {
        final ItemStack itemStack = p_playerInteractEvent.getItem();
        if (itemStack == null) {
            return;
        }

        final CustomItem customItem = isCustomItem(itemStack);

        if (customItem == null) {
            return;
        }

        p_playerInteractEvent.setCancelled(true);

        final Player player = p_playerInteractEvent.getPlayer();
        if (p_playerInteractEvent.getAction() == Action.LEFT_CLICK_AIR || p_playerInteractEvent.getAction() == Action.LEFT_CLICK_BLOCK) {
            customItem.onLeftClick(player, p_playerInteractEvent);
        } else if (p_playerInteractEvent.getAction() == Action.RIGHT_CLICK_AIR || p_playerInteractEvent.getAction() == Action.RIGHT_CLICK_BLOCK) {
            customItem.onRightClick(player, p_playerInteractEvent);
        }
    }

    public CustomItem isCustomItem(final ItemStack p_itemStack) {
        final ItemMeta meta = p_itemStack.getItemMeta();
        final PersistentDataContainer container = meta.getPersistentDataContainer();

        if (container.has(CustomItemRegister.NAMESPACED_KEY, PersistentDataType.INTEGER)) {
            return CustomItemRegister.getCustomItem(container.get(CustomItemRegister.NAMESPACED_KEY, PersistentDataType.INTEGER));
        }
        return null;
    }
}
