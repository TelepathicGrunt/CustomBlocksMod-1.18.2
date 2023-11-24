package net.tb.customblocksmod.block.custom;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.enchantment.Enchantments;
import net.tb.customblocksmod.item.ModItems;
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

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class DirtBlock extends Block {
    public DirtBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
        if(!pLevel.isClientSide()) {
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
        int randomNum = (int) (Math.random() * 4);

        switch (randomNum) {
            case 0:
                pPlacer.sendMessage(new TextComponent("Dirt Sphere"), pPlacer.getUUID());
                dirtSphere(pLevel, pPlacer);
                break;
            case 1:
                pPlacer.sendMessage(new TextComponent("Dirt Desert Temple"), pPlacer.getUUID());
                dirtDesertTemple(pLevel, pPos);
                break;
            case 2:
                pPlacer.sendMessage(new TextComponent("Poop Armor"), pPlacer.getUUID());
                poopArmor(pLevel, pPos);
                break;
            case 3:
                pPlacer.sendMessage(new TextComponent("Void Hole"), pPlacer.getUUID());
                voidHole(pLevel, pPos);
                break;
        }
    }

    private void dirtSphere(Level pLevel, LivingEntity pPlacer) {
        ServerLevel serverLevel = pLevel.getServer().getLevel(pLevel.dimension());
        ResourceLocation resourceLocation = new ResourceLocation(CustomBlocksMod.MOD_ID, "dirtsphere");
        BlockPos playerPos = pPlacer.blockPosition();
        BlockPos offsetPos = new BlockPos(playerPos.getX() + 3, playerPos.getY() - 2, playerPos.getZ() + 3);

        StructureMethods.generateStructure(offsetPos, serverLevel, resourceLocation, 10, false, false,
                true, false, 123L);
    }

    private void dirtDesertTemple(Level pLevel, BlockPos pPos) {
        ServerLevel serverLevel = pLevel.getServer().getLevel(pLevel.dimension());
        ResourceLocation resourceLocation = new ResourceLocation(CustomBlocksMod.MOD_ID, "dirtdeserttemple/start_pool");
        BlockPos offsetPos = new BlockPos(pPos.getX() - 15, pPos.getY(), pPos.getZ() + 11);

        StructureMethods.generateStructure(offsetPos, serverLevel, resourceLocation, 10, true, false,
                true, false, 123L);
    }

    private void poopArmor(Level level, BlockPos pPos) {
        ItemStack[] itemStacks = {
                new ItemStack(ModItems.POOP_HELMET.get()),
                new ItemStack(ModItems.POOP_CHESTPLATE.get()),
                new ItemStack(ModItems.POOP_LEGGING.get()),
                new ItemStack(ModItems.POOP_BOOTS.get())
        };

        for(ItemStack itemStack : itemStacks) {
            itemStack.enchant(Enchantments.UNBREAKING, 3);
            itemStack.enchant(Enchantments.BINDING_CURSE, 1);
            spawnItemAtLocation(level, pPos, itemStack);

        }
    }

    private void spawnItemAtLocation(Level level, BlockPos pPos, ItemStack itemStack) {

        ItemEntity itemEntity = new ItemEntity(level, pPos.getX(), pPos.getY(), pPos.getZ(), itemStack);
        level.addFreshEntity(itemEntity);
    }

    private void voidHole(Level level, BlockPos pPos) {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            int i = 0;

            @Override
            public void run() {
                if (i <= pPos.getY() + 65) {
                    level.destroyBlock(pPos.below(i), true);
                    i++;
                } else {
                    timer.cancel();
                    timer.purge();
                }
            }
        }, 0, 100);
    }

    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, Random pRandom) {
        float chance = 0.35f;

        if (chance < pRandom.nextFloat()) {
            double offsetX = (pRandom.nextDouble() - 0.5) * 0.6;
            double offsetY = pRandom.nextDouble() * 0.6 - 0.3;
            double offsetZ = (pRandom.nextDouble() - 0.5) * 0.6;

            pLevel.addParticle(ParticleTypes.DRAGON_BREATH,
                    pPos.getX() + 0.5 + offsetX,
                    pPos.getY() + 0.5 + offsetY,
                    pPos.getZ() + 0.5 + offsetZ,
                    (pRandom.nextDouble() - 0.5) * 0.05, // X hızı
                    (pRandom.nextDouble() - 0.5) * 0.05, // Y hızı
                    (pRandom.nextDouble() - 0.5) * 0.05  // Z hızı
                );
        }
    }
}

