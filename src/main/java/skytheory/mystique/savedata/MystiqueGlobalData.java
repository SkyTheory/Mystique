package skytheory.mystique.savedata;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;

public class MystiqueGlobalData extends SavedData {

	public static final String PATH = "mystique_global";
	
	public static final String KEY_HARVEST = "Harvest";
	
	private final PlayerDataStorage<ChainHarvestData> harvestData;
	
	public static MystiqueGlobalData getGlobalData(ServerLevel level) {
		return level.getServer().overworld().getDataStorage().computeIfAbsent(MystiqueGlobalData::new, MystiqueGlobalData::new, PATH);
	}

	private MystiqueGlobalData() {
		this.harvestData = new PlayerDataStorage<ChainHarvestData>(this, ChainHarvestData::new);
	}
	
	private MystiqueGlobalData(CompoundTag nbt) {
		this();
		harvestData.deserializeNBT(nbt.getCompound(KEY_HARVEST));
	}
	
	public PlayerDataStorage<ChainHarvestData> getHarvestData() {
		return harvestData;
	}
	
	@Override
	public CompoundTag save(CompoundTag pCompoundTag) {
		pCompoundTag.put(KEY_HARVEST, harvestData.serializeNBT());
		return pCompoundTag;
	}

}
