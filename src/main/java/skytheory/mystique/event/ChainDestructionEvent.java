package skytheory.mystique.event;

import java.util.HashSet;
import java.util.Set;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import skytheory.lib.util.LevelUtils;
import skytheory.mystique.savedata.ChainTarget;
import skytheory.mystique.savedata.MystiqueLevelData;

public class ChainDestructionEvent {

	public static final int MAX_DISTANCE = 64;
	public static final int MAX_ITERATE = 16;

	@SubscribeEvent
	public static void onTick(TickEvent.LevelTickEvent event) {
		if (event.level instanceof ServerLevel level) {
			var entries = MystiqueLevelData.getChainDestructionData(level).poll();
			Set<ChainTarget> nextTargets = new HashSet<>();
			for (ChainTarget entry : entries) {
				if (entry.isLoaded(level.getServer())) {
					if (entry.isValid(level)) {
						BlockPos pos = entry.target();
						BlockState state = entry.state();
						LevelUtils.harvestBlock(level, pos, state);
						nextTargets.addAll(getNextEntries(level, entry));
					}
				}
			}
			if (!nextTargets.isEmpty()) {
				MystiqueLevelData.getChainDestructionData(level).push(nextTargets);
			}
		}
	}

	private static Set<ChainTarget> getNextEntries(ServerLevel level, ChainTarget entry) {
		Set<BlockPos> targets = LevelUtils.getAdjacentPosIncludeDiagonal(entry.target());
		Set<ChainTarget> nextTargets = new HashSet<>();
		for (BlockPos target : targets) {
			ChainTarget next = new ChainTarget(entry.level(), target, entry.origin(), entry.state(), entry.maxDistance());
			if (next.isValid(level)) {
				nextTargets.add(next);
			}
		}
		return nextTargets;
	}

}
