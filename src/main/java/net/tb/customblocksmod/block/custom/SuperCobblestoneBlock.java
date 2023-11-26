package net.tb.customblocksmod.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class SuperCobblestoneBlock extends Block {
    public SuperCobblestoneBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
        if(!pLevel.isClientSide()) {
            BlockPos belowPos = pPos.below(5);
            int radius = 10, height = 15;

            pLevel.destroyBlock(pPos, false);
            pLevel.setBlock(pPos.below(1), Blocks.LAVA.defaultBlockState(), 3);

            Timer timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                int i = 0;

                @Override
                public void run() {
                    if (i < radius) {

                        BlockPos a = new BlockPos(belowPos.getX() - i, belowPos.getY(), belowPos.getZ() + i);
                        BlockPos b = new BlockPos(belowPos.getX() - i, belowPos.getY(), belowPos.getZ() - i);
                        BlockPos c = new BlockPos(belowPos.getX() + i, belowPos.getY(), belowPos.getZ() - i);
                        BlockPos d = new BlockPos(belowPos.getX() + i, belowPos.getY(), belowPos.getZ() + i);

                        for(int j = 0; j < i * 2; j++) {
                            for(int k = 0; k < height; k++) {
                                BlockPos controlPos = new BlockPos(a.getX(), a.getY() + k, a.getZ() - j);
                                if(pLevel.getBlockState(controlPos).getBlock() != Blocks.AIR) {
                                    BlockState newBlockState = Math.floor(Math.random() * 2) == 0 ?
                                            Blocks.FURNACE.defaultBlockState() : Blocks.LAVA.defaultBlockState();
                                    pLevel.setBlock(controlPos, newBlockState, 3);
                                }
                            }
                        }

                        for(int j = 0; j < i * 2; j++) {
                            for(int k = 0; k < height; k++) {
                                BlockPos controlPos = new BlockPos(b.getX() + j, b.getY() + k, b.getZ());
                                if(pLevel.getBlockState(controlPos).getBlock() != Blocks.AIR) {
                                    BlockState newBlockState = Math.floor(Math.random() * 2) == 0 ?
                                            Blocks.FURNACE.defaultBlockState() : Blocks.LAVA.defaultBlockState();
                                    pLevel.setBlock(controlPos, newBlockState, 3);
                                }
                            }
                        }

                        for(int j = 0; j < i * 2; j++) {
                            for(int k = 0; k < height; k++) {
                                BlockPos controlPos = new BlockPos(c.getX(), c.getY() + k, c.getZ() + j);
                                if(pLevel.getBlockState(controlPos).getBlock() != Blocks.AIR) {
                                    BlockState newBlockState = Math.floor(Math.random() * 2) == 0 ?
                                            Blocks.FURNACE.defaultBlockState() : Blocks.LAVA.defaultBlockState();
                                    pLevel.setBlock(controlPos, newBlockState, 3);
                                }
                            }
                        }

                        for(int j = 0; j < i * 2; j++) {
                            for(int k = 0; k < height; k++) {
                                BlockPos controlPos = new BlockPos(d.getX() - j, d.getY() + k, d.getZ());
                                if(pLevel.getBlockState(controlPos).getBlock() != Blocks.AIR) {
                                    BlockState newBlockState = Math.floor(Math.random() * 2) == 0 ?
                                            Blocks.FURNACE.defaultBlockState() : Blocks.LAVA.defaultBlockState();
                                    pLevel.setBlock(controlPos, newBlockState, 3);
                                }
                            }
                        }

                        i++;
                    } else {
                        timer.cancel();
                        timer.purge();
                    }
                }
            }, 0, 1000);
        }
    }
}
