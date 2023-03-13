package skytheory.mystique.entity.ai.behavior.collect;

import java.util.Map;
import java.util.Optional;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.EntityTracker;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.item.ItemEntity;
import skytheory.mystique.entity.AbstractElemental;
import skytheory.mystique.init.MystiqueMemoryModuleTypes;

public class WalkToItemBehavior extends Behavior<AbstractElemental> {

	public WalkToItemBehavior() {
		super(Map.ofEntries(
				Map.entry(MemoryModuleType.IS_PANICKING, MemoryStatus.VALUE_ABSENT),
				Map.entry(MystiqueMemoryModuleTypes.COLLECT_ITEM, MemoryStatus.VALUE_PRESENT)));
	}

	@Override
	protected boolean checkExtraStartConditions(ServerLevel pLevel, AbstractElemental pOwner) {
		return pOwner.getItemInHand(InteractionHand.MAIN_HAND).isEmpty();
	}

	@Override
	protected boolean canStillUse(ServerLevel pLevel, AbstractElemental pEntity, long pGameTime) {
		Optional<ItemEntity> opt = pEntity.getBrain().getMemory(MystiqueMemoryModuleTypes.COLLECT_ITEM);
		return opt.map(itemEntity -> !itemEntity.isRemoved()).orElse(false);
	}

	@Override
	protected void tick(ServerLevel pLevel, AbstractElemental pOwner, long pGameTime) {
		Optional<ItemEntity> opt = pOwner.getBrain().getMemory(MystiqueMemoryModuleTypes.COLLECT_ITEM);
		if (opt.isPresent()) {
			Brain<AbstractElemental> brain = pOwner.getBrain();
			ItemEntity itemEntity = opt.get();
			WalkTarget walkTarget = new WalkTarget(new EntityTracker(itemEntity, false), 1.0f, 0);
			brain.setMemory(MemoryModuleType.WALK_TARGET, walkTarget);
		}
	}

	protected void stop(ServerLevel pLevel, AbstractElemental pEntity, long pGameTime) {
		Brain<?> brain = pEntity.getBrain();
		brain.eraseMemory(MemoryModuleType.WALK_TARGET);
		brain.eraseMemory(MemoryModuleType.LOOK_TARGET);
	}

}
