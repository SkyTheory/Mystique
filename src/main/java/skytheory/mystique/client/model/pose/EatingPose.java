package skytheory.mystique.client.model.pose;

import org.joml.Vector3f;

import net.minecraft.util.Mth;
import skytheory.lib.util.FloatUtils;
import skytheory.mystique.client.model.AbstractElementalModel;
import skytheory.mystique.client.model.RotateParameters;
import skytheory.mystique.entity.AbstractElemental;
import skytheory.mystique.entity.ai.behavior.eat.EatItem;

public class EatingPose {

	public static void transform(AbstractElementalModel<? extends AbstractElemental> model, ElementalRenderContext ctx) {
		Vector3f vec3f =  new Vector3f(Mth.cos(ctx.limbSwing() * 0.662f * RotateParameters.LIMB_SWING_WEIGHT / ctx.entity().getScale()) * ctx.limbSwingAmount(), 0.0f, 0.0f);
		DefaultPose.animHead(model, ctx);
		DefaultPose.swingLegs(model, vec3f);
		int eatingTicks = ctx.entity().getEntityData().get(AbstractElemental.DATA_EATING_TICKS);
		float stage = calcEatingPoseAmount(eatingTicks, ctx.partialTick());
		poseArm(model, ctx, stage);
	}

	public static float calcEatingPoseAmount(int ticks, float partialTick) {
		float progress = getEatingPoseProgress(ticks, partialTick);
		return progress * progress;
	}

	public static float getEatingPoseProgress(int ticks, float partialTick) {
		if (ticks < EatItem.DURATION_EAT_BEFORE) {
			return getEatingPoseProgressBefore(ticks, partialTick);
		}
		return 1.0f;
	}
	
	public static float getEatingPoseProgressBefore(float ticks, float partialTick) {
		float total = ticks + partialTick;
		total /= EatItem.DURATION_EAT_BEFORE;
		return total * total;
	}

	public static void poseArm(AbstractElementalModel<? extends AbstractElemental> model, ElementalRenderContext ctx, float stage) {
		// アイテムを渡したい！　みたいな感じで使えそうなポーズ
//		Vector3f vec3fLeft = new Vector3f(FloatUtils.toRadian(-82.5f), FloatUtils.toRadian(-10.0f), FloatUtils.toRadian(-20.0f));
		Vector3f vec3fLeft = new Vector3f(FloatUtils.toRadian(-100.0f), FloatUtils.toRadian(10.0f), FloatUtils.toRadian(-20.0f));
		vec3fLeft.mul(stage);
		Vector3f vec3fRight = new Vector3f(vec3fLeft);
		vec3fRight.mul(1.0f, -1.0f, -1.0f);
		model.armLeft.offsetRotation(vec3fLeft);
		model.armRight.offsetRotation(vec3fRight);
	}

}
