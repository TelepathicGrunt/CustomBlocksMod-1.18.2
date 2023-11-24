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

            //dirtSphere(pLevel, pPlacer);
            //dirtDesertTemple(pLevel, pPos);
        }
    }

    private void dirtDesertTemple(Level pLevel, BlockPos pPos) {
        ServerLevel serverLevel = pLevel.getServer().getLevel(pLevel.dimension());
        ResourceLocation resourceLocation = new ResourceLocation(CustomBlocksMod.MOD_ID, "dirtdeserttemple/start_pool");
        BlockPos offsetPos = new BlockPos(pPos.getX() - 15, pPos.getY(), pPos.getZ() + 11);

        StructureMethods.generateStructure(offsetPos, serverLevel, resourceLocation, 10, true, false,
                true, false, 123L);
    }

    private void dirtSphere(Level pLevel, LivingEntity pPlacer) {
        ServerLevel serverLevel = pLevel.getServer().getLevel(pLevel.dimension());
        ResourceLocation resourceLocation = new ResourceLocation(CustomBlocksMod.MOD_ID, "dirtsphere");
        BlockPos playerPos = pPlacer.blockPosition();
        BlockPos offsetPos = new BlockPos(playerPos.getX() + 3, playerPos.getY() - 2, playerPos.getZ() + 3);

        StructureMethods.generateStructure(offsetPos, serverLevel, resourceLocation, 10, false, false,
                true, false, 123L);
    }
}
