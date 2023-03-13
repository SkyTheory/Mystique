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
			MemoryModuleType.NEAREST_LIVING_ENTITIES,
			MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES,
			MemoryModuleType.NEAREST_PLAYERS,
			MemoryModuleType.NEAREST_VISIBLE_PLAYER,
			MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER,
			MemoryModuleType.HURT_BY,
			MemoryModuleType.HURT_BY_ENTITY,
			MemoryModuleType.LOOK_TARGET,
			MemoryModuleType.WALK_TARGET,
			MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
			MemoryModuleType.PATH,
			MemoryModuleType.NEAREST_HOSTILE,
			MemoryModuleType.TEMPTING_PLAYER,
			MemoryModuleType.IS_TEMPTED,
			MemoryModuleType.IS_PANICKING,
			MystiqueMemoryModuleTypes.CONTRACT);

	private static final Set<SensorType<? extends Sensor<? super AbstractElemental>>> DEFAULT_SENSOR_TYPES = ImmutableSet.of(
			SensorType.NEAREST_LIVING_ENTITIES,
			SensorType.NEAREST_PLAYERS,
			SensorType.HURT_BY);

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
			obj.getMemoryModules().forEach(ADDITIONAL_MEMORY_TYPES::add);
			obj.getSensorTypes().forEach(ADDITIONAL_SENSOR_TYPES::add);
		}
	}

}
