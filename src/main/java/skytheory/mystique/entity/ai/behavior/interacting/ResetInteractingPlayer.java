package skytheory.mystique.entity.ai.behavior.interacting;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.behavior.OneShot;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import skytheory.mystique.container.ElementalContainerMenu;
import skytheory.mystique.entity.AbstractElemental;

public class ResetInteractingPlayer extends OneShot<AbstractElemental> {

	@Override
	public boolean trigger(ServerLevel pLevel, AbstractElemental pEntity, long pGameTime) {
		if (pEntity.getBrain().hasMemoryValue(MemoryModuleType.IS_PANICKING)
				|| pEntity.getInteractingPlayer() == null
				|| !(pEntity.getInteractingPlayer().containerMenu instanceof ElementalContainerMenu)) {
			pEntity.getBrain().useDefaultActivity();
			pEntity.getBrain().eraseMemory(MemoryModuleType.WALK_TARGET);
			pEntity.getBrain().eraseMemory(MemoryModuleType.LOOK_TARGET);
		}
		return true;
	}

}
