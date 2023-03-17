package skytheory.mystique.entity.ai.behavior.util;

import java.util.Collection;
import java.util.List;

import com.google.common.collect.ImmutableList;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.behavior.Behavior.Status;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import skytheory.lib.entity.ai.behavior.BehaviorSelector;
import skytheory.mystique.entity.AbstractElemental;
import skytheory.mystique.entity.ai.contract.MystiqueContract;

public class ContractBehavior extends BehaviorSelector<AbstractElemental> {

	public final MystiqueContract contract;
	public final List<BehaviorControl<? super AbstractElemental>> behaviors;
	
	public ContractBehavior(MystiqueContract contract) {
		this.contract = contract;
		this.behaviors = ImmutableList.copyOf(contract.getActions());
	}

	@Override
	public void updateBehaviorStatus(ServerLevel pLevel, AbstractElemental pEntity, long pGameTime) {
		if (contract.equals(pEntity.getContract())) {
			this.behaviors.stream()
			.filter(behavior -> behavior.getStatus() == Status.STOPPED)
			.forEach(behavior -> behavior.tryStart(pLevel, pEntity, pGameTime));
		}
	}
	
	@Override
	public void tickOrStop(ServerLevel pLevel, AbstractElemental pEntity, long pGameTime) {
		if (contract.equals(pEntity.getContract())) {
			super.tickOrStop(pLevel, pEntity, pGameTime);
		} else {
			this.doStop(pLevel, pEntity, pGameTime);
		}
	}

	@Override
	protected Collection<BehaviorControl<? super AbstractElemental>> getAllBehaviors() {
		return this.behaviors;
	}

}
