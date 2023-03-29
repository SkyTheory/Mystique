package skytheory.mystique.capability;

import com.mojang.logging.LogUtils;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntArrayTag;
import net.minecraft.util.Mth;

public class ManaHandlerBase extends ElementStack implements ManaHandler {

	private static final int PROTOCOL = 1;

	protected final int[] capacities = new int[ElementQuality.values().length];

	public ManaHandlerBase(int capacity) {
		for (ElementQuality quality : ElementQuality.values()) {
			capacities[quality.ordinal()] = capacity;
		}
	}
	
	public void setCapacity(ElementQuality quality, int capacity) {
		int i = quality.ordinal();
		capacities[i] = capacity;
		cache[i] = Math.min(capacities[i], cache[i]);
	}
	
	@Override
	public int getCapacity(ElementQuality quality) {
		return capacities[quality.ordinal()];
	}

	@Override
	public ElementStack insert(ElementStack component, ManaHandlerMode mode) {
		ElementStack result = component.copy();
		for (ElementQuality quality : ElementQuality.values()) {
			int inserted = Math.min(component.getAmount(quality), getCapacity(quality) - getAmount(quality));
			result.sub(quality, inserted);
			if (mode == ManaHandlerMode.EXECUTE) this.add(quality, inserted);
		}
		return result;
	}

	@Override
	public ElementStack extract(ElementStack component, ManaHandlerMode mode) {
		ElementStack result = new ElementStack();
		for (ElementQuality quality : ElementQuality.values()) {
			int extracted = Math.min(component.getAmount(quality), getAmount(quality));
			result.setAmount(quality, extracted);
			if (mode == ManaHandlerMode.EXECUTE) this.sub(quality, extracted);
		}
		return result;
	}

	@Override
	public boolean consume(ElementStack component, ManaHandlerMode mode) {
		if (this.has(component)) {
			extract(component, mode);
			return true;
		}
		return false;
	}

	@Override
	public CompoundTag serializeNBT() {
		CompoundTag nbt = new CompoundTag();
		nbt.putInt("Protocol", PROTOCOL);
		IntArrayTag amountTag = new IntArrayTag(cache);
		IntArrayTag capacitiesTag = new IntArrayTag(capacities);
		nbt.put("Amount", amountTag);
		nbt.put("Capacity", capacitiesTag);
		return nbt;
	}

	@Override
	public void deserializeNBT(CompoundTag nbt) {
		if (nbt.getInt("Protocol") == PROTOCOL) {
			int[] amountData = nbt.getIntArray("Amount");
			int[] capacitiesData = nbt.getIntArray("Capacity");
			int length = ElementQuality.values().length;
			if (capacitiesData.length == length && amountData.length == length) {
				for (ElementQuality quality : ElementQuality.values()) {
					int index = quality.ordinal();
					setAmount(quality, amountData[index]);
					setCapacity(quality, capacitiesData[index]);
				}
			} else {
				LogUtils.getLogger().warn("Deserialize skipped: Invalid NBT.");
			}
		}
	}
	
	@Override
	public void add(ElementQuality quality, int amount) {
		super.add(quality, amount);
		this.setAmount(quality, Mth.clamp(this.getAmount(quality), 0, this.getCapacity(quality)));
	}
	
	@Override
	public void multiply(ElementQuality quality, int amount) {
		super.multiply(quality, amount);
		this.setAmount(quality, Mth.clamp(this.getAmount(quality), 0, this.getCapacity(quality)));
	}

}
