package skytheory.mystique.client.renderer.itemlayer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemStack;
import skytheory.mystique.client.model.AbstractElementalModel;
import skytheory.mystique.entity.AbstractElemental;

public class DefaultItemRenderer {

	public static <T extends AbstractElemental> void render(AbstractElementalModel<T> model, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, T pLivingEntity, ItemInHandRenderer pItemInHandRenderer) {
		ItemStack stack = pLivingEntity.getMainHandItem();
		if (!stack.isEmpty()) {
			HumanoidArm arm = pLivingEntity.getMainArm();
			TransformType type = arm == HumanoidArm.LEFT ? TransformType.THIRD_PERSON_LEFT_HAND : TransformType.THIRD_PERSON_RIGHT_HAND;
			float scale = (1.0f + pLivingEntity.getScale()) / 2.0f;
			pPoseStack.pushPose();
			model.translateToHand(arm, pPoseStack);
			pPoseStack.mulPose(Axis.XP.rotationDegrees(-90.0F));
			pPoseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
			pPoseStack.scale(scale, scale, scale);
			pItemInHandRenderer.renderItem(pLivingEntity, stack, type, arm == HumanoidArm.LEFT, pPoseStack, pBuffer, pPackedLight);
			pPoseStack.popPose();
		} 
	}

}
