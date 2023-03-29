package skytheory.mystique.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import skytheory.mystique.init.MystiqueBlockEntities;

public class ManaCondenserEntity extends BlockEntity {

	public ManaCondenserEntity(BlockPos pPos, BlockState pBlockState) {
		super(MystiqueBlockEntities.MANA_CONDENSER, pPos, pBlockState);
	}

}
