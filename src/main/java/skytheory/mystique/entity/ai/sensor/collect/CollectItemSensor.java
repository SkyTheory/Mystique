package skytheory.mystique.entity.ai.sensor.collect;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.google.common.collect.ImmutableSet;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.item.ItemEntity;
import skytheory.mystique.entity.AbstractElemental;
import skytheory.mystique.entity.ai.sensor.util.ContractSensorBase;
import skytheory.mystique.init.MystiqueContracts;
import skytheory.mystique.init.MystiqueMemoryModuleTypes;
import skytheory.mystique.item.MystiqueContract;

public class CollectItemSensor extends ContractSensorBase {

	public static final double HORIZONTAL_RANGE = 32.0d;
	public static final double VERTICAL_RANGE = 16.0d;
	public static final double DISTANCE = 32.0d;

	@Override
	protected void doTickContract(ServerLevel pLevel, AbstractElemental pEntity, Brain<?> pBrain) {
		List<ItemEntity> list = pLevel.getEntitiesOfClass(ItemEntity.class, pEntity.getBoundingBox().inflate(HORIZONTAL_RANGE, VERTICAL_RANGE, HORIZONTAL_RANGE));
		Optional<ItemEntity> toPickup = list.stream()
				.filter(itemEntity -> pEntity.closerThan(itemEntity, DISTANCE))
				.filter(itemEntity -> pEntity.wantsToPickUp(itemEntity.getItem()))
				.filter(itemEntity -> pEntity.isValidItem(itemEntity.getItem()))
				.filter(itemEntity -> !itemEntity.hasPickUpDelay())
				.filter(pEntity::hasLineOfSight)
				.sorted(Comparator.comparingDouble(itemEntity -> itemEntity.distanceToSqr(pEntity)))
				.findFirst();
		pBrain.setMemory(MystiqueMemoryModuleTypes.COLLECT_ITEM, toPickup);
	}

	@Override
	public Set<MemoryModuleType<?>> requires() {
		return ImmutableSet.of(MystiqueMemoryModuleTypes.COLLECT_ITEM);
	}

	@Override
	protected MystiqueContract getContract() {
		return MystiqueContracts.COLLECT;
	}

}
