package skytheory.mystique.entity.ai.behavior.collect;

import java.util.Optional;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.ai.behavior.OneShot;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import skytheory.mystique.entity.AbstractElemental;
import skytheory.mystique.init.MystiqueMemoryModuleTypes;

public class PickUpItemBehavior extends OneShot<AbstractElemental> {

	@Override
	public boolean trigger(ServerLevel pLevel, AbstractElemental pEntity, long pGameTime) {
		Optional<ItemEntity> opt = pEntity.getBrain().getMemory(MystiqueMemoryModuleTypes.COLLECT_ITEM);
		if (opt.isPresent()) {
			ItemEntity itemEntity = opt.get();
			if (!itemEntity.isRemoved()) {
				if (pEntity.closerThan(itemEntity, 1.0d)) {
					ItemStack stack = itemEntity.getItem();
					if (pEntity.getMainHandItem().isEmpty() && !stack.isEmpty()) { 
						pEntity.onItemPickup(itemEntity);
						pEntity.take(itemEntity, stack.getCount());
						pEntity.setItemInHand(InteractionHand.MAIN_HAND, stack.copy());
						stack.shrink(stack.getCount());
						itemEntity.discard();
						pEntity.getBrain().eraseMemory(MystiqueMemoryModuleTypes.COLLECT_ITEM);
						pEntity.getBrain().eraseMemory(MemoryModuleType.WALK_TARGET);
						pEntity.getBrain().eraseMemory(MemoryModuleType.LOOK_TARGET);
						return true;
					}
				}
			}
		}
		return false;
	}

}
