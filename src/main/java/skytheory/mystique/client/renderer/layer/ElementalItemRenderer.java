package skytheory.mystique.client.renderer.layer;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import skytheory.mystique.client.model.AbstractElementalModel;
import skytheory.mystique.entity.AbstractElemental;

@FunctionalInterface
public interface ElementalItemRenderer {

	public <T extends AbstractElemental> void render(AbstractElementalModel<T> model, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, T pLivingEntity, ItemInHandRenderer pItemInHandRenderer);
	
}
