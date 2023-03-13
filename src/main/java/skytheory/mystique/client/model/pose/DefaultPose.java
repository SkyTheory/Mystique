package skytheory.mystique.client.model.pose;

import org.joml.Vector3f;

import net.minecraft.util.Mth;
import skytheory.lib.util.FloatUtils;
import skytheory.mystique.client.model.AbstractElementalModel;
import skytheory.mystique.client.model.RotateParameters;
import skytheory.mystique.entity.AbstractElemental;
import skytheory.mystique.util.ModelPoseUtils;

/**
 * AbstractElementalModelのポーズを変更するためのクラス
 * @author SkyTheory
 *
 */
public class DefaultPose {

	/**
	 * デフォルトのポーズ
	 * @param model
	 * @param ctx
	 */
	public static void transform(AbstractElementalModel<? extends AbstractElemental> model, ElementalRenderContext ctx) {
		Vector3f vec3f =  new Vector3f(Mth.cos(ctx.limbSwing() * 0.662f * RotateParameters.LIMB_SWING_WEIGHT / ctx.entity().getScale()) * ctx.limbSwingAmount(), 0.0f, 0.0f);
		animHead(model, ctx);
		swingArms(model, vec3f);
		swingLegs(model, vec3f);
	}
	
	public static void animHead(AbstractElementalModel<? extends AbstractElemental> model, ElementalRenderContext ctx) {
		model.head.setRotation(FloatUtils.toRadian(ctx.headPitch()), FloatUtils.toRadian(ctx.netHeadYaw()), 0.0f);
	}
	
	public static void swingArms(AbstractElementalModel<? extends AbstractElemental> model, Vector3f vec3f) {
		ModelPoseUtils.mirrorMotion(model.armLeft, model.armRight, vec3f, RotateParameters.ARM_SWING_AMOUNT);
	}

	public static void swingLegs(AbstractElementalModel<? extends AbstractElemental> model, Vector3f vec3f) {
		ModelPoseUtils.mirrorMotion(model.legRight, model.legLeft, vec3f, RotateParameters.LEG_SWING_AMOUNT);
	}
	
}
