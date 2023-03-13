package skytheory.mystique.entity.ai.behavior.idle;

import net.minecraft.world.entity.ai.behavior.DoNothing;
import net.minecraft.world.entity.ai.behavior.RandomStroll;
import net.minecraft.world.entity.ai.behavior.SetWalkTargetFromLookTarget;
import skytheory.lib.entity.ai.behavior.RunOneForEach;
import skytheory.mystique.entity.AbstractElemental;

public class IdleWalkingBehavior extends RunOneForEach<AbstractElemental> {

	public IdleWalkingBehavior() {
		this.addBehavior(RandomStroll.stroll(1.0f));
		this.addBehavior(SetWalkTargetFromLookTarget.create(1.0f, 3));
		this.addBehavior(new DoNothing(60, 120));
	}

}
