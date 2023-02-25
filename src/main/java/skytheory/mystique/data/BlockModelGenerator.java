package skytheory.mystique.data;

import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.ModelFile.UncheckedModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import skytheory.lib.data.AbstractBlockModelGenerator;
import skytheory.mystique.Mystique;
import skytheory.mystique.init.MystiqueBlocks;

public class BlockModelGenerator extends AbstractBlockModelGenerator {

	public BlockModelGenerator(PackOutput gen, String modid, ExistingFileHelper exFileHelper) {
		super(gen, modid, exFileHelper);
	}

	@Override
	protected void registerModels() {
		layeredOre(MystiqueBlocks.ORE_STONE_FIRE, Blocks.STONE, new ResourceLocation(Mystique.MODID, "block/ore_overlay_fire"));
		layeredOre(MystiqueBlocks.ORE_STONE_AIR, Blocks.STONE, new ResourceLocation(Mystique.MODID, "block/ore_overlay_air"));
		layeredOre(MystiqueBlocks.ORE_STONE_WATER, Blocks.STONE, new ResourceLocation(Mystique.MODID, "block/ore_overlay_water"));
		layeredOre(MystiqueBlocks.ORE_STONE_EARTH, Blocks.STONE, new ResourceLocation(Mystique.MODID, "block/ore_overlay_earth"));
		
		layeredOre(MystiqueBlocks.ORE_DEEPSLATE_FIRE, Blocks.DEEPSLATE, new ResourceLocation(Mystique.MODID, "block/ore_overlay_fire"));
		layeredOre(MystiqueBlocks.ORE_DEEPSLATE_AIR, Blocks.DEEPSLATE, new ResourceLocation(Mystique.MODID, "block/ore_overlay_air"));
		layeredOre(MystiqueBlocks.ORE_DEEPSLATE_WATER, Blocks.DEEPSLATE, new ResourceLocation(Mystique.MODID, "block/ore_overlay_water"));
		layeredOre(MystiqueBlocks.ORE_DEEPSLATE_EARTH, Blocks.DEEPSLATE, new ResourceLocation(Mystique.MODID, "block/ore_overlay_earth"));

		horizontalWaterLogged(MystiqueBlocks.DEVICE_WRITING_TABLE, new ResourceLocation(Mystique.MODID, "block/device_writing_table"));
		horizontalWaterLogged(MystiqueBlocks.DEVICE_WRITING_CHEST, new ResourceLocation(Mystique.MODID, "block/device_writing_chest"));
		horizontalWaterLogged(MystiqueBlocks.DEVICE_WALL_BOOKSHELF, new ResourceLocation(Mystique.MODID, "block/device_wall_bookshelf"));
		
		blockEntity(MystiqueBlocks.DEVICE_MANA_INFUSER, this.getPrefixedLocation(Blocks.IRON_BLOCK));
	}

	public void horizontalWaterLogged(Block block, ResourceLocation modelLocation) {
		ModelFile parent = new UncheckedModelFile(modelLocation);
		this.getVariantBuilder(block).forAllStatesExcept(state -> {
        	Direction direction = state.getValue(HorizontalDirectionalBlock.FACING);
            return ConfiguredModel.builder().modelFile(parent)
                	.rotationY(((int) direction.toYRot() + 180) % 360)
                    .build();
		}, BlockStateProperties.WATERLOGGED);
	}
	
}
