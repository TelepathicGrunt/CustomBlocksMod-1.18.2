package net.tb.customblocksmod.item;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.tb.customblocksmod.CustomBlocksMod;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, CustomBlocksMod.MOD_ID);

    public static final RegistryObject<Item> POOP_HELMET = ITEMS.register("poop_helmet",
            () -> new ArmorItem(ArmorMaterials.LEATHER, EquipmentSlot.HEAD,
                    new Item.Properties().tab(ModCreativeModeTab.CUSTOMBLOCKS_TAB)));
    public static final RegistryObject<Item> POOP_CHESTPLATE = ITEMS.register("poop_chestplate",
            () -> new ArmorItem(ArmorMaterials.LEATHER, EquipmentSlot.CHEST,
                    new Item.Properties().tab(ModCreativeModeTab.CUSTOMBLOCKS_TAB)));
    public static final RegistryObject<Item> POOP_LEGGINGS = ITEMS.register("poop_leggings",
            () -> new ArmorItem(ArmorMaterials.LEATHER, EquipmentSlot.LEGS,
                    new Item.Properties().tab(ModCreativeModeTab.CUSTOMBLOCKS_TAB)));
    public static final RegistryObject<Item> POOP_BOOTS = ITEMS.register("poop_boots",
            () -> new ArmorItem(ArmorMaterials.LEATHER, EquipmentSlot.FEET,
                    new Item.Properties().tab(ModCreativeModeTab.CUSTOMBLOCKS_TAB)));

    public static final RegistryObject<Item> GOLD_SWORD_BLOCK_SWORD = ITEMS.register("gold_sword_block_sword",
            () -> new SwordItem(Tiers.GOLD, 3, -2.4F,
                    new Item.Properties().tab(ModCreativeModeTab.CUSTOMBLOCKS_TAB)));
    public static final RegistryObject<Item> IRON_SWORD_BLOCK_SWORD = ITEMS.register("iron_sword_block_sword",
            () -> new SwordItem(Tiers.IRON, 3, -2.4F,
                    new Item.Properties().tab(ModCreativeModeTab.CUSTOMBLOCKS_TAB)));
    public static final RegistryObject<Item> DIAMOND_SWORD_BLOCK_SWORD = ITEMS.register("diamond_sword_block_sword",
            () -> new SwordItem(Tiers.DIAMOND, 3, -2.4F,
                    new Item.Properties().tab(ModCreativeModeTab.CUSTOMBLOCKS_TAB)));
    public static final RegistryObject<Item> NETHERITE_SWORD_BLOCK_SWORD = ITEMS.register("netherite_sword_block_sword",
            () -> new SwordItem(Tiers.NETHERITE, 3, -2.4F,
                    new Item.Properties().tab(ModCreativeModeTab.CUSTOMBLOCKS_TAB)));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
