package skytheory.mystique.item;

import org.jetbrains.annotations.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.LeftClickBlock;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.LeftClickEmpty;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickEmpty;
import skytheory.lib.item.InteractiveItem;
import skytheory.lib.util.BlockRotation;
import skytheory.lib.util.BlockSide;
import skytheory.mystique.entity.AbstractCoordinatedEntity;

public abstract class AbstractCoordinatedEntityPlacerItem<T extends AbstractCoordinatedEntity> extends Item implements InteractiveItem {

	public AbstractCoordinatedEntityPlacerItem(Properties pProperties) {
		super(pProperties);
	}

	public boolean canAttackBlock(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer) {
		return !pPlayer.isCreative();
	}

	@Override
	public void onLeftClickBlock(LeftClickBlock event, Player player, Level level, InteractionHand hand, BlockPos pos,
			@Nullable Direction face, ItemStack stack) {
	}

	@Override
	public void onRightClickBlock(RightClickBlock event, Player player, Level level, InteractionHand hand, BlockPos pos,
			@Nullable Direction face, ItemStack stack) {
		BlockPos placePos = pos.relative(face);
		BlockPos supportPos = pos;
		if (checkCondition(level, player, placePos, supportPos, face, stack)) {
			placeEntity(event, player, level, placePos, face);
			finishUse(event, player, level, hand, pos, face, stack);
			event.setCanceled(true);
		} else if (checkCondition(level, player, placePos.relative(face.getOpposite()), supportPos.relative(face.getOpposite()), face, stack)) {
			placeEntity(event, player, level, placePos.relative(face.getOpposite()), face);
			finishUse(event, player, level, hand, pos, face, stack);
			event.setCanceled(true);
		}
	}

	public boolean checkCondition(Level level, Player player, BlockPos placePos, BlockPos supportPos, Direction face, ItemStack stack) {
		if (player != null && !player.mayUseItemAt(supportPos, face, stack)) {
			return false;
		}
		if (level.isOutsideBuildHeight(placePos)) {
			return false;
		}
		if (!level.getBlockState(placePos).canBeReplaced()) {
			return false;
		}
		if (!level.getEntities(getEntityType(), new AABB(placePos), e -> e.getRotation().getDirection(BlockSide.BACK) == face).isEmpty()) {
			return false;
		}
		return true;
	}
	
	public void placeEntity(RightClickBlock event, Player player, Level level, BlockPos pos, Direction face) {
		double x = pos.getX() + 0.5d;
		double y = pos.getY() + 0.5d;
		double z = pos.getZ() + 0.5d;
		if (!level.isClientSide()) {
			T entity = getEntityType().create(level);
			entity.setPos(x, pos.getY(), z);
			BlockRotation rotation = getRotation(event, level, face);
			entity.setRotation(rotation);
			level.gameEvent(player, GameEvent.ENTITY_PLACE, entity.position());
			level.addFreshEntity(entity);
		} else {
			playPlaceSound(level, player, x, y, z);
		}
		player.swing(InteractionHand.MAIN_HAND);
	}

	protected void finishUse(RightClickBlock event, Player player, Level level, InteractionHand hand, BlockPos pos,
			@Nullable Direction face, ItemStack stack) {
		if (!player.isCreative()) {
			stack.shrink(1);
			if (stack.isEmpty()) player.setItemInHand(hand, ItemStack.EMPTY);
		}
	}

	public BlockRotation getRotation(RightClickBlock event, Level level, @Nullable Direction face) {
		return BlockRotation.fromDirection(face.getOpposite());
	}

	protected abstract void playPlaceSound(Level level, Player player, double x, double y, double z);

	protected abstract EntityType<T> getEntityType();

	@Override
	public void onLeftClickEmpty(LeftClickEmpty event, Player player, Level level, InteractionHand hand,
			ItemStack stack) {
	}

	@Override
	public void onRightClickEmpty(RightClickEmpty event, Player player, Level level, InteractionHand hand,
			ItemStack stack) {
	}

}
