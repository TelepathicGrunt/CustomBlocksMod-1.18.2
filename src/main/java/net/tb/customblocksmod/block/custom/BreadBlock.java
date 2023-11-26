package net.tb.customblocksmod.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.tb.customblocksmod.CustomBlocksMod;
import net.tb.customblocksmod.util.StructureMethods;
import org.jetbrains.annotations.Nullable;


import java.util.*;

public class BreadBlock extends Block {
    public BreadBlock(Properties pProperties) {
        super(pProperties);
    }

    private static final Random random = new Random();

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
        if (!pLevel.isClientSide()) {
            spawnItemInCircularShape(pLevel, pPos);
            breadSpawn(pLevel, pPos);
            hayBale(pLevel, pPos);
            pLevel.destroyBlock(pPos, false);

        }
    }

    private void spawnItemInCircularShape(Level pLevel, BlockPos pPos) {
        Timer timer = new Timer();

        List<EntityType> animals = new ArrayList<>();
        animals.add(EntityType.COW);
        animals.add(EntityType.CHICKEN);
        animals.add(EntityType.SHEEP);

        List<EntityType> animalsCopy = new ArrayList<>(animals); // Kopya oluştur

        timer.scheduleAtFixedRate(new TimerTask() {
            int i = 0;

            @Override
            public void run() {
                if (i <= 360) {
                    double angleInRadians = Math.toRadians(i);

                    int randomIndex = random.nextInt(animalsCopy.size());
                    EntityType randomLivingEntity = animalsCopy.get(randomIndex);

                    // EntityType'yi kullanarak entiteyi oluştur
                    Entity livingEntity = randomLivingEntity.create(pLevel);

                    // Konumu ayarla
                    livingEntity.setPos(
                            pPos.getX() + 0.5 + Math.cos(angleInRadians) * 6f,
                            pPos.getY() + 0.5,
                            pPos.getZ() + 0.5 + Math.sin(angleInRadians) * 6f);

                    // Entiteyi dünyaya ekle
                    pLevel.addFreshEntity(livingEntity);

                    i += 40;
                } else {
                    timer.cancel();
                    timer.purge();
                }
            }
        }, 0, 100);
    }


    private void breadSpawn(Level pLevel, BlockPos pPos) {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            int i = 0;

            @Override
            public void run() {
                if (i <= 88) {

                    ItemStack breadStack = new ItemStack(Items.BREAD);
                    ItemEntity itemEntity = new ItemEntity(pLevel, pPos.getX(), pPos.getY() + 1.5f,
                            pPos.getZ(), breadStack);
                    // Entiteyi dünyaya ekle
                    pLevel.addFreshEntity(itemEntity);

                    i += 1;
                } else {
                    timer.cancel();
                    timer.purge();
                }
            }
        }, 0, 80);
    } //spawnItemInCircularShape

    private void hayBale(Level pLevel, BlockPos pPos) {
        ServerLevel serverLevel = pLevel.getServer().getLevel(pLevel.dimension());
        ResourceLocation resourceLocation = new ResourceLocation(CustomBlocksMod.MOD_ID, "haybale");
        BlockPos offsetPos = new BlockPos(pPos.getX() + 8, pPos.getY(), pPos.getZ() + 8);

        StructureMethods.generateStructure(offsetPos, serverLevel, resourceLocation, 10, true, false,
                true, false, 123L);
    }
}