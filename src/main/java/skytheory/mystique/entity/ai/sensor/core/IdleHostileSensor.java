package skytheory.mystique.entity.ai.sensor.core;

import java.util.Optional;
import java.util.Set;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Spider;
import skytheory.mystique.entity.AbstractElemental;
import skytheory.mystique.entity.ai.sensor.util.ContractSensorBase;
import skytheory.mystique.init.MystiqueContracts;
import skytheory.mystique.init.MystiqueMemoryModuleTypes;
import skytheory.mystique.item.MystiqueContract;

public class IdleHostileSensor extends ContractSensorBase {

	@Override
	protected void doTickContract(ServerLevel pLevel, AbstractElemental pEntity, Brain<?> pBrain) {
		Optional<LivingEntity> target = pEntity.getBrain().getMemory(MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES)
				.flatMap(entities -> entities
						.find(Enemy.class::isInstance)
						.filter(t -> !Spider.class.isInstance(t))
						.findFirst());
		pBrain.setMemory(MystiqueMemoryModuleTypes.NEAREST_HOSTILE, target);
	}
	
	@Override
	protected MystiqueContract getContract() {
		return MystiqueContracts.DEFAULT;
	}

	@Override
	public Set<MemoryModuleType<?>> requires() {
		return Set.of(MystiqueMemoryModuleTypes.NEAREST_HOSTILE);
	}

}
