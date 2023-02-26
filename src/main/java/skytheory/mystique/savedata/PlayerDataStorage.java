package skytheory.mystique.savedata;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Supplier;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.util.INBTSerializable;

/**
 * ログアウト中のプレイヤーも含めたプレイヤー情報を取得するために、セーブデータ側にデータを保存するための抽象クラス
 * @author SkyTheory
 *
 * @param <T>
 */
public class PlayerDataStorage<T extends INBTSerializable<CompoundTag>> implements INBTSerializable<CompoundTag> {

	protected final MystiqueGlobalData owner;
	
	private final Map<UUID, T> storage;
	private final Supplier<T> factory;
	
	public PlayerDataStorage(MystiqueGlobalData owner, Supplier<T> factory) {
		this.owner = owner;
		this.storage = new HashMap<>();
		this.factory = factory;
	}
	
	public T getPersonalData(Player player) {
		this.setDirty();
		return storage.computeIfAbsent(player.getUUID(), uuid -> factory.get());
	}
	
	public void removePersonalData(Player player) {
		this.removePersonalData(player.getUUID());
	}
	
	public void removePersonalData(UUID uuid) {
		storage.computeIfPresent(uuid, (key, data) -> null);
	}

	public Set<UUID> getAllKeys(){
		return ImmutableSet.copyOf(storage.keySet());
	}

	public Map<UUID, T> getAllData(){
		return ImmutableMap.copyOf(storage);
	}
	
	public void removeAllData() {
		storage.clear();
	}

	@Override
	public CompoundTag serializeNBT() {
		CompoundTag nbt = new CompoundTag();
		ListTag list = new ListTag();
		storage.forEach((uuid, entry) -> list.add(serializePersonal(uuid, entry)));
		nbt.put("Players", list);
		return nbt;
	}

	private CompoundTag serializePersonal(UUID uuid, T entry) {
		CompoundTag nbt = new CompoundTag();
		nbt.putUUID("UUID", uuid);
		nbt.put("Data", entry.serializeNBT());
		return nbt;
	}
	
	@Override
	public void deserializeNBT(CompoundTag nbt) {
		ListTag list = nbt.getList("Players", Tag.TAG_COMPOUND);
		for (Tag tag : list) {
			if (tag instanceof CompoundTag personalNbt) {
				UUID uuid = personalNbt.getUUID("UUID");
				T data = factory.get();
				data.deserializeNBT(personalNbt.getCompound("Data"));
				this.storage.put(uuid, data);
			}
		}
	}
	
	/**
	 * データの編集をした際に呼び出すこと
	 */
	public void setDirty() {
		this.owner.setDirty();
	}
	
}
