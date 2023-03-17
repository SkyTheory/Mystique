package skytheory.mystique.entity.ai;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.Brain.Provider;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.schedule.Activity;
import skytheory.mystique.entity.AbstractElemental;
import skytheory.mystique.entity.ai.behavior.util.ContractBehavior;
import skytheory.mystique.entity.ai.contract.MystiqueContract;
import skytheory.mystique.init.ContractManager;
import skytheory.mystique.init.ElementalAIManager;
import skytheory.mystique.init.MystiqueActivities;

public class ElementalAI {

	public static Provider<AbstractElemental> brainProvider() {
		return Brain.provider(ElementalAIManager.getMemoryTypes(), ElementalAIManager.getSensorTypes());
	}

	public static void initBrain(AbstractElemental pEntity, Brain<AbstractElemental> pBrain) {
		registerCoreActions(pBrain);
		registerContractActions(pBrain);
		pBrain.setCoreActivities(ImmutableSet.of(Activity.CORE));
		pBrain.setDefaultActivity(MystiqueActivities.CONTRACT);
		pBrain.useDefaultActivity();
	}
	
	private static void registerCoreActions(Brain<AbstractElemental> brain) {
		List<? extends BehaviorControl<? super AbstractElemental>> behaviors = ContractManager.getAllContracts().stream()
				.map(MystiqueContract::getCoreActions)
				.flatMap(List::stream)
				.distinct()
				.toList();
		brain.addActivity(Activity.CORE, 0, ImmutableList.copyOf(behaviors));
	}

	private static void registerContractActions(Brain<AbstractElemental> pBrain) {
		List<ContractBehavior> behaviors = ContractManager.getAllContracts().stream()
		.map(ContractBehavior::new)
		.toList();
		pBrain.addActivity(MystiqueActivities.CONTRACT, 0, ImmutableList.copyOf(behaviors));
	}

}
