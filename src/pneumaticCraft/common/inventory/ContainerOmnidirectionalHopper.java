package pneumaticCraft.common.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import pneumaticCraft.common.item.Itemss;
import pneumaticCraft.common.tileentity.TileEntityOmnidirectionalHopper;

public class ContainerOmnidirectionalHopper extends ContainerPneumaticBase<TileEntityOmnidirectionalHopper>{

    public ContainerOmnidirectionalHopper(InventoryPlayer inventoryPlayer, TileEntityOmnidirectionalHopper te){
        super(te);

        // Add the hopper slots.
        for(int i = 0; i < 5; i++)
            addSlotToContainer(new Slot(te, i, 68 + i * 18, 36));

        // add the upgrade slots
        addSlotToContainer(new SlotUpgrade(te, 5, 23, 29));
        addSlotToContainer(new SlotUpgrade(te, 6, 41, 29));
        addSlotToContainer(new SlotUpgrade(te, 7, 23, 47));
        addSlotToContainer(new SlotUpgrade(te, 8, 41, 47));

        // Add the player's inventory slots to the container
        for(int inventoryRowIndex = 0; inventoryRowIndex < 3; ++inventoryRowIndex) {
            for(int inventoryColumnIndex = 0; inventoryColumnIndex < 9; ++inventoryColumnIndex) {
                addSlotToContainer(new Slot(inventoryPlayer, inventoryColumnIndex + inventoryRowIndex * 9 + 9, 8 + inventoryColumnIndex * 18, 84 + inventoryRowIndex * 18));
            }
        }

        // Add the player's action bar slots to the container
        for(int actionBarSlotIndex = 0; actionBarSlotIndex < 9; ++actionBarSlotIndex) {
            addSlotToContainer(new Slot(inventoryPlayer, actionBarSlotIndex, 8 + actionBarSlotIndex * 18, 142));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer player){
        return te.isUseableByPlayer(player);
    }

    /**
     * @param itemStack
     *            ItemStack to merge into inventory
     * @param start
     *            minimum slot to attempt fill
     * @param end
     *            maximum slot to attempt fill
     * @param backwards
     *            go backwards
     * @return true if stacks merged successfully public boolean
     *         mergeItemStack(itemStack, start, end, backwards)
     */

    @Override
    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2){

        ItemStack var3 = null;
        Slot var4 = (Slot)inventorySlots.get(par2);

        if(var4 != null && var4.getHasStack()) {
            ItemStack var5 = var4.getStack();
            var3 = var5.copy();

            if(par2 < 9) {
                if(!mergeItemStack(var5, 9, 45, false)) return null;

                var4.onSlotChange(var5, var3);
            } else {

                if(var5.getItem() == Itemss.machineUpgrade) {
                    if(!mergeItemStack(var5, 5, 9, false)) return null;
                } else if(!mergeItemStack(var5, 0, 5, false)) return null;
                var4.onSlotChange(var5, var3);
            }

            if(var5.stackSize == 0) {
                var4.putStack((ItemStack)null);
            } else {
                var4.onSlotChanged();
            }

            if(var5.stackSize == var3.stackSize) return null;

            var4.onPickupFromSlot(par1EntityPlayer, var5);
        }

        return var3;
    }
}
