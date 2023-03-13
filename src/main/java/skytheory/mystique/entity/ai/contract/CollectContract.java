package skytheory.mystique.entity.ai.contract;

import java.util.Collection;
import java.util.Set;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.item.ItemStack;
import skytheory.lib.util.ItemHandlerMode;
import skytheory.lib.util.ItemHandlerUtils;
import skytheory.mystique.entity.AbstractElemental;
import skytheory.mystique.entity.ai.behavior.collect.PickUpItemBehavior;
import skytheory.mystique.entity.ai.behavior.collect.WalkToItemBehavior;
import skytheory.mystique.entity.ai.behavior.core.FollowTemptationWithoutCooldown;
import skytheory.mystique.init.MystiqueActivities;
import skytheory.mystique.init.MystiqueMemoryModuleTypes;
import skytheory.mystique.init.MystiqueSensorTypes;
import skytheory.mystique.item.MystiqueContract;

public class CollectContract implements MystiqueContract {

	@Override
	public Activity getActivity() {
		return MystiqueActivities.COLLECT;
	}

	@Override
	public Collection<MemoryModuleType<?>> getMemoryModules() {
		return Set.of(MystiqueMemoryModuleTypes.COLLECT_ITEM);
	}

	@Override
	public Collection<SensorType<? extends Sensor<? super AbstractElemental>>> getSensorTypes() {
		return Set.of(MystiqueSensorTypes.COLLECT_ITEM);
	}

	@Override
	public Collection<? extends BehaviorControl<? super AbstractElemental>> getActions() {
		return Set.of(
				new PickUpItemBehavior(),
				new WalkToItemBehavior(),
				new FollowTemptationWithoutCooldown(1.0f)
				);
	}

	@Override
	public InteractionResult interaction(AbstractElemental entity, Player player) {
		ItemStack entityHold = entity.getItemInHand(InteractionHand.MAIN_HAND);
		if (!entityHold.isEmpty()) {
			ItemStack remainder = ItemHandlerUtils.giveItemToPlayer(player, entityHold, ItemHandlerMode.EXECUTE);
			entity.setItemInHand(InteractionHand.MAIN_HAND, remainder);
			player.playNotifySound(SoundEvents.ITEM_PICKUP, SoundSource.PLAYERS, 1.0f, 1.0f);
			return InteractionResult.CONSUME;
		}
		return InteractionResult.PASS;
	}

}
