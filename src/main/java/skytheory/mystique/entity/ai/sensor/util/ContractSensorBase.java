package skytheory.mystique.entity.ai.sensor.util;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.sensing.Sensor;
import skytheory.mystique.entity.AbstractElemental;
import skytheory.mystique.item.MystiqueContract;

public abstract class ContractSensorBase extends Sensor<AbstractElemental> {

	public ContractSensorBase() {
		this(20);
	}

	public ContractSensorBase(int pScanRate) {
		super(pScanRate);
	}
	
	@Override
	protected void doTick(ServerLevel pLevel, AbstractElemental pEntity) {
		if (pEntity.getContract() == getContract()) {
			doTickContract(pLevel, pEntity, pEntity.getBrain());
		}
	}

	protected abstract MystiqueContract getContract();
	protected abstract void doTickContract(ServerLevel pLevel, AbstractElemental pEntity, Brain<?> pBrain);

}
