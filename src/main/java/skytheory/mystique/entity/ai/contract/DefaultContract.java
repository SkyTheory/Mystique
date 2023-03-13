package skytheory.mystique.entity.ai.contract;

import java.util.Collection;
import java.util.Set;

import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.SetWalkTargetAwayFrom;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.schedule.Activity;
import skytheory.lib.entity.ai.behavior.RunOnePrioritized;
import skytheory.mystique.entity.AbstractElemental;
import skytheory.mystique.entity.ai.behavior.core.FollowTemptationWithoutCooldown;
import skytheory.mystique.entity.ai.behavior.idle.IdleWalkingBehavior;
import skytheory.mystique.init.MystiqueMemoryModuleTypes;
import skytheory.mystique.init.MystiqueSensorTypes;
import skytheory.mystique.item.MystiqueContract;

public class DefaultContract implements MystiqueContract {

	@Override
	public Activity getActivity() {
		return Activity.IDLE;
	}

	@Override
	public int getPriority() {
		return Integer.MAX_VALUE;
	}

	@Override
	public boolean canApplyContract(AbstractElemental entity) {
		return true;
	}
	
	@Override
	public Collection<MemoryModuleType<?>> getMemoryModules() {
		return Set.of(
				MystiqueMemoryModuleTypes.NEAREST_HOSTILE
				);
	}

	@Override
	public Collection<SensorType<? extends Sensor<? super AbstractElemental>>> getSensorTypes() {
		return Set.of(
				MystiqueSensorTypes.TEMPTING,
				MystiqueSensorTypes.HOSTILES
				);
	}

	@Override
	public Collection<? extends BehaviorControl<? super AbstractElemental>> getActions() {
		return Set.of(
				new RunOnePrioritized<AbstractElemental>()
				.addBehavior(SetWalkTargetAwayFrom.entity(MemoryModuleType.NEAREST_HOSTILE, 1.0f, 6, false))
				.addBehavior(new FollowTemptationWithoutCooldown(1.25f))
				.addBehavior(new IdleWalkingBehavior())
				);
	}

}
