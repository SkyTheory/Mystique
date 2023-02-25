package skytheory.mystique.init;

import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.entity.schedule.Schedule;
import net.minecraft.world.entity.schedule.ScheduleBuilder;

public class MystiqueEntitySchedules {

	public static final Schedule ELEMENTAL_SCHEDULE = new ScheduleBuilder(new Schedule())
			.changeActivityAt(10, Activity.IDLE)
			.changeActivityAt(2000, Activity.WORK)
			.changeActivityAt(11000, Activity.IDLE)
			.changeActivityAt(12000, Activity.REST)
			.build();
	
}
