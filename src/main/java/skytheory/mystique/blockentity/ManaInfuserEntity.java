package skytheory.mystique.blockentity;

import org.jetbrains.annotations.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.items.IItemHandler;
import skytheory.lib.block.RightClickReceiverBlockEntity;
import skytheory.lib.block.TickerBlockEntity;
import skytheory.lib.capability.itemhandler.InventoryHandler;
import skytheory.lib.capability.itemhandler.InventoryHolder;
import skytheory.lib.util.ItemHandlerMode;
import skytheory.lib.util.ItemHandlerUtils;
import skytheory.mystique.init.MystiqueBlockEntities;

// TODO Mana関連の処理
public class ManaInfuserEntity extends BlockEntity implements TickerBlockEntity, RightClickReceiverBlockEntity, InventoryHolder<CompoundTag> {

	public final InventoryHandler handler;
	
	public ManaInfuserEntity(BlockPos pPos, BlockState pBlockState) {
		super(MystiqueBlockEntities.MANA_INFUSER, pPos, pBlockState);
		this.handler = new InventoryHandler(1);
	}

	public boolean isOn() {
		return true;
	}

	public int getTicks() {
		return 0;
	}

	@Override
	public void tick() {
	}

	@Override
	public void onRightClicked(RightClickBlock event, Player player, InteractionHand hand, @Nullable Direction face, ItemStack stack) {
		event.setCanceled(true);
		if (hand != InteractionHand.MAIN_HAND) return;
		if (ItemHandlerUtils.isEmpty(handler)) {
			ItemStack handheld = ItemHandlerUtils.takeItemFromPlayer(player, InteractionHand.MAIN_HAND, 64, ItemHandlerMode.SIMULATE);
			if (!handheld.isEmpty()) {
				ItemStack remainder = ItemHandlerUtils.insertItem(handler, handheld, ItemHandlerMode.SIMULATE);
				if (remainder.getCount() != handheld.getCount()) {
					ItemStack insert = ItemHandlerUtils.takeItemFromPlayer(player, InteractionHand.MAIN_HAND, handheld.getCount() - remainder.getCount(), ItemHandlerMode.EXECUTE);
					ItemHandlerUtils.insertItem(handler, insert, ItemHandlerMode.EXECUTE);
					return;
				}
			}
		} else {
			ItemStack contents = ItemHandlerUtils.extractItem(handler, 64, ItemHandlerMode.EXECUTE);
			ItemHandlerUtils.giveItemToPlayerWithDropItem(player, contents);
			return;
		}
	}

	@Override
	public @Nullable IItemHandler getItemHandler(Direction side) {
		return this.handler;
	}

	@Override
	public INBTSerializable<CompoundTag> getItemHandlerSerializer() {
		return this.handler;
	}
	
}