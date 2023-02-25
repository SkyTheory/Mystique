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

		this.tag(Tags.Blocks.ORES_IN_GROUND_STONE).add(
				MystiqueBlocks.ORE_STONE_AIR,
				MystiqueBlocks.ORE_STONE_EARTH,
				MystiqueBlocks.ORE_STONE_FIRE,
				MystiqueBlocks.ORE_STONE_WATER
				);

		this.tag(Tags.Blocks.ORES_IN_GROUND_DEEPSLATE)
		.add(
				MystiqueBlocks.ORE_DEEPSLATE_AIR,
				MystiqueBlocks.ORE_DEEPSLATE_EARTH,
				MystiqueBlocks.ORE_DEEPSLATE_FIRE,
				MystiqueBlocks.ORE_DEEPSLATE_WATER
				);

		this.tag(MystiqueBlockTags.PROHIBIT_CHAIN_DESTRUCTION)
		.add(
				Blocks.STONE,
				Blocks.DIRT,
				Blocks.GRASS,
				Blocks.SAND,
				Blocks.SANDSTONE,
				Blocks.RED_SAND,
				Blocks.RED_SANDSTONE,
				Blocks.PODZOL,
				Blocks.SNOW
				);

	}

}
