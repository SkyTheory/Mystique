package skytheory.mystique.entity.ai.behavior.util;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.behavior.Behavior.Status;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import skytheory.mystique.entity.AbstractElemental;
import skytheory.mystique.entity.ai.contract.MystiqueContract;

public class BehaviorWrapper implements BehaviorControl<AbstractElemental> {

	public final AbstractElemental entity;
	public final MystiqueContract contract;
	public final BehaviorControl<? super AbstractElemental> behavior;
	
	public BehaviorWrapper(AbstractElemental entity, MystiqueContract contract, BehaviorControl<? super AbstractElemental> behavior){
		this.entity = entity;
		this.contract = contract;
		this.behavior = behavior;
	}
	
	@Override
	public Status getStatus() {
		return this.behavior.getStatus();
	}

	@Override
	public boolean tryStart(ServerLevel pLevel, AbstractElemental pEntity, long pGameTime) {
		if (entity.getContract() == contract) {
			return behavior.tryStart(pLevel, pEntity, pGameTime);
		}
		return false;
	}

	@Override
	public void tickOrStop(ServerLevel pLevel, AbstractElemental pEntity, long pGameTime) {
		if (this.entity.getContract() == contract) {
			behavior.tickOrStop(pLevel, pEntity, pGameTime);
		} else {
			behavior.doStop(pLevel, pEntity, pGameTime);
		}
	}

	@Override
	public void doStop(ServerLevel pLevel, AbstractElemental pEntity, long pGameTime) {
		behavior.doStop(pLevel, pEntity, pGameTime);
	}

	@Override
	public String debugString() {
		return "BehaviorWrapper: " + behavior.debugString();
	}

}
