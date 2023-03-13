package skytheory.mystique.entity.ai.contract;

import java.util.Collection;
import java.util.Set;

import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.schedule.Activity;
import skytheory.mystique.container.ElementalContainerMenu;
import skytheory.mystique.entity.AbstractElemental;
import skytheory.mystique.entity.ai.behavior.interacting.FollowInteractingPlayer;
import skytheory.mystique.init.MystiqueActivities;

public class InteractingPlayerContract implements MystiqueContract {

	@Override
	public Activity getActivity() {
		return MystiqueActivities.INTERACTING;
	}

	/**
	 * 通常のContractより優先して実行する
	 */
	@Override
	public int getPriority() {
		return 50;
	}

	@Override
	public boolean canApplyContract(AbstractElemental entity) {
		if (entity.getBrain().hasMemoryValue(MemoryModuleType.IS_PANICKING)) return false;
		Player player = entity.getInteractingPlayer();
		if (player == null) return false;
		return player.containerMenu instanceof ElementalContainerMenu;
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
		return Set.of(new FollowInteractingPlayer(1.0f));
	}

}
