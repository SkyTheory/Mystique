package skytheory.mystique.entity.ai.contract;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.CountDownCooldownTicks;
import net.minecraft.world.entity.ai.behavior.DoNothing;
import net.minecraft.world.entity.ai.behavior.RandomStroll;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import skytheory.lib.entity.ai.behavior.RunOnePrioritized;
import skytheory.lib.entity.ai.behavior.RunOneRandomized;
import skytheory.lib.util.ItemHandlerMode;
import skytheory.lib.util.ItemHandlerUtils;
import skytheory.mystique.entity.AbstractElemental;
import skytheory.mystique.entity.ai.behavior.collect.PickUpItemBehavior;
import skytheory.mystique.entity.ai.behavior.collect.WalkToItemBehavior;
import skytheory.mystique.entity.ai.behavior.common.LookAtNearestPlayer;
import skytheory.mystique.entity.ai.behavior.common.MoveToNearestPlayer;
import skytheory.mystique.init.MystiqueMemoryModuleTypes;
import skytheory.mystique.init.MystiqueSensorTypes;

public class CollectContract implements MystiqueContract {

	@Override
	public Collection<MemoryModuleType<?>> getMemoryModules() {
		return Set.of(MystiqueMemoryModuleTypes.COLLECT_ITEM);
	}

	@Override
	public Collection<SensorType<? extends Sensor<? super AbstractElemental>>> getSensorTypes() {
		return Set.of(
				MystiqueSensorTypes.COLLECT_ITEM
				);
	}

	@Override
	public List<? extends BehaviorControl<? super AbstractElemental>> getActions() {
		return List.of(
				new CountDownCooldownTicks(MystiqueMemoryModuleTypes.COMMON_LOOK_COOLDOWN),
				new RunOnePrioritized<AbstractElemental>()
				.addBehavior(new PickUpItemBehavior())
				.addBehavior(new WalkToItemBehavior())
				.addBehavior(new LookAtNearestPlayer())
				.addBehavior(new RunOneRandomized<AbstractElemental>()
						.addBehavior(RandomStroll.stroll(1.0f))
						.addBehavior(new MoveToNearestPlayer(1.0f, 6.0d, 2.0d))
						.addBehavior(new DoNothing(120, 240))
						)
				);
	}

	@Override
	public InteractionResult activeInteract(AbstractElemental entity, Player player) {
		ItemStack entityHold = entity.getItemInHand(InteractionHand.MAIN_HAND);
		if (!entityHold.isEmpty()) {
			ItemStack remainder = ItemHandlerUtils.giveItemToPlayer(player, entityHold, ItemHandlerMode.EXECUTE);
			entity.setItemInHand(InteractionHand.MAIN_HAND, remainder);
			if (entityHold.getCount() != entity.getItemInHand(InteractionHand.MAIN_HAND).getCount()) {
				player.playNotifySound(SoundEvents.ITEM_PICKUP, SoundSource.PLAYERS, 1.0f, 1.0f);
			}
			return InteractionResult.CONSUME;
		}
		return InteractionResult.PASS;
	}
	
}
