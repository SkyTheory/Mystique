package skytheory.mystique.util;

import java.util.List;
import java.util.Objects;

import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.items.IItemHandler;
import skytheory.lib.util.ItemHandlerStream;
import skytheory.mystique.capability.ElementStack;
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
	
	
	public static boolean consume(Player player, ElementStack component, ManaHandlerMode mode) {
		List<ManaHandler> handlers = getManaHandlers(player);
		for (ManaHandler handler : handlers) {
			if (handler.consume(component, mode)) return true;
		}
		return false;
	}
	
	public static ElementStack insert(ElementStack stack, ICapabilityProvider provider, Direction direction) {
		return insert(stack, provider, direction, ManaHandlerMode.EXECUTE);
	}
	
	public static ElementStack insert(ElementStack stack, ICapabilityProvider provider, Direction direction, ManaHandlerMode mode) {
		return provider.getCapability(MystiqueCapabilities.MANA_CAPABILITY, direction)
				.map(handler -> handler.insert(stack, mode)).orElse(stack);
	}
	
	public static ElementStack extract(ElementStack stack, ICapabilityProvider provider, Direction direction) {
		return extract(stack, provider, direction, ManaHandlerMode.EXECUTE);
	}
	
	public static ElementStack extract(ElementStack stack, ICapabilityProvider provider, Direction direction, ManaHandlerMode mode) {
		return provider.getCapability(MystiqueCapabilities.MANA_CAPABILITY, direction)
				.map(handler -> handler.extract(stack, mode)).orElse(new ElementStack());
	}

	public static boolean consume(ElementStack stack, ICapabilityProvider provider, Direction direction) {
		return consume(stack, provider, direction, ManaHandlerMode.EXECUTE);
	}
	
	public static boolean consume(ElementStack stack, ICapabilityProvider provider, Direction direction, ManaHandlerMode mode) {
		return provider.getCapability(MystiqueCapabilities.MANA_CAPABILITY, direction)
				.map(handler -> handler.consume(stack, mode)).orElse(false);
	}
	
	public static ElementStack tryMove(ElementStack stack, ManaHandler source, ManaHandler dest) {
		return tryMove(stack, source, dest, ManaHandlerMode.EXECUTE);
	}

	public static ElementStack tryMove(ElementStack stack, ManaHandler source, ManaHandler dest, ManaHandlerMode mode) {
		stack = source.extract(stack.copy(), ManaHandlerMode.SIMULATE);
		stack.sub(dest.insert(stack.copy(), ManaHandlerMode.SIMULATE));
		if (mode == ManaHandlerMode.EXECUTE) {
			source.extract(stack.copy(), ManaHandlerMode.EXECUTE);
			dest.insert(stack.copy(), ManaHandlerMode.EXECUTE);
		}
		return stack;
	}

	public static ElementStack tryMove(ElementStack stack, ICapabilityProvider source, Direction direction, ManaHandler dest) {
		return tryMove(stack, source, direction, dest, ManaHandlerMode.EXECUTE);
	}
	
	public static ElementStack tryMove(ElementStack stack, ICapabilityProvider source, Direction direction, ManaHandler dest, ManaHandlerMode mode) {
		return source.getCapability(MystiqueCapabilities.MANA_CAPABILITY, direction)
				.map(handler -> tryMove(stack, handler, dest, mode)).orElse(new ElementStack());
	}
	
	public static ElementStack tryMove(ElementStack stack, ManaHandler source, ICapabilityProvider dest, Direction direction) {
		return tryMove(stack, source, dest, direction, ManaHandlerMode.EXECUTE);
	}
	
	public static ElementStack tryMove(ElementStack stack, ManaHandler source, ICapabilityProvider dest, Direction direction, ManaHandlerMode mode) {
		return dest.getCapability(MystiqueCapabilities.MANA_CAPABILITY, direction)
				.map(handler -> tryMove(stack, source, handler, mode)).orElse(new ElementStack());
	}
	
}
