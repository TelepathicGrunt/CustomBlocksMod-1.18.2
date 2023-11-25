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

public class SuperSandBlock extends Block {
    public SuperSandBlock(Properties pProperties) {
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
                    randomRun(pLevel, pPos, pPlacer);
                }
            }, 1000);
        }
    }

    private void randomRun(Level pLevel, BlockPos pPos, LivingEntity pPlacer) {
        int randomNum = (int) (Math.random() * 2);

        switch (randomNum) {
            case 0:
                pPlacer.sendMessage(new TextComponent("Desert Well"), pPlacer.getUUID());
                desertWell(pLevel, pPos);
                break;
            case 1:
                pPlacer.sendMessage(new TextComponent("Desert Temple"), pPlacer.getUUID());
                desertTemple(pLevel, pPos);
                break;
        }
    }

    private void desertWell(Level pLevel, BlockPos pPos) {
        ServerLevel serverLevel = pLevel.getServer().getLevel(pLevel.dimension());
        ResourceLocation resourceLocation = new ResourceLocation(CustomBlocksMod.MOD_ID, "desertwell");
        BlockPos offsetPos = new BlockPos(pPos.getX() + 2, pPos.getY(), pPos.getZ() + 2);

        StructureMethods.generateStructure(offsetPos, serverLevel, resourceLocation, 10, false, false,
                true, false, 123L);
    }

    private void desertTemple(Level pLevel, BlockPos pPos) {
        ServerLevel serverLevel = pLevel.getServer().getLevel(pLevel.dimension());
        ResourceLocation resourceLocation = new ResourceLocation(CustomBlocksMod.MOD_ID, "deserttemple/start_pool");
        BlockPos offsetPos = new BlockPos(pPos.getX() - 15, pPos.getY(), pPos.getZ() + 11);

        StructureMethods.generateStructure(offsetPos, serverLevel, resourceLocation, 10, true, false,
                true, false, 123L);
    }
}
