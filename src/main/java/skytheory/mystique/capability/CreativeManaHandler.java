package skytheory.mystique.capability;

import net.minecraft.nbt.CompoundTag;
import skytheory.mystique.capability.ElementComponent.ElementQuality;

public class CreativeManaHandler implements IManaHandler {

	@Override
	public CompoundTag serializeNBT() {
		return new CompoundTag();
	}

	@Override
	public void deserializeNBT(CompoundTag nbt) {
	}

	@Override
	public int getAmount(ElementQuality quality) {
		return Integer.MAX_VALUE;
	}

	@Override
	public int getCapacity(ElementQuality quality) {
		return Integer.MAX_VALUE;
	}

	@Override
	public void setAmount(ElementQuality quality, int amount) {
	}

	@Override
	public ElementComponent insert(ElementComponent component, ManaHandlerMode mode) {
		return new ElementComponent();
	}

	@Override
	public ElementComponent extract(ElementComponent component, ManaHandlerMode mode) {
		return component.copy();
	}

	@Override
	public boolean consume(ElementComponent component, ManaHandlerMode mode) {
		return true;
	}

}
