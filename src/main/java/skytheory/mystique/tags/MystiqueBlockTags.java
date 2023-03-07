package skytheory.mystique.tags;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import skytheory.mystique.Mystique;

public class MystiqueBlockTags {

	public static final TagKey<Block> ORES_FIRE_ELEMENT = BlockTags.create(new ResourceLocation("forge", "ores/mystique_element_fire"));
	public static final TagKey<Block> ORES_AIR_ELEMENT = BlockTags.create(new ResourceLocation("forge", "ores/mystique_element_air"));
	public static final TagKey<Block> ORES_WATER_ELEMENT = BlockTags.create(new ResourceLocation("forge", "ores/mystique_element_water"));
	public static final TagKey<Block> ORES_EARTH_ELEMENT = BlockTags.create(new ResourceLocation("forge", "ores/mystique_element_earth"));
	
	public static final TagKey<Block> CHAIN_HARVEST_BLACKLIST = BlockTags.create(new ResourceLocation(Mystique.MODID, "chain_harvest_blacklist"));
	public static final TagKey<Block> TINY_TNT_BLACKLIST = BlockTags.create(new ResourceLocation(Mystique.MODID, "tiny_tnt_blacklist"));
	
}
