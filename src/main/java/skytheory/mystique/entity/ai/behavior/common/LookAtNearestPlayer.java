package skytheory.mystique.entity.ai.behavior.common;

import java.util.Map;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.EntityTracker;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.player.Player;
import skytheory.mystique.init.MystiqueMemoryModuleTypes;

public class LookAtNearestPlayer extends Behavior<LivingEntity> {

	public final int cooldown;
	public final double range;

	public LookAtNearestPlayer() {
		this(100, 200, 100, 4.0d);
	}

	public LookAtNearestPlayer(int minDuration, int maxDuration, int cooldown, double range) {
		super(Map.ofEntries(
				Map.entry(MemoryModuleType.NEAREST_VISIBLE_PLAYER, MemoryStatus.VALUE_PRESENT),
				Map.entry(MemoryModuleType.IS_PANICKING, MemoryStatus.VALUE_ABSENT),
				Map.entry(MystiqueMemoryModuleTypes.COMMON_LOOK_COOLDOWN, MemoryStatus.VALUE_ABSENT)
				), minDuration, maxDuration);
		this.cooldown = cooldown;
		this.range = range;
	}
	
	@Override
	protected boolean checkExtraStartConditions(ServerLevel pLevel, LivingEntity pOwner) {
		return pOwner.getBrain().getMemory(MemoryModuleType.NEAREST_VISIBLE_PLAYER).get().closerThan(pOwner, range);
	}
	
	@Override
	protected void start(ServerLevel pLevel, LivingEntity pEntity, long pGameTime) {
		super.start(pLevel, pEntity, pGameTime);
	}
	
	@Override
	protected boolean canStillUse(ServerLevel pLevel, LivingEntity pEntity, long pGameTime) {
		return true;
	}
	
	@Override
	protected void tick(ServerLevel pLevel, LivingEntity pOwner, long pGameTime) {
		Player player = pOwner.getBrain().getMemory(MemoryModuleType.NEAREST_VISIBLE_PLAYER).get();
		pOwner.getBrain().setMemory(MemoryModuleType.LOOK_TARGET, new EntityTracker(player, true));
	}
	
	@Override
	protected void stop(ServerLevel pLevel, LivingEntity pEntity, long pGameTime) {
		pEntity.getBrain().eraseMemory((MemoryModuleType.LOOK_TARGET));
		if (cooldown > 0) {
			pEntity.getBrain().setMemory(MystiqueMemoryModuleTypes.COMMON_LOOK_COOLDOWN, cooldown);
		}
	}

}
