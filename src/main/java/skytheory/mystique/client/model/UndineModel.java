package skytheory.mystique.client.model;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;
import skytheory.mystique.Mystique;
import skytheory.mystique.entity.Undine;

// Made with Blockbench 4.4.1
// Exported for Minecraft version 1.17 - 1.18 with Mojang mappings
// Paste this class into your mod and generate all required imports


public class UndineModel extends AbstractElementalModel<Undine> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(Mystique.MODID, "undine"), "main");

	public UndineModel(ModelPart root) {
		super(root);
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -7.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(-0.5F)), PartPose.offset(0.0F, 8.0F, 0.0F));

		PartDefinition veil = head.addOrReplaceChild("veil", CubeListBuilder.create().texOffs(0, 42).addBox(-1.0F, -2.0F, 3.0F, 2.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		veil.addOrReplaceChild("hatRight_r1", CubeListBuilder.create().texOffs(20, 29).addBox(-4.1F, 0.1F, -4.1F, 4.0F, 2.0F, 8.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.0F, -7.6F, 0.0F, 0.0F, 0.0F, -0.0873F));

		veil.addOrReplaceChild("hatLeft_r1", CubeListBuilder.create().texOffs(20, 29).mirror().addBox(0.1F, 0.1F, -4.1F, 4.0F, 2.0F, 8.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offsetAndRotation(0.0F, -7.6F, 0.0F, 0.0F, 0.0F, 0.0873F));

		veil.addOrReplaceChild("veilRight_r1", CubeListBuilder.create().texOffs(0, 42).addBox(-4.7F, -23.0F, -4.0F, 4.0F, 14.0F, 8.0F, new CubeDeformation(-0.05F)), PartPose.offsetAndRotation(0.0F, 16.0F, 0.0F, 0.0F, 0.0F, 0.0436F));

		veil.addOrReplaceChild("veilLeft_r1", CubeListBuilder.create().texOffs(0, 42).mirror().addBox(0.7F, -23.0F, -4.0F, 4.0F, 14.0F, 8.0F, new CubeDeformation(-0.05F)).mirror(false), PartPose.offsetAndRotation(0.0F, 16.0F, 0.0F, 0.0F, 0.0F, -0.0436F));

		PartDefinition earLeft = head.addOrReplaceChild("earLeft", CubeListBuilder.create().texOffs(48, 0).addBox(-2.8F, -0.2F, -0.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(5.3F, -2.8F, 0.0F, 0.0873F, -0.4363F, 0.2182F));

		earLeft.addOrReplaceChild("earLeftDowner_r1", CubeListBuilder.create().texOffs(48, 0).addBox(-2.8F, -0.2F, -0.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.2618F));

		PartDefinition earRight = head.addOrReplaceChild("earRight", CubeListBuilder.create().texOffs(48, 0).addBox(-0.2F, -0.2F, -0.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(-5.3F, -2.8F, 0.0F, 0.0873F, 0.4363F, -0.2182F));

		earRight.addOrReplaceChild("earRightDowner_r1", CubeListBuilder.create().texOffs(48, 0).addBox(-0.2F, -0.2F, -0.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.2618F));

		PartDefinition torso = partdefinition.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(24, 16).addBox(-3.0F, 0.0F, -2.0F, 6.0F, 9.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(44, 35).addBox(-3.0F, 7.1F, -2.0F, 6.0F, 2.0F, 4.0F, new CubeDeformation(0.15F))
		.texOffs(0, 32).addBox(-3.0F, 1.0F, -2.0F, 6.0F, 3.0F, 4.0F, new CubeDeformation(0.15F)), PartPose.offset(0.0F, 8.0F, 0.0F));

		PartDefinition scapula = torso.addOrReplaceChild("scapula", CubeListBuilder.create(), PartPose.offset(0.0F, 1.5F, 0.0F));

		PartDefinition armLeft = scapula.addOrReplaceChild("armLeft", CubeListBuilder.create().texOffs(40, 0).addBox(-0.1F, -1.0F, -1.0F, 2.0F, 9.0F, 2.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(3.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.1745F));

		PartDefinition sleeveLeft = armLeft.addOrReplaceChild("sleeveLeft", CubeListBuilder.create().texOffs(16, 39).mirror().addBox(-1.1F, -0.5F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(-1.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

		sleeveLeft.addOrReplaceChild("sleeveLeft_r1", CubeListBuilder.create().texOffs(56, 0).addBox(3.5F, -9.5F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, 14.5F, 0.0F, 0.0F, 0.0F, -0.0436F));

		armLeft.addOrReplaceChild("itemLeftHand", CubeListBuilder.create(), PartPose.offset(1.0F, 7.8F, -0.8F));

		PartDefinition armRight = scapula.addOrReplaceChild("armRight", CubeListBuilder.create().texOffs(32, 0).addBox(-1.9F, -1.0F, -1.0F, 2.0F, 9.0F, 2.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(-3.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.1745F));

		PartDefinition sleeveRight = armRight.addOrReplaceChild("sleeveRight", CubeListBuilder.create().texOffs(16, 39).addBox(-2.9F, -0.5F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(-1.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		sleeveRight.addOrReplaceChild("sleeveRight_r1", CubeListBuilder.create().texOffs(56, 0).mirror().addBox(-5.5F, -9.5F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(3.0F, 14.5F, 0.0F, 0.0F, 0.0F, 0.0436F));

		armRight.addOrReplaceChild("itemRightHand", CubeListBuilder.create(), PartPose.offset(-1.0F, 7.8F, -0.8F));

		torso.addOrReplaceChild("underwear", CubeListBuilder.create().texOffs(44, 7).addBox(-3.0F, 0.0F, -2.0F, 6.0F, 9.0F, 4.0F, new CubeDeformation(0.05F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		torso.addOrReplaceChild("dress", CubeListBuilder.create().texOffs(44, 20).addBox(-3.0F, 0.0F, -2.0F, 6.0F, 9.0F, 4.0F, new CubeDeformation(0.1F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cape = torso.addOrReplaceChild("cape", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		cape.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(26, 42).mirror().addBox(-0.5F, -16.0F, -3.5F, 6.0F, 15.0F, 7.0F, new CubeDeformation(-1.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 16.0F, 0.0F, 0.0F, 0.0436F, -0.0873F));

		cape.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(26, 42).addBox(-5.5F, -16.0F, -3.5F, 6.0F, 15.0F, 7.0F, new CubeDeformation(-1.0F)), PartPose.offsetAndRotation(0.0F, 16.0F, 0.0F, 0.0F, -0.0436F, 0.0873F));

		PartDefinition pelvis = partdefinition.addOrReplaceChild("pelvis", CubeListBuilder.create(), PartPose.offset(0.0F, 17.0F, 0.0F));

		PartDefinition legLeft = pelvis.addOrReplaceChild("legLeft", CubeListBuilder.create().texOffs(12, 16).addBox(-1.0F, -1.1F, -1.5F, 3.0F, 7.0F, 3.0F, new CubeDeformation(-0.1F))
		.texOffs(14, 26).addBox(-1.0F, 5.0F, -2.5F, 3.0F, 2.0F, 4.0F, new CubeDeformation(-0.1F))
		.texOffs(52, 57).mirror().addBox(-1.0F, 0.9F, -1.5F, 3.0F, 4.0F, 3.0F, new CubeDeformation(-0.05F)).mirror(false), PartPose.offset(1.0F, 0.0F, 0.0F));

		legLeft.addOrReplaceChild("sandalLeft", CubeListBuilder.create().texOffs(50, 41).addBox(-1.0F, 6.0F, -2.5F, 3.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(48, 3).addBox(-1.5F, 4.5F, -2.4F, 4.0F, 2.0F, 2.0F, new CubeDeformation(-0.5F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition legRight = pelvis.addOrReplaceChild("legRight", CubeListBuilder.create().texOffs(0, 16).addBox(-2.0F, -1.1F, -1.5F, 3.0F, 7.0F, 3.0F, new CubeDeformation(-0.1F))
		.texOffs(0, 26).addBox(-2.0F, 5.0F, -2.5F, 3.0F, 2.0F, 4.0F, new CubeDeformation(-0.1F))
		.texOffs(52, 57).addBox(-2.0F, 0.9F, -1.5F, 3.0F, 4.0F, 3.0F, new CubeDeformation(-0.05F)), PartPose.offset(-1.0F, 0.0F, 0.0F));

		legRight.addOrReplaceChild("sandalRight", CubeListBuilder.create().texOffs(50, 41).mirror().addBox(-2.0F, 6.0F, -2.5F, 3.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(48, 3).addBox(-2.5F, 4.5F, -2.4F, 4.0F, 2.0F, 2.0F, new CubeDeformation(-0.5F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

}