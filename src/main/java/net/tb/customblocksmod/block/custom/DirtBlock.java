package net.tb.customblocksmod.block.custom;

import net.tb.customblocksmod.util.StructureMethods;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.tb.customblocksmod.CustomBlocksMod;
import org.jetbrains.annotations.Nullable;

public class DirtBlock extends Block {
    public DirtBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
        if(!pLevel.isClientSide()) {
            pPlacer.sendMessage(new TextComponent("Dirt Block başarıyla yerleştirildi."), pPlacer.getUUID());

            ServerLevel serverLevel = pLevel.getServer().getLevel(pLevel.dimension());
            ResourceLocation resourceLocation = new ResourceLocation(CustomBlocksMod.MOD_ID, "woodentree");
            BlockPos offsetPos = new BlockPos(pPos.getX() + 2, pPos.getY() + 1, pPos.getZ() + 2);

            StructureMethods.generateStructure(offsetPos, serverLevel, resourceLocation, 10, false, false, true,
                    false, 123L);
        }
    }
}
