package skytheory.mystique.init;

import net.minecraft.world.entity.ai.sensing.SensorType;
import skytheory.mystique.entity.ai.sensor.ElementalTemptingSensor;
import skytheory.mystique.entity.ai.sensor.HostileSensor;

public class MystiqueEntitySensors {

	public static final SensorType<ElementalTemptingSensor> TEMPTING = new SensorType<ElementalTemptingSensor>(ElementalTemptingSensor::new);
	public static final SensorType<HostileSensor> HOSTILES = new SensorType<HostileSensor>(HostileSensor::new);
	
}
