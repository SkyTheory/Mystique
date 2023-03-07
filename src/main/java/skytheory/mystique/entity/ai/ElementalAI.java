package skytheory.mystique.entity.ai;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.Brain.Provider;
import net.minecraft.world.entity.ai.behavior.AnimalPanic;
import net.minecraft.world.entity.ai.behavior.DoNothing;
import net.minecraft.world.entity.ai.behavior.LookAtTargetSink;
import net.minecraft.world.entity.ai.behavior.MoveToTargetSink;
import net.minecraft.world.entity.ai.behavior.RandomStroll;
import net.minecraft.world.entity.ai.behavior.SetWalkTargetAwayFrom;
import net.minecraft.world.entity.ai.behavior.SetWalkTargetFromLookTarget;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.schedule.Activity;
import skytheory.lib.entity.ai.behavior.RunOneForEach;
import skytheory.lib.entity.ai.behavior.RunOnePrioritized;
import skytheory.mystique.entity.AbstractElemental;
import skytheory.mystique.entity.ai.behavior.core.FloatFluid;
import skytheory.mystique.entity.ai.behavior.core.FollowTemptationWithoutCooldown;
import skytheory.mystique.entity.ai.behavior.core.UpdateElementalActivities;
import skytheory.mystique.entity.ai.behavior.eat.EatItem;
import skytheory.mystique.entity.ai.behavior.eat.ResetEatItem;
import skytheory.mystique.entity.ai.behavior.interacting.FollowInteractingPlayer;
import skytheory.mystique.entity.ai.behavior.interacting.ResetInteractingPlayer;
import skytheory.mystique.init.ElementalAIRegistry;
import skytheory.mystique.init.MystiqueEntityActivities;

public class ElementalAI {

	public static Provider<AbstractElemental> brainProvider() {
		return Brain.provider(ElementalAIRegistry.getMemoryTypes(), ElementalAIRegistry.getSensorTypes());
	}

	public static void initBrain(Brain<AbstractElemental> pBrain) {
		//pBrain.setSchedule(MystiqueEntitySchedules.ELEMENTAL_SCHEDULE);
		initCoreActivity(pBrain);
		initIdleActivity(pBrain);
		initEatingActivity(pBrain);
		initInteractingActivity(pBrain);
		pBrain.setCoreActivities(ImmutableSet.of(Activity.CORE));
		pBrain.setDefaultActivity(Activity.IDLE);
		pBrain.useDefaultActivity();
	}

	private static void initCoreActivity(Brain<AbstractElemental> brain) {
		brain.addActivity(Activity.CORE, 0, ImmutableList.of(
				new FloatFluid(),
				new AnimalPanic(2.0f),
				new LookAtTargetSink(45, 90),
				new MoveToTargetSink(),
				new UpdateElementalActivities()
				//UpdateActivityFromSchedule.create()
				));
	}

	private static void initIdleActivity(Brain<AbstractElemental> brain) {
		brain.addActivity(Activity.IDLE, 0, ImmutableList.of(
				new RunOnePrioritized<AbstractElemental>()
				.addBehavior(SetWalkTargetAwayFrom.entity(MemoryModuleType.NEAREST_HOSTILE, 1.0f, 6, false))
				.addBehavior(new FollowTemptationWithoutCooldown(1.25f))
				.addBehavior(
						new RunOneForEach<AbstractElemental>()
						.addBehavior(RandomStroll.stroll(1.0f))
						.addBehavior(SetWalkTargetFromLookTarget.create(1.0f, 3))
						.addBehavior(new DoNothing(60, 120))
						)
				));
	}

	private static void initEatingActivity(Brain<AbstractElemental> brain) {
		brain.addActivity(MystiqueEntityActivities.EAT, 0, ImmutableList.of(
				new EatItem(),
				new ResetEatItem()
				));
	}

	private static void initInteractingActivity(Brain<AbstractElemental> brain) {
		brain.addActivity(MystiqueEntityActivities.INTERACTING, 0, ImmutableList.of(
				new FollowInteractingPlayer(1.0f),
				new ResetInteractingPlayer()
				));
	}

}
