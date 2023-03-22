package skytheory.mystique.blockentity;

import java.util.List;

import org.jetbrains.annotations.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.items.IItemHandler;
import skytheory.lib.block.RightClickReceiverBlockEntity;
import skytheory.lib.block.TickerBlockEntity;
import skytheory.lib.capability.DataProvider;
import skytheory.lib.capability.itemhandler.InventoryHandler;
import skytheory.lib.capability.itemhandler.InventoryHolder;
import skytheory.mystique.capability.CreativeManaHandler;
import skytheory.mystique.capability.ManaHandler;
import skytheory.mystique.capability.ManaHandlerHolder;
import skytheory.mystique.init.MystiqueBlockEntities;
import skytheory.mystique.init.MystiqueCapabilities;

// TODO Mana関連の処理
public class ManaChannelerEntity extends BlockEntity implements TickerBlockEntity, RightClickReceiverBlockEntity, InventoryHolder<CompoundTag>, ManaHandlerHolder {

	public final InventoryHandler itemHandler;
	public final ManaHandler manaHandler;
	
	public ManaChannelerEntity(BlockPos pPos, BlockState pBlockState) {
		super(MystiqueBlockEntities.MANA_CHANNELER, pPos, pBlockState);
		this.itemHandler = new InventoryHandler(1);
		this.manaHandler = new CreativeManaHandler();
	}

	@Override
	public void tick() {
	}

	@Override
	public void onRightClicked(RightClickBlock event, Player player, InteractionHand hand, @Nullable Direction face, ItemStack stack) {
	}

	@Override
	public IItemHandler getItemHandler(Direction direction) {
		return itemHandler;
	}

	@Override
	public INBTSerializable<CompoundTag> getItemHandlerSerializer() {
		return itemHandler;
	}
	
	@Override
	public ManaHandler getManaHandler() {
		return manaHandler;
	}

	@Override
	public List<CapabilityEntry> getCapabilityProviders() {
		return List.of(
				new CapabilityEntry(InventoryHolder.ITEM_HANDLER_KEY, new DataProvider<>(ForgeCapabilities.ITEM_HANDLER, this::getItemHandler, this::getItemHandlerSerializer)),
				new CapabilityEntry(ManaHandlerHolder.MANA_KEY, new DataProvider<>(MystiqueCapabilities.MANA_CAPABILITY, this::getManaHandler, this::getManaHandler))
				);
		}

}