package skytheory.mystique.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import skytheory.lib.util.BlockRotation;
import skytheory.mystique.Mystique;
import skytheory.mystique.client.model.FieldMarkerModel;
import skytheory.mystique.entity.FieldMarker;

public class FieldMarkerRenderer extends EntityRenderer<FieldMarker> {

	public static ResourceLocation TEXTURE = new ResourceLocation(Mystique.MODID, "textures/entity/field_marker.png");

	private final FieldMarkerModel model;

	public FieldMarkerRenderer(EntityRendererProvider.Context ctx) {
		super(ctx);
		this.model = new FieldMarkerModel(ctx.bakeLayer(FieldMarkerModel.LAYER_LOCATION));
	}

	public void render(FieldMarker pEntity, float pEntityYaw, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
		if (!pEntity.isPickable()) return;
		if (pEntity == null || pEntity.isInvisible()) return;
		pPoseStack.pushPose();
		pPoseStack.scale(-1.0f, -1.0f, 1.0f);
		pPoseStack.translate(0.0f, -0.5f, 0.0f);
		BlockRotation rotation = pEntity.getRotation();
		pPoseStack.pushPose();
		pPoseStack.mulPose(rotation.getQuaternion());
		pPoseStack.translate(0.0f, -1.0f, 0.0f);
		this.model.renderToBuffer(pPoseStack, pBuffer.getBuffer(this.model.renderType(getTextureLocation(pEntity))), pPackedLight, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0f);
		pPoseStack.popPose();
		pPoseStack.popPose();
	}

	@Override
	public ResourceLocation getTextureLocation(FieldMarker pEntity) {
		return TEXTURE;
	}

}
