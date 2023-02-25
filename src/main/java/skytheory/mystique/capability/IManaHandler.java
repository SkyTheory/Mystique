package skytheory.mystique.capability;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;
import net.minecraftforge.common.util.INBTSerializable;
import skytheory.mystique.capability.ElementComponent.ElementQuality;

@AutoRegisterCapability
public interface IManaHandler extends INBTSerializable<CompoundTag> {

	public int getAmount(ElementQuality quality);
	public int getCapacity(ElementQuality quality);

	/**
	 * 内容量を指定された値にする
	 */
	public void setAmount(ElementQuality quality, int amount);

	/**
	 * 指定された属性のエネルギーの注入を試みて、注入できなかった余りを返す
	 * 
	 * @param amount
	 * @param element
	 * @param mode
	 * @return remain
	 */
	public ElementComponent insert(ElementComponent component, ManaHandlerMode mode);

	/**
	 * 指定された属性のエネルギーの抽出を試みて、実際に抽出できた量を返す
	 * 
	 * @param amount
	 * @param element
	 * @param mode
	 * @return extracted
	 */
	public ElementComponent extract(ElementComponent component, ManaHandlerMode mode);

	/**
	 * 指定された量のエネルギーの消費を試みて、実際に消費できたかを返す
	 * 抽出と消費で別の計算方法を利用したくなったので実装
	 * 
	 * @param element
	 * @param mode
	 * @return succeed
	 */
	public boolean consume(ElementComponent component, ManaHandlerMode mode);

	public static enum ManaHandlerMode {
		
		SIMULATE,
		EXECUTE
		
	}
	
}
