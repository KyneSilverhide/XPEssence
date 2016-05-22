package kyne.xpessence.tileentities.base;

import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TileEntityContentConfig {

    private final Map<Integer, Integer> fields = new HashMap<Integer, Integer>();
    private final Map<Integer, ItemStack> slots = new HashMap<Integer, ItemStack>();
    private final Map<Integer, String> fieldNames = new HashMap<Integer, String>();
    private final Map<Integer, Slot> slotContainers = new HashMap<Integer, Slot>();

    public int getFieldWithIndex(final int index) {
        return fields.containsKey(index) ? fields.get(index) : 0;
    }

    public ItemStack getStackAtIndex(final int index) {
        return slots.get(index);
    }

    public void setFieldWithIndex(final int index, final int value) {
        fields.put(index, value);
    }

    public void setItemStackAtIndex(final int index, final ItemStack stack) {
        slots.put(index, stack);
    }

    public void increaseFieldAtIndex(final int index) {
        final int field = getFieldWithIndex(index);
        fields.put(index, field + 1);
    }
    public void decreaseFieldAtIndex(final int index) {
        final int field = getFieldWithIndex(index);
        fields.put(index, field - 1);
    }

    public List<Integer> getSlotIndexes() {
        return new ArrayList<Integer>(slots.keySet());
    }

    public List<Integer> getFieldIndexes() {
        return new ArrayList<Integer>(fields.keySet());
    }

    public int getInventorySize() {
        return slots.size();
    }

    protected void registerSlot(final int index, final Slot slot) {
        if(!slots.containsKey(index)) {
            slots.put(index, null);
        }
        if(slotContainers.containsKey(index)) {
            slotContainers.put(index, slot);
        }
    }

    public Slot getSlotDefinition(final int index) {
        return slotContainers.get(index);
    }

    protected void registerField(final int index, final String name) {
        if(!fields.containsKey(index)) {
            fields.put(index, 0);
        }
        fieldNames.put(index, name);
    }

    public String getFieldName(final int index) {
        return fieldNames.get(index);
    }
}
