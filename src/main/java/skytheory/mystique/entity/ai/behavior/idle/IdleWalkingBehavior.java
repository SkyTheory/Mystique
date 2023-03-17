package skytheory.mystique.entity.ai.behavior.idle;

import net.minecraft.world.entity.ai.behavior.DoNothing;
import net.minecraft.world.entity.ai.behavior.RandomStroll;
import net.minecraft.world.entity.ai.behavior.SetWalkTargetFromLookTarget;
import skytheory.lib.entity.ai.behavior.RunOneForEach;
import skytheory.lib.entity.ai.behavior.RunOnePrioritized;
import skytheory.mystique.entity.AbstractElemental;
import skytheory.mystique.entity.ai.behavior.common.LookAtNearestPlayer;

public class IdleWalkingBehavior extends RunOnePrioritized<AbstractElemental> {

	public IdleWalkingBehavior() {
		this.addBehavior(new LookAtNearestPlayer());
		this.addBehavior(new RunOneForEach<AbstractElemental>()
				.addBehavior(RandomStroll.stroll(1.0f))
				.addBehavior(SetWalkTargetFromLookTarget.create(1.0f, 3))
				.addBehavior(new DoNothing(120, 240))
				);
	}

}
