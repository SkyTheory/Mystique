package skytheory.mystique.entity.ai.contract;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.SetWalkTargetAwayFrom;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import skytheory.lib.entity.ai.behavior.RunOnePrioritized;
import skytheory.mystique.entity.AbstractElemental;
import skytheory.mystique.entity.ai.behavior.common.FollowTemptedPlayer;
import skytheory.mystique.entity.ai.behavior.idle.IdleWalkingBehavior;

public class DefaultContract implements MystiqueContract {

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
				);
	}

	@Override
	public Collection<SensorType<? extends Sensor<? super AbstractElemental>>> getSensorTypes() {
		return Set.of(
				);
	}

	@Override
	public List<? extends BehaviorControl<? super AbstractElemental>> getActions() {
		return List.of(
				new RunOnePrioritized<AbstractElemental>()
				.addBehavior(SetWalkTargetAwayFrom.entity(MemoryModuleType.NEAREST_HOSTILE, 1.0f, 6, false))
				.addBehavior(new FollowTemptedPlayer(1.25f))
				.addBehavior(new IdleWalkingBehavior())
				);
	}

}
