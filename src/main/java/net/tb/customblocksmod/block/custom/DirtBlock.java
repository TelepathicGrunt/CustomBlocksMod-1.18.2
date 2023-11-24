package net.tb.customblocksmod.block.custom;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.BlockGetter;
import net.minecraftforge.fml.common.Mod;
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

import java.util.List;

public class DirtBlock extends Block {
    public DirtBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
        if(!pLevel.isClientSide()) {
            poopArmor(pPos, pLevel);

            pLevel.destroyBlock(pPos, false);
        }
    }

    public void poopArmor(BlockPos pPos, Level level){

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
}
