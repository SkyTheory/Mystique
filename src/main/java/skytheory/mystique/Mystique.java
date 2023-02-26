package skytheory.mystique;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import skytheory.mystique.event.ChainDestructionEvent;
import skytheory.mystique.event.ChainHarvestEvent;
import skytheory.mystique.event.MystiqueCapabilityEvent;
import skytheory.mystique.init.SetupEvent;

@Mod(Mystique.MODID)
public class Mystique {
	
	public static final String MODID = "mystique";
	
	public Mystique() {
		
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus(); 
		bus.register(SetupEvent.class);
		
		MinecraftForge.EVENT_BUS.register(MystiqueCapabilityEvent.class);
		MinecraftForge.EVENT_BUS.register(ChainDestructionEvent.class);
		MinecraftForge.EVENT_BUS.register(ChainHarvestEvent.class);
	}

}
