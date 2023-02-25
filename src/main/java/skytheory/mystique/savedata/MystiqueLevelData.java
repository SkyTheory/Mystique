package skytheory.mystique.savedata;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;

public class MystiqueLevelData extends SavedData {

	public final static String PATH = "mystique";
	public final static String KEY_CHAIN_DESTRUCTION = "ChainDesturction";

	private final ChainDestructionData chainDestructionData;

	public static MystiqueLevelData getLevelData(ServerLevel level) {
		return level.getDataStorage().computeIfAbsent(MystiqueLevelData::new, MystiqueLevelData::new, PATH);
	}

	public static ChainDestructionData getChainDestructionData(ServerLevel level) {
		return getLevelData(level).getChainDestructionData();
	}

	private MystiqueLevelData() {
		this.chainDestructionData = new ChainDestructionData(this);
	}
	
	private MystiqueLevelData(CompoundTag tag) {
		this.chainDestructionData = new ChainDestructionData(this);
		chainDestructionData.deserializeNBT(tag.getCompound(KEY_CHAIN_DESTRUCTION));
		this.setDirty();
	}
	
	@Override
	public CompoundTag save(CompoundTag tag) {
		tag.put(KEY_CHAIN_DESTRUCTION, chainDestructionData.serializeNBT());
		return tag;
	}

	public ChainDestructionData getChainDestructionData() {
		return chainDestructionData;
	}
	
}
