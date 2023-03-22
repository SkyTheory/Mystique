package skytheory.mystique.client.rendertype;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;

import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class MystiqueRenderTypes {

	public static RenderType translucentNoCull(ResourceLocation texture) {
		return Internal.translucentNoCull(texture);
	}

	private static class Internal extends RenderType {

		private Internal(String name, VertexFormat fmt, VertexFormat.Mode glMode, int size, boolean doCrumbling, boolean depthSorting, Runnable onEnable, Runnable onDisable) {
			super(name, fmt, glMode, size, doCrumbling, depthSorting, onEnable, onDisable);
			throw new IllegalStateException("This class must not be instantiated");
		}

		private static RenderType translucentNoCull(ResourceLocation texture) {
			CompositeState renderState = CompositeState.builder()
			.setShaderState(RenderType.RENDERTYPE_TRANSLUCENT_SHADER)
			.setTextureState(new RenderStateShard.TextureStateShard(texture, false, false))
			.setTransparencyState(TRANSLUCENT_TRANSPARENCY)
			.setCullState(NO_CULL)
			.setLightmapState(LIGHTMAP)
			.createCompositeState(true);
			return create("forge_entity_unsorted_translucent", DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 256, true, false, renderState);
		}
	}

}
