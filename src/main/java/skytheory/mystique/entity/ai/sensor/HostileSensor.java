package skytheory.mystique.entity.ai.sensor;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.NearestVisibleLivingEntitySensor;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Spider;

public class HostileSensor extends NearestVisibleLivingEntitySensor {

	@Override
	protected boolean isMatchingEntity(LivingEntity pAttacker, LivingEntity pTarget) {
		return !(pTarget instanceof Spider) && pTarget instanceof Enemy;
	}

	@Override
	protected MemoryModuleType<LivingEntity> getMemory() {
		return MemoryModuleType.NEAREST_HOSTILE;
	}

}
