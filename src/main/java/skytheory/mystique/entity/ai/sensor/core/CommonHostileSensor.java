package skytheory.mystique.entity.ai.sensor.core;

import java.util.Optional;
import java.util.Set;

import com.google.common.base.Predicates;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Spider;
import skytheory.mystique.init.MystiqueMemoryModuleTypes;

public class CommonHostileSensor extends Sensor<LivingEntity> {

	@Override
	protected void doTick(ServerLevel pLevel, LivingEntity pEntity) {
		Optional<LivingEntity> target = pEntity.getBrain().getMemory(MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES)
				.flatMap(entities -> entities
						.find(Enemy.class::isInstance)
						.findFirst());
		pEntity.getBrain().setMemory(MemoryModuleType.NEAREST_HOSTILE, target);
		Optional<LivingEntity> targetWithoutSpider = pEntity.getBrain().getMemory(MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES)
				.flatMap(entities -> entities
						.find(Enemy.class::isInstance)
						.filter(Predicates.not(Spider.class::isInstance))
						.findFirst());
		pEntity.getBrain().setMemory(MystiqueMemoryModuleTypes.COMMON_NEAREST_HOSTILE_WITHOUT_SPIDER, targetWithoutSpider);
	}

	@Override
	public Set<MemoryModuleType<?>> requires() {
		return Set.of(
				MemoryModuleType.NEAREST_HOSTILE,
				MystiqueMemoryModuleTypes.COMMON_NEAREST_HOSTILE_WITHOUT_SPIDER
				);
	}

}
