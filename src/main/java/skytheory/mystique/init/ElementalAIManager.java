package skytheory.mystique.init;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.google.common.collect.ImmutableSet;

import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraftforge.registries.IForgeRegistryInternal;
import skytheory.mystique.entity.AbstractElemental;
import skytheory.mystique.entity.ai.contract.MystiqueContract;

/**
 * ElementalのAIに登録するMemoryModuleとSensorの管理を行うクラス
 * @author SkyTheory
 *
 */
public class ElementalAIManager {

	private static final Set<MemoryModuleType<?>> ADDITIONAL_MEMORY_TYPES = new HashSet<>();
	private static final Set<SensorType<? extends Sensor<? super AbstractElemental>>> ADDITIONAL_SENSOR_TYPES = new HashSet<>();

	static void init(IForgeRegistryInternal<MystiqueContract> owner) {
		owner.getValues().stream()
		.map(MystiqueContract::getMemoryModules)
		.flatMap(Collection::stream)
		.distinct()
		.forEach(ADDITIONAL_MEMORY_TYPES::add);
		owner.getValues().stream()
		.map(MystiqueContract::getSensorTypes)
		.flatMap(Collection::stream)
		.distinct()
		.forEach(ADDITIONAL_SENSOR_TYPES::add);
	}
	
	public static Set<MemoryModuleType<?>> getMemoryTypes() {
		Set<MemoryModuleType<?>> set = new HashSet<>();
		ADDITIONAL_MEMORY_TYPES.forEach(set::add);
		return ImmutableSet.copyOf(set);
	}

	public static Set<SensorType<? extends Sensor<? super AbstractElemental>>> getSensorTypes() {
		Set<SensorType<? extends Sensor<? super AbstractElemental>>> set = new HashSet<>();
		ADDITIONAL_SENSOR_TYPES.forEach(set::add);
		return ImmutableSet.copyOf(set);
	}
	
}
