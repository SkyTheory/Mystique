package skytheory.mystique.client.model;

import org.joml.Vector3f;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.logging.LogUtils;

import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import skytheory.lib.util.FloatUtils;
import skytheory.mystique.entity.AbstractElemental;
import skytheory.mystique.entity.ai.behavior.eat.EatItem;
import skytheory.mystique.util.ModelPoseUtils;

public class AbstractElementalModel<T extends AbstractElemental> extends EntityModel<T> implements ArmedModel {
	
	protected float partialTick;
	
	protected final ModelPart root;
	protected final ModelPart head;
	protected final ModelPart torso;
	protected final ModelPart scapula;
	protected final ModelPart armLeft;
	protected final ModelPart armRight;
	protected final ModelPart pelvis;
	protected final ModelPart legLeft;
	protected final ModelPart legRight;
	protected final ModelPart itemLeftHand;
	protected final ModelPart itemRightHand;

	public AbstractElementalModel(ModelPart root) {
		this.root = root;
		this.head = root.getChild("head");
		this.torso = root.getChild("torso");
		this.scapula = torso.getChild("scapula");
		this.armLeft = scapula.getChild("armLeft");
		this.armRight = scapula.getChild("armRight");
		this.pelvis = root.getChild("pelvis");
		this.legLeft = pelvis.getChild("legLeft");
		this.legRight = pelvis.getChild("legRight");
		this.itemLeftHand = armLeft.getChild("itemLeftHand");
		this.itemRightHand = armRight.getChild("itemRightHand");
	}

	@Override
	public void prepareMobModel(T pEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTick) {
		this.partialTick = pPartialTick;
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.armLeft.resetPose();
		this.armRight.resetPose();
		this.legLeft.resetPose();
		this.legRight.resetPose();
		this.itemLeftHand.resetPose();
		this.itemRightHand.resetPose();
		this.animHead(netHeadYaw, headPitch);
		Vector3f vec3f =  new Vector3f(Mth.cos(limbSwing * 0.662f * RotateParameters.LIMB_SWING_WEIGHT / entity.getScale()) * limbSwingAmount, 0.0f, 0.0f);
		this.poseArms(entity, vec3f);
		this.swingLegs(vec3f);
	}

	protected void poseArms(T entity, Vector3f vec3f) {
		if (entity.isEatingItem()) {
			int eatingTicks = entity.getEntityData().get(AbstractElemental.DATA_EATING_TICKS);
			this.eatingPoseArm(this.calcEatingPoseAmount(eatingTicks, partialTick));
		} else {
			this.swingArms(vec3f);
		}
	}

	public float calcEatingPoseAmount(int ticks, float partialTick) {
		float progress = getEatingPoseProgress(ticks, partialTick);
		return progress * progress;
	}

	public float getEatingPoseProgress(int ticks, float partialTick) {
		if (ticks < EatItem.DURATION_EAT_BEFORE) {
			return getEatingPoseProgressBefore(ticks, partialTick);
		}
		return 1.0f;
	}
	
	public float getEatingPoseProgressBefore(float ticks, float partialTick) {
		float total = ticks + partialTick;
		total /= EatItem.DURATION_EAT_BEFORE;
		return total * total;
	}
	
	public float getEatingPoseProgressAfter(float ticks, float partialTick) {
		float total = ticks - (EatItem.DURATION_EAT_BEFORE + EatItem.DURATION_EAT_BEFORE);
		LogUtils.getLogger().debug(ticks + " : " + total);
		total = EatItem.DURATION_EAT_BEFORE - total;
		total /= EatItem.DURATION_EAT_BEFORE;
		total = Math.min(total - partialTick, 0.0f);
		return total * total;
	}
	public void animHead(float netHeadYaw, float headPitch) {
		this.head.setRotation(FloatUtils.toRadian(headPitch), FloatUtils.toRadian(netHeadYaw), 0.0f);
	}

	public void eatingPoseArm(float stage) {
		// アイテムを渡したい！　みたいな感じで使えそうなポーズ
//		Vector3f vec3fLeft = new Vector3f(FloatUtils.toRadian(-82.5f), FloatUtils.toRadian(-10.0f), FloatUtils.toRadian(-20.0f));
		Vector3f vec3fLeft = new Vector3f(FloatUtils.toRadian(-100.0f), FloatUtils.toRadian(10.0f), FloatUtils.toRadian(-20.0f));
		vec3fLeft.mul(stage);
		Vector3f vec3fRight = new Vector3f(vec3fLeft);
		vec3fRight.mul(1.0f, -1.0f, -1.0f);
		this.armLeft.offsetRotation(vec3fLeft);
		this.armRight.offsetRotation(vec3fRight);
	}

	public void swingArms(Vector3f vec3f) {
		ModelPoseUtils.mirrorMotion(armLeft, armRight, vec3f, RotateParameters.ARM_SWING_AMOUNT);
	}

	public void swingLegs(Vector3f vec3f) {
		ModelPoseUtils.mirrorMotion(legRight, legLeft, vec3f, RotateParameters.LEG_SWING_AMOUNT);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public void translateToHand(HumanoidArm pSide, PoseStack pPoseStack) {
		ModelPart arm = pSide.equals(HumanoidArm.LEFT) ? armLeft : armRight;
		ModelPart hand = pSide.equals(HumanoidArm.LEFT) ? itemLeftHand : itemRightHand;
		root.translateAndRotate(pPoseStack);
		torso.translateAndRotate(pPoseStack);
		scapula.translateAndRotate(pPoseStack);
		arm.translateAndRotate(pPoseStack);
		hand.translateAndRotate(pPoseStack);
	}

}