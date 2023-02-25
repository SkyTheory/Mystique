package skytheory.mystique.init;

import net.minecraft.world.level.block.entity.BlockEntityType;
import skytheory.mystique.block.ManaInfuserEntity;

public class MystiqueBlockEntities {

	public static final BlockEntityType<ManaInfuserEntity> mana_infuser = BlockEntityType.Builder
			.of(ManaInfuserEntity::new, MystiqueBlocks.DEVICE_MANA_INFUSER)
			.build(null);

}
