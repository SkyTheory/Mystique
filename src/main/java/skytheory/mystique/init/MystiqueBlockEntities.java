package skytheory.mystique.init;

import net.minecraft.world.level.block.entity.BlockEntityType;
import skytheory.mystique.blockentity.ManaChannelerEntity;
import skytheory.mystique.blockentity.ManaCondenserEntity;
import skytheory.mystique.blockentity.ManaInfuserEntity;

public class MystiqueBlockEntities {

	public static final BlockEntityType<ManaChannelerEntity> MANA_CHANNELER = BlockEntityType.Builder
			.of(ManaChannelerEntity::new, MystiqueBlocks.DEVICE_MANA_CHANNELER)
			.build(null);

	public static final BlockEntityType<ManaCondenserEntity> MANA_CONDENSER = BlockEntityType.Builder
			.of(ManaCondenserEntity::new, MystiqueBlocks.DEVICE_MANA_CONDENSER)
			.build(null);

	public static final BlockEntityType<ManaInfuserEntity> MANA_INFUSER = BlockEntityType.Builder
			.of(ManaInfuserEntity::new, MystiqueBlocks.DEVICE_MANA_INFUSER)
			.build(null);

}
