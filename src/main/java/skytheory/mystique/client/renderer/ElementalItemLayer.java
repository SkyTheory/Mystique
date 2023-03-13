package skytheory.mystique.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import skytheory.mystique.client.model.AbstractElementalModel;
import skytheory.mystique.entity.AbstractElemental;

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
		pLivingEntity.getContract().getItemRenderer(pLivingEntity).render(getParentModel(), pPoseStack, pBuffer, pPackedLight, pLivingEntity, itemInHandRenderer);
	}

}
