package net.tb.customblocksmod.block;

import net.tb.customblocksmod.CustomBlocksMod;
import net.tb.customblocksmod.block.custom.*;
import net.tb.customblocksmod.item.ModCreativeModeTab;
import net.tb.customblocksmod.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, CustomBlocksMod.MOD_ID);

    public static final RegistryObject<Block> SUPER_DIRT_BLOCK = registerBlock("super_dirt_block",
            () -> new SuperDirtBlock(BlockBehaviour.Properties.of(Material.DIRT).strength(9f).requiresCorrectToolForDrops()),
            ModCreativeModeTab.CUSTOMBLOCKS_TAB);

    public static final RegistryObject<Block> BREAD_BLOCK = registerBlock("bread_block",
            () -> new BreadBlock(BlockBehaviour.Properties.of(Material.METAL).strength(9f).requiresCorrectToolForDrops()),
            ModCreativeModeTab.CUSTOMBLOCKS_TAB);

    public static final RegistryObject<Block> SUPER_SAND_BLOCK = registerBlock("super_sand_block",
            () -> new SuperSandBlock(BlockBehaviour.Properties.of(Material.SAND).strength(9f).requiresCorrectToolForDrops()),
            ModCreativeModeTab.CUSTOMBLOCKS_TAB);

    public static final RegistryObject<Block> STICK_BLOCK = registerBlock("stick_block",
            () -> new StickBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(9f).requiresCorrectToolForDrops()),
            ModCreativeModeTab.CUSTOMBLOCKS_TAB);

    public static final RegistryObject<Block> SUPER_COBBLESTONE_BLOCK = registerBlock("super_cobblestone_block",
            () -> new SuperCobblestoneBlock(BlockBehaviour.Properties.of(Material.STONE).strength(9f).requiresCorrectToolForDrops()),
            ModCreativeModeTab.CUSTOMBLOCKS_TAB);

    public static final RegistryObject<Block> SUPER_STONE_BLOCK = registerBlock("super_stone_block",
            () -> new SuperStoneBlock(BlockBehaviour.Properties.of(Material.STONE).strength(9f).requiresCorrectToolForDrops()),
            ModCreativeModeTab.CUSTOMBLOCKS_TAB);

    public static final RegistryObject<Block> WATER_BUCKET_BLOCK = registerBlock("water_bucket_block",
            () -> new WaterBucketBlock(BlockBehaviour.Properties.of(Material.METAL).strength(9f).requiresCorrectToolForDrops()),
            ModCreativeModeTab.CUSTOMBLOCKS_TAB);

    public static final RegistryObject<Block> LAVA_BUCKET_BLOCK = registerBlock("lava_bucket_block",
            () -> new LavaBucketBlock(BlockBehaviour.Properties.of(Material.METAL).strength(9f).requiresCorrectToolForDrops()),
            ModCreativeModeTab.CUSTOMBLOCKS_TAB);

    public static final RegistryObject<Block> SUPER_IRON_BLOCK = registerBlock("super_iron_block",
            () -> new SuperIronBlock(BlockBehaviour.Properties.of(Material.DIRT).strength(9f).requiresCorrectToolForDrops()),
            ModCreativeModeTab.CUSTOMBLOCKS_TAB);

    public static final RegistryObject<Block> BOW_BLOCK = registerBlock("bow_block",
            () -> new BowBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(9f).requiresCorrectToolForDrops()),
            ModCreativeModeTab.CUSTOMBLOCKS_TAB);

    public static final RegistryObject<Block> SUPER_DIAMOND_BLOCK = registerBlock("super_diamond_block",
            () -> new SuperDiamondBlock(BlockBehaviour.Properties.of(Material.DIRT).strength(9f).requiresCorrectToolForDrops()),
            ModCreativeModeTab.CUSTOMBLOCKS_TAB);



    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, CreativeModeTab tab) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, tab);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block, CreativeModeTab tab) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(tab)));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
