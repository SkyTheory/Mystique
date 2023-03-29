package skytheory.mystique.capability;

import net.minecraft.nbt.CompoundTag;
import skytheory.mystique.capability.ElementStack.ElementQuality;

public class CreativeManaHandler implements ManaHandler {

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
	public ElementStack insert(ElementStack component, ManaHandlerMode mode) {
		return new ElementStack();
	}

	@Override
	public ElementStack extract(ElementStack component, ManaHandlerMode mode) {
		return component.copy();
	}

	@Override
	public boolean consume(ElementStack component, ManaHandlerMode mode) {
		return true;
	}

}
