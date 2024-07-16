package world.ntdi.api.item.custom;

import lombok.AllArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import world.ntdi.api.Api;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.WeakHashMap;

@AllArgsConstructor
public class CustomItemListener implements Listener {
    private final Map<UUID, Map<Integer, Long>> m_uuidItemCooldownMap = new WeakHashMap<>();

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

        final Player player = p_playerInteractEvent.getPlayer();

        p_playerInteractEvent.setCancelled(true);

        if (!m_uuidItemCooldownMap.containsKey(player.getUniqueId())) {
            m_uuidItemCooldownMap.put(player.getUniqueId(), new HashMap<>());
        }

        int id = customItem.getId();

        Map<Integer, Long> cooldownsMap = m_uuidItemCooldownMap.get(player.getUniqueId());
        if (cooldownsMap.containsKey(id)) {
            if (System.currentTimeMillis() < cooldownsMap.get(id)) {
                // Send cooldown message
                final TextComponent message = Component.text(
                        convertMillisToReadableTime(cooldownsMap.get(id) - System.currentTimeMillis()), NamedTextColor.AQUA);

                Api.getInstance().adventure().player(player).sendActionBar(message);
                return;
            } else {
                cooldownsMap.remove(id);
            }
        }

        if (p_playerInteractEvent.getAction() == Action.LEFT_CLICK_AIR || p_playerInteractEvent.getAction() == Action.LEFT_CLICK_BLOCK) {
            customItem.onLeftClick(player, p_playerInteractEvent);
        } else if (p_playerInteractEvent.getAction() == Action.RIGHT_CLICK_AIR || p_playerInteractEvent.getAction() == Action.RIGHT_CLICK_BLOCK) {
            customItem.onRightClick(player, p_playerInteractEvent);
        }

        cooldownsMap.put(id, System.currentTimeMillis() + customItem.getCooldown());
        m_uuidItemCooldownMap.put(player.getUniqueId(), cooldownsMap);
    }

    public CustomItem isCustomItem(final ItemStack p_itemStack) {
        final ItemMeta meta = p_itemStack.getItemMeta();
        final PersistentDataContainer container = meta.getPersistentDataContainer();

        if (container.has(CustomItemRegister.NAMESPACED_KEY, PersistentDataType.INTEGER)) {
            return CustomItemRegister.getCustomItem(container.get(CustomItemRegister.NAMESPACED_KEY, PersistentDataType.INTEGER));
        }
        return null;
    }

    private String convertMillisToReadableTime(long millis) {
        long totalSeconds = millis / 1000;
        long seconds = totalSeconds % 60;

        return String.format("%ds", seconds);
    }
}
