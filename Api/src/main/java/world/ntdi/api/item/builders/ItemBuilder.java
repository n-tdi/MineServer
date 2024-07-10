package world.ntdi.api.item.builders;

import lombok.NonNull;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemBuilder {
    protected ItemStack m_itemStack;
    protected int m_amount;
    protected int m_modelData;
    protected String m_name;
    protected List<String> m_lore;
    protected Map<Enchantment, Integer> m_enchantments;
    protected List<ItemFlag> m_flags;
    protected boolean m_glow;
    protected Material m_material;
    protected boolean m_unbreakable;
    protected short m_durability;

    public ItemBuilder() {
        this.m_material = Material.BEDROCK;
        this.m_lore = new ArrayList<>();
        this.m_enchantments = new HashMap<>();
        this.m_flags = new ArrayList<>();
        this.m_durability = this.m_material.getMaxDurability();
    }

    public ItemBuilder item(@NonNull final ItemStack item) {
        this.m_itemStack = item;
        this.m_material = item.getType();
        return this;
    }

    public ItemBuilder material(Material material) {
        this.m_material = material;
        return this;
    }

    public ItemBuilder amount(final int amount) {
        this.m_amount = amount;
        return this;
    }

    public ItemBuilder modelData(final int modelData) {
        this.m_modelData = modelData;
        return this;
    }

    public ItemBuilder name(@NonNull final String name) {
        this.m_name = name;
        return this;
    }

    public ItemBuilder lores(@NonNull final List<String> lore) {
        this.m_lore = lore;
        return this;
    }

    public ItemBuilder lores(@NonNull final String... lore) {
        this.m_lore = List.of(lore);
        return this;
    }

    public ItemBuilder lore(@NonNull final String... lore) {
        this.m_lore.addAll(List.of(lore));
        return this;
    }

    public ItemBuilder enchantments(@NonNull final Map<Enchantment, Integer> enchantments) {
        this.m_enchantments = enchantments;
        return this;
    }

    public ItemBuilder enchantment(@NonNull final Enchantment enchantment, final int level) {
        this.m_enchantments.put(enchantment, level);
        return this;
    }

    public ItemBuilder flags(List<ItemFlag> flags) {
        this.m_flags = flags;
        return this;
    }

    public ItemBuilder flag(ItemFlag flag) {
        this.m_flags.add(flag);
        return this;
    }

    public ItemBuilder glow(boolean glow) {
        this.m_glow = glow;
        return this;
    }

    public ItemBuilder unbreakable(boolean unbreakable) {
        this.m_unbreakable = unbreakable;
        return this;
    }

    public ItemBuilder durability(short durability) {
        this.m_durability = durability;
        return this;
    }

    public ItemStack build() {

        if (this.m_itemStack == null) {
            this.m_itemStack = new ItemStack(this.m_material);
        }
        final Damageable meta = (Damageable) m_itemStack.getItemMeta();

        if (meta == null) {
            throw new RuntimeException("ItemMeta cannot be null!");
        }

        if (this.m_amount != 0) {
            m_itemStack.setAmount(this.m_amount);
        }

        if (this.m_name != null) {
            meta.setDisplayName(this.m_name);
        }

        if (this.m_lore != null) {
            meta.setLore(this.m_lore);
        }

        if (this.m_enchantments != null) {
            this.m_enchantments.forEach((enchantment, level) -> meta.addEnchant(enchantment, level, true));
        }

        if (this.m_flags != null) {
            meta.addItemFlags(this.m_flags.toArray(new ItemFlag[0]));
        }

        if (this.m_glow) {
            meta.addEnchant(Enchantment.LUCK_OF_THE_SEA, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }

        if (this.m_unbreakable) {
            meta.setUnbreakable(true);
        }

        if (this.m_modelData != 0) {
            meta.setCustomModelData(this.m_modelData);
        }

        if (this.m_durability != this.m_material.getMaxDurability()) {
            meta.setDamage(this.m_durability);
        }

        m_itemStack.setItemMeta(meta);
        return m_itemStack;
    }

    public static ItemBuilder builder() {
        return new ItemBuilder();
    }

    public static ItemBuilder of(@NonNull final Material material) {
        return new ItemBuilder().material(material);
    }

    public static ItemBuilder of(@NonNull final Material material, final int amount) {
        return new ItemBuilder().material(material).amount(amount);
    }

    public static ItemBuilder of(@NonNull final Material material, final int amount, final int modelData) {
        return new ItemBuilder().material(material).amount(amount).modelData(modelData);
    }

    public static ItemBuilder of(@NonNull final Material material, final int amount, @NonNull final String name) {
        return new ItemBuilder().material(material).amount(amount).name(name);
    }

    public static ItemBuilder of(@NonNull final Material material, final int amount, @NonNull final String name,
                                 @NonNull final String... lore) {
        return new ItemBuilder().material(material).amount(amount).name(name).lore(lore);
    }
}
