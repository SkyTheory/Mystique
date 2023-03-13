package skytheory.mystique.entity.ai;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.Brain.Provider;
import net.minecraft.world.entity.ai.behavior.AnimalPanic;
import net.minecraft.world.entity.ai.behavior.LookAtTargetSink;
import net.minecraft.world.entity.ai.behavior.MoveToTargetSink;
import net.minecraft.world.entity.schedule.Activity;
import skytheory.mystique.entity.AbstractElemental;
import skytheory.mystique.entity.ai.behavior.core.FloatFluid;
import skytheory.mystique.entity.ai.behavior.core.UpdateElementalActivities;
import skytheory.mystique.entity.ai.contract.MystiqueContract;
import skytheory.mystique.init.ElementalAIRegistry;
import skytheory.mystique.init.MystiqueContracts;
import skytheory.mystique.init.MystiqueRegistries;

public class ElementalAI {

	public static Provider<AbstractElemental> brainProvider() {
		return Brain.provider(ElementalAIRegistry.getMemoryTypes(), ElementalAIRegistry.getSensorTypes());
	}

	public static void initBrain(AbstractElemental pEntity, Brain<AbstractElemental> pBrain) {
		initCoreActivity(pBrain);
		initContractActivities(pBrain);
		pBrain.setCoreActivities(ImmutableSet.of(Activity.CORE));
		pBrain.setDefaultActivity(MystiqueContracts.DEFAULT.getActivity());
		pBrain.setActiveActivityIfPossible(Activity.CORE);
	}

	private static void initContractActivities(Brain<AbstractElemental> pBrain) {
		MystiqueRegistries.CONTRACTS.getValues().stream().forEach(contract -> registerContractActions(pBrain, contract));
	}

	private static void initCoreActivity(Brain<AbstractElemental> brain) {
		brain.addActivity(Activity.CORE, 0, ImmutableList.of(
				new FloatFluid(),
				new UpdateElementalActivities(),
				new AnimalPanic(2.0f),
				new LookAtTargetSink(45, 90),
				new MoveToTargetSink()
				));
	}

	private static void registerContractActions(Brain<AbstractElemental> brain, MystiqueContract contract) {
		brain.addActivity(contract.getActivity(), 0, ImmutableList.copyOf(contract.getActions()));
	}

}
