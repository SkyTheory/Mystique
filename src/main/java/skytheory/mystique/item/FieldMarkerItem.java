package skytheory.mystique.item;

import java.util.List;

import org.jetbrains.annotations.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SupportType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.LeftClickBlock;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.LeftClickEmpty;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickEmpty;
import skytheory.lib.item.InteractiveItem;
import skytheory.lib.util.BlockSide;
import skytheory.mystique.entity.FieldMarker;
import skytheory.mystique.init.MystiqueEntityTypes;

public class FieldMarkerItem extends Item implements InteractiveItem {

	public FieldMarkerItem(Properties pProperties) {
		super(pProperties);
	}

	@Override
	public void onLeftClickBlock(LeftClickBlock event, Player player, Level level, InteractionHand hand, BlockPos pos,
			@Nullable Direction face, ItemStack stack) {
		if (face == null) {
			return;
		}
		List<FieldMarker> markers = level.getEntitiesOfClass(FieldMarker.class, new AABB(pos.relative(face)));
		markers = markers.stream().filter(e -> e.getRotation().getDirection(BlockSide.BACK) == face).toList();
		if (!markers.isEmpty()) {
			markers.forEach(marker -> marker.playDiscardSound());
			markers.forEach(marker -> marker.discard());
			event.setCanceled(true);
		}
	}

	@Override
	public void onRightClickBlock(RightClickBlock event, Player player, Level level, InteractionHand hand, BlockPos pos,
			@Nullable Direction face, ItemStack stack) {
		BlockPos placePos = pos.relative(face);
		BlockPos supportPos = placePos.relative(face.getOpposite());
		BlockState state = level.getBlockState(supportPos);
		if (player != null && !player.mayUseItemAt(pos, face, stack)) {
			return;
		}
		if (level.isOutsideBuildHeight(placePos)) {
			return;
		}
		if (!level.getBlockState(placePos).isAir()) {
			return;
		}
		if (!level.getEntitiesOfClass(FieldMarker.class, new AABB(placePos), (e -> e.getRotation().getDirection(BlockSide.BACK) == face)).isEmpty()) {
			return;
		}
		if (!state.isFaceSturdy(level, supportPos, face, SupportType.CENTER)) {
			return;
		}
		double x = placePos.getX() + 0.5d;
		double y = placePos.getY() + 0.5d;
		double z = placePos.getZ() + 0.5d;
		float volume = 1.0f;
		float pitch = 2.0f;
		level.playSound(player, x, y, z, SoundEvents.STONE_PLACE, SoundSource.NEUTRAL, volume, pitch);
		if (!level.isClientSide()) {
			FieldMarker marker = new FieldMarker(MystiqueEntityTypes.FIELD_MARKER, level);
			marker.setPos(x, placePos.getY(), z);
			marker.setRotation(face.getOpposite());
			level.gameEvent(player, GameEvent.ENTITY_PLACE, marker.position());
			level.addFreshEntity(marker);
		}
		player.swing(InteractionHand.MAIN_HAND);
		event.setCanceled(true);
	}

	@Override
	public void onLeftClickEmpty(LeftClickEmpty event, Player player, Level level, InteractionHand hand,
			ItemStack stack) {
	}

	@Override
	public void onRightClickEmpty(RightClickEmpty event, Player player, Level level, InteractionHand hand,
			ItemStack stack) {
	}

}
