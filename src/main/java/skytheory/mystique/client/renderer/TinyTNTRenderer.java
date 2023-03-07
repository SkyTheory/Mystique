package skytheory.mystique.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import skytheory.lib.util.BlockRotation;
import skytheory.mystique.Mystique;
import skytheory.mystique.client.model.TinyTNTModel;
import skytheory.mystique.entity.TinyTNT;

public class TinyTNTRenderer extends EntityRenderer<TinyTNT> {

	public static ResourceLocation TEXTURE = new ResourceLocation(Mystique.MODID, "textures/entity/tiny_tnt.png");
	public static ResourceLocation TEXTURE_WHITE = new ResourceLocation(Mystique.MODID, "textures/entity/tiny_tnt_white.png");

	private final TinyTNTModel model;

	public TinyTNTRenderer(EntityRendererProvider.Context ctx) {
		super(ctx);
		this.model = new TinyTNTModel(ctx.bakeLayer(TinyTNTModel.LAYER_LOCATION));
	}

	public void render(TinyTNT pEntity, float pEntityYaw, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
		if (!pEntity.isPickable()) return;
		pPoseStack.pushPose();
		pPoseStack.scale(-1.0f, -1.0f, 1.0f);
		pPoseStack.translate(0.0f, -0.5f, 0.0f);
		BlockRotation rotation = pEntity.getRotation();
		pPoseStack.pushPose();
		pPoseStack.mulPose(rotation.getQuaternion());
		pPoseStack.translate(0.0f, -1.0f, 0.0f);
		float alpha = (float) Mth.sin((((float)pEntity.getFuse()) - pPartialTick) * 0.6f) * 0.5f + 0.5f;
		this.model.renderToBuffer(pPoseStack, pBuffer.getBuffer(this.model.renderType(getTextureLocation(pEntity))), pPackedLight, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0f);
		this.model.renderToBuffer(pPoseStack, pBuffer.getBuffer(RenderType.entityTranslucent(TEXTURE_WHITE)), pPackedLight, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, alpha);
		pPoseStack.popPose();
		pPoseStack.popPose();
	}

	@Override
	public ResourceLocation getTextureLocation(TinyTNT pEntity) {
		return TEXTURE;
	}

}
