package skytheory.mystique.init;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryManager;
import skytheory.mystique.item.MystiqueContract;

public class MystiqueRegistries {

	public static IForgeRegistry<MystiqueContract> CONTRACTS = RegistryManager.ACTIVE.getRegistry(Keys.CONTRACTS);;

    public static final class Keys {
    	public static final ResourceKey<Registry<MystiqueContract>> CONTRACTS = ResourceKey.createRegistryKey(MystiqueContract.REGISTRY_LOCATION);
    }

}
