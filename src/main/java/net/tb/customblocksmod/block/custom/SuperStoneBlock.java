package net.tb.customblocksmod.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.Tags;
import org.jetbrains.annotations.Nullable;

import java.util.Timer;
import java.util.TimerTask;

public class SuperStoneBlock extends Block {
    public SuperStoneBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
        if(!pLevel.isClientSide()) {
            int radius = 10;
            BlockState newBlockState = Blocks.AIR.defaultBlockState();

            Timer timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                int i = 0;

                @Override
                public void run() {
                    if (i < radius) {

                        for(int k = 0; k < i + 1; k++) {
                            BlockPos controlPos = new BlockPos(pPos.getX(),  pPos.getY() - (k + 1), pPos.getZ());
                            if(!Registry.BLOCK.getHolderOrThrow(Registry.BLOCK.getResourceKey(pLevel.getBlockState(controlPos).getBlock()).get()).is(Tags.Blocks.ORES)) {
                                pLevel.setBlock(controlPos, newBlockState, 3);
                            }
                            controlPos = new BlockPos(pPos.getX(),  pPos.getY() + (k + 1), pPos.getZ());
                            if(!Registry.BLOCK.getHolderOrThrow(Registry.BLOCK.getResourceKey(pLevel.getBlockState(controlPos).getBlock()).get()).is(Tags.Blocks.ORES)) {
                                pLevel.setBlock(controlPos, newBlockState, 3);
                            }
                        }

                        for(int m = 0; m < i + 1 ; m++) {
                            BlockPos belowPos = pPos.below(i + 1);
                            BlockPos a = new BlockPos(belowPos.getX() - (m + 1), belowPos.getY(), belowPos.getZ() + (m + 1));
                            BlockPos b = new BlockPos(belowPos.getX() - (m + 1), belowPos.getY(), belowPos.getZ() - (m + 1));
                            BlockPos c = new BlockPos(belowPos.getX() + (m + 1), belowPos.getY(), belowPos.getZ() - (m + 1));
                            BlockPos d = new BlockPos(belowPos.getX() + (m + 1), belowPos.getY(), belowPos.getZ() + (m + 1));

                            for(int j = 0; j < 2*m + 3; j++) {
                                for(int k = 0; k < 2*i + 3; k++) {
                                    BlockPos controlPos = new BlockPos(a.getX(), a.getY() + k, a.getZ() - j);
                                    if(!Registry.BLOCK.getHolderOrThrow(Registry.BLOCK.getResourceKey(pLevel.getBlockState(controlPos).getBlock()).get()).is(Tags.Blocks.ORES)) {
                                        pLevel.setBlock(controlPos, newBlockState, 3);
                                    }
                                }
                            }

                            for(int j = 0; j < 2*m + 3; j++) {
                                for(int k = 0; k < 2*i + 3; k++) {
                                    BlockPos controlPos = new BlockPos(b.getX() + j, b.getY() + k, b.getZ());
                                    if(!Registry.BLOCK.getHolderOrThrow(Registry.BLOCK.getResourceKey(pLevel.getBlockState(controlPos).getBlock()).get()).is(Tags.Blocks.ORES)) {
                                        pLevel.setBlock(controlPos, newBlockState, 3);
                                    }
                                }
                            }

                            for(int j = 0; j < 2*m + 3; j++) {
                                for(int k = 0; k < 2*i + 3; k++) {
                                    BlockPos controlPos = new BlockPos(c.getX(), c.getY() + k, c.getZ() + j);
                                    if(!Registry.BLOCK.getHolderOrThrow(Registry.BLOCK.getResourceKey(pLevel.getBlockState(controlPos).getBlock()).get()).is(Tags.Blocks.ORES)) {
                                        pLevel.setBlock(controlPos, newBlockState, 3);
                                    }
                                }
                            }

                            for(int j = 0; j < 2*m + 3; j++) {
                                for(int k = 0; k < 2*i + 3; k++) {
                                    BlockPos controlPos = new BlockPos(d.getX() - j, d.getY() + k, d.getZ());
                                    if(!Registry.BLOCK.getHolderOrThrow(Registry.BLOCK.getResourceKey(pLevel.getBlockState(controlPos).getBlock()).get()).is(Tags.Blocks.ORES)) {
                                        pLevel.setBlock(controlPos, newBlockState, 3);
                                    }
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
