package skytheory.mystique.entity.ai.behavior.core;

import java.util.Comparator;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.behavior.OneShot;
import skytheory.mystique.entity.AbstractElemental;
import skytheory.mystique.entity.ai.contract.MystiqueContract;
import skytheory.mystique.init.MystiqueContracts;
import skytheory.mystique.init.MystiqueRegistries;

public class UpdateElementalActivities extends OneShot<AbstractElemental> {

	@Override
	public boolean trigger(ServerLevel pLevel, AbstractElemental pEntity, long pGameTime) {
		MystiqueContract prev = pEntity.getContract();
		MystiqueContract next = MystiqueRegistries.CONTRACTS.getValues().stream()
				.sorted(Comparator.comparingInt(MystiqueContract::getPriority))
				.filter(c -> c.canApplyContract(pEntity))
				.findFirst().orElse(MystiqueContracts.DEFAULT);
		if (prev == next) return false;
		pEntity.getEntityData().set(AbstractElemental.DATA_CONTRACT, next);
		prev.leaveContract(pEntity);
		next.enterContract(pEntity);
		return true;
	}

}
