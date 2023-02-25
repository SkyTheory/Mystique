package skytheory.mystique.client.renderer;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import skytheory.mystique.Mystique;
import skytheory.mystique.client.model.SalamanderModel;
import skytheory.mystique.entity.Salamander;

public class SalamanderRenderer extends AbstractElementalRenderer<Salamander, SalamanderModel> {

	public static ResourceLocation TEXTURE = new ResourceLocation(Mystique.MODID, "textures/entity/salamander.png");

	public SalamanderRenderer(EntityRendererProvider.Context ctx) {
		super(ctx, new SalamanderModel(ctx.bakeLayer(SalamanderModel.LAYER_LOCATION)));
	}
	
	@Override
	public ResourceLocation getTextureLocation(Salamander entity) {
		return TEXTURE;
	}

}
