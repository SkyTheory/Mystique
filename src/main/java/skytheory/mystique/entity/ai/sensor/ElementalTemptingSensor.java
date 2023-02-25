package skytheory.mystique.entity.ai.sensor;

import java.util.Comparator;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import skytheory.mystique.entity.AbstractElemental;

public class ElementalTemptingSensor extends Sensor<AbstractElemental> {

	protected void doTick(ServerLevel pLevel, AbstractElemental pEntity) {
		Brain<?> brain = pEntity.getBrain();
		Optional<ServerPlayer> playerOpt = pLevel.players().stream()
				.filter(EntitySelector.NO_SPECTATORS)
				.filter(player -> pEntity.closerThan(player, 10.0d))
				.filter(this.playerTargetable(pEntity))
				.filter(this.playerNotPassenger(pEntity))
				.filter(this.playerHasTemptationItem(pEntity))
				.sorted(Comparator.comparingDouble(pEntity::distanceToSqr))
				.findFirst();
		playerOpt.ifPresentOrElse(
				player -> brain.setMemory(MemoryModuleType.TEMPTING_PLAYER, player),
				() -> brain.eraseMemory(MemoryModuleType.TEMPTING_PLAYER)
				);
	}
	
	protected Predicate<Player> playerTargetable(AbstractElemental entity) {
		return player -> TargetingConditions.forNonCombat().range(10.0d).ignoreLineOfSight().test(entity, player);
	}
	
	protected Predicate<Player> playerNotPassenger(AbstractElemental entity) {
		return player -> !entity.hasPassenger(player);
	}
	
	private Predicate<Player> playerHasTemptationItem(AbstractElemental entity) {
		return player -> entity.isTemptingItem(player.getMainHandItem());
	}

	public Set<MemoryModuleType<?>> requires() {
		return Set.of(MemoryModuleType.TEMPTING_PLAYER);
	}
}
