package skytheory.mystique.entity.ai.contract;

import java.util.Collection;
import java.util.Set;

import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import skytheory.mystique.client.model.pose.EatingPose;
import skytheory.mystique.client.model.pose.ElementalPoseTransformer;
import skytheory.mystique.client.renderer.itemlayer.EatingItemRenderer;
import skytheory.mystique.client.renderer.itemlayer.ElementalItemRenderer;
import skytheory.mystique.entity.AbstractElemental;
import skytheory.mystique.entity.ai.behavior.eat.EatItem;
import skytheory.mystique.entity.ai.behavior.eat.ThrowNonEdibleItem;
import skytheory.mystique.init.MystiqueActivities;

public class EatItemContract implements MystiqueContract {

	@Override
	public Activity getActivity() {
		return MystiqueActivities.EAT;
	}
	
	@Override
	public boolean canApplyContract(AbstractElemental entity) {
		if (entity.getBrain().hasMemoryValue(MemoryModuleType.IS_PANICKING)) return false;
		return !entity.getEatingItem().isEmpty(); 
	}
	
	@Override
	public int getPriority() {
		return 60;
	}

	@Override
	public Collection<MemoryModuleType<?>> getMemoryModules() {
		return Set.of();
	}

	@Override
	public Collection<SensorType<? extends Sensor<? super AbstractElemental>>> getSensorTypes() {
		return Set.of();
	}

	@Override
	public Collection<? extends BehaviorControl<? super AbstractElemental>> getActions() {
		return Set.of(
				new EatItem(),
				new ThrowNonEdibleItem()
				);
	}
	
	@OnlyIn(Dist.CLIENT)
	@Override
	public ElementalPoseTransformer getPoseTransformer(AbstractElemental entity) {
		return EatingPose::transform;
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public ElementalItemRenderer getItemRenderer(AbstractElemental entity) {
		return EatingItemRenderer::render;
	}
	
}
