package world.ntdi.api.gui;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import world.ntdi.api.Api;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class GUI implements Listener {
    public static final ItemStack FILLER;
    private final Inventory m_inventory;

    @Getter
    private Set<Integer> m_openSlots;

    @Setter
    private Runnable m_onDestroy;

    @Setter
    private BiConsumer<InventoryClickEvent, List<Integer>> m_onClickOpenSlot;

    @Setter
    private Consumer<InventoryDragEvent> m_onDragOpenSlot;
    private final Map<Integer, Button> m_buttons;
    private boolean m_returnItems;

    @Setter
    private boolean m_destroyOnClose;
    private boolean m_destroyed;

    /**
     * Generate an GUI
     * @param p_inventory The Bukkit Inventory you want to clone
     */
    public GUI(Inventory p_inventory) {
        this.m_openSlots = new LinkedHashSet();
        this.m_onClickOpenSlot = (e, i) -> {};
        this.m_onDragOpenSlot = (e) -> {};
        this.m_buttons = new HashMap();
        this.m_returnItems = true;
        this.m_destroyOnClose = true;
        this.m_inventory = p_inventory;
        this.m_destroyed = false;
        Bukkit.getPluginManager().registerEvents(this, Api.getInstance());
    }

    /**
     * Generate an GUI
     * @param p_name The name of the GUI
     * @param p_rows The amount of rows needed for a gui (less than or equal to 6)
     */
    public GUI(String p_name, int p_rows) {
        this(Bukkit.createInventory(null, p_rows * 9, p_name));
    }

    /**
     * Add a button to the GUI
     * @param p_button Button Object
     * @param p_slot Slot that the button should go in.
     */
    public void addButton(Button p_button, int p_slot) {
        p_button.setSlot(p_slot);
        this.m_inventory.setItem(p_slot, p_button.getItemStack());
        this.m_buttons.put(p_slot, p_button);
    }

    /**
     * Add a button to the GUI
     * @param p_button Button Object
     * @param p_x The X coordinate for the button
     * @param p_y The Y coordinate for the button
     */
    public void addButton(Button p_button, int p_x, int p_y) {
        int slot = p_x + p_y * 9;
        this.addButton(p_button, slot);
    }

    /**
     * Fill the GUI with a certain ItemStack.
     * @param start Where to start the fill
     * @param end Where to end the fill
     * @param item The ItemStack to use
     */
    public void fill(int start, int end, ItemStack item) {
        for(int i = start; i < end; ++i) {
            this.m_inventory.setItem(i, item == null ? null : item.clone());
        }

    }

    /**
     * Remove a button from a GUI
     * @param button The button object to remove
     */
    public void removeButton(Button button) {
        this.m_inventory.setItem(button.getSlot(), new ItemStack(Material.AIR));
        this.m_buttons.remove(button.getSlot());
    }

    /**
     * Get all the buttons in the GUI
     * @return
     */
    public List<Button> getButtons() {
        return new ArrayList(this.m_buttons.values());
    }

    /**
     * Get the button in a given slot
     * @param slot The slot where the button would be
     * @return Button object
     */
    public Button getButton(int slot) {
        return this.m_buttons.get(slot);
    }

    /**
     * Remove a button or other item from a slot
     * @param slot The targeted slot
     */
    public void clearSlot(int slot) {
        Button button = (Button)this.m_buttons.get(slot);
        if (button != null) {
            this.removeButton(button);
        } else {
            this.m_inventory.setItem(slot, new ItemStack(Material.AIR));
        }
    }

    /**
     * Update the GUI with the newest information
     */
    public void update() {
        Iterator var1 = this.m_buttons.values().iterator();

        while(var1.hasNext()) {
            Button button = (Button)var1.next();
            this.m_inventory.setItem(button.getSlot(), button.getItemStack());
        }

    }

    /**
     * Open a Slot for players to interact with.
     * @param slot
     */
    public void openSlot(int slot) {
        this.m_openSlots.add(slot);
    }

    /**
     * Open multiple slots for players to interact with.
     * @param start Starting index
     * @param end Ending Index
     */
    public void openSlots(int start, int end) {
        for(int i = start; i < end; ++i) {
            this.m_openSlots.add(i);
        }

    }

    /**
     * Open multiple slots for players to interact with.
     * @param x1 Starting X Index
     * @param y1 Starting Y Index
     * @param x2 Ending X Index
     * @param y2 Ending Y Index
     */
    public void openSlots(int x1, int y1, int x2, int y2) {
        for(int y = y1; y < y2; ++y) {
            for(int x = x1; x < x2; ++x) {
                this.m_openSlots.add(y * 9 + x);
            }
        }

    }

    /**
     * Close a slot from player interaction.
     * @param slot The targeted slot.
     */
    public void closeSlot(int slot) {
        this.m_openSlots.remove(slot);
    }

    /**
     * Close multiple slots from player interaction.
     * @param start Starting index
     * @param end Ending index
     */
    public void closeSlots(int start, int end) {
        for(int i = start; i < end; ++i) {
            this.m_openSlots.remove(i);
        }

    }

    /**
     * Close multiple slots from player interaction.
     * @param x1 Starting X Index
     * @param y1 Starting Y Index
     * @param x2 Ending X Index
     * @param y2 Ending Y Index
     */
    public void closeSlots(int x1, int y1, int x2, int y2) {
        for(int y = y1; y < y2; ++y) {
            for(int x = x1; x < x2; ++x) {
                this.m_openSlots.remove(y * 9 + x);
            }
        }

    }

    /**
     * Open the GUI to the player
     * @param player The targeted player
     */
    public void open(Player player) {
        player.openInventory(this.m_inventory);
    }

    /**
     * Check if the GUI should return items put in open slots to the player when it's closed
     * @return True or False
     */
    public boolean returnsItems() {
        return this.m_returnItems;
    }

    /**
     * Change if the GUI should return items put in open slots of the GUI to the player when it's closed
     * @param returnItems if the GUI should return items put in open slots of the GUI to the player when it's closed
     */
    public void setReturnsItems(boolean returnItems) {
        this.m_returnItems = returnItems;
    }

    /**
     * Whether the GUI should destroy itself when it's closed
     * @return If the GUI should destroy itself when it's closed
     */
    public boolean destroysOnClose() {
        return this.m_destroyOnClose;
    }

    public void destroy(Player lastViewer) {
        if (this.m_onDestroy != null) {
            this.m_onDestroy.run();
        }

        HandlerList.unregisterAll(this);
        if (this.m_returnItems && lastViewer != null) {
            Iterator var2 = this.m_openSlots.iterator();

            while(var2.hasNext()) {
                int slot = (Integer)var2.next();
                ItemStack item = this.m_inventory.getItem(slot);
                if (item != null) {
                    lastViewer.getInventory().addItem(new ItemStack[]{item}).values().forEach((i) -> {
                        lastViewer.getWorld().dropItem(lastViewer.getLocation(), i);
                    });
                }
            }
        }
        this.m_destroyed = true;

        this.m_inventory.clear();
        this.m_buttons.clear();
    }

    /**
     * Destroys the GUI
     */
    public void destroy() {
        this.destroy((Player)null);
    }

    /**
     * Clears the GUI of all it's items
     */
    public void clear() {
        this.m_inventory.clear();
        this.m_buttons.clear();
    }

    /**
     * If the GUI has been destroyed or not.
     * @return boolean
     */
    public boolean getDestroyed() {
        return this.m_destroyed;
    }

    @EventHandler
    public void onDrag(InventoryDragEvent e) {
        List<Integer> slots = e.getRawSlots().stream().filter((s) -> this.getInventory(e.getView(), s).equals(this.m_inventory)).collect(Collectors.toList());
        if (slots.size() != 0) {
            if (!this.m_openSlots.containsAll(slots)) {
                e.setCancelled(true);
            } else {
                this.m_onDragOpenSlot.accept(e);
            }
        }
    }

    private Inventory getInventory(InventoryView view, int rawSlot) {
        return rawSlot < view.getTopInventory().getSize() ? view.getTopInventory() : view.getBottomInventory();
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (this.m_inventory.equals(e.getView().getTopInventory())) {
            if (e.getAction() == InventoryAction.COLLECT_TO_CURSOR && !e.getClickedInventory().equals(this.m_inventory)) {
                e.setCancelled(true);
            } else {
                if (!this.m_inventory.equals(e.getClickedInventory()) && e.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY) {
                    if (this.m_openSlots.size() > 0) {
                        Map<Integer, ItemStack> slots = new HashMap();
                        int amount = e.getCurrentItem().getAmount();
                        Iterator var4 = this.m_openSlots.iterator();

                        while(var4.hasNext()) {
                            int slot = (Integer)var4.next();
                            if (amount <= 0) {
                                break;
                            }

                            ItemStack item = this.m_inventory.getItem(slot);
                            int max;
                            if (item == null) {
                                max = Math.min(amount, e.getCurrentItem().getType().getMaxStackSize());
                                amount -= max;
                                ItemStack clone = e.getCurrentItem().clone();
                                clone.setAmount(max);
                                slots.put(slot, clone);
                            } else if (e.getCurrentItem().isSimilar(item)) {
                                max = item.getType().getMaxStackSize() - item.getAmount();
                                int diff = Math.min(max, e.getCurrentItem().getAmount());
                                amount -= diff;
                                ItemStack clone = item.clone();
                                clone.setAmount(clone.getAmount() + diff);
                                slots.put(slot, clone);
                            }
                        }

                        if (slots.size() == 0) {
                            return;
                        }

                        this.m_onClickOpenSlot.accept(e, new ArrayList(slots.keySet()));
                        if (e.isCancelled()) {
                            return;
                        }

                        e.setCancelled(true);
                        ItemStack item = e.getCurrentItem();
                        item.setAmount(amount);
                        e.setCurrentItem(item);
                        Inventory var10001 = this.m_inventory;
                        Objects.requireNonNull(var10001);
                        slots.forEach(var10001::setItem);
                        Bukkit.getScheduler().scheduleSyncDelayedTask(Api.getInstance(), () -> {
                            ((Player)e.getWhoClicked()).updateInventory();
                        });
                        return;
                    }

                    e.setCancelled(true);
                }

                if (e.getInventory().equals(e.getClickedInventory())) {
                    if (this.m_openSlots.contains(e.getSlot())) {
                        List<Integer> list = new ArrayList();
                        list.add(e.getSlot());
                        this.m_onClickOpenSlot.accept(e, list);
                        return;
                    }

                    e.setCancelled(true);
                    Button button = (Button)this.m_buttons.get(e.getSlot());
                    if (button != null) {
                        button.onClick(e);
                    }
                }

            }
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        if (e.getInventory().equals(this.m_inventory) && this.m_destroyOnClose && e.getViewers().size() <= 1) {
            this.destroy((Player)e.getPlayer());
        }

    }

    static {
        FILLER = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
    }
}
