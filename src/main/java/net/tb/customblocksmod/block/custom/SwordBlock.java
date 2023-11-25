package net.tb.customblocksmod.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.RegistryObject;
import net.tb.customblocksmod.item.ModItems;
import org.jetbrains.annotations.Nullable;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.Enchantment;

import java.util.*;
import java.util.stream.Collectors;



public class SwordBlock extends Block {
    public SwordBlock(Properties pProperties) {
        super(pProperties);
    }
    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
        if(!pLevel.isClientSide()) {
            spawnItemInCircularShape(pLevel, pPos);
            pLevel.destroyBlock(pPos, false);
        } //if
    } //setplaced

    private static final Random random = new Random();
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, Random pRandom) {
        float chance = 0.35f;

        // Daha fazla particle spawnlamak için şansı yükselt
        if (chance < pRandom.nextFloat()) {
            for (int i = 0; i < 5; i++) { // Örneğin, 5 kez çağırarak particle sayısını artırabilirsiniz
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

    public static void addRandomEnchantments(ItemStack itemStack) {
        // Kullanılabilir tüm büyüler / tüm nasıl çekiliyor bulunca güncellenecek
        Enchantment[] enchantments = new Enchantment[] {
                Enchantments.BANE_OF_ARTHROPODS,
                Enchantments.FIRE_ASPECT,
                Enchantments.SHARPNESS,
                Enchantments.KNOCKBACK,
                Enchantments.MENDING,
                Enchantments.SMITE,
                Enchantments.SWEEPING_EDGE,
                Enchantments.UNBREAKING,
                Enchantments.MOB_LOOTING
        };

        // Zaten eklenen büyüleri takip etmek için bir küme oluştur
        Set<Enchantment> addedEnchantments = new HashSet<>();

        // Kılıca rastgele 5 farklı büyü eklemek için döngü
        for (int i = 0; i < 5; i++) {
            // Random büyü seç
            Enchantment enchantment = getRandomEnchantment(enchantments, addedEnchantments);

            if (enchantment != null) {
                // Rastgele level seç
                int level = getRandomEnchantmentLevel(enchantment);

                // Seçilenleri kılıca ekle
                itemStack.enchant(enchantment, level);

                // Eklenen büyüleri takip etmek için kümeye ekle
                addedEnchantments.add(enchantment);
            }
        }
    }

    private static Enchantment getRandomEnchantment(Enchantment[] enchantments, Set<Enchantment> addedEnchantments) {
        List<Enchantment> availableEnchantments = Arrays.stream(enchantments)
                .filter(enchantment -> !addedEnchantments.contains(enchantment))
                .collect(Collectors.toList());

        // Rastgele bir büyü seç
        return availableEnchantments.get(random.nextInt(availableEnchantments.size()));
    }

    private static int getRandomEnchantmentLevel(Enchantment enchantment) {
        // Enchantment.getMaxLevel() ile belirtilen en yüksek seviye
        int maxLevel = enchantment.getMaxLevel();

        // Rastgele bir seviye seç (1 ile belirtilen en yüksek seviye arasında)
        return random.nextInt(maxLevel) + 1;
    }


    private void spawnItemInCircularShape(Level pLevel, BlockPos pPos) {
        Timer timer = new Timer();

        List<RegistryObject<Item>> swords = new ArrayList<>();
        swords.add(ModItems.GOLD_SWORD_BLOCK_SWORD);
        swords.add(ModItems.IRON_SWORD_BLOCK_SWORD);
        swords.add(ModItems.DIAMOND_SWORD_BLOCK_SWORD);
        swords.add(ModItems.NETHERITE_SWORD_BLOCK_SWORD);
        timer.scheduleAtFixedRate(new TimerTask() {
            int i = 0;
            @Override
            public void run() {
                if (i <= 360) {
                    double angleInRadians = Math.toRadians(i);
                    // Listenin içinden rastgele bir item seç
                    int randomIndex = random.nextInt(swords.size());
                    RegistryObject<Item> randomSwordRegistryObject = swords.get(randomIndex);

                    // RegistryObject'ten gerçek Item nesnesini al
                    Item randomSwordItem = randomSwordRegistryObject.get();

                    // Rastgele seçilen item ile bir ItemStack oluştur
                    ItemStack itemStack = ItemStack.EMPTY; // Varsayılan olarak boş bir ItemStack oluştur
                    itemStack = new ItemStack(randomSwordItem);

                    // Rastgele büyüler ekle
                    addRandomEnchantments(itemStack);

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
            } //run
        }, 0, 100);
    } //spawnItemInCircularShape
} //SwordBlock






