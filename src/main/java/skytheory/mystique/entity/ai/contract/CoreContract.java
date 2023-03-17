package skytheory.mystique.entity.ai.contract;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import net.minecraft.world.entity.ai.behavior.AnimalPanic;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.LookAtTargetSink;
import net.minecraft.world.entity.ai.behavior.MoveToTargetSink;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import skytheory.mystique.entity.AbstractElemental;
import skytheory.mystique.entity.ai.behavior.core.FloatFluid;
import skytheory.mystique.entity.ai.behavior.core.UpdateElementalActivities;
import skytheory.mystique.init.MystiqueMemoryModuleTypes;
import skytheory.mystique.init.MystiqueSensorTypes;

public class CoreContract implements MystiqueContract {

	@Override
	public int getPriority() {
		return 0;
	}

	@Override
	public boolean canApplyContract(AbstractElemental entity) {
		return false;
	}
	
	@Override
	public Collection<MemoryModuleType<?>> getMemoryModules() {
		return Set.of(
				MemoryModuleType.NEAREST_LIVING_ENTITIES,
				MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES,
				MemoryModuleType.NEAREST_PLAYERS,
				MemoryModuleType.NEAREST_VISIBLE_PLAYER,
				MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER,
				MemoryModuleType.HURT_BY,
				MemoryModuleType.HURT_BY_ENTITY,
				MemoryModuleType.LOOK_TARGET,
				MemoryModuleType.WALK_TARGET,
				MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
				MemoryModuleType.PATH,
				MemoryModuleType.NEAREST_HOSTILE,
				MemoryModuleType.TEMPTING_PLAYER,
				MemoryModuleType.TEMPTATION_COOLDOWN_TICKS,
				MemoryModuleType.IS_TEMPTED,
				MemoryModuleType.IS_PANICKING,
				MystiqueMemoryModuleTypes.COMMON_LOOK_COOLDOWN,
				MystiqueMemoryModuleTypes.COMMON_NEAREST_HOSTILE_WITHOUT_SPIDER
				);
	}

	@Override
	public Collection<SensorType<? extends Sensor<? super AbstractElemental>>> getSensorTypes() {
		return Set.of(
				SensorType.NEAREST_LIVING_ENTITIES,
				SensorType.NEAREST_PLAYERS,
				SensorType.HURT_BY,
				MystiqueSensorTypes.CORE_TEMPTING,
				MystiqueSensorTypes.CORE_HOSTILES
				);
	}

	@Override
	public List<? extends BehaviorControl<? super AbstractElemental>> getCoreActions() {
		return List.of(
				new FloatFluid(),
				new UpdateElementalActivities(),
				new AnimalPanic(2.0f),
				new LookAtTargetSink(45, 90),
				new MoveToTargetSink()
				);
	}

	@Override
	public List<? extends BehaviorControl<? super AbstractElemental>> getActions() {
		return List.of();
	}
	
}
