package skytheory.mystique.init;

import net.minecraft.world.entity.ai.sensing.SensorType;
import skytheory.mystique.entity.ai.sensor.collect.CollectItemSensor;
import skytheory.mystique.entity.ai.sensor.core.IdleTemptingSensor;
import skytheory.mystique.entity.ai.sensor.core.IdleHostileSensor;

public class MystiqueSensorTypes {

	public static final SensorType<IdleTemptingSensor> TEMPTING = new SensorType<>(IdleTemptingSensor::new);
	public static final SensorType<IdleHostileSensor> HOSTILES = new SensorType<>(IdleHostileSensor::new);
	public static final SensorType<CollectItemSensor> COLLECT_ITEM = new SensorType<>(CollectItemSensor::new);
	
}
