package skytheory.mystique.entity.ai.behavior.core;

import java.util.Optional;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.OneShot;
import net.minecraft.world.entity.schedule.Activity;
import skytheory.mystique.entity.AbstractElemental;
import skytheory.mystique.init.MystiqueContracts;
import skytheory.mystique.init.MystiqueMemoryModuleTypes;
import skytheory.mystique.item.MystiqueContract;

public class UpdateElementalActivities extends OneShot<AbstractElemental> {

	@Override
	public boolean trigger(ServerLevel pLevel, AbstractElemental pEntity, long pGameTime) {
		updateActivity(pEntity);
		return true;
	}

	public static void updateActivity(AbstractElemental entity) {
		Brain<AbstractElemental> brain = entity.getBrain();
		Optional<MystiqueContract> prevOpt = brain.getMemory(MystiqueMemoryModuleTypes.CONTRACT);
		MystiqueContract contract = entity.getContract();
		prevOpt.ifPresent(prev -> {
			if (!prev.equals(contract)) prev.leaveContract(entity);
		});
		brain.setActiveActivityIfPossible(contract.getActivity());
		Optional<Activity> nextOpt = brain.getActiveNonCoreActivity();
		brain.setMemory(MystiqueMemoryModuleTypes.CONTRACT, contract);
		nextOpt.ifPresentOrElse(
				(activity -> {
					if (!activity.equals(MystiqueContracts.DEFAULT.getActivity())) {
						brain.setMemory(MystiqueMemoryModuleTypes.CONTRACT, contract);
						contract.enterContract(entity);
					}
				}),
				() -> {
					brain.eraseMemory(MystiqueMemoryModuleTypes.CONTRACT);
				});
		contract.enterContract(entity);
	}

}
