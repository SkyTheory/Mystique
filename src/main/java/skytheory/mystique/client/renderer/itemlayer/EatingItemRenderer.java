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
import skytheory.mystique.entity.ai.behavior.eat.EatItem;

public class EatingItemRenderer {
	
	public static <T extends AbstractElemental> void render(AbstractElementalModel<T> model, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, T pLivingEntity, ItemInHandRenderer pItemInHandRenderer) {
		int eatingTicks = pLivingEntity.getEntityData().get(AbstractElemental.DATA_EATING_TICKS);
		if (eatingTicks > EatItem.DURATION_EAT_BEFORE) {
			ItemStack stack = pLivingEntity.getEatingItem();
			HumanoidArm arm = pLivingEntity.getMainArm();
			TransformType type = arm == HumanoidArm.LEFT ? TransformType.THIRD_PERSON_LEFT_HAND : TransformType.THIRD_PERSON_RIGHT_HAND;
			pPoseStack.pushPose();
			pPoseStack.translate(0.0d, 0.5d, -0.4d);
			pPoseStack.mulPose(Axis.XP.rotationDegrees(180.0f));
			pItemInHandRenderer.renderItem(pLivingEntity, stack, type, arm == HumanoidArm.LEFT, pPoseStack, pBuffer, pPackedLight);
			pPoseStack.popPose();
		}
	}
	
}
