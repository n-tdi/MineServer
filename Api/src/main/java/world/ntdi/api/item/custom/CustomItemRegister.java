package world.ntdi.api.item.custom;

import org.bukkit.NamespacedKey;

import java.util.HashMap;
import java.util.Map;

public final class CustomItemRegister {
    private static final Map<Integer, CustomItem> m_customItemMap = new HashMap<>();
    public final static NamespacedKey NAMESPACED_KEY = new NamespacedKey("tazkaboom", "custom-item");

    public static CustomItem getCustomItem(int p_i) {
        return m_customItemMap.get(p_i);
    }

    public static void registerCustomItem(CustomItem p_customItem) {
        m_customItemMap.put(p_customItem.getId(), p_customItem);
    }
}
