package skytheory.mystique.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemStack;
import skytheory.mystique.client.model.AbstractElementalModel;
import skytheory.mystique.entity.AbstractElemental;
import skytheory.mystique.entity.ai.behavior.eat.EatItem;

public class ElementalItemLayer<T extends AbstractElemental, M extends AbstractElementalModel<T>> extends RenderLayer<T, M> {

	public final ItemInHandRenderer itemInHandRenderer;

	public ElementalItemLayer(RenderLayerParent<T, M> pRenderer, ItemInHandRenderer itemInHandRenderer) {
		super(pRenderer);
		this.itemInHandRenderer = itemInHandRenderer;
	}

	@Override
	public void render(PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, T pLivingEntity,
			float pLimbSwing, float pLimbSwingAmount, float pPartialTick, float pAgeInTicks, float pNetHeadYaw,
			float pHeadPitch) {
		if (pLivingEntity.isEatingItem()) {
			renderEatingItem(pPoseStack, pBuffer, pPackedLight, pLivingEntity);
		} else {
			renderHandheldItem(pPoseStack, pBuffer, pPackedLight, pLivingEntity);
		}
	}

	protected void renderEatingItem(PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, T pLivingEntity) {
		int eatingTicks = pLivingEntity.getEntityData().get(AbstractElemental.DATA_EATING_TICKS);
		if (eatingTicks > EatItem.DURATION_EAT_BEFORE) {
			ItemStack stack = pLivingEntity.getEatingItem();
			HumanoidArm arm = pLivingEntity.getMainArm();
			TransformType type = arm == HumanoidArm.LEFT ? TransformType.THIRD_PERSON_LEFT_HAND : TransformType.THIRD_PERSON_RIGHT_HAND;
			pPoseStack.pushPose();
			pPoseStack.translate(0.0d, 0.5d, -0.4d);
			pPoseStack.mulPose(Axis.XP.rotationDegrees(180.0f));
			this.itemInHandRenderer.renderItem(pLivingEntity, stack, type, arm == HumanoidArm.LEFT, pPoseStack, pBuffer, pPackedLight);
			pPoseStack.popPose();
		}
	}

	protected void renderHandheldItem(PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, T pLivingEntity) {
		ItemStack stack = pLivingEntity.getMainHandItem();
		if (!stack.isEmpty()) {
			HumanoidArm arm = pLivingEntity.getMainArm();
			TransformType type = arm == HumanoidArm.LEFT ? TransformType.THIRD_PERSON_LEFT_HAND : TransformType.THIRD_PERSON_RIGHT_HAND;
			float scale = (1.0f + pLivingEntity.getScale()) / 2.0f;
			pPoseStack.pushPose();
			this.getParentModel().translateToHand(arm, pPoseStack);
			pPoseStack.mulPose(Axis.XP.rotationDegrees(-90.0F));
			pPoseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
			pPoseStack.scale(scale, scale, scale);
			this.itemInHandRenderer.renderItem(pLivingEntity, stack, type, arm == HumanoidArm.LEFT, pPoseStack, pBuffer, pPackedLight);
			pPoseStack.popPose();

		}
	}

}
