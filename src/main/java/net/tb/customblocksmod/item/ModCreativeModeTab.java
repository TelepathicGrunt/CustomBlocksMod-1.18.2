package net.tb.customblocksmod.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.tb.customblocksmod.block.ModBlocks;

public class ModCreativeModeTab {
    public static final CreativeModeTab CUSTOMBLOCKS_TAB = new CreativeModeTab("customblockstab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModBlocks.SUPER_DIRT_BLOCK.get());
        }
    };
}
