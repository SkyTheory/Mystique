package skytheory.mystique.entity.ai.behavior.common;

import java.util.Map;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.EntityTracker;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.player.Player;

public class MoveToNearestPlayer extends Behavior<PathfinderMob> {

	private final float speedModifier;
	private final double startDist;
	private final double reachDist;

	public MoveToNearestPlayer(float speedModifier, double startDist, double reachDist) {
		super(Map.ofEntries(
				Map.entry(MemoryModuleType.NEAREST_VISIBLE_PLAYER, MemoryStatus.VALUE_PRESENT),
				Map.entry(MemoryModuleType.IS_PANICKING, MemoryStatus.VALUE_ABSENT)
				));
		this.speedModifier = speedModifier;
		this.startDist = startDist;
		this.reachDist = reachDist;
	}

	@Override
	protected boolean checkExtraStartConditions(ServerLevel pLevel, PathfinderMob pOwner) {
		return pOwner.getBrain().getMemory(MemoryModuleType.NEAREST_VISIBLE_PLAYER)
				.map(player -> pOwner.closerThan(player, startDist) && !pOwner.closerThan(player, reachDist)).orElse(false);
	}

	@Override
	protected boolean canStillUse(ServerLevel pLevel, PathfinderMob pEntity, long pGameTime) {
		return checkExtraStartConditions(pLevel, pEntity);
	}

	@Override
	protected void tick(ServerLevel pLevel, PathfinderMob pOwner, long pGameTime) {
		Brain<?> brain = pOwner.getBrain();
		Player player = brain.getMemory(MemoryModuleType.NEAREST_VISIBLE_PLAYER).get();
		brain.setMemory(MemoryModuleType.LOOK_TARGET, new EntityTracker(player, true));
		brain.setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(new EntityTracker(player, false), speedModifier, 0));
	}
	
	@Override
	protected void stop(ServerLevel pLevel, PathfinderMob pEntity, long pGameTime) {
		Brain<?> brain = pEntity.getBrain();
		brain.eraseMemory(MemoryModuleType.LOOK_TARGET);
		brain.eraseMemory(MemoryModuleType.WALK_TARGET);
	}

}
