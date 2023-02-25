package skytheory.mystique.entity.ai.behavior.core;

import java.util.Collections;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fluids.FluidType;

public class FloatFluid extends Behavior<Mob> {

	public FloatFluid() {
		super(Collections.emptyMap());
	}

	protected boolean checkExtraStartConditions(ServerLevel pLevel, Mob pOwner) {
		if (pOwner.isInFluidType((fluidType, height) -> pOwner.canSwimInFluidType(fluidType) && height > pOwner.getFluidJumpThreshold())) {
			return true;
		}
		return false;
	}

	protected boolean canStillUse(ServerLevel pLevel, Mob pEntity, long pGameTime) {
		return this.checkExtraStartConditions(pLevel, pEntity);
	}

	protected void tick(ServerLevel pLevel, Mob pOwner, long pGameTime) {
		FluidType fluid = pOwner.getEyeInFluidType();
		if (fluid.isAir()) {
			fluid = pOwner.getMaxHeightFluidType();
			if (!fluid.isAir()) {
				Vec3 vec3 = pOwner.getDeltaMovement();
				pOwner.setDeltaMovement(vec3.x, Math.min(0.1d, vec3.y + 0.1d), vec3.z);
			}
		} else {
			pOwner.getJumpControl().jump();
		}
	}
}
