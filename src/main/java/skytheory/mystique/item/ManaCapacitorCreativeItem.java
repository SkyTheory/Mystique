package skytheory.mystique.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import skytheory.mystique.capability.CreativeManaHandler;
import skytheory.mystique.capability.IManaHandler;
import skytheory.mystique.capability.ManaHandlerItem;

public class ManaCapacitorCreativeItem extends Item implements ManaHandlerItem {

	public ManaCapacitorCreativeItem(Properties pProperties) {
		super(pProperties);
	}

	@Override
	public boolean isBarVisible(ItemStack pStack) {
		return false;
	}

	@Override
	public IManaHandler createManaHandler() {
		IManaHandler handler = new CreativeManaHandler();
		return handler;
	}

}
