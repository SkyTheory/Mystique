package skytheory.mystique.block;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import skytheory.mystique.Mystique;
import skytheory.mystique.blockentity.ManaChannelerEntity;

public class ManaChannelerBlock extends Block implements EntityBlock {

	public static final ResourceLocation TEXTURE = new ResourceLocation(Mystique.MODID, "textures/entity/mana_channeler.png");

	public ManaChannelerBlock(Properties pProperties) {
		super(pProperties);
	}
	
	@Override
	public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
		return new ManaChannelerEntity(pPos, pState);
	}

}
