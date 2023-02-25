package skytheory.mystique.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import skytheory.mystique.Mystique;

// Made with Blockbench 4.4.3
// Exported for Minecraft version 1.17 - 1.18 with Mojang mappings
// Paste this class into your mod and generate all required imports


public class ManaInfuserModel extends Model {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(Mystique.MODID, "mana_infuser"), "main");
	private final ModelPart head;
	private final ModelPart base;

	public ManaInfuserModel(ModelPart root) {
		super(RenderType::entityTranslucent);
		this.head = root.getChild("head");
		this.base = root.getChild("base");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 31).addBox(-4.0F, -16.0F, -4.0F, 8.0F, 1.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(32, 31).addBox(-4.0F, -3.0F, -4.0F, 8.0F, 1.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(60, 0).addBox(2.0F, -15.0F, 2.0F, 1.0F, 12.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(48, 0).addBox(-3.0F, -15.0F, -3.0F, 1.0F, 12.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(52, 0).addBox(2.0F, -15.0F, -3.0F, 1.0F, 12.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(56, 0).addBox(-3.0F, -15.0F, 2.0F, 1.0F, 12.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(40, 13).addBox(-3.0F, -15.0F, -3.0F, 6.0F, 12.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		partdefinition.addOrReplaceChild("base", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, -1.0F, -6.0F, 12.0F, 1.0F, 12.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-8.0F, -2.0F, 5.0F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(36, 0).addBox(5.0F, -2.0F, 5.0F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(36, 5).addBox(5.0F, -2.0F, -8.0F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(0, 5).addBox(-8.0F, -2.0F, -8.0F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(20, 13).addBox(-4.0F, -2.0F, 5.0F, 8.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 13).addBox(-4.0F, -2.0F, -7.0F, 8.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 17).addBox(5.0F, -2.0F, -4.0F, 2.0F, 2.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(20, 17).addBox(-7.0F, -2.0F, -4.0F, 2.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 40);
	}

	@Override
	public void renderToBuffer(PoseStack pPoseStack, VertexConsumer pBuffer, int pPackedLight, int pPackedOverlay,
			float pRed, float pGreen, float pBlue, float pAlpha) {
		this.head.render(pPoseStack, pBuffer, pPackedLight, pPackedOverlay, pRed, pGreen, pBlue, pAlpha);
		this.base.render(pPoseStack, pBuffer, pPackedLight, pPackedOverlay, pRed, pGreen, pBlue, pAlpha);
	}

}