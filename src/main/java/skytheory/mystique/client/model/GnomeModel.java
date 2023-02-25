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
import skytheory.mystique.entity.Gnome;

// Made with Blockbench 4.4.1
// Exported for Minecraft version 1.17 - 1.18 with Mojang mappings
// Paste this class into your mod and generate all required imports


public class GnomeModel extends AbstractElementalModel<Gnome> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(Mystique.MODID, "gnome"), "main");

	public GnomeModel(ModelPart root) {
		super(root);
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -7.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(-0.5F)), PartPose.offset(0.0F, 8.0F, 0.0F));

		PartDefinition earLeft = head.addOrReplaceChild("earLeft", CubeListBuilder.create().texOffs(48, 0).addBox(-2.8F, -0.2F, -0.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(5.3F, -2.8F, 0.0F, 0.0873F, -0.4363F, 0.2182F));

		earLeft.addOrReplaceChild("earLeftDowner_r1", CubeListBuilder.create().texOffs(48, 0).addBox(-2.8F, -0.2F, -0.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.2618F));

		PartDefinition earRight = head.addOrReplaceChild("earRight", CubeListBuilder.create().texOffs(48, 0).addBox(-0.2F, -0.2F, -0.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(-5.3F, -2.8F, 0.0F, 0.0873F, 0.4363F, -0.2182F));

		earRight.addOrReplaceChild("earRightDowner_r1", CubeListBuilder.create().texOffs(48, 0).addBox(-0.2F, -0.2F, -0.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.2618F));

		PartDefinition hat = head.addOrReplaceChild("hat", CubeListBuilder.create().texOffs(34, 34).addBox(-4.0F, -7.0F, -3.5F, 8.0F, 8.0F, 7.0F, new CubeDeformation(0.05F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		hat.addOrReplaceChild("frontRight_r1", CubeListBuilder.create().texOffs(48, 2).addBox(-4.0F, -0.04F, 0.0F, 4.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -7.0F, -4.5F, 0.0F, 0.2618F, 0.0F));

		hat.addOrReplaceChild("frontLeft_r1", CubeListBuilder.create().texOffs(48, 2).mirror().addBox(0.0F, -0.04F, 0.0F, 4.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -7.0F, -4.5F, 0.0F, -0.2618F, 0.0F));

		PartDefinition goggle = hat.addOrReplaceChild("goggle", CubeListBuilder.create().texOffs(6, 32).addBox(-4.0F, -5.5F, -4.2F, 8.0F, 1.0F, 8.0F, new CubeDeformation(0.1F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		goggle.addOrReplaceChild("beltFrontRight_r1", CubeListBuilder.create().texOffs(11, 41).mirror().addBox(0.0F, 1.5F, 0.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(0, 32).addBox(0.0F, 0.0F, -1.5F, 4.0F, 4.0F, 3.0F, new CubeDeformation(-1.2F)), PartPose.offsetAndRotation(0.0F, -7.0F, -5.0F, 0.0F, -0.2618F, 0.0F));

		goggle.addOrReplaceChild("beltFrontLeft_r1", CubeListBuilder.create().texOffs(11, 41).addBox(-4.0F, 1.5F, 0.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 32).addBox(-4.0F, 0.0F, -1.5F, 4.0F, 4.0F, 3.0F, new CubeDeformation(-1.2F)), PartPose.offsetAndRotation(0.0F, -7.0F, -5.0F, 0.0F, 0.2618F, 0.0F));

		PartDefinition torso = partdefinition.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(24, 16).addBox(-3.0F, 0.0F, -2.0F, 6.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 8.0F, 0.0F));

		PartDefinition scapula = torso.addOrReplaceChild("scapula", CubeListBuilder.create(), PartPose.offset(0.0F, 1.5F, 0.0F));

		scapula.addOrReplaceChild("armLeft", CubeListBuilder.create().texOffs(40, 0).addBox(-0.1F, -1.0F, -1.0F, 2.0F, 9.0F, 2.0F, new CubeDeformation(-0.2F))
		.texOffs(30, 29).mirror().addBox(0.0F, -1.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(3.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.1745F));

		scapula.addOrReplaceChild("armRight", CubeListBuilder.create().texOffs(32, 0).addBox(-1.9F, -1.0F, -1.0F, 2.0F, 9.0F, 2.0F, new CubeDeformation(-0.2F))
		.texOffs(30, 29).addBox(-2.0F, -1.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.1745F));

		PartDefinition cloth = torso.addOrReplaceChild("cloth", CubeListBuilder.create().texOffs(0, 46).mirror().addBox(-0.9F, -0.8F, -3.5F, 5.0F, 11.0F, 7.0F, new CubeDeformation(-0.9F)).mirror(false)
		.texOffs(0, 46).addBox(-4.1F, -0.8F, -3.5F, 5.0F, 11.0F, 7.0F, new CubeDeformation(-0.9F))
		.texOffs(44, 20).addBox(-3.0F, 0.0F, -2.0F, 6.0F, 9.0F, 4.0F, new CubeDeformation(0.1F))
		.texOffs(44, 7).addBox(-3.0F, 0.0F, -2.0F, 6.0F, 9.0F, 4.0F, new CubeDeformation(0.05F))
		.texOffs(24, 47).addBox(-3.4F, 3.4F, -3.6F, 3.0F, 6.0F, 2.0F, new CubeDeformation(-0.9F))
		.texOffs(24, 47).mirror().addBox(0.4F, 3.4F, -3.6F, 3.0F, 6.0F, 2.0F, new CubeDeformation(-0.9F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

		cloth.addOrReplaceChild("jacketRight_r1", CubeListBuilder.create().texOffs(40, 49).addBox(-4.3F, -7.1F, -6.4F, 5.0F, 8.0F, 7.0F, new CubeDeformation(-0.7F)), PartPose.offsetAndRotation(0.0F, 6.6F, 3.1F, 0.0F, -0.0436F, 0.0F));

		cloth.addOrReplaceChild("jacketLeft_r1", CubeListBuilder.create().texOffs(40, 49).mirror().addBox(-0.7F, -7.1F, -6.4F, 5.0F, 8.0F, 7.0F, new CubeDeformation(-0.7F)).mirror(false), PartPose.offsetAndRotation(0.0F, 6.6F, 3.1F, 0.0F, 0.0436F, 0.0F));

		PartDefinition pelvis = partdefinition.addOrReplaceChild("pelvis", CubeListBuilder.create(), PartPose.offset(0.0F, 17.0F, 0.0F));

		pelvis.addOrReplaceChild("legLeft", CubeListBuilder.create().texOffs(12, 16).addBox(-1.0F, -1.1F, -1.5F, 3.0F, 7.0F, 3.0F, new CubeDeformation(-0.1F))
		.texOffs(14, 26).addBox(-1.0F, 5.0F, -2.5F, 3.0F, 2.0F, 4.0F, new CubeDeformation(-0.1F))
		.texOffs(24, 55).mirror().addBox(-0.9F, -1.0F, -1.5F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.05F)).mirror(false)
		.texOffs(17, 41).mirror().addBox(-0.9F, 5.0F, -2.5F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.1F)).mirror(false)
		.texOffs(0, 41).mirror().addBox(-1.4F, 4.5F, -3.0F, 4.0F, 3.0F, 2.0F, new CubeDeformation(-0.39F)).mirror(false)
		.texOffs(35, 51).mirror().addBox(-0.8F, 2.9F, -1.5F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.2F)).mirror(false), PartPose.offset(1.0F, 0.0F, 0.0F));

		pelvis.addOrReplaceChild("legRight", CubeListBuilder.create().texOffs(0, 16).addBox(-2.0F, -1.1F, -1.5F, 3.0F, 7.0F, 3.0F, new CubeDeformation(-0.1F))
		.texOffs(0, 26).addBox(-2.0F, 5.0F, -2.5F, 3.0F, 2.0F, 4.0F, new CubeDeformation(-0.1F))
		.texOffs(17, 41).addBox(-2.1F, 5.0F, -2.5F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.1F))
		.texOffs(0, 41).addBox(-2.6F, 4.5F, -3.0F, 4.0F, 3.0F, 2.0F, new CubeDeformation(-0.39F))
		.texOffs(24, 55).addBox(-2.1F, -1.0F, -1.5F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.05F))
		.texOffs(35, 51).addBox(-2.2F, 2.9F, -1.5F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.2F)), PartPose.offset(-1.0F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

}