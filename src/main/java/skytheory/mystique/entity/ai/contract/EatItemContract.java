package skytheory.mystique.entity.ai.contract;

import java.util.Collection;
import java.util.Set;

import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.schedule.Activity;
import skytheory.mystique.entity.AbstractElemental;
import skytheory.mystique.entity.ai.behavior.eat.EatItem;
import skytheory.mystique.entity.ai.behavior.eat.ThrowNonEdibleItem;
import skytheory.mystique.init.MystiqueActivities;
import skytheory.mystique.item.MystiqueContract;

public class EatItemContract implements MystiqueContract {

	@Override
	public Activity getActivity() {
		return MystiqueActivities.EAT;
	}
	
	@Override
	public boolean canApplyContract(AbstractElemental entity) {
		if (entity.getBrain().hasMemoryValue(MemoryModuleType.IS_PANICKING)) return false;
		return !entity.getEatingItem().isEmpty(); 
	}
	
	@Override
	public int getPriority() {
		return 60;
	}

	@Override
	public Collection<MemoryModuleType<?>> getMemoryModules() {
		return Set.of();
	}

	@Override
	public Collection<SensorType<? extends Sensor<? super AbstractElemental>>> getSensorTypes() {
		return Set.of();
	}

	@Override
	public Collection<? extends BehaviorControl<? super AbstractElemental>> getActions() {
		return Set.of(
				new EatItem(),
				new ThrowNonEdibleItem()
				);
	}

}
