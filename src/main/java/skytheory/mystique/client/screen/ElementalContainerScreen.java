package skytheory.mystique.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import skytheory.mystique.Mystique;
import skytheory.mystique.entity.AbstractElemental;
import skytheory.mystique.gui.ElementalContainerMenu;

public class ElementalContainerScreen extends AbstractContainerScreen<ElementalContainerMenu> {

	private static final ResourceLocation BACKGROUND = new ResourceLocation(Mystique.MODID, "textures/gui/gui_mainframe.png");

	public ElementalContainerMenu menu;
	private final AbstractElemental entity;
	private float mouseX;
	private float mouseY;

	public ElementalContainerScreen(ElementalContainerMenu menu, Inventory playerInv, Component component) {
		super(menu, playerInv, component);
		// デフォルト値だが、明示的に宣言しておけばコードを見返したときに理解しやすい
		this.imageWidth = 176;
		this.imageHeight = 166;
		this.entity = menu.entity;
	}

	@Override
	protected void renderLabels(PoseStack pPoseStack, int pMouseX, int pMouseY) {
		this.font.draw(pPoseStack, this.title, (float)this.titleLabelX, (float)this.titleLabelY, 0x404040);
		this.font.draw(pPoseStack, this.playerInventoryTitle, (float)this.inventoryLabelX, (float)this.inventoryLabelY, 0x404040);
	}

	/**
	 * 背景を描画し、内容を描画し、マウスオーバー時の説明を描画する
	 */
	@Override
	public void render(PoseStack poseStack, int x, int y, float ticks) {
		this.mouseX = x;
		this.mouseY = y;
		this.renderBackground(poseStack);
		super.render(poseStack, x, y, ticks);
		this.renderTooltip(poseStack, x, y);
	}

	/**
	 * renderBackgroundから呼ばれてGUIの背景画像を描画する
	 */
	@Override
	protected void renderBg(PoseStack poseStack, float ticks, int x, int y) {
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
		RenderSystem.setShaderTexture(0, BACKGROUND);
		int fixedX = (this.width - this.imageWidth) / 2;
		int fixedY = (this.height - this.imageHeight) / 2;
		this.blit(poseStack, fixedX, fixedY, 0, 0, this.imageWidth, this.imageHeight);
		int i = (this.width - this.imageWidth) / 2;
		int j = (this.height - this.imageHeight) / 2;
		int guiX = 37;
		int guiY = 62;
		int mouseYRev = -32;
		int scale = (int) (24.0f / entity.getScale());
		InventoryScreen.renderEntityInInventory(i + guiX, j + guiY, scale, (float)(i + guiX) - this.mouseX, (float)(j + guiY) - this.mouseY + mouseYRev, this.entity);
	}

}
