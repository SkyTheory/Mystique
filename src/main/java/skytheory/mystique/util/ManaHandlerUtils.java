package skytheory.mystique.util;

import java.util.List;
import java.util.Objects;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.IItemHandler;
import skytheory.lib.util.ItemHandlerStream;
import skytheory.mystique.capability.ElementComponent;
import skytheory.mystique.capability.IManaHandler;
import skytheory.mystique.capability.IManaHandler.ManaHandlerMode;
import skytheory.mystique.init.MystiqueCapabilities;

public class ManaHandlerUtils {
	
	public static List<IManaHandler> getManaHandlers(Player player) {
		IItemHandler handler = player.getCapability(ForgeCapabilities.ITEM_HANDLER).orElse(null);
		if (handler != null) {
			return ItemHandlerStream
			.create(handler)
			.map(slot -> slot.getStackInSlot())
			.map(stack -> stack.getCapability(MystiqueCapabilities.MANA_CAPABILITY))
			.map(opt -> opt.orElse(null))
			.filter(Objects::nonNull)
			.toList();
		}
		return List.of();
	}
	
	
	public static boolean consume(Player player, ElementComponent component, ManaHandlerMode mode) {
		List<IManaHandler> handlers = getManaHandlers(player);
		for (IManaHandler handler : handlers) {
			if (handler.consume(component, mode)) return true;
		}
		return false;
	}
}
