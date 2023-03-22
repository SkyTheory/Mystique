package skytheory.mystique.item;

import net.minecraft.world.item.Item;
import skytheory.mystique.capability.CreativeManaHandler;
import skytheory.mystique.capability.ManaHandler;
import skytheory.mystique.capability.ManaHandlerItem;

public class ManaCapacitorCreativeItem extends Item implements ManaHandlerItem {

	public ManaCapacitorCreativeItem(Properties pProperties) {
		super(pProperties);
	}

	@Override
	public ManaHandler createManaHandler() {
		return new CreativeManaHandler();
	}

}
