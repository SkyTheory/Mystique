package skytheory.mystique.entity.ai.behavior.interacting;

import java.util.Map;
import java.util.Optional;

import com.google.common.collect.ImmutableMap;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.EntityTracker;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.player.Player;
import skytheory.mystique.entity.AbstractElemental;
import skytheory.mystique.gui.ElementalContainerMenu;

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
		Optional<Player> playerOpt = pEntity.getInteractingPlayer();
		if (playerOpt.isEmpty()) return false;
		if (!pEntity.isAlive()) return false;
		if (pEntity.isInWater()) return false;
		if (pEntity.hurtMarked) return false;
		Player player = playerOpt.get();
		if (pEntity.distanceToSqr(player) > 64.0d) return false;
		if (pEntity.distanceToSqr(player) <= 4.0d) return false;
		return player.containerMenu instanceof ElementalContainerMenu;
	}

	protected boolean canStillUse(ServerLevel pLevel, AbstractElemental pEntity, long pGameTime) {
		return this.checkExtraStartConditions(pLevel, pEntity);
	}

	protected void tick(ServerLevel pLevel, AbstractElemental pOwner, long pGameTime) {
		Optional<Player> playerOpt = pOwner.getInteractingPlayer();
		playerOpt.ifPresent(player -> {
			Brain<?> brain = pOwner.getBrain();
			brain.setMemory(MemoryModuleType.LOOK_TARGET, new EntityTracker(player, true));
			brain.setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(new EntityTracker(player, false), this.speedModifier, 2));
		});
	}

	protected void stop(ServerLevel pLevel, AbstractElemental pEntity, long pGameTime) {
		Brain<?> brain = pEntity.getBrain();
		brain.eraseMemory(MemoryModuleType.LOOK_TARGET);
		brain.eraseMemory(MemoryModuleType.WALK_TARGET);
	}
	
}
