package net.tb.customblocksmod.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.tb.customblocksmod.CustomBlocksMod;
import net.tb.customblocksmod.util.StructureMethods;
import org.jetbrains.annotations.Nullable;

public class AppleBlock extends Block {
    public AppleBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
        if (!pLevel.isClientSide()) {
            appletreespawn(pLevel, pPos);
            spawnItemInCircularShape(pLevel, pPlacer);

        }
    }

    private void appletreespawn(Level pLevel, BlockPos pPos) {
        pLevel.destroyBlock(pPos, false);
        ServerLevel serverLevel = pLevel.getServer().getLevel(pLevel.dimension());
        ResourceLocation resourceLocation = new ResourceLocation(CustomBlocksMod.MOD_ID, "appletree");
        BlockPos offsetPos = new BlockPos(pPos.getX() + 2, pPos.getY(), pPos.getZ() + 2);
        StructureMethods.generateStructure(offsetPos, serverLevel, resourceLocation, 10, false,
                false, true, false, 123L);
    }

    private void spawnItemInCircularShape(Level pLevel, @Nullable LivingEntity pPlacer) {
        if (pPlacer != null) {
            int circleRadius = 5; // Dairenin yarıçapı, isteğinize göre ayarlayabilirsiniz

            for (int i = 0; i < 360; i += 36) {
                double angleInRadians = Math.toRadians(i);

                ItemStack appleStack = new ItemStack(Items.APPLE); // Elma öğesini oluştur

                double xOffset = circleRadius * Math.cos(angleInRadians);
                double zOffset = circleRadius * Math.sin(angleInRadians);

                ItemEntity appleEntity = new ItemEntity(pLevel,
                        pPlacer.getX() + 0.5 + xOffset,
                        pPlacer.getY() + 100, //
                        pPlacer.getZ() + 0.5 + zOffset, appleStack);

                appleEntity.setDeltaMovement(0, -0.2, 0);
                pLevel.addFreshEntity(appleEntity);
            }
        }
    }
}
