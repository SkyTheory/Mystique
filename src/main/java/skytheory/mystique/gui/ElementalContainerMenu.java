package skytheory.mystique.gui;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import net.minecraftforge.network.IContainerFactory;
import skytheory.lib.gui.ContainerMenuBase;
import skytheory.mystique.entity.AbstractElemental;
import skytheory.mystique.init.MystiqueMenuTypes;

public class ElementalContainerMenu extends ContainerMenuBase {

	public static final int PLAYER_SLOT_X = 8;
	public static final int PLAYER_SLOT_Y = 84;
	public static final int HOTBAR_SLOT_X = 8;
	public static final int HOTBAR_SLOT_Y = 142;
	public static final int CONTRACT_SLOT_X = 80;
	public static final int CONTRACT_SLOT_Y = 16;
	public static final int FILTER_SLOT_X = 80;
	public static final int FILTER_SLOT_Y = 52;
	public static final int PLAYER_ITEMS_COUNT = Inventory.INVENTORY_SIZE;

	public static final IContainerFactory<ElementalContainerMenu> CLIENT_CONTAINER_FACTORY = new IContainerFactory<>() {
		@Override
		public ElementalContainerMenu create(int windowId, Inventory inv, FriendlyByteBuf data) {
			Entity entity = inv.player.getLevel().getEntity(data.readInt());
			if (entity instanceof AbstractElemental elemental) {
				return new ElementalContainerMenu(windowId, inv, inv.player, elemental);
			}
			return null;
		}
	};

	public final Player player;
	public final AbstractElemental entity;

	public ElementalContainerMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer, AbstractElemental entity) {
		super(MystiqueMenuTypes.ELEMENTAL_MENU, pContainerId);
		this.player = pPlayer;
		this.entity = entity;
		IItemHandler playerItems = new InvWrapper(pPlayerInventory);
		// プレイヤーのインベントリ
		for (int i = 0; i < 27; i++) {
			int x = PLAYER_SLOT_X + (i % 9) * 18;
			int y = PLAYER_SLOT_Y + (i / 9) * 18;
			this.addSlot(new SlotItemHandler(playerItems, i + 9, x, y));
		}
		for (int i = 0; i < 9; i++) {
			int x = HOTBAR_SLOT_X + i * 18;
			int y = HOTBAR_SLOT_Y;
			this.addSlot(new SlotItemHandler(playerItems, i, x, y));
		}
		this.addSlot(new SlotItemHandler(entity.getMainInventory(), AbstractElemental.SLOT_CONTRACT, CONTRACT_SLOT_X, CONTRACT_SLOT_Y));
		for (int i = 0; i < 5; i++) {
			int x = FILTER_SLOT_X + i * 18;
			int y = FILTER_SLOT_Y;
			this.addFilterSlot(new SlotItemHandler(entity.getFilterInventory(), i, x, y));
		}
	}

	@Override
	public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
		Slot slot = this.slots.get(pIndex);
		if (slot.hasItem()) {
			ItemStack prevItem = slot.getItem().copy();
			ItemStack slotItem = prevItem.copy();
			if (pIndex >= Inventory.INVENTORY_SIZE) {
				if (!this.moveItemStackTo(slotItem, 0, Inventory.INVENTORY_SIZE, true)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.moveItemStackTo(slotItem, Inventory.INVENTORY_SIZE, this.slots.size(), false)) {
				return ItemStack.EMPTY;
			}
			if (slotItem.isEmpty()) {
				slot.set(ItemStack.EMPTY);
			} else {
				slot.set(slotItem);
			}
			slot.onTake(pPlayer, prevItem);
			return slotItem;
		}
		return ItemStack.EMPTY;
	}
	
	@Override
	public void removed(Player pPlayer) {
		this.entity.resetInteractingPlayer();
		super.removed(pPlayer);
	}

	@Override
	public boolean stillValid(Player pPlayer) {
		return entity.distanceToSqr(pPlayer) <= 64.0d;
	}

}
