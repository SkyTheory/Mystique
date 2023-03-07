package skytheory.mystique.container;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import net.minecraftforge.network.IContainerFactory;
import skytheory.mystique.entity.AbstractElemental;
import skytheory.mystique.init.MystiqueMenuTypes;

public class ElementalContainerMenu extends AbstractContainerMenu {

	public static final int PLAYER_SLOT_X = 8;
	public static final int PLAYER_SLOT_Y = 84;
	public static final int HOTBAR_SLOT_X = 8;
	public static final int HOTBAR_SLOT_Y = 142;
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
	
	private final Player player;
	private final AbstractElemental entity;

	public ElementalContainerMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer, AbstractElemental entity) {
		super(MystiqueMenuTypes.ELEMENTAL_MENU, pContainerId);
		this.player = pPlayer;
		this.entity = entity;
		IItemHandler playerItems = new InvWrapper(pPlayerInventory);
		// プレイヤーのインベントリ
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 3; j++) {
				int x = PLAYER_SLOT_X + i * 18;
				int y = PLAYER_SLOT_Y + j * 18;
				this.addSlot(new SlotItemHandler(playerItems, i + j * 9 + 9, x, y));
			}
		}
		for (int i = 0; i < 9; i++) {
			int x = HOTBAR_SLOT_X + i * 18;
			int y = HOTBAR_SLOT_Y;
			this.addSlot(new SlotItemHandler(playerItems, i, x, y));
		}
		if (entity != null) {
			this.addSlot(new SlotItemHandler(entity.itemHandler, AbstractElemental.SLOT_CONTRACT, 80, 10));
		}
	}

	@Override
	public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.slots.get(pIndex);
		if (slot != null && slot.hasItem()) {
			ItemStack itemstack1 = slot.getItem();
			itemstack = itemstack1.copy();
			if (pIndex >= Inventory.INVENTORY_SIZE) {
				if (!this.moveItemStackTo(itemstack1, 0, Inventory.INVENTORY_SIZE, true)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.moveItemStackTo(itemstack1, Inventory.INVENTORY_SIZE, this.slots.size(), false)) {
				return ItemStack.EMPTY;
			}
			if (itemstack1.isEmpty()) {
				slot.set(ItemStack.EMPTY);
			} else {
				slot.setChanged();
			}
			slot.onTake(pPlayer, itemstack1);
		}
		return itemstack;
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
