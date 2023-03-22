package skytheory.mystique.init;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import skytheory.mystique.capability.ManaHandler;

public class MystiqueCapabilities {

	public static final Capability<ManaHandler> MANA_CAPABILITY = CapabilityManager.get(new CapabilityToken<>(){});
	
}
