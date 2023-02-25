package skytheory.mystique.capability;

import java.util.Arrays;
import java.util.List;

import com.google.gson.JsonObject;

import net.minecraft.network.FriendlyByteBuf;

public class ElementComponent {
	
	protected final int[] cache;

	public ElementComponent() {
		this.cache = new int[ElementQuality.values().length];
	}
	
	public static ElementComponent of(int amount) {
		ElementComponent component = new ElementComponent();
		for (ElementQuality quality : ElementQuality.values()) {
			component.setAmount(quality, amount);
		}
		return component;
	}
	
	public void setAmount(ElementQuality quality, int amount) {
		cache[quality.ordinal()] = amount;
	}
	
	public int getAmount(ElementQuality quality) {
		return cache[quality.ordinal()];
	}


	public boolean isEmpty() {
		for (ElementQuality quality : ElementQuality.values()) {
			if (this.getAmount(quality) != 0) return false;
		}
		return true;
	}

	public int getTotal() {
		int total = 0;
		for (ElementQuality quality : ElementQuality.values()) {
			total += this.getAmount(quality);
		}
		return total;
	}
	
	public boolean has(ElementQuality quality, int amount) {
		return this.getAmount(quality) >= amount;
	}
	
	public boolean has(ElementComponent component) {
		for (ElementQuality quality : ElementQuality.values()) {
			if (!has(quality, component.getAmount(quality))) return false;
		}
		return true;
	}

	public List<ElementQuality> getColors(){
		return Arrays.stream(ElementQuality.values()).filter(q -> this.getAmount(q) > 0).toList();
	}

	public List<ElementQuality> getComplementaryColors(){
		return Arrays.stream(ElementQuality.values()).filter(q -> this.getAmount(q) == 0).toList();
	}

	public List<ElementQuality> getNegativeColors(){
		return Arrays.stream(ElementQuality.values()).filter(q -> this.getAmount(q) < 0).toList();
	}

	public void add(ElementQuality quality, int amount) {
		cache[quality.ordinal()] += amount;
	}

	public void sub(ElementQuality quality, int amount) {
		this.add(quality, -amount);
	}
	
	public void multiply(ElementQuality quality, int amount) {
		this.cache[quality.ordinal()] *= amount;
	}

	public void add(ElementComponent other) {
		for (ElementQuality quality : ElementQuality.values()) {
			add(quality, other.getAmount(quality));
		}
	}

	public void sub(ElementComponent other) {
		ElementComponent inversed = other.copy();
		inversed.multiply(-1);
		this.add(inversed);
	}

	public void multiply(int amount) {
		for (ElementQuality quality : ElementQuality.values()) {
			cache[quality.ordinal()] *= amount;
		}
	}

	public ElementComponent copy() {
		ElementComponent copy = new ElementComponent();
		for (ElementQuality quality : ElementQuality.values()) {
			copy.setAmount(quality, this.getAmount(quality));
		}
		return copy;
	}

	public boolean isAmountEquals(ElementComponent other) {
		for (ElementQuality quality : ElementQuality.values()) {
			if (this.getAmount(quality) != other.getAmount(quality)) return false;
		}
		return true;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ElementComponent component && obj.getClass() == this.getClass()) {
			return isAmountEquals(component);
		}
		return false;
	}

	// パケット関連
	
	public static ElementComponent readFromBuffer(FriendlyByteBuf pBuffer) {
		ElementComponent component = new ElementComponent();
		for (ElementQuality quality : ElementQuality.values()) {
			component.setAmount(quality, pBuffer.readInt());
		}
		return component;
	}

	public static void writeToBuffer(ElementComponent component, FriendlyByteBuf pBuffer) {
		for (ElementQuality quality : ElementQuality.values()) {
			pBuffer.writeInt(component.getAmount(quality));
		}
	}
	
	// Jsonコーデック
	
	public static void encode(JsonObject jsonObject, ElementComponent component) {
		JsonObject element = new JsonObject();
		for (ElementQuality quality : ElementQuality.values()) {
			element.addProperty(quality.toString(), component.getAmount(quality));
		}
		jsonObject.add("elements", element);
	}
	
	public static ElementComponent decode(JsonObject jsonObject) {
		JsonObject element = jsonObject.getAsJsonObject("elements");
		ElementComponent component = new ElementComponent();
		for (ElementQuality quality : ElementQuality.values()) {
			if (element.has(quality.toString())) {
				component.setAmount(quality, element.get(quality.name()).getAsInt());
			}
		}
		return component;
	}
	
	public static enum ElementQuality {

		LIGHT,
		DARK,
		LIFE,
		DEATH;

	}

}