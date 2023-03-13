package skytheory.mystique.block;

import java.util.List;

import org.jetbrains.annotations.Nullable;

import com.mojang.logging.LogUtils;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.LeftClickBlock;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.items.IItemHandler;
import skytheory.lib.block.InteractiveBlockEntityMainHand;
import skytheory.lib.block.TickerBlockEntity;
import skytheory.lib.capability.itemhandler.InventoryHandler;
import skytheory.lib.capability.itemhandler.ItemHandlerEntity;
import skytheory.lib.util.ItemHandlerMode;
import skytheory.lib.util.ItemHandlerUtils;
import skytheory.mystique.init.MystiqueBlockEntities;

public class ManaInfuserEntity extends BlockEntity implements TickerBlockEntity, ItemHandlerEntity, InteractiveBlockEntityMainHand {

	public IItemHandler handler;
	
	public ManaInfuserEntity(BlockPos pPos, BlockState pBlockState) {
		super(MystiqueBlockEntities.mana_infuser, pPos, pBlockState);
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
	public void onLeftClicked(LeftClickBlock event, Player player, @Nullable Direction face, ItemStack stack) {
	}

	@Override
	public void onRightClicked(RightClickBlock event, Player player, @Nullable Direction face, ItemStack stack) {
		if (ItemHandlerUtils.isEmpty(handler)) {
			ItemStack handheld = ItemHandlerUtils.takeItemFromPlayer(player, InteractionHand.MAIN_HAND, 64, ItemHandlerMode.SIMULATE);
			if (!handheld.isEmpty()) {
				ItemStack remainder = ItemHandlerUtils.insertItem(handler, handheld, ItemHandlerMode.SIMULATE);
				if (remainder.getCount() != handheld.getCount()) {
					ItemHandlerUtils.takeItemFromPlayer(player, InteractionHand.MAIN_HAND, handheld.getCount() - remainder.getCount(), ItemHandlerMode.EXECUTE);
					ItemHandlerUtils.insertItem(handler, handheld, ItemHandlerMode.EXECUTE);
					LogUtils.getLogger().info(handler.getStackInSlot(0).toString());
					event.setCanceled(true);
					return;
				}
			}
		} else {
			ItemStack contents = ItemHandlerUtils.extractItem(handler, 64, ItemHandlerMode.EXECUTE);
			ItemHandlerUtils.giveItemToPlayerWithDropItem(player, contents);
			event.setCanceled(true);
			return;
		}
	}

	@Override
	public @Nullable IItemHandler getItemHandler(Direction side) {
		return this.handler;
	}

	@Override
	public List<IItemHandler> createAllHandlers() {
		this.handler = new InventoryHandler(1);
		return List.of(this.handler);
	}

}