package skytheory.mystique.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;

public class ManaCondenserModel extends Model {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	private final ModelPart bone;
	private final ModelPart inner;
	private final ModelPart xz;
	private final ModelPart xy;
	private final ModelPart yz;

	public ManaCondenserModel(ModelPart root) {
		super(RenderType::entityTranslucent);
		this.bone = root.getChild("bone");
		this.inner = root.getChild("inner");
		this.xz = root.getChild("xz");
		this.xy = root.getChild("xy");
		this.yz = root.getChild("yz");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		partdefinition.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(72, 72).addBox(-5.0F, -13.0F, -5.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		partdefinition.addOrReplaceChild("inner", CubeListBuilder.create().texOffs(8, 46).addBox(-1.0F, -3.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(8, 29).addBox(-1.0F, -9.0F, -7.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 29).addBox(-1.0F, -9.0F, 5.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 46).addBox(-1.0F, -15.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(8, 12).addBox(5.0F, -9.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 12).addBox(-7.0F, -9.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		partdefinition.addOrReplaceChild("xz", CubeListBuilder.create().texOffs(48, 1).addBox(-8.0F, -16.0F, -8.0F, 16.0F, 1.0F, 16.0F, new CubeDeformation(0.0F))
		.texOffs(0, 34).addBox(-8.0F, -1.0F, -8.0F, 16.0F, 1.0F, 16.0F, new CubeDeformation(0.0F))
		.texOffs(0, 17).addBox(-8.0F, -7.0F, -8.0F, 16.0F, 1.0F, 16.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-8.0F, -10.0F, -8.0F, 16.0F, 1.0F, 16.0F, new CubeDeformation(0.0F))
		.texOffs(0, 83).addBox(-7.0F, -9.0F, -7.0F, 6.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(18, 59).addBox(1.0F, -9.0F, -7.0F, 6.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(18, 51).addBox(1.0F, -9.0F, 1.0F, 6.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(36, 53).addBox(-7.0F, -9.0F, 1.0F, 6.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(66, 18).addBox(-7.0F, -7.0F, -1.0F, 6.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 59).addBox(1.0F, -7.0F, -1.0F, 6.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(48, 18).addBox(-7.0F, -15.0F, -1.0F, 6.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 51).addBox(1.0F, -15.0F, -1.0F, 6.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		partdefinition.addOrReplaceChild("xy", CubeListBuilder.create().texOffs(96, 0).addBox(-8.0F, -16.0F, 7.0F, 16.0F, 16.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(67, 92).addBox(-8.0F, -16.0F, -8.0F, 16.0F, 16.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(84, 18).addBox(-8.0F, -16.0F, 1.0F, 16.0F, 16.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(33, 82).addBox(-8.0F, -16.0F, -2.0F, 16.0F, 16.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		partdefinition.addOrReplaceChild("yz", CubeListBuilder.create().texOffs(0, 51).addBox(7.0F, -16.0F, -8.0F, 1.0F, 16.0F, 16.0F, new CubeDeformation(0.0F))
		.texOffs(66, 34).addBox(-8.0F, -16.0F, -8.0F, 1.0F, 16.0F, 16.0F, new CubeDeformation(0.0F))
		.texOffs(48, 50).addBox(-2.0F, -16.0F, -8.0F, 1.0F, 16.0F, 16.0F, new CubeDeformation(0.0F))
		.texOffs(48, 18).addBox(1.0F, -16.0F, -8.0F, 1.0F, 16.0F, 16.0F, new CubeDeformation(0.0F))
		.texOffs(48, 0).addBox(-1.0F, -15.0F, -7.0F, 2.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(0, 34).addBox(-1.0F, -7.0F, -7.0F, 2.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-1.0F, -15.0F, 1.0F, 2.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(0, 17).addBox(-1.0F, -7.0F, 1.0F, 2.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 144, 144);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		bone.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		inner.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		xz.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		xy.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		yz.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}