package skytheory.mystique.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import skytheory.mystique.client.model.AbstractElementalModel;
import skytheory.mystique.entity.AbstractElemental;

public abstract class AbstractElementalRenderer<T extends AbstractElemental, M extends AbstractElementalModel<T>> extends MobRenderer<T, M> {

	public static final float SHADOW = 0.5f;

	public AbstractElementalRenderer(EntityRendererProvider.Context ctx, M model) {
		super(ctx, model, SHADOW);
		this.addLayer(new ElementalItemLayer<>(this, ctx.getItemRenderer()));
	}
	
	@Override
	public void render(T pEntity, float pEntityYaw, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
		if (pEntity == null || pEntity.isInvisible()) return;
		this.resize(pEntity, pPoseStack);
		super.render(pEntity, pEntityYaw, pPartialTick, pPoseStack, pBuffer, pPackedLight);
	}

	public void resize(T pEntity, PoseStack pPoseStack) {
		float size = pEntity.getScale();
		if (size != 1.0f) {
			this.shadowRadius = SHADOW * size;
			pPoseStack.scale(size, size, size);
		}
	}

}
