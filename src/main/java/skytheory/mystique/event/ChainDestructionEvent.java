package skytheory.mystique.event;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.network.PacketDistributor;
import skytheory.mystique.init.MystiqueItems;
import skytheory.mystique.network.BreakEffectMessage;
import skytheory.mystique.network.MystiqueNetwork;
import skytheory.mystique.savedata.ChainDestructionData;
import skytheory.mystique.savedata.MystiqueLevelData;

public class ChainDestructionEvent {

	public static final int MAX_DISTANCE = 64;

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
				next(level, player.getUUID(), state, pos, pos);
				gatherDrops(level, pos, state, player);
			}
			event.setCanceled(true);
		}
	}

	@SubscribeEvent
	public static void onTick(TickEvent.LevelTickEvent event) {
		if (event.level instanceof ServerLevel level) {
			Set<ChainDestructionData.TargetEntry> entries = MystiqueLevelData.getChainDestructionData(level).pop();
			for (ChainDestructionData.TargetEntry entry : entries) {
				UUID uuid = entry.uuid;
				Player player = uuid != null ? level.getPlayerByUUID(uuid) : null;
				BlockState state = entry.state;
				BlockPos pos = entry.pos;
				BlockPos origin = entry.origin;
				if (level.isLoaded(pos) && isInRange(pos, origin)) {
					BlockState s = level.getBlockState(pos);
					if (state.getBlock() == s.getBlock()) {
						next(level, uuid, state, pos, origin);
						gatherDrops(level, pos, state, player);
					}
					MystiqueNetwork.INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(() -> level.getChunkAt(pos)), new BreakEffectMessage(event.level, pos, state));
				}
			}
		}
	}

	private static void next(ServerLevel level, UUID uuid, BlockState state, BlockPos pos, BlockPos origin) {
		Set<BlockPos> targets = getAdjacentPosIncludeDiagonal(pos);
		for (BlockPos target : targets) {
			if (level.isLoaded(target) && level.getBlockState(target).getBlock() == state.getBlock()) {
				MystiqueLevelData.getChainDestructionData(level).push(uuid, state, target, origin);
			}
		}
	}

	private static Set<BlockPos> getAdjacentPosIncludeDiagonal(BlockPos pos) {
		Set<BlockPos> result = new HashSet<>();
		for (int x = -1; x <= 1; x++) {
			for (int y = -1; y <= 1; y++) {
				for (int z = -1; z <= 1; z++) {
					if (x != 0 || y != 0 || z != 0 && Math.abs(x) + Math.abs(y) + Math.abs(z) < 3) {
						result.add(pos.offset(x, y, z));
					}
				}
			}
		}
		return result;
	}

	private static boolean isInRange(BlockPos target, BlockPos origin) {
		return target.distManhattan(origin) <= MAX_DISTANCE;
	}

	private static void gatherDrops(ServerLevel level, BlockPos pos, BlockState state, Player player) {
		List<ItemStack> drops = Block.getDrops(state, level, pos, null);
		for (ItemStack stack : drops)  {
			if (player != null) {
				if (!player.isCreative()) ItemHandlerHelper.giveItemToPlayer(player, stack);
			} else {
				popItemEntityWithModulate(level, Vec3.atCenterOf(pos), stack, true);
			}
			SoundType soundtype = state.getSoundType(level, pos, null);
			level.playSound(null, pos, soundtype.getBreakSound(), SoundSource.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
			level.removeBlock(pos, false);
		}
	}

	private static void popItemEntityWithModulate(ServerLevel level, Vec3 pos, ItemStack stack, boolean setPickUpDelay) {
		double hMod = EntityType.ITEM.getHeight() / 2.0d;
		double x = pos.x() + Mth.nextDouble(level.random, -0.25d, 0.25d);
		double y = pos.y() + Mth.nextDouble(level.random, -0.25d, 0.25d) - hMod;
		double z = pos.z() + Mth.nextDouble(level.random, -0.25d, 0.25d);
		double xMod = Mth.nextDouble(level.getRandom(), -0.1d, 0.1d);
		double yMod = Mth.nextDouble(level.getRandom(), 0.0d, 0.1d);
		double zMod = Mth.nextDouble(level.getRandom(), -0.1d, 0.1d);
		ItemEntity itemEntity = new ItemEntity(level, x, y, z, stack, xMod, yMod, zMod);
		itemEntity.setDefaultPickUpDelay();
		if (setPickUpDelay) itemEntity.setDefaultPickUpDelay();
		level.addFreshEntity(itemEntity);
	}

}
