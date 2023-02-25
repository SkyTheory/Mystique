package skytheory.mystique.entity.ai;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;

import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.Brain.Provider;
import net.minecraft.world.entity.ai.behavior.AnimalPanic;
import net.minecraft.world.entity.ai.behavior.DoNothing;
import net.minecraft.world.entity.ai.behavior.LookAtTargetSink;
import net.minecraft.world.entity.ai.behavior.MoveToTargetSink;
import net.minecraft.world.entity.ai.behavior.RandomStroll;
import net.minecraft.world.entity.ai.behavior.RunOne;
import net.minecraft.world.entity.ai.behavior.SetWalkTargetAwayFrom;
import net.minecraft.world.entity.ai.behavior.SetWalkTargetFromLookTarget;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.schedule.Activity;
import skytheory.mystique.entity.AbstractElemental;
import skytheory.mystique.entity.ai.behavior.core.FloatFluid;
import skytheory.mystique.entity.ai.behavior.core.FollowTemptationWithoutCooldown;
import skytheory.mystique.entity.ai.behavior.core.UpdateElementalActivities;
import skytheory.mystique.entity.ai.behavior.eat.EatItem;
import skytheory.mystique.entity.ai.behavior.eat.ResetEatItem;
import skytheory.mystique.init.ElementalAIRegistry;
import skytheory.mystique.init.MystiqueEntityActivities;

public class ElementalAI {

	public static final List<Activity> HOLDING_ACTIVITIES = ImmutableList.of(
			MystiqueEntityActivities.EAT
			);

	public static Provider<AbstractElemental> brainProvider() {
		return Brain.provider(ElementalAIRegistry.getMemoryTypes(), ElementalAIRegistry.getSensorTypes());
	}

	public static Brain<AbstractElemental> makeBrain(Brain<AbstractElemental> pBrain) {
		//pBrain.setSchedule(MystiqueEntitySchedules.ELEMENTAL_SCHEDULE);
		initCoreActivity(pBrain);
		initIdleActivity(pBrain);
		initEatingActivity(pBrain);
		pBrain.setCoreActivities(ImmutableSet.of(Activity.CORE));
		pBrain.setDefaultActivity(Activity.IDLE);
		pBrain.useDefaultActivity();
		return pBrain;
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
				new FollowTemptationWithoutCooldown(1.25f),
				SetWalkTargetAwayFrom.entity(MemoryModuleType.NEAREST_HOSTILE, 1.0f, 6, false),
				new RunOne<AbstractElemental>(ImmutableList.of(
						Pair.of(RandomStroll.stroll(1.0f), 2),
						Pair.of(SetWalkTargetFromLookTarget.create(1.0f, 3), 2),
						Pair.of(new DoNothing(30, 60), 1)))
				));
	}

	private static void initEatingActivity(Brain<AbstractElemental> brain) {
		brain.addActivity(MystiqueEntityActivities.EAT, 0, ImmutableList.of(
				new EatItem(),
				new ResetEatItem()
				));
	}

}
