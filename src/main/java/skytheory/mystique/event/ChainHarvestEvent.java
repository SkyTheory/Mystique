package skytheory.mystique.event;

import java.util.HashSet;
import java.util.Set;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import skytheory.lib.util.LevelUtils;
import skytheory.mystique.init.MystiqueItems;
import skytheory.mystique.savedata.ChainHarvestData;
import skytheory.mystique.savedata.ChainTarget;
import skytheory.mystique.savedata.MystiqueGlobalData;
import skytheory.mystique.savedata.PlayerDataStorage;

public class ChainHarvestEvent {

	public static final int MAX_DIST_MANHATTAN = 16;
	
	@SubscribeEvent
	public static void trigger(BlockEvent.BreakEvent event) {
		Player player = event.getPlayer();
		LevelAccessor levelAccessor = event.getLevel();
		ItemStack tool = player.getMainHandItem();
		// TODO デバッグ用にクリエイティブキャパシタでのみ動作するように
		if (tool.getItem() == MystiqueItems.MANA_CAPACITOR_CREATIVE) {
			BlockPos pos = event.getPos();
			BlockState state = levelAccessor.getBlockState(pos);
			/**
			 *  TODO デバッグ用に樫の原木でのみ動作するように
			 *  タグによるブラックリストを作成すること
			 */
			if (state.getBlock() != Blocks.OAK_LOG) return;
			if (levelAccessor instanceof ServerLevel level) {
				ChainTarget target = new ChainTarget(level.dimension(), pos, pos, level.getBlockState(pos), 64);
				LevelUtils.gatherDropsToPlayer(level, pos, state, player);
				LevelUtils.removeBlock(level, pos, state);
				Set<ChainTarget> nextTargets = getNextEntries(level, target);
				PlayerDataStorage<ChainHarvestData> data = MystiqueGlobalData.getGlobalData(level).getHarvestData();
				data.getPersonalData(player).push(nextTargets);
				data.setDirty();
			}
			event.setCanceled(true);
		}
	}

	@SubscribeEvent
	public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
		Player player = event.player;
		if (player.getLevel() instanceof ServerLevel level) {
			MystiqueGlobalData globalData = MystiqueGlobalData.getGlobalData(level);
			PlayerDataStorage<ChainHarvestData> harvestData = globalData.getHarvestData();
			Set<ChainTarget> nextTargets = new HashSet<>();
			for (ChainTarget target : harvestData.getPersonalData(player).poll()) {
				if (target.isValid(level)) {
					LevelUtils.harvestBlock(level, target.target(), target.state(), player);
					nextTargets.addAll(getNextEntries(level, target));
				}
			}
			if (!nextTargets.isEmpty()) {
				harvestData.getPersonalData(player).push(nextTargets);
			}
			harvestData.setDirty();
		}
	}
	
	private static Set<ChainTarget> getNextEntries(ServerLevel level, ChainTarget entry) {
		Set<BlockPos> targets = LevelUtils.getAdjacentPosIncludeDiagonal(entry.target());
		Set<ChainTarget> nextTargets = new HashSet<>();
		for (BlockPos target : targets) {
			ChainTarget next = new ChainTarget(level.dimension(), target, entry.origin(), entry.state(), entry.maxDistance());
			if (next.isValid(level)) {
				nextTargets.add(next);
			}
		}
		return nextTargets;
	}

}
