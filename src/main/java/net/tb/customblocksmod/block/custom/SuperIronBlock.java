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

public class SuperIronBlock extends Block {
    public SuperIronBlock(Properties pProperties) {
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
                    ironMonument(pLevel, pPos);
                    pPlacer.sendMessage(new TextComponent("Iron Monument"), pPlacer.getUUID());
                }
            }, 1000);
        }
    }

    private void ironMonument(Level pLevel, BlockPos pPos) {
        ServerLevel serverLevel = pLevel.getServer().getLevel(pLevel.dimension());
        ResourceLocation resourceLocation = new ResourceLocation(CustomBlocksMod.MOD_ID, "ironmonument/start_pool");
        BlockPos offsetPos = new BlockPos(pPos.getX(), pPos.getY(), pPos.getZ());

        StructureMethods.generateStructure(offsetPos, serverLevel, resourceLocation, 10, true, false,
                true, false, 123L);
    }
}
