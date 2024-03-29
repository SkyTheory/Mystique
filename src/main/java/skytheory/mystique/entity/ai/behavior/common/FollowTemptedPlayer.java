package skytheory.mystique.entity.ai.behavior.common;

import java.util.Map;
import java.util.Optional;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.EntityTracker;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.player.Player;

public class FollowTemptedPlayer extends Behavior<PathfinderMob> {

	public final int cooldown;
	public final float speedModifier;
	
	public FollowTemptedPlayer() {
		this(0, 1.0f);
	}
	
	public FollowTemptedPlayer(float speedModifier) {
		this(0, speedModifier);
	}
	
	public FollowTemptedPlayer(int cooldown, float speedModifier) {
		super(Map.ofEntries(
				Map.entry(MemoryModuleType.LOOK_TARGET, MemoryStatus.REGISTERED),
				Map.entry(MemoryModuleType.WALK_TARGET, MemoryStatus.REGISTERED),
				Map.entry(MemoryModuleType.IS_TEMPTED, MemoryStatus.REGISTERED),
				Map.entry(MemoryModuleType.TEMPTING_PLAYER, MemoryStatus.VALUE_PRESENT),
				Map.entry(MemoryModuleType.TEMPTATION_COOLDOWN_TICKS, MemoryStatus.VALUE_ABSENT),
				Map.entry(MemoryModuleType.IS_PANICKING, MemoryStatus.VALUE_ABSENT)
				));
		this.cooldown = cooldown;
		this.speedModifier = speedModifier;
	}

	private Optional<Player> getTemptingPlayer(PathfinderMob pPathfinder) {
		return pPathfinder.getBrain().getMemory(MemoryModuleType.TEMPTING_PLAYER);
	}

	protected boolean timedOut(long pGameTime) {
		return false;
	}

	protected boolean canStillUse(ServerLevel pLevel, PathfinderMob pEntity, long pGameTime) {
		return this.getTemptingPlayer(pEntity).isPresent() && !pEntity.getBrain().hasMemoryValue(MemoryModuleType.IS_PANICKING);
	}

	protected void start(ServerLevel pLevel, PathfinderMob pEntity, long pGameTime) {
		pEntity.getBrain().setMemory(MemoryModuleType.IS_TEMPTED, true);
	}

	protected void stop(ServerLevel pLevel, PathfinderMob pEntity, long pGameTime) {
		Brain<?> brain = pEntity.getBrain();
		brain.setMemory(MemoryModuleType.IS_TEMPTED, false);
		if (this.cooldown > 0) {
			brain.setMemory(MemoryModuleType.TEMPTATION_COOLDOWN_TICKS, cooldown);
		}
		brain.eraseMemory(MemoryModuleType.WALK_TARGET);
		brain.eraseMemory(MemoryModuleType.LOOK_TARGET);
	}

	protected void tick(ServerLevel pLevel, PathfinderMob pOwner, long pGameTime) {
		Player player = this.getTemptingPlayer(pOwner).get();
		Brain<?> brain = pOwner.getBrain();
		brain.setMemory(MemoryModuleType.LOOK_TARGET, new EntityTracker(player, true));
		if (pOwner.distanceToSqr(player) < 6.25D) {
			brain.eraseMemory(MemoryModuleType.WALK_TARGET);
		} else {
			brain.setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(new EntityTracker(player, false), speedModifier, 2));
		}

	}
}
