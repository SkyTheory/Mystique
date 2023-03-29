package skytheory.mystique.util;

import skytheory.mystique.capability.ElementStack;
import skytheory.mystique.capability.ElementStack.ElementQuality;

public record ManaComponent(ElementQuality... qualities) {

	public static final ManaComponent FIRE = new ManaComponent(ElementQuality.LIGHT, ElementQuality.DEATH);
	public static final ManaComponent AIR = new ManaComponent(ElementQuality.LIGHT, ElementQuality.LIFE);
	public static final ManaComponent WATER = new ManaComponent(ElementQuality.DARK, ElementQuality.LIFE);
	public static final ManaComponent EARTH = new ManaComponent(ElementQuality.DARK, ElementQuality.DEATH);
	public static final ManaComponent ALL = new ManaComponent(ElementQuality.values());

	public ElementStack amountOf(int amount) {
		ElementStack component = ElementStack.of(0);
		for (ElementQuality quality : qualities) {
			component.setAmount(quality, amount);
		}
		return component;
	}

}