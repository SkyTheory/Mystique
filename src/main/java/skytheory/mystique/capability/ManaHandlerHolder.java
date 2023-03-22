package skytheory.mystique.capability;

import java.util.List;

import net.minecraft.resources.ResourceLocation;
import skytheory.lib.capability.CapabilityHolder;
import skytheory.lib.capability.DataProvider;
import skytheory.mystique.Mystique;
import skytheory.mystique.init.MystiqueCapabilities;

public interface ManaHandlerHolder extends CapabilityHolder {

	public static ResourceLocation MANA_KEY = new ResourceLocation(Mystique.MODID, "mana");
	
	ManaHandler getManaHandler();
	
	@Override
	default List<CapabilityEntry> getCapabilityProviders() {
		return List.of(new CapabilityEntry(MANA_KEY, new DataProvider<>(MystiqueCapabilities.MANA_CAPABILITY, this::getManaHandler, this::getManaHandler)));
	}
	
}
