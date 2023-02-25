package skytheory.mystique.tags;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import skytheory.mystique.Mystique;

public class MystiqueBlockTags {
	
	public static final TagKey<Block> PROHIBIT_CHAIN_DESTRUCTION = BlockTags.create(new ResourceLocation(Mystique.MODID, "prohibit_chain_destruction"));
	
}
