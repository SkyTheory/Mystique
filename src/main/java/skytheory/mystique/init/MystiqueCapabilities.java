package skytheory.mystique.init;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import skytheory.mystique.capability.IManaHandler;

public class MystiqueCapabilities {

	public static final Capability<IManaHandler> MANA_CAPABILITY = CapabilityManager.get(new CapabilityToken<>(){});
	
}
