package skytheory.mystique.event;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import skytheory.lib.capability.DataProvider;
import skytheory.mystique.Mystique;
import skytheory.mystique.capability.IManaHandler;
import skytheory.mystique.capability.ManaHandlerItem;
import skytheory.mystique.init.MystiqueCapabilities;

public class MystiqueCapabilityEvent {

	public static final ResourceLocation MANA_HANDLER_KEY = new ResourceLocation(Mystique.MODID, "mana_handler");

	@SubscribeEvent
	public static void attachCapabilitiesToItem(AttachCapabilitiesEvent<ItemStack> event) {
		if (event.getObject().getItem() instanceof ManaHandlerItem target) {
			IManaHandler handler = target.createManaHandler();
			ICapabilityProvider provider = DataProvider.createSingle(MystiqueCapabilities.MANA_CAPABILITY, handler, handler);
			event.addCapability(MANA_HANDLER_KEY, provider);
		}
	}

}
