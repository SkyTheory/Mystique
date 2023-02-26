package skytheory.mystique.init;

import java.util.HashSet;
import java.util.Set;

import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import com.google.common.collect.ImmutableSet;
import com.mojang.logging.LogUtils;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraftforge.registries.IForgeRegistryInternal;
import net.minecraftforge.registries.RegistryManager;
import skytheory.mystique.entity.AbstractElemental;
import skytheory.mystique.item.MystiqueContract;

/**
 * ElementalのAIに登録するMemoryModuleとSensorのレジストリ
 * @author SkyTheory
 *
 */
public class ElementalAIRegistry {

	public static Logger LOGGER = LogUtils.getLogger();

	private static final Set<MemoryModuleType<?>> DEFAULT_MEMORY_TYPES = ImmutableSet.of(
			MemoryModuleType.LOOK_TARGET,
			MemoryModuleType.NEAREST_HOSTILE,
			MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
			MemoryModuleType.WALK_TARGET,
			MemoryModuleType.PATH,
			MemoryModuleType.TEMPTING_PLAYER,
			MemoryModuleType.IS_TEMPTED,
			MemoryModuleType.IS_PANICKING);

	private static final Set<SensorType<? extends Sensor<? super AbstractElemental>>> DEFAULT_SENSOR_TYPES = ImmutableSet.of(
			SensorType.NEAREST_LIVING_ENTITIES,
			SensorType.NEAREST_PLAYERS,
			SensorType.NEAREST_ITEMS,
			SensorType.HURT_BY,
			MystiqueEntitySensors.TEMPTING,
			MystiqueEntitySensors.HOSTILES);

	private static final Set<MemoryModuleType<?>> ADDITIONAL_MEMORY_TYPES = new HashSet<>();
	private static final Set<SensorType<? extends Sensor<? super AbstractElemental>>> ADDITIONAL_SENSOR_TYPES = new HashSet<>();

	public static Set<MemoryModuleType<?>> getMemoryTypes() {
		Set<MemoryModuleType<?>> set = new HashSet<>();
		set.addAll(DEFAULT_MEMORY_TYPES);
		ADDITIONAL_MEMORY_TYPES.forEach(set::add);
		return ImmutableSet.copyOf(set);
	}

	public static Set<SensorType<? extends Sensor<? super AbstractElemental>>> getSensorTypes() {
		Set<SensorType<? extends Sensor<? super AbstractElemental>>> set = new HashSet<>();
		set.addAll(DEFAULT_SENSOR_TYPES);
		ADDITIONAL_SENSOR_TYPES.forEach(set::add);
		return ImmutableSet.copyOf(set);
	}
	
	static void onAdd(IForgeRegistryInternal<MystiqueContract> owner, RegistryManager stage, int id, ResourceKey<MystiqueContract> key, MystiqueContract obj, @Nullable MystiqueContract oldObj) {
		if (stage == RegistryManager.FROZEN) {
			LogUtils.getLogger().debug("Registering Mystique contract: " + key.location().toString());
			
		}
	}

}
