package skytheory.mystique.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import skytheory.mystique.client.model.AbstractElementalModel;
import skytheory.mystique.entity.AbstractElemental;
import skytheory.mystique.entity.ai.behavior.eat.EatItem;

public class ElementalItemLayer<T extends AbstractElemental, M extends AbstractElementalModel<T>> extends RenderLayer<T, M> {

	public final ItemRenderer itemRenderer;

	public ElementalItemLayer(RenderLayerParent<T, M> pRenderer, ItemRenderer itemRenderer) {
		super(pRenderer);
		this.itemRenderer = itemRenderer;
	}

	@Override
	public void render(PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, T pLivingEntity,
			float pLimbSwing, float pLimbSwingAmount, float pPartialTick, float pAgeInTicks, float pNetHeadYaw,
			float pHeadPitch) {
		if (pLivingEntity.isEatingItem()) {
			renderEatingItem(pPoseStack, pBuffer, pPackedLight, pLivingEntity);
			return;
		}
	}

	protected void renderEatingItem(PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, T pLivingEntity) {
		int eatingTicks = pLivingEntity.getEntityData().get(AbstractElemental.DATA_EATING_TICKS);
		if (eatingTicks > EatItem.DURATION_EAT_BEFORE) {
			ItemStack stack = pLivingEntity.getEatingItem();
			pPoseStack.pushPose();
			pPoseStack.translate(0.0d, 0.5d, -0.4d);
			pPoseStack.mulPose(Axis.XP.rotationDegrees(180.0f));
			Level level = pLivingEntity.getLevel();
			boolean leftHand = false;
			int id = pLivingEntity.getId();
			this.itemRenderer.renderStatic(pLivingEntity, stack, ItemTransforms.TransformType.GROUND, leftHand, pPoseStack, pBuffer, level, pPackedLight, OverlayTexture.NO_OVERLAY, id);
			pPoseStack.popPose();
		}
	}

}
