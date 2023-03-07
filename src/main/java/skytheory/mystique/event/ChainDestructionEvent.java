package skytheory.mystique.event;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.function.ToIntFunction;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistries;
import skytheory.lib.util.LevelUtils;
import skytheory.mystique.savedata.ChainDestructionData;
import skytheory.mystique.savedata.ChainTarget;
import skytheory.mystique.savedata.MystiqueLevelData;
import skytheory.mystique.tags.MystiqueBlockTags;

public class ChainDestructionEvent {

	public static final int MAX_DISTANCE = 64;

	public static final ToIntFunction<ChainTarget> DIST_COMPARATOR = (t) -> (t.origin().distManhattan(t.target()));
	public static final ToIntFunction<ChainTarget> X_COMPARATOR = (t) -> (t.target().getX());
	public static final ToIntFunction<ChainTarget> Y_COMPARATOR = (t) -> (t.target().getY());
	public static final ToIntFunction<ChainTarget> Z_COMPARATOR = (t) -> (t.target().getZ());
	public static final Comparator<ChainTarget> COMPARATOR = Comparator.comparingInt(DIST_COMPARATOR)
			.thenComparingInt(X_COMPARATOR)
			.thenComparingInt(Y_COMPARATOR)
			.thenComparingInt(Z_COMPARATOR);

	public static void trigger(ServerLevel level, BlockPos origin, int maxBlocks, int maxDist) {
		Set<ChainTarget> targets = new HashSet<>();
		Set<ChainTarget> nextTargets = new HashSet<>();
		BlockState state = level.getBlockState(origin);
		if (!ForgeRegistries.BLOCKS.tags().getTag(MystiqueBlockTags.TINY_TNT_BLACKLIST).contains(state.getBlock())) {
			targets.add(new ChainTarget(level.dimension(), origin, origin, state, maxDist));
			nextTargets.add(new ChainTarget(level.dimension(), origin, origin, state, maxDist));
			while (!nextTargets.isEmpty() && targets.size() < maxBlocks) {
				var checkSet = Set.copyOf(nextTargets);
				nextTargets.clear();
				for (var checkTarget : checkSet) {
					Set<BlockPos> posSet = LevelUtils.collectTangentPos(checkTarget.target());
					for (var pos : posSet) {
						ChainTarget target = new ChainTarget(level.dimension(), pos, origin, state, maxDist);
						if (target.isValid(level) && targets.add(target)) {
							nextTargets.add(target);
						}
					}
				}
			}
			var sorted = targets.stream()
					.sorted(COMPARATOR)
					.limit(maxBlocks)
					.toList();
			MystiqueLevelData.getLevelData(level).getChainDestructionData().enqueue(sorted);
		}
	}

	@SubscribeEvent
	public static void onTick(TickEvent.LevelTickEvent event) {
		if (event.level instanceof ServerLevel level) {
			ChainDestructionData data = MystiqueLevelData.getChainDestructionData(level);
			// Tick内でのターゲットを取得し、破壊する
			Set<BlockPos> preventFall = new HashSet<>();
			for (ChainTarget entry : data.dequeue()) {
				if (entry.isPosLoaded(level.getServer())) {
					if (entry.isValid(level)) {
						BlockPos pos = entry.target();
						BlockState state = entry.state();
						LevelUtils.harvestBlock(level, pos, state);
						Block block = state.getBlock();
						if (block instanceof FallingBlock && level.getBlockState(pos.above()).getBlock().equals(block)) {
							preventFall.add(pos.above());
						}
					}
				}
			}
			// FallingBlockの落下処理を無効化しておく
			for (BlockPos pos : preventFall) {
				LevelUtils.removeTick(level, pos);
			}
		}
	}

}
