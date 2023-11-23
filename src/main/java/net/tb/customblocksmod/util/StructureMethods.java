package net.tb.customblocksmod.util;

import net.minecraft.commands.CommandRuntimeException;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.ProcessorLists;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.protocol.game.ClientboundLevelChunkWithLightPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.RandomSupport;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.XoroshiroRandomSource;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.PoolElementStructurePiece;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGenerator;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGeneratorSupplier;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
import net.minecraft.world.level.levelgen.structure.pools.JigsawPlacement;
import net.minecraft.world.level.levelgen.structure.pools.SinglePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.tb.customblocksmod.CustomBlocksMod;

import java.util.List;
import java.util.Optional;

public class StructureMethods {
    public static void generateStructure(BlockPos blockPos, ServerLevel serverLevel, ResourceLocation structureStartPoolRL, int depth, boolean heightmapSnap,
                                         boolean legacyBoundingBoxRule, boolean disableProcessors, boolean sendChunkLightingPacket, Long randomSeed) {
        ServerLevel level = serverLevel;
        BlockPos centerPos = blockPos;
        if(heightmapSnap) centerPos = centerPos.below(centerPos.getY()); //not a typo. Needed so heightmap is not offset by player height.

        StructureTemplatePool templatePool = level.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY).get(structureStartPoolRL);

        if(templatePool == null || templatePool.size() == 0) {
            String errorMsg = structureStartPoolRL + " template pool does not exist or is empty";
            CustomBlocksMod.LOGGER.error(errorMsg);
            throw new CommandRuntimeException(new TextComponent(errorMsg));
        }

        JigsawConfiguration newConfig = new JigsawConfiguration(
                Holder.direct(templatePool),
                depth
        );

        // Create a new context with the new config that has our json pool. We will pass this into JigsawPlacement.addPieces
        PieceGeneratorSupplier.Context<JigsawConfiguration> newContext = new PieceGeneratorSupplier.Context<>(
                level.getChunkSource().getGenerator(),
                level.getChunkSource().getGenerator().getBiomeSource(),
                randomSeed == null ? level.getSeed() : randomSeed,
                randomSeed == null ? new ChunkPos(centerPos) : new ChunkPos(0, 0),
                newConfig,
                level,
                (b) -> true,
                level.getStructureManager(),
                level.registryAccess()
        );

        Optional<PieceGenerator<JigsawConfiguration>> pieceGenerator = JigsawPlacement.addPieces(
                newContext,
                PoolElementStructurePiece::new,
                centerPos,
                legacyBoundingBoxRule,
                heightmapSnap);

        if(pieceGenerator.isPresent()) {
            StructurePiecesBuilder structurepiecesbuilder = new StructurePiecesBuilder();
            pieceGenerator.get().generatePieces(
                    structurepiecesbuilder,
                    new PieceGenerator.Context<>(
                            newContext.config(),
                            newContext.chunkGenerator(),
                            newContext.structureManager(),
                            newContext.chunkPos(),
                            newContext.heightAccessor(),
                            new WorldgenRandom(new LegacyRandomSource(0L)),
                            newContext.seed()));

            WorldgenRandom worldgenrandom;
            if(randomSeed == null) {
                worldgenrandom = new WorldgenRandom(new XoroshiroRandomSource(RandomSupport.seedUniquifier()));
                long i = worldgenrandom.setDecorationSeed(newContext.seed(), centerPos.getX(), centerPos.getZ());
                worldgenrandom.setFeatureSeed(i, 0, 0);
            }
            else {
                worldgenrandom = new WorldgenRandom(new LegacyRandomSource(randomSeed));
            }

            BlockPos finalCenterPos = centerPos;
            List<StructurePiece> structurePieceList = structurepiecesbuilder.build().pieces();

            structurePieceList.forEach(piece -> {
                if(disableProcessors) {
                    if(piece instanceof PoolElementStructurePiece poolElementStructurePiece) {
                        if(poolElementStructurePiece.getElement() instanceof SinglePoolElement singlePoolElement) {
                            Holder<StructureProcessorList> oldProcessorList = singlePoolElement.processors;
                            singlePoolElement.processors = ProcessorLists.EMPTY;
                            generatePiece(level, newContext, worldgenrandom, finalCenterPos, piece);
                            singlePoolElement.processors = oldProcessorList; // Set the processors back or else our change is permanent.
                        }
                    }
                }
                else {
                    generatePiece(level, newContext, worldgenrandom, finalCenterPos, piece);
                }
            });

            if(!structurePieceList.isEmpty()) {
                if(sendChunkLightingPacket) {
                    refreshChunksOnClients(level);
                }
            }
            else {
                String errorMsg = structureStartPoolRL + " Template Pool spawned no pieces.";
                CustomBlocksMod.LOGGER.error(errorMsg);
                throw new CommandRuntimeException(new TextComponent(errorMsg));
            }
        }
        else {
            String errorMsg = structureStartPoolRL + " Template Pool spawned no pieces.";
            CustomBlocksMod.LOGGER.error(errorMsg);
            throw new CommandRuntimeException(new TextComponent(errorMsg));
        }
    }

    private static void generatePiece(ServerLevel level, PieceGeneratorSupplier.Context<JigsawConfiguration> newContext, WorldgenRandom worldgenrandom,
                                      BlockPos finalCenterPos, StructurePiece piece) {
        piece.postProcess(
                level,
                level.structureFeatureManager(),
                newContext.chunkGenerator(),
                worldgenrandom,
                BoundingBox.infinite(),
                newContext.chunkPos(),
                finalCenterPos
        );
    }

    public static void refreshChunksOnClients(ServerLevel level) {
        int viewDistance = level.getChunkSource().chunkMap.viewDistance;
        level.players().forEach(player -> {
            for(int x = -viewDistance; x <= viewDistance; x++) {
                for(int z = -viewDistance; z <= viewDistance; z++) {
                    if(x + z < viewDistance) {
                        ChunkAccess chunkAccess = level.getChunk(new ChunkPos(player.chunkPosition().x + x, player.chunkPosition().z + z).getWorldPosition());
                        if(chunkAccess instanceof LevelChunk levelChunk) {
                            ClientboundLevelChunkWithLightPacket lightPacket = new ClientboundLevelChunkWithLightPacket(levelChunk, level.getLightEngine(),
                                    null, null, true);
                            player.untrackChunk(levelChunk.getPos());
                            player.trackChunk(levelChunk.getPos(), lightPacket);
                        }
                    }
                }
            }
        });
    }
}