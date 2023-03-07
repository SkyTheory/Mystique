package skytheory.mystique.data;

import java.util.concurrent.CompletableFuture;

import org.jetbrains.annotations.Nullable;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import skytheory.mystique.init.MystiqueBlocks;
import skytheory.mystique.tags.MystiqueBlockTags;

public class BlockTagsGenerator extends BlockTagsProvider {

	public BlockTagsGenerator(PackOutput output, CompletableFuture<Provider> lookupProvider, String modId,
			@Nullable ExistingFileHelper existingFileHelper) {
		super(output, lookupProvider, modId, existingFileHelper);
	}

	@Override
	protected void addTags(Provider pProvider) {

		this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(
				MystiqueBlocks.ORE_STONE_AIR,
				MystiqueBlocks.ORE_STONE_EARTH,
				MystiqueBlocks.ORE_STONE_FIRE,
				MystiqueBlocks.ORE_STONE_WATER,
				MystiqueBlocks.ORE_DEEPSLATE_AIR,
				MystiqueBlocks.ORE_DEEPSLATE_EARTH,
				MystiqueBlocks.ORE_DEEPSLATE_FIRE,
				MystiqueBlocks.ORE_DEEPSLATE_WATER
				);
		
		this.tag(BlockTags.NEEDS_IRON_TOOL).add(
				MystiqueBlocks.ORE_STONE_AIR,
				MystiqueBlocks.ORE_STONE_EARTH,
				MystiqueBlocks.ORE_STONE_FIRE,
				MystiqueBlocks.ORE_STONE_WATER,
				MystiqueBlocks.ORE_DEEPSLATE_AIR,
				MystiqueBlocks.ORE_DEEPSLATE_EARTH,
				MystiqueBlocks.ORE_DEEPSLATE_FIRE,
				MystiqueBlocks.ORE_DEEPSLATE_WATER
				);

		this.tag(Tags.Blocks.ORES).add(
				MystiqueBlocks.ORE_STONE_FIRE,
				MystiqueBlocks.ORE_STONE_AIR,
				MystiqueBlocks.ORE_STONE_WATER,
				MystiqueBlocks.ORE_STONE_EARTH,
				MystiqueBlocks.ORE_DEEPSLATE_FIRE,
				MystiqueBlocks.ORE_DEEPSLATE_AIR,
				MystiqueBlocks.ORE_DEEPSLATE_WATER,
				MystiqueBlocks.ORE_DEEPSLATE_EARTH
				);
		
		this.tag(Tags.Blocks.ORES_IN_GROUND_STONE).add(
				MystiqueBlocks.ORE_STONE_AIR,
				MystiqueBlocks.ORE_STONE_EARTH,
				MystiqueBlocks.ORE_STONE_FIRE,
				MystiqueBlocks.ORE_STONE_WATER
				);

		this.tag(Tags.Blocks.ORES_IN_GROUND_DEEPSLATE).add(
				MystiqueBlocks.ORE_DEEPSLATE_AIR,
				MystiqueBlocks.ORE_DEEPSLATE_EARTH,
				MystiqueBlocks.ORE_DEEPSLATE_FIRE,
				MystiqueBlocks.ORE_DEEPSLATE_WATER
				);

		this.tag(MystiqueBlockTags.ORES_FIRE_ELEMENT).add(
				MystiqueBlocks.ORE_STONE_FIRE,
				MystiqueBlocks.ORE_DEEPSLATE_FIRE
				);

		this.tag(MystiqueBlockTags.ORES_AIR_ELEMENT).add(
				MystiqueBlocks.ORE_STONE_AIR,
				MystiqueBlocks.ORE_DEEPSLATE_AIR
				);

		this.tag(MystiqueBlockTags.ORES_WATER_ELEMENT).add(
				MystiqueBlocks.ORE_STONE_WATER,
				MystiqueBlocks.ORE_DEEPSLATE_WATER
				);

		this.tag(MystiqueBlockTags.ORES_EARTH_ELEMENT).add(
				MystiqueBlocks.ORE_STONE_EARTH,
				MystiqueBlocks.ORE_DEEPSLATE_EARTH
				);
		
		this.tag(MystiqueBlockTags.CHAIN_HARVEST_BLACKLIST).add(
				Blocks.STONE,
				Blocks.DEEPSLATE,
				Blocks.DIRT,
				Blocks.COARSE_DIRT,
				Blocks.ROOTED_DIRT,
				Blocks.GRASS_BLOCK,
				Blocks.PODZOL,
				Blocks.MYCELIUM,
				Blocks.SNOW,
				Blocks.SNOW_BLOCK,
				Blocks.ICE,
				Blocks.PACKED_ICE,
				Blocks.SAND,
				Blocks.SANDSTONE,
				Blocks.RED_SAND,
				Blocks.RED_SANDSTONE,
				Blocks.TERRACOTTA,
				Blocks.WHITE_TERRACOTTA,
				Blocks.ORANGE_TERRACOTTA,
				Blocks.MAGENTA_TERRACOTTA,
				Blocks.LIGHT_BLUE_TERRACOTTA,
				Blocks.YELLOW_TERRACOTTA,
				Blocks.LIME_TERRACOTTA,
				Blocks.PINK_TERRACOTTA,
				Blocks.GRAY_TERRACOTTA,
				Blocks.LIGHT_GRAY_TERRACOTTA,
				Blocks.CYAN_TERRACOTTA,
				Blocks.PURPLE_TERRACOTTA,
				Blocks.BLUE_TERRACOTTA,
				Blocks.BROWN_TERRACOTTA,
				Blocks.GREEN_TERRACOTTA,
				Blocks.RED_TERRACOTTA,
				Blocks.BLACK_TERRACOTTA,
				Blocks.NETHERRACK,
				Blocks.NETHER_BRICKS,
				Blocks.SOUL_SAND,
				Blocks.PRISMARINE,
				Blocks.PRISMARINE_BRICKS,
				Blocks.DARK_PRISMARINE,
				Blocks.SEA_LANTERN,
				Blocks.OBSIDIAN,
				Blocks.CRYING_OBSIDIAN
				);
		
		this.tag(MystiqueBlockTags.TINY_TNT_BLACKLIST).add(
				Blocks.STONE,
				Blocks.DEEPSLATE,
				Blocks.DIRT,
				Blocks.COARSE_DIRT,
				Blocks.ROOTED_DIRT,
				Blocks.GRASS_BLOCK,
				Blocks.PODZOL,
				Blocks.MYCELIUM,
				Blocks.SNOW,
				Blocks.SNOW_BLOCK,
				Blocks.ICE,
				Blocks.PACKED_ICE,
				Blocks.SAND,
				Blocks.SANDSTONE,
				Blocks.RED_SAND,
				Blocks.RED_SANDSTONE,
				Blocks.TERRACOTTA,
				Blocks.WHITE_TERRACOTTA,
				Blocks.ORANGE_TERRACOTTA,
				Blocks.MAGENTA_TERRACOTTA,
				Blocks.LIGHT_BLUE_TERRACOTTA,
				Blocks.YELLOW_TERRACOTTA,
				Blocks.LIME_TERRACOTTA,
				Blocks.PINK_TERRACOTTA,
				Blocks.GRAY_TERRACOTTA,
				Blocks.LIGHT_GRAY_TERRACOTTA,
				Blocks.CYAN_TERRACOTTA,
				Blocks.PURPLE_TERRACOTTA,
				Blocks.BLUE_TERRACOTTA,
				Blocks.BROWN_TERRACOTTA,
				Blocks.GREEN_TERRACOTTA,
				Blocks.RED_TERRACOTTA,
				Blocks.BLACK_TERRACOTTA,
				Blocks.NETHERRACK,
				Blocks.NETHER_BRICKS,
				Blocks.SOUL_SAND,
				Blocks.PRISMARINE,
				Blocks.PRISMARINE_BRICKS,
				Blocks.DARK_PRISMARINE,
				Blocks.SEA_LANTERN,
				Blocks.OBSIDIAN,
				Blocks.CRYING_OBSIDIAN
				);

	}

}
