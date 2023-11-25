package net.tb.customblocksmod.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.Timer;
import java.util.TimerTask;

public class BlazeRodBlock extends Block {
    public BlazeRodBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
        if(!pLevel.isClientSide()) {
            spawnItemInCircularShape(pLevel, pPos);
            pLevel.destroyBlock(pPos, false);

        }
    }

    private void spawnItemInCircularShape(Level pLevel, BlockPos pPos) {
        for (int i = 0; i <= 360; i += 30) {
            double angleInRadians = Math.toRadians(i);
            Blaze blaze = new Blaze(EntityType.BLAZE, pLevel);
            blaze.moveTo(pPos.getX() + 0.5 + Math.cos(angleInRadians) * 5f,
                    pPos.getY() + 0.5,
                    pPos.getZ() + 0.5 + Math.sin(angleInRadians) * 5f);

            pLevel.addFreshEntity(blaze);

            // Timer kullanarak belirli bir süre sonra Blaze'i kaldır
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    blaze.kill();
                    timer.cancel();
                }
            }, 4000); // 4000 milisaniye (4 saniye) sonra task çalışacak
        }
    }
}
