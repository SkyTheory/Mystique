package skytheory.mystique.entity.ai.contract;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.player.Player;
import skytheory.mystique.entity.AbstractElemental;
import skytheory.mystique.entity.ai.behavior.interacting.FollowInteractingPlayer;
import skytheory.mystique.gui.ElementalContainerMenu;

public class InteractingPlayerContract implements MystiqueContract {

	/**
	 * 通常のContractより優先して実行する
	 */
	@Override
	public int getPriority() {
		return 10;
	}

	@Override
	public boolean canApplyContract(AbstractElemental entity) {
		if (entity.getBrain().hasMemoryValue(MemoryModuleType.IS_PANICKING)) return false;
		Optional<Player> playerOpt = entity.getInteractingPlayer();
		if (playerOpt.isEmpty()) return false;
		return playerOpt.get().containerMenu instanceof ElementalContainerMenu;
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
	public List<? extends BehaviorControl<? super AbstractElemental>> getActions() {
		return List.of(new FollowInteractingPlayer(1.0f));
	}

}
