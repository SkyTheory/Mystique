package skytheory.mystique.entity.ai.behavior.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.mojang.datafixers.util.Pair;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.GateBehavior;
import net.minecraft.world.entity.ai.behavior.GateBehavior.OrderPolicy;
import net.minecraft.world.entity.ai.behavior.GateBehavior.RunningPolicy;

public class RunOneOrdered {

	public static <T extends LivingEntity> BehaviorControl<T> create(List<BehaviorControl<? super T>> pBehaviors) {
		List<Pair<? extends BehaviorControl<? super T>, Integer>> behaviors = new ArrayList<>();
		pBehaviors.stream().forEach(b -> behaviors.add(Pair.of(b, 1)));
		return new GateBehavior<T>(Collections.emptyMap(), Collections.emptySet(), OrderPolicy.ORDERED, RunningPolicy.RUN_ONE, behaviors);
	}
	
}
