package net.tb.customblocksmod.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
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

public class StickBlock extends Block {
    public StickBlock(Properties pProperties) {
        super(pProperties);

    }

    private final Object lock = new Object(); // Kilitleme nesnesi

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
        if(!pLevel.isClientSide()) {
            spawnItemInCircularShape(pLevel, pPos);
            woodentreespawn(pLevel, pPlacer, pPos);

        }
    }

    public class WoodenItems {
        public static final List<Item> ITEMS = Arrays.asList(
                Items.WOODEN_PICKAXE,
                Items.WOODEN_AXE,
                Items.WOODEN_SHOVEL,
                Items.WOODEN_HOE
        );
    }
    private void spawnItemInCircularShape(Level pLevel, BlockPos pPos) {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            int i = 0;
            @Override
            public void run() {
                if (i <= 360) {
                    double angleInRadians = Math.toRadians(i);
                    // Listenin içinden rastgele bir item seç
                    Item randomWoodenItem = WoodenItems.ITEMS.get(pLevel.random.nextInt(WoodenItems.ITEMS.size()));
                    // Rastgele seçilen item ile bir ItemStack oluştur
                    ItemStack itemStack = new ItemStack(randomWoodenItem);
                    // ItemEntity'yi oluştur ve dünyaya ekle
                    ItemEntity itemEntity = new ItemEntity(pLevel,
                            pPos.getX() + 0.5 + Math.cos(angleInRadians) * 2.5f,
                            pPos.getY() + 0.5,
                            pPos.getZ() + 0.5 + Math.sin(angleInRadians) * 2.5f, itemStack);
                    itemEntity.lifespan = 6000;
                    pLevel.addFreshEntity(itemEntity);
                    i += 5;
                } else {
                    timer.cancel();
                    timer.purge();
                }
            }
        }, 0, 100);
    }

    //bloğu koyduğumuz yerde ağaç spawnlar
    private void woodentreespawn(Level pLevel, LivingEntity pPlacer, BlockPos pPos) {
        pLevel.destroyBlock(pPos, true);
        ServerLevel serverLevel = pLevel.getServer().getLevel(pLevel.dimension());
        ResourceLocation resourceLocation = new ResourceLocation(CustomBlocksMod.MOD_ID, "woodentree");
        BlockPos offsetPos = new BlockPos(pPos.getX() + 2, pPos.getY() + 1, pPos.getZ() + 2);
        StructureMethods.generateStructure(offsetPos, serverLevel, resourceLocation, 10, false,
                    false, true, false, 123L);
    }
}





