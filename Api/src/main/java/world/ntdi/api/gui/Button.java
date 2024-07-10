package world.ntdi.api.gui;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Button Class for creating GUI buttons
 */
@Setter
@Getter
public abstract class Button {

    protected ItemStack m_itemStack;
    private int m_slot;

    /**
     * Creates a static button with no functionality.
     * @param p_itemStack The ItemStack you want to use.
     * @return A static button.
     */
    public static Button createBasic(ItemStack p_itemStack) {
        return new Button(p_itemStack) {
            public void onClick(InventoryClickEvent e) {
                // Do nothing
            }
        };
    }

    /**
     * Create a functional button.
     * @param p_itemStack The ItemStack you want to use.
     * @param p_inventoryClickEventConsumer What should happen when the button is clicked on.
     * @return A functional button.
     */
    public static Button create(ItemStack p_itemStack, final Consumer<InventoryClickEvent> p_inventoryClickEventConsumer) {
        return new Button(p_itemStack) {
            public void onClick(InventoryClickEvent e) {
                p_inventoryClickEventConsumer.accept(e);
            }
        };
    }

    /**
     * Create a functional button.
     * @param p_itemStack The ItemStack you want to use.
     * @param p_inventoryClickEventButtonBiConsumer What should happen when the button is clicked on. Gives you both the ClickEvent and the Button that was clicked.
     * @return A functional button.
     */
    public static Button create(ItemStack p_itemStack, final BiConsumer<InventoryClickEvent, Button> p_inventoryClickEventButtonBiConsumer) {
        return new Button(p_itemStack) {
            public void onClick(InventoryClickEvent e) {
                p_inventoryClickEventButtonBiConsumer.accept(e, this);
            }
        };
    }

    public Button(ItemStack p_itemStack) {
        this.m_itemStack = p_itemStack;
    }

    /**
     * When the button is clicked
     * @param var1 The inventory click event
     */
    public abstract void onClick(InventoryClickEvent var1);
}