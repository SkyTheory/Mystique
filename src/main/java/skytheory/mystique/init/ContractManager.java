package skytheory.mystique.init;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;

import org.jetbrains.annotations.Nullable;

import com.google.common.collect.ImmutableList;
import com.mojang.logging.LogUtils;

import net.minecraft.resources.ResourceKey;
import net.minecraftforge.registries.IForgeRegistryInternal;
import net.minecraftforge.registries.RegistryManager;
import skytheory.mystique.entity.ai.contract.MystiqueContract;

public class ContractManager {

	private static final List<MystiqueContract> CONTRACTS = new ArrayList<>();
	private static Comparator<Entry<ResourceKey<MystiqueContract>, MystiqueContract>> priorityComparator = Comparator.comparing(entry -> entry.getValue().getPriority());
	private static Comparator<Entry<ResourceKey<MystiqueContract>, MystiqueContract>> locationComparator = Comparator.comparing(entry -> entry.getKey());

	static void onAdd(IForgeRegistryInternal<MystiqueContract> owner, RegistryManager stage, int id, ResourceKey<MystiqueContract> key, MystiqueContract obj, @Nullable MystiqueContract oldObj) {
		LogUtils.getLogger().debug("Registering mystique contract: " + obj.getClass().getCanonicalName() + " -> " + key.location());
	}
	
	static void init(IForgeRegistryInternal<MystiqueContract> owner) {
		CONTRACTS.clear();
		owner.getEntries().stream()
		.sorted(priorityComparator.thenComparing(locationComparator))
		.map(Entry::getValue)
		.forEach(CONTRACTS::add);
	}

	/**
	 * 優先度順に並べられたContractのリストを取得する
	 * @return
	 */
	public static List<MystiqueContract> getAllContracts(){
		return ImmutableList.copyOf(CONTRACTS);
	}
}
