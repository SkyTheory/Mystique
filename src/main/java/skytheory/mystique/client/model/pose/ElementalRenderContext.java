package skytheory.mystique.client.model.pose;

import skytheory.mystique.entity.AbstractElemental;

public record ElementalRenderContext(AbstractElemental entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float partialTick) {	
}
