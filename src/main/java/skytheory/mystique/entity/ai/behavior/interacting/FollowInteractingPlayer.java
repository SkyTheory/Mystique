package skytheory.mystique.entity.ai.behavior.interacting;

import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.mojang.logging.LogUtils;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.EntityTracker;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.player.Player;
import skytheory.mystique.container.ElementalContainerMenu;
import skytheory.mystique.entity.AbstractElemental;

public class FollowInteractingPlayer extends Behavior<AbstractElemental> {
	private final float speedModifier;

	public FollowInteractingPlayer(float pSpeedModifier) {
		super(ImmutableMap.ofEntries(
				Map.entry(MemoryModuleType.LOOK_TARGET, MemoryStatus.REGISTERED),
				Map.entry(MemoryModuleType.WALK_TARGET, MemoryStatus.REGISTERED)
				), Integer.MAX_VALUE);
		this.speedModifier = pSpeedModifier;
	}

	protected boolean checkExtraStartConditions(ServerLevel pLevel, AbstractElemental pEntity) {
		Player player = pEntity.getInteractingPlayer();
		if (player == null) return false;
		if (!pEntity.isAlive()) return false;
		if (pEntity.isInWater()) return false;
		if (pEntity.hurtMarked) return false;
		if (pEntity.distanceToSqr(player) > 64.0d) return false;
		if (pEntity.distanceToSqr(player) <= 4.0d) return false;
		return player.containerMenu instanceof ElementalContainerMenu;
	}

	protected boolean canStillUse(ServerLevel pLevel, AbstractElemental pEntity, long pGameTime) {
		return this.checkExtraStartConditions(pLevel, pEntity);
	}

	protected void start(ServerLevel pLevel, AbstractElemental pEntity, long pGameTime) {
		this.followPlayer(pEntity);
	}

	protected void stop(ServerLevel pLevel, AbstractElemental pEntity, long pGameTime) {
		Brain<?> brain = pEntity.getBrain();
		brain.eraseMemory(MemoryModuleType.WALK_TARGET);
		LogUtils.getLogger().info("Behaviour Stopped!");
	}

	protected void tick(ServerLevel pLevel, AbstractElemental pOwner, long pGameTime) {
	}

	protected boolean timedOut(long pGameTime) {
		return false;
	}

	private void followPlayer(AbstractElemental pEntity) {
		Brain<?> brain = pEntity.getBrain();
		brain.setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(new EntityTracker(pEntity.getInteractingPlayer(), false), this.speedModifier, 2));
		brain.setMemory(MemoryModuleType.LOOK_TARGET, new EntityTracker(pEntity.getInteractingPlayer(), true));
	}
}
