package skytheory.mystique.init;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.mojang.logging.LogUtils;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraftforge.registries.ForgeRegistries;
import skytheory.mystique.entity.AbstractElemental;

/**
 * ElementalのAIに登録するMemoryModuleとSensorのレジストリ
 * @author SkyTheory
 *
 */
public class ElementalAIRegistry {

	public static Logger LOGGER = LogUtils.getLogger();

	private static final List<MemoryModuleType<?>> DEFAULT_MEMORY_TYPES = Lists.newArrayList(
			MemoryModuleType.LOOK_TARGET,
			MemoryModuleType.NEAREST_HOSTILE,
			MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
			MemoryModuleType.WALK_TARGET,
			MemoryModuleType.PATH,
			MemoryModuleType.TEMPTING_PLAYER,
			MemoryModuleType.IS_TEMPTED,
			MemoryModuleType.IS_PANICKING);

	private static final List<SensorType<? extends Sensor<? super AbstractElemental>>> DEFAULT_SENSOR_TYPES = Lists.newArrayList(
			SensorType.NEAREST_LIVING_ENTITIES,
			SensorType.NEAREST_PLAYERS,
			SensorType.NEAREST_ITEMS,
			SensorType.HURT_BY,
			MystiqueEntitySensors.TEMPTING,
			MystiqueEntitySensors.HOSTILES);

	private static final List<MemoryModuleType<?>> ADDITIONAL_MEMORY_TYPES = new ArrayList<>();
	private static final List<SensorType<? extends Sensor<? super AbstractElemental>>> ADDITIONAL_SENSOR_TYPES = new ArrayList<>();

	private static ImmutableList<MemoryModuleType<?>> MEMORY_TYPES;
	private static ImmutableList<SensorType<? extends Sensor<? super AbstractElemental>>> SENSOR_TYPES;

	static void initRegistries() {
		MEMORY_TYPES =  Stream.concat(DEFAULT_MEMORY_TYPES.stream(), ADDITIONAL_MEMORY_TYPES.stream())
				.collect(ImmutableList.toImmutableList());
		SENSOR_TYPES = Stream.concat(DEFAULT_SENSOR_TYPES.stream(), ADDITIONAL_SENSOR_TYPES.stream())
				.collect(ImmutableList.toImmutableList());
	}

	public static List<MemoryModuleType<?>> getMemoryTypes() {
		return MEMORY_TYPES;
	}

	public static List<SensorType<? extends Sensor<? super AbstractElemental>>> getSensorTypes() {
		return SENSOR_TYPES;
	}

	static void addMemoryType(MemoryModuleType<?> memory) {
		Validate.notNull(memory);
		if (DEFAULT_MEMORY_TYPES.contains(memory) || ADDITIONAL_MEMORY_TYPES.contains(memory)) {
			ResourceLocation location = ForgeRegistries.MEMORY_MODULE_TYPES.getKey(memory);
			LOGGER.error("Duplicate MemoryModuleType registrations, may cause unexpected problems: " + location.toString());
		} else {
			ADDITIONAL_MEMORY_TYPES.add(memory);
		}
	}

	static void addSensorType(SensorType<? extends Sensor<? super AbstractElemental>> sensor) {
		Validate.notNull(sensor);
		if (DEFAULT_SENSOR_TYPES.contains(sensor) || ADDITIONAL_SENSOR_TYPES.contains(sensor)) {
			ResourceLocation location = ForgeRegistries.SENSOR_TYPES.getKey(sensor);
			LOGGER.error("Duplicate SensorType registrations, may cause unexpected problems: "+ location.toString());
		} else {
			ADDITIONAL_SENSOR_TYPES.add(sensor);
		}
	}

}
