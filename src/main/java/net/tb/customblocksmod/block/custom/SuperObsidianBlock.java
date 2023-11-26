package net.tb.customblocksmod.block.custom;

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
import net.tb.customblocksmod.util.StructureMethods;
import org.jetbrains.annotations.Nullable;

import java.util.Timer;
import java.util.TimerTask;

public class SuperObsidianBlock extends Block {
    public SuperObsidianBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
        if (!pLevel.isClientSide()) {
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    pLevel.destroyBlock(pPos, false);
                    bigPortal(pLevel, pPos);
                    pPlacer.sendMessage(new TextComponent("Big Portal"), pPlacer.getUUID());
                }
            }, 1000);
        }
    }

    private void bigPortal(Level pLevel, BlockPos pPos) {
        ServerLevel serverLevel = pLevel.getServer().getLevel(pLevel.dimension());
        ResourceLocation resourceLocation = new ResourceLocation(CustomBlocksMod.MOD_ID, "bigportal");
        BlockPos offsetPos = new BlockPos(pPos.getX() - 7, pPos.getY(), pPos.getZ() + 7);

        StructureMethods.generateStructure(offsetPos, serverLevel, resourceLocation, 10, false, false,
                true, false, 123L);
    }
}
