package skytheory.mystique.util;

import java.util.List;
import java.util.Objects;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.IItemHandler;
import skytheory.lib.util.ItemHandlerStream;
import skytheory.mystique.capability.ElementComponent;
import skytheory.mystique.capability.ManaHandler;
import skytheory.mystique.capability.ManaHandler.ManaHandlerMode;
import skytheory.mystique.init.MystiqueCapabilities;

public class ManaHandlerUtils {
	
	public static List<ManaHandler> getManaHandlers(Player player) {
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
		List<ManaHandler> handlers = getManaHandlers(player);
		for (ManaHandler handler : handlers) {
			if (handler.consume(component, mode)) return true;
		}
		return false;
	}
}
