package skytheory.mystique.entity.ai.behavior.core;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.OneShot;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import skytheory.mystique.entity.AbstractElemental;
import skytheory.mystique.init.MystiqueEntityActivities;

public class UpdateElementalActivities extends OneShot<AbstractElemental> {

	@Override
	public boolean trigger(ServerLevel pLevel, AbstractElemental pEntity, long pGameTime) {
		Brain<AbstractElemental> brain = pEntity.getBrain();
		if (!brain.hasMemoryValue(MemoryModuleType.IS_PANICKING)) {
			if (pEntity.isEatingItem() || !pEntity.getEatingItem().isEmpty()) {
				brain.setActiveActivityIfPossible(MystiqueEntityActivities.EAT);
			}
		}
		return true;
	}

}
