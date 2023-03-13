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
import skytheory.mystique.entity.Salamander;

// Made with Blockbench 4.4.1
// Exported for Minecraft version 1.17 - 1.18 with Mojang mappings
// Paste this class into your mod and generate all required imports


public class SalamanderModel extends AbstractElementalModel<Salamander> {
	
	public SalamanderModel(ModelPart root) {
		super(root);
	}

	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(Mystique.MODID, "salamander"), "main");

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -7.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(-0.5F))
		.texOffs(23, 29).addBox(-4.0F, -6.0F, -1.0F, 8.0F, 10.0F, 5.0F, new CubeDeformation(-0.4F)), PartPose.offset(0.0F, 8.0F, 0.0F));

		PartDefinition hat = head.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.offset(0.0F, 16.0F, 0.0F));

		hat.addOrReplaceChild("hatRightTop_r1", CubeListBuilder.create().texOffs(0, 37).addBox(6.0F, -5.0F, 1.0F, 5.0F, 4.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-10.0F, -17.0F, -4.0F, 0.0F, 0.0436F, -0.0873F));

		hat.addOrReplaceChild("hatRightFront_r1", CubeListBuilder.create().texOffs(24, 50).addBox(-4.0F, -6.0F, -1.0F, 5.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -17.0F, -4.0F, 0.0F, 0.3927F, -0.0873F));

		hat.addOrReplaceChild("hatLeftTop_r1", CubeListBuilder.create().texOffs(0, 37).mirror().addBox(-11.0F, -5.0F, 1.0F, 5.0F, 4.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(10.0F, -17.0F, -4.0F, 0.0F, -0.0436F, 0.0873F));

		hat.addOrReplaceChild("hatLeftFront_r1", CubeListBuilder.create().texOffs(24, 50).mirror().addBox(-1.0F, -6.0F, -1.0F, 5.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -17.0F, -4.0F, 0.0F, -0.3927F, 0.0873F));

		hat.addOrReplaceChild("honeLeft", CubeListBuilder.create().texOffs(54, 43).mirror().addBox(0.0F, 0.0F, 1.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(58, 45).mirror().addBox(3.0F, -1.0F, 1.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(60, 47).mirror().addBox(4.0F, -1.0F, 2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(2.0F, -23.0F, -3.0F, 0.0873F, -0.7854F, -0.9599F));

		hat.addOrReplaceChild("honeRight", CubeListBuilder.create().texOffs(54, 43).addBox(-4.0F, 0.0F, 1.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(58, 45).addBox(-5.0F, -1.0F, 1.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(60, 47).addBox(-5.0F, -1.0F, 2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, -23.0F, -3.0F, 0.0873F, 0.7854F, 0.9599F));

		PartDefinition earLeft = head.addOrReplaceChild("earLeft", CubeListBuilder.create().texOffs(48, 0).addBox(-2.8F, -0.2F, -0.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(5.3F, -2.8F, 0.0F, 0.0873F, -0.4363F, 0.2182F));

		earLeft.addOrReplaceChild("earLeftDowner_r1", CubeListBuilder.create().texOffs(48, 0).addBox(-2.8F, -0.2F, -0.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.2618F));

		PartDefinition earRight = head.addOrReplaceChild("earRight", CubeListBuilder.create().texOffs(48, 0).addBox(-0.2F, -0.2F, -0.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(-5.3F, -2.8F, 0.0F, 0.0873F, 0.4363F, -0.2182F));

		earRight.addOrReplaceChild("earRightDowner_r1", CubeListBuilder.create().texOffs(48, 0).addBox(-0.2F, -0.2F, -0.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.2618F));

		PartDefinition torso = partdefinition.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(24, 16).addBox(-3.0F, 0.0F, -2.0F, 6.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 8.0F, 0.0F));

		torso.addOrReplaceChild("cloth", CubeListBuilder.create().texOffs(32, 50).addBox(-4.0F, -1.0F, -3.8F, 8.0F, 7.0F, 7.0F, new CubeDeformation(-0.8F))
		.texOffs(26, 45).addBox(-3.0F, 1.9F, -2.9F, 6.0F, 3.0F, 1.0F, new CubeDeformation(-0.05F))
		.texOffs(0, 49).addBox(-4.5F, 5.9F, -3.5F, 9.0F, 8.0F, 7.0F, new CubeDeformation(-1.1F))
		.texOffs(44, 7).addBox(-3.0F, 0.0F, -2.0F, 6.0F, 9.0F, 4.0F, new CubeDeformation(0.05F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		torso.addOrReplaceChild("inner", CubeListBuilder.create().texOffs(44, 20).addBox(-3.0F, 0.0F, -2.0F, 6.0F, 9.0F, 4.0F, new CubeDeformation(0.1F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition scapula = torso.addOrReplaceChild("scapula", CubeListBuilder.create(), PartPose.offset(0.0F, 1.5F, 0.0F));

		PartDefinition armLeft = scapula.addOrReplaceChild("armLeft", CubeListBuilder.create().texOffs(40, 0).addBox(-0.1F, -1.0F, -1.0F, 2.0F, 9.0F, 2.0F, new CubeDeformation(-0.2F))
		.texOffs(0, 32).mirror().addBox(-0.6F, 0.5F, -1.5F, 3.0F, 2.0F, 3.0F, new CubeDeformation(-0.5F)).mirror(false)
		.texOffs(56, 53).mirror().addBox(-0.1F, 5.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offsetAndRotation(3.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.1745F));

		armLeft.addOrReplaceChild("itemLeftHand", CubeListBuilder.create(), PartPose.offset(1.0F, 7.8F, -0.8F));

		PartDefinition armRight = scapula.addOrReplaceChild("armRight", CubeListBuilder.create().texOffs(32, 0).addBox(-1.9F, -1.0F, -1.0F, 2.0F, 9.0F, 2.0F, new CubeDeformation(-0.2F))
		.texOffs(0, 32).addBox(-2.4F, 0.5F, -1.5F, 3.0F, 2.0F, 3.0F, new CubeDeformation(-0.5F))
		.texOffs(56, 53).addBox(-1.9F, 5.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(-3.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.1745F));

		armRight.addOrReplaceChild("itemRightHand", CubeListBuilder.create(), PartPose.offset(-1.0F, 7.8F, -0.8F));

		PartDefinition pelvis = partdefinition.addOrReplaceChild("pelvis", CubeListBuilder.create(), PartPose.offset(0.0F, 17.0F, 0.0F));

		pelvis.addOrReplaceChild("legLeft", CubeListBuilder.create().texOffs(12, 16).addBox(-1.0F, -1.1F, -1.5F, 3.0F, 7.0F, 3.0F, new CubeDeformation(-0.1F))
		.texOffs(14, 26).addBox(-1.0F, 5.1F, -2.5F, 3.0F, 2.0F, 4.0F, new CubeDeformation(-0.1F))
		.texOffs(50, 33).mirror().addBox(-1.0F, 5.05F, -2.5F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(52, 39).mirror().addBox(-1.0F, 4.1F, -1.5F, 3.0F, 1.0F, 3.0F, new CubeDeformation(-0.05F)).mirror(false), PartPose.offset(1.0F, 0.0F, 0.0F));

		pelvis.addOrReplaceChild("legRight", CubeListBuilder.create().texOffs(0, 16).addBox(-2.0F, -1.1F, -1.5F, 3.0F, 7.0F, 3.0F, new CubeDeformation(-0.1F))
		.texOffs(0, 26).addBox(-2.0F, 5.1F, -2.5F, 3.0F, 2.0F, 4.0F, new CubeDeformation(-0.1F))
		.texOffs(50, 33).addBox(-2.0F, 5.05F, -2.5F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(52, 39).addBox(-2.0F, 4.1F, -1.5F, 3.0F, 1.0F, 3.0F, new CubeDeformation(-0.05F)), PartPose.offset(-1.0F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}
	
}