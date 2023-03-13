package skytheory.mystique.entity.ai.behavior.core;

import java.util.Optional;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.OneShot;
import skytheory.mystique.entity.AbstractElemental;
import skytheory.mystique.entity.ai.contract.MystiqueContract;
import skytheory.mystique.init.MystiqueMemoryModuleTypes;

public class UpdateElementalActivities extends OneShot<AbstractElemental> {

	@Override
	public boolean trigger(ServerLevel pLevel, AbstractElemental pEntity, long pGameTime) {
		updateActivity(pEntity);
		return true;
	}

	public static void updateActivity(AbstractElemental entity) {
		Brain<AbstractElemental> brain = entity.getBrain();
		Optional<MystiqueContract> prevOpt = brain.getMemory(MystiqueMemoryModuleTypes.CONTRACT);
		MystiqueContract prev = prevOpt.orElse(null);
		MystiqueContract next = entity.getContract();
		if (prev == next) return;
		brain.setActiveActivityIfPossible(next.getActivity());
		brain.setMemory(MystiqueMemoryModuleTypes.CONTRACT, next);
		entity.getEntityData().set(AbstractElemental.DATA_CONTRACT, next);
		next.enterContract(entity);
	}

}
