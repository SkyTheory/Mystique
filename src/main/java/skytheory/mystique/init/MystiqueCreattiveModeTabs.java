package skytheory.mystique.init;

import java.util.Comparator;
import java.util.List;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;
import skytheory.lib.util.CapabilityUtils;
import skytheory.mystique.Mystique;
import skytheory.mystique.capability.ElementStack.ElementQuality;
import skytheory.mystique.capability.ManaHandler;
import skytheory.mystique.util.CreativeModeTabAliases;

public class MystiqueCreattiveModeTabs {
	
	public static final void buildMainTab(CreativeModeTab.Builder builder) {
		builder.title(Component.translatable("itemGroup.mystique"))
		.icon(() -> {
			ItemStack stack = new ItemStack(MystiqueItems.MANA_CAPACITOR);
			ManaHandler handler = CapabilityUtils.getCapability(MystiqueCapabilities.MANA_CAPABILITY, stack);
			for (ElementQuality quality : ElementQuality.values()) {
				handler.setAmount(quality, handler.getCapacity(quality));
			}
			return stack;
		})
		.displayItems((pEnabledFeatures, pOutput, pDisplayOperatorCreativeTab) -> {
			List<Item> items = ForgeRegistries.ITEMS.getEntries().stream()
					.filter(entry -> entry.getKey().location().getNamespace().equals(Mystique.MODID))
					.map(entry -> entry.getValue())
					.sorted(mainComparator)
					.toList();
			for (Item item : items) {
				if (item instanceof CreativeModeTabAliases entry) {
					pOutput.acceptAll(entry.getAliases());
				} else if (item instanceof BlockItem blockItem && blockItem instanceof CreativeModeTabAliases entry) {
					pOutput.acceptAll(entry.getAliases());
				} else {
					pOutput.accept(new ItemStack(item));
				}
			}
		});
	}
	
	public static Comparator<Item> comparator1 = Comparator.comparing(BlockItem.class::isInstance);
	public static Comparator<Item> comparator2 = Comparator.comparing(ForgeRegistries.ITEMS::getKey);
	public static Comparator<Item> mainComparator = comparator1.thenComparing(comparator2);
}
