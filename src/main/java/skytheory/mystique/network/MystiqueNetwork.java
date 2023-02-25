package skytheory.mystique.network;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import skytheory.mystique.Mystique;

public class MystiqueNetwork {
	private static final String PROTOCOL_VERSION = "1";
	public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
	  new ResourceLocation(Mystique.MODID, "main"),
	  () -> PROTOCOL_VERSION,
	  PROTOCOL_VERSION::equals,
	  PROTOCOL_VERSION::equals
	);

	private static int ID = 0;
	
	public static void setup() {
		INSTANCE.registerMessage(ID++, BreakEffectMessage.class,
				BreakEffectMessage::encode,
				BreakEffectMessage::decode,
				BreakEffectMessage::process);
	}
	
}
