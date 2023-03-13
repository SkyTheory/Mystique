package skytheory.mystique.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.HumanoidArm;
import skytheory.mystique.client.model.pose.ElementalRenderContext;
import skytheory.mystique.entity.AbstractElemental;

public class AbstractElementalModel<T extends AbstractElemental> extends EntityModel<T> implements ArmedModel {
	
	protected float partialTick;
	
	public final ModelPart root;
	public final ModelPart head;
	public final ModelPart torso;
	public final ModelPart scapula;
	public final ModelPart armLeft;
	public final ModelPart armRight;
	public final ModelPart pelvis;
	public final ModelPart legLeft;
	public final ModelPart legRight;
	public final ModelPart itemLeftHand;
	public final ModelPart itemRightHand;

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
		ElementalRenderContext ctx = new ElementalRenderContext(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, partialTick);
		entity.getContract().getPoseTransformer(entity).transform(this, ctx);
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