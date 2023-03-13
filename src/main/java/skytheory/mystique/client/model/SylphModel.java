package skytheory.mystique.client.model;

import org.joml.Vector3f;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import skytheory.lib.util.FloatUtils;
import skytheory.mystique.Mystique;
import skytheory.mystique.entity.Sylph;
import skytheory.mystique.util.ModelPoseUtils;

// Made with Blockbench 4.4.1
// Exported for Minecraft version 1.17 - 1.18 with Mojang mappings
// Paste this class into your mod and generate all required imports


public class SylphModel extends AbstractElementalModel<Sylph> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(Mystique.MODID, "sylph"), "main");

	protected ModelPart wingLeft;
	protected ModelPart wingRight;
	protected ModelPart wingLeftRoot;
	protected ModelPart wingRightRoot;
	protected ModelPart wingLeftMiddle;
	protected ModelPart wingRightMiddle;
	protected ModelPart wingLeftTip;
	protected ModelPart wingRightTip;

	public SylphModel(ModelPart root) {
		super(root);
		this.wingLeft = torso.getChild("wingLeft");
		this.wingRight = torso.getChild("wingRight");
		this.wingLeftRoot = wingLeft.getChild("rootLeft");
		this.wingRightRoot = wingRight.getChild("rootRight");
		this.wingLeftMiddle = wingLeftRoot.getChild("middleLeft");
		this.wingRightMiddle = wingRightRoot.getChild("middleRight");
		this.wingLeftTip = wingLeftMiddle.getChild("tipLeft");
		this.wingRightTip = wingRightMiddle.getChild("tipRight");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -7.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(-0.5F)), PartPose.offset(0.0F, 8.0F, 0.0F));

		head.addOrReplaceChild("hair", CubeListBuilder.create().texOffs(12, 37).addBox(1.4F, -7.0F, -4.6F, 3.0F, 6.0F, 3.0F, new CubeDeformation(-0.7F))
		.texOffs(0, 36).addBox(-5.0F, -6.5F, -0.8F, 3.0F, 7.0F, 3.0F, new CubeDeformation(-0.4F))
		.texOffs(23, 45).addBox(-1.5F, -5.0F, 3.0F, 3.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(24, 0).addBox(1.0F, -5.8F, -4.3F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.4F))
		.texOffs(24, 2).addBox(1.9F, -5.5F, -4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition earLeft = head.addOrReplaceChild("earLeft", CubeListBuilder.create().texOffs(48, 0).addBox(-2.8F, -0.2F, -0.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(5.3F, -2.8F, 0.0F, 0.0873F, -0.4363F, 0.2182F));

		earLeft.addOrReplaceChild("earLeftDowner_r1", CubeListBuilder.create().texOffs(48, 0).addBox(-2.8F, -0.2F, -0.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.2618F));

		PartDefinition earRight = head.addOrReplaceChild("earRight", CubeListBuilder.create().texOffs(48, 0).addBox(-0.2F, -0.2F, -0.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(-5.3F, -2.8F, 0.0F, 0.0873F, 0.4363F, -0.2182F));

		earRight.addOrReplaceChild("earRightDowner_r1", CubeListBuilder.create().texOffs(48, 0).addBox(-0.2F, -0.2F, -0.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.2618F));

		PartDefinition torso = partdefinition.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(24, 16).addBox(-3.0F, 0.0F, -2.0F, 6.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 8.0F, 0.0F));

		PartDefinition scapula = torso.addOrReplaceChild("scapula", CubeListBuilder.create(), PartPose.offset(0.0F, 1.5F, 0.0F));

		PartDefinition armLeft = scapula.addOrReplaceChild("armLeft", CubeListBuilder.create().texOffs(40, 0).addBox(-0.1F, -1.0F, -1.0F, 2.0F, 9.0F, 2.0F, new CubeDeformation(-0.2F))
		.texOffs(29, 50).mirror().addBox(-0.1F, 1.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offsetAndRotation(3.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.1745F));

		armLeft.addOrReplaceChild("itemLeftHand", CubeListBuilder.create(), PartPose.offset(1.0F, 7.8F, -0.8F));

		PartDefinition armRight = scapula.addOrReplaceChild("armRight", CubeListBuilder.create().texOffs(32, 0).addBox(-1.9F, -1.0F, -1.0F, 2.0F, 9.0F, 2.0F, new CubeDeformation(-0.2F))
		.texOffs(29, 50).addBox(-1.9F, 1.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(-3.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.1745F));

		armRight.addOrReplaceChild("itemRightHand", CubeListBuilder.create(), PartPose.offset(-1.0F, 7.8F, -0.8F));

		torso.addOrReplaceChild("cloth", CubeListBuilder.create().texOffs(44, 7).addBox(-3.0F, 0.0F, -2.0F, 6.0F, 9.0F, 4.0F, new CubeDeformation(0.05F))
		.texOffs(44, 20).addBox(-3.0F, 0.0F, -2.0F, 6.0F, 9.0F, 4.0F, new CubeDeformation(0.1F))
		.texOffs(0, 46).addBox(-4.0F, 0.5F, -3.0F, 8.0F, 12.0F, 6.0F, new CubeDeformation(-0.6F))
		.texOffs(22, 29).addBox(-4.0F, 7.5F, -3.0F, 8.0F, 4.0F, 6.0F, new CubeDeformation(-0.8F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition wingLeft = torso.addOrReplaceChild("wingLeft", CubeListBuilder.create(), PartPose.offsetAndRotation(1.4F, 2.9F, 2.4F, 0.0F, -0.0436F, 0.0F));

		PartDefinition rootLeft = wingLeft.addOrReplaceChild("rootLeft", CubeListBuilder.create().texOffs(54, 41).mirror().addBox(-1.4F, -0.4F, -0.4F, 4.0F, 11.0F, 1.0F, new CubeDeformation(-0.4F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.3927F));

		PartDefinition middleLeft = rootLeft.addOrReplaceChild("middleLeft", CubeListBuilder.create().texOffs(56, 33).mirror().addBox(-0.4F, -6.6F, -0.4F, 3.0F, 7.0F, 1.0F, new CubeDeformation(-0.4F)).mirror(false), PartPose.offset(2.2F, 4.7F, 0.2F));

		middleLeft.addOrReplaceChild("tipLeft", CubeListBuilder.create().texOffs(32, 58).mirror().addBox(-0.4F, -4.6F, -0.4F, 6.0F, 5.0F, 1.0F, new CubeDeformation(-0.4F)).mirror(false), PartPose.offset(2.2F, -3.0F, 0.2F));

		PartDefinition wingRight = torso.addOrReplaceChild("wingRight", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.4F, 2.9F, 2.4F, 0.0F, 0.0436F, 0.0F));

		PartDefinition rootRight = wingRight.addOrReplaceChild("rootRight", CubeListBuilder.create().texOffs(54, 41).addBox(-2.6F, -0.4F, -0.4F, 4.0F, 11.0F, 1.0F, new CubeDeformation(-0.4F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.3927F));

		PartDefinition middleRight = rootRight.addOrReplaceChild("middleRight", CubeListBuilder.create().texOffs(56, 33).addBox(-2.6F, -6.6F, -0.4F, 3.0F, 7.0F, 1.0F, new CubeDeformation(-0.4F)), PartPose.offset(-2.2F, 4.7F, 0.2F));

		middleRight.addOrReplaceChild("tipRight", CubeListBuilder.create().texOffs(32, 58).addBox(-5.6F, -4.6F, -0.4F, 6.0F, 5.0F, 1.0F, new CubeDeformation(-0.4F)), PartPose.offset(-2.2F, -3.0F, 0.2F));

		PartDefinition pelvis = partdefinition.addOrReplaceChild("pelvis", CubeListBuilder.create(), PartPose.offset(0.0F, 17.0F, 0.0F));

		PartDefinition legLeft = pelvis.addOrReplaceChild("legLeft", CubeListBuilder.create().texOffs(12, 16).addBox(-1.0F, -1.1F, -1.5F, 3.0F, 7.0F, 3.0F, new CubeDeformation(-0.1F))
		.texOffs(14, 26).addBox(-1.0F, 5.1F, -2.5F, 3.0F, 2.0F, 4.0F, new CubeDeformation(-0.1F))
		.texOffs(37, 51).mirror().addBox(-1.0F, 5.05F, -2.5F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(52, 2).mirror().addBox(-1.0F, 3.1F, -1.5F, 3.0F, 2.0F, 3.0F, new CubeDeformation(-0.05F)).mirror(false), PartPose.offset(1.0F, 0.0F, 0.0F));

		legLeft.addOrReplaceChild("hemLeft_r1", CubeListBuilder.create().texOffs(46, 53).mirror().addBox(-5.5F, 4.0F, -2.0F, 5.0F, 7.0F, 4.0F, new CubeDeformation(-0.4F)).mirror(false), PartPose.offsetAndRotation(2.0F, -6.0F, 0.0F, 0.0F, -0.0436F, -0.1309F));

		PartDefinition legRight = pelvis.addOrReplaceChild("legRight", CubeListBuilder.create().texOffs(0, 16).addBox(-2.0F, -1.1F, -1.5F, 3.0F, 7.0F, 3.0F, new CubeDeformation(-0.1F))
		.texOffs(0, 26).addBox(-2.0F, 5.1F, -2.5F, 3.0F, 2.0F, 4.0F, new CubeDeformation(-0.1F))
		.texOffs(37, 51).addBox(-2.0F, 5.05F, -2.5F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(52, 2).addBox(-2.0F, 3.1F, -1.5F, 3.0F, 2.0F, 3.0F, new CubeDeformation(-0.05F)), PartPose.offset(-1.0F, 0.0F, 0.0F));

		legRight.addOrReplaceChild("hemRight_r1", CubeListBuilder.create().texOffs(46, 53).addBox(0.5F, 4.0F, -2.0F, 5.0F, 7.0F, 4.0F, new CubeDeformation(-0.4F)), PartPose.offsetAndRotation(-2.0F, -6.0F, 0.0F, 0.0F, 0.0436F, 0.1309F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(Sylph entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
		float flapping = Mth.abs(Mth.cos(limbSwing * 0.662f * RotateParameters.LIMB_SWING_WEIGHT / entity.getScale()) * limbSwingAmount);
		wingLeft.resetPose();
		wingLeftRoot.resetPose();
		wingLeftMiddle.resetPose();
		wingLeftTip.resetPose();
		wingRight.resetPose();
		wingRightRoot.resetPose();
		wingRightMiddle.resetPose();
		wingRightTip.resetPose();
		this.flapWings(flapping, limbSwingAmount);
	}
	
	protected void flapWings(float flapping, float limbSwingAmount) {
		Vector3f vec3f1 = new Vector3f(0.0f, flapping * FloatUtils.toRadian(-20.0f), 0.0f);
		Vector3f vec3f2 = new Vector3f(0.0f, 0.0f, flapping * FloatUtils.toRadian(2.5f));
		Vector3f vec3f3 = new Vector3f(0.0f, flapping * FloatUtils.toRadian(-12.5f), flapping * FloatUtils.toRadian(-2.5f));
		Vector3f vec3f4 = new Vector3f(0.0f, flapping * FloatUtils.toRadian(-12.5f), flapping * FloatUtils.toRadian(-2.5f));
		ModelPoseUtils.mirrorMotion(wingLeft, wingRight, vec3f1);
		ModelPoseUtils.mirrorMotion(wingLeftRoot, wingRightRoot, vec3f2);
		ModelPoseUtils.mirrorMotion(wingLeftMiddle, wingRightMiddle, vec3f3);
		ModelPoseUtils.mirrorMotion(wingLeftTip, wingRightTip, vec3f4);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}