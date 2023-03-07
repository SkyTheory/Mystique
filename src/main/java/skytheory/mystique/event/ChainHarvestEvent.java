package skytheory.mystique.event;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.tags.IReverseTag;
import skytheory.lib.util.LevelUtils;
import skytheory.mystique.init.MystiqueItems;
import skytheory.mystique.savedata.ChainHarvestData;
import skytheory.mystique.savedata.ChainTarget;
import skytheory.mystique.savedata.MystiqueGlobalData;
import skytheory.mystique.savedata.PlayerDataStorage;
import skytheory.mystique.tags.MystiqueBlockTags;

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
			Block block = state.getBlock();
			Optional<IReverseTag<Block>> reverseTagOpt = ForgeRegistries.BLOCKS.tags().getReverseTag(block);
			if (reverseTagOpt.isPresent()) {
				IReverseTag<Block> reverseTag = reverseTagOpt.get();
				if (reverseTag.containsTag(BlockTags.LOGS)) {
					// TODO Logsタグを持っているブロック
				}
			}
			/**
			 *  TODO デバッグ用に樫の原木でのみ動作するように
			 *  タグによるブラックリストを作成すること
			 */
			if (state.getBlock() != Blocks.OAK_LOG) return;
			if (levelAccessor instanceof ServerLevel level) {
				ChainTarget target = new ChainTarget(level.dimension(), pos, pos, level.getBlockState(pos), 64);
				if (!ForgeRegistries.BLOCKS.tags().getTag(MystiqueBlockTags.CHAIN_HARVEST_BLACKLIST).contains(state.getBlock())) {
					LevelUtils.gatherDropsToPlayer(level, pos, state, player);
					LevelUtils.removeBlock(level, pos, state);
					Set<ChainTarget> nextTargets = getNextEntries(level, target);
					PlayerDataStorage<ChainHarvestData> data = MystiqueGlobalData.getGlobalData(level).getHarvestData();
					data.getPersonalData(player).enqueue(nextTargets);
					data.setDirty();
					event.setCanceled(true);
				}
			}
		}
	}

	@SubscribeEvent
	public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
		Player player = event.player;
		if (player.getLevel() instanceof ServerLevel level) {
			MystiqueGlobalData globalData = MystiqueGlobalData.getGlobalData(level);
			PlayerDataStorage<ChainHarvestData> harvestData = globalData.getHarvestData();
			Set<ChainTarget> nextTargets = new HashSet<>();
			Set<BlockPos> preventFall = new HashSet<>();
			for (ChainTarget target : harvestData.getPersonalData(player).dequeue()) {
				BlockPos pos = target.target();
				BlockState state = target.state();
				Block block = state.getBlock();
				if (target.isValid(level)) {
					LevelUtils.harvestBlock(level, pos, state, player);
					nextTargets.addAll(getNextEntries(level, target));
				}
				if (block instanceof FallingBlock && level.getBlockState(pos.above()).getBlock().equals(block)) {
					preventFall.add(pos.above());
				}
			}
			if (!nextTargets.isEmpty()) {
				harvestData.getPersonalData(player).enqueue(nextTargets);
			}
			harvestData.setDirty();
			for (BlockPos pos : preventFall) {
				LevelUtils.removeTick(level, pos);
			}
		}
	}

	private static Set<ChainTarget> getNextEntries(ServerLevel level, ChainTarget entry) {
		Set<BlockPos> targets = LevelUtils.collectTangentPos(entry.target());
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
