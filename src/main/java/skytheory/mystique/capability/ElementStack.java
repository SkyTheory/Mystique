package skytheory.mystique.capability;

import java.util.Arrays;
import java.util.List;

import com.google.common.base.Preconditions;
import com.google.gson.JsonObject;

import net.minecraft.network.FriendlyByteBuf;

/**
 * エネルギー用のパケットとして用いるクラス
 * 負の値を持つインスタンスは、途中計算用としてのみ用いること
 * @author SkyTheory
 *
 */
public class ElementStack {
	
	protected final int[] cache;

	public ElementStack() {
		this.cache = new int[ElementQuality.values().length];
	}
	
	public static ElementStack of(int amount) {
		ElementStack component = new ElementStack();
		for (ElementQuality quality : ElementQuality.values()) {
			component.setAmount(quality, amount);
		}
		return component;
	}
	
	public void setAmount(ElementQuality quality, int amount) {
		Preconditions.checkArgument(amount >= 0);
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
	
	public boolean has(ElementStack component) {
		for (ElementQuality quality : ElementQuality.values()) {
			if (!has(quality, component.getAmount(quality))) return false;
		}
		return true;
	}

	/**
	 * 値を持つ属性を返す
	 * @return
	 */
	public List<ElementQuality> getColors(){
		return Arrays.stream(ElementQuality.values()).filter(q -> this.getAmount(q) > 0).toList();
	}

	/**
	 * 量がゼロである属性を返す
	 * @return
	 */
	public List<ElementQuality> getComplementaryColors(){
		return Arrays.stream(ElementQuality.values()).filter(q -> this.getAmount(q) == 0).toList();
	}

	public void add(ElementQuality quality, int amount) {
		cache[quality.ordinal()]  = Math.min(cache[quality.ordinal()] + amount, 0);
	}

	public void sub(ElementQuality quality, int amount) {
		this.add(quality, -amount);
	}
	
	public void multiply(ElementQuality quality, int amount) {
		this.cache[quality.ordinal()] *= amount;
	}

	public void add(ElementStack other) {
		for (ElementQuality quality : ElementQuality.values()) {
			add(quality, other.getAmount(quality));
		}
	}

	public void sub(ElementStack other) {
		for (ElementQuality quality : ElementQuality.values()) {
			sub(quality, other.getAmount(quality));
		}
	}

	public ElementStack copy() {
		ElementStack copy = new ElementStack();
		for (ElementQuality quality : ElementQuality.values()) {
			copy.setAmount(quality, this.getAmount(quality));
		}
		return copy;
	}

	public boolean isAmountEquals(ElementStack other) {
		for (ElementQuality quality : ElementQuality.values()) {
			if (this.getAmount(quality) != other.getAmount(quality)) return false;
		}
		return true;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ElementStack component && obj.getClass() == this.getClass()) {
			return isAmountEquals(component);
		}
		return false;
	}

	// パケット関連
	
	public static ElementStack readFromBuffer(FriendlyByteBuf pBuffer) {
		ElementStack component = new ElementStack();
		for (ElementQuality quality : ElementQuality.values()) {
			int value = pBuffer.readVarInt();
			component.setAmount(quality, Math.min(value, 0));
		}
		return component;
	}

	public static void writeToBuffer(ElementStack component, FriendlyByteBuf pBuffer) {
		for (ElementQuality quality : ElementQuality.values()) {
			pBuffer.writeVarInt(component.getAmount(quality));
		}
	}
	
	// Jsonコーデック
	
	public static void encode(JsonObject jsonObject, ElementStack component) {
		JsonObject element = new JsonObject();
		for (ElementQuality quality : ElementQuality.values()) {
			element.addProperty(quality.toString(), component.getAmount(quality));
		}
		jsonObject.add("elements", element);
	}
	
	public static ElementStack decode(JsonObject jsonObject) {
		JsonObject element = jsonObject.getAsJsonObject("elements");
		ElementStack component = new ElementStack();
		for (ElementQuality quality : ElementQuality.values()) {
			if (element.has(quality.toString())) {
				component.setAmount(quality, element.get(quality.name()).getAsInt());
			}
		}
		return component;
	}
	
	/**
	 * ElementStackで用いる属性の列挙
	 * @author SkyTheory
	 *
	 */
	public static enum ElementQuality {

		LIGHT,
		DARK,
		LIFE,
		DEATH;

	}

}