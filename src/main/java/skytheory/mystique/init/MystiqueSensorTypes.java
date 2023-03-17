package skytheory.mystique.init;

import net.minecraft.world.entity.ai.sensing.SensorType;
import skytheory.mystique.entity.ai.sensor.collect.CollectItemSensor;
import skytheory.mystique.entity.ai.sensor.core.CommonHostileSensor;
import skytheory.mystique.entity.ai.sensor.core.CommonTemptingSensor;

public class MystiqueSensorTypes {

	public static final SensorType<CommonTemptingSensor> CORE_TEMPTING = new SensorType<>(CommonTemptingSensor::new);
	public static final SensorType<CommonHostileSensor> CORE_HOSTILES = new SensorType<>(CommonHostileSensor::new);
	public static final SensorType<CollectItemSensor> COLLECT_ITEM = new SensorType<>(CollectItemSensor::new);
	
}
