package skytheory.mystique.item;

import java.util.List;

import net.minecraft.util.Mth;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import skytheory.lib.util.CapabilityUtils;
import skytheory.mystique.capability.ElementStack.ElementQuality;
import skytheory.mystique.capability.GenericManaHandler;
import skytheory.mystique.capability.ManaHandler;
import skytheory.mystique.capability.ManaHandlerItem;
import skytheory.mystique.init.MystiqueCapabilities;
import skytheory.mystique.util.CreativeModeTabAliases;
import skytheory.mystique.util.ExperienceUtils;

public class ManaCapacitorItem extends Item implements ManaHandlerItem, CreativeModeTabAliases {

	public static final float WIDTH_DURABILITY_BAR = 13.0f;

	public ManaCapacitorItem(Properties pProperties) {
		super(pProperties);
	}
	public boolean canBeDepleted() {
		return false;
	}

	@Override
	public boolean isBarVisible(ItemStack pStack) {
		ManaHandler handler = CapabilityUtils.getCapability(MystiqueCapabilities.MANA_CAPABILITY, pStack);
		return getManaRatio(handler) != 1.0f;
	}

	public int getBarWidth(ItemStack pStack) {
		ManaHandler handler = CapabilityUtils.getCapability(MystiqueCapabilities.MANA_CAPABILITY, pStack);
		return Math.round(getManaRatio(handler) * WIDTH_DURABILITY_BAR);
	}

	public int getBarColor(ItemStack pStack) {
		ManaHandler handler = CapabilityUtils.getCapability(MystiqueCapabilities.MANA_CAPABILITY, pStack);
		return Mth.hsvToRgb(getManaRatio(handler) / 3.0f, 1.0f, 1.0f);
	}

	public float getManaRatio(ManaHandler handler) {
		int amounts = 0;
		int capacities = 0;
		int types = 0;
		int minAmount = Integer.MAX_VALUE;
		for (ElementQuality quality : ElementQuality.values()) {
			if (handler.getCapacity(quality) != 0) {
				amounts += handler.getAmount(quality);
				capacities += handler.getCapacity(quality);
				minAmount = Math.min(minAmount, handler.getAmount(quality));
				types++;
			}
		}
		if (types == 0) return 0.0f;
		return (float) (amounts + minAmount * types) / (float) (capacities * 2.0f);
	}

	@Override
	public ManaHandler createManaHandler() {
		GenericManaHandler handler = new GenericManaHandler(ExperienceUtils.getNeededXpTotal(30));
		return handler;
	}
	
	@Override
	public List<ItemStack> getAliases() {
		ItemStack full = new ItemStack(this);
		ManaHandler handler = CapabilityUtils.getCapability(MystiqueCapabilities.MANA_CAPABILITY, full);
		for (ElementQuality quality : ElementQuality.values()) {
			handler.setAmount(quality, handler.getCapacity(quality));
		}
		return List.of(new ItemStack(this), full);
	}
	
}
