package net.tb.customblocksmod.item;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.tb.customblocksmod.CustomBlocksMod;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, CustomBlocksMod.MOD_ID);

    public static final RegistryObject<Item> CITRINE = ITEMS.register("citrine",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.CUSTOMBLOCKS_TAB)));

    public static final RegistryObject<Item> POOP_HELMET = ITEMS.register("poop_helmet",
            () -> new ArmorItem(ArmorMaterials.LEATHER, EquipmentSlot.HEAD,
                    new Item.Properties().tab(ModCreativeModeTab.CUSTOMBLOCKS_TAB)));
    public static final RegistryObject<Item> POOP_CHESTPLATE = ITEMS.register("poop_chestplate",
            () -> new ArmorItem(ArmorMaterials.LEATHER, EquipmentSlot.CHEST,
                    new Item.Properties().tab(ModCreativeModeTab.CUSTOMBLOCKS_TAB)));
    public static final RegistryObject<Item> POOP_LEGGING = ITEMS.register("poop_leggings",
            () -> new ArmorItem(ArmorMaterials.LEATHER, EquipmentSlot.LEGS,
                    new Item.Properties().tab(ModCreativeModeTab.CUSTOMBLOCKS_TAB)));
    public static final RegistryObject<Item> POOP_BOOTS = ITEMS.register("poop_boots",
            () -> new ArmorItem(ArmorMaterials.LEATHER, EquipmentSlot.FEET,
                    new Item.Properties().tab(ModCreativeModeTab.CUSTOMBLOCKS_TAB)));



    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
