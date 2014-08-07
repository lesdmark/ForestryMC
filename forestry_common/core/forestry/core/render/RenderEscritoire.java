/*******************************************************************************
 * Copyright (c) 2011-2014 SirSengir.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 * 
 * Various Contributors including, but not limited to:
 * SirSengir (original work), CovertJaguar, Player, Binnie, MysteriousAges
 ******************************************************************************/
package forestry.core.render;

import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.common.util.ForgeDirection;

import org.lwjgl.opengl.GL11;

import forestry.core.config.Defaults;
import forestry.core.gadgets.TileEscritoire;
import forestry.core.interfaces.IBlockRenderer;
import forestry.core.proxy.Proxies;
import forestry.core.utils.ForestryResource;

public class RenderEscritoire extends TileEntitySpecialRenderer implements IBlockRenderer {

	ModelEscritoire modelEscritoire;
	ResourceLocation texture = new ForestryResource(Defaults.TEXTURE_PATH_BLOCKS + "/escritoire.png");
	//private final EntityItem dummyEntityItem = new EntityItem(null);
	private final RenderItem customRenderItem;

	public RenderEscritoire() {
		modelEscritoire = new ModelEscritoire();

		customRenderItem = new RenderItem() {
			@Override
			public boolean shouldBob() {
				return false;
			}

			@Override
			public boolean shouldSpreadItems() {
				return false;
			};
		};
		customRenderItem.setRenderManager(RenderManager.instance);

	}

	@Override
	public void inventoryRender(double x, double y, double z, float f, float f1) {
		render(null, ForgeDirection.EAST, x, y, z);
	}

	@Override
	public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float f) {
		TileEscritoire tile = (TileEscritoire)tileentity;
		render(tile.getStackInSlot(TileEscritoire.SLOT_ANALYZE), tile.getOrientation(), x, y, z);
	}

	private void render(ItemStack itemstack, ForgeDirection orientation, double x, double y, double z) {
		float factor = (float) (1.0 / 16.0);

		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glTranslatef((float) x + 0.5f, (float) y + 0.875f, (float) z + 0.5f);

		float[] angle = { (float) Math.PI, 0, 0 };

		if (orientation == null)
			orientation = ForgeDirection.WEST;
		switch (orientation) {
		case EAST:
			angle[1] = (float)Math.PI / 2;
			break;
		case SOUTH:
			break;
		case NORTH:
			angle[1] = (float)Math.PI;
			break;
		case WEST:
		default:
			angle[1] = -(float)Math.PI / 2;
			break;
		}

		Proxies.common.bindTexture(texture);
		modelEscritoire.render(null, angle[0], angle[1], angle[2], 0f, 0f, factor);

		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();

		/*
		if(itemstack != null) {
			float renderScale = 1.0f;

			GL11.glPushMatrix();
			GL11.glTranslatef((float) x, (float) y, (float) z);
			GL11.glTranslatef(0.6f, 0.8f, 0.5f);
			GL11.glRotatef(90.0f, 1.0F, 0.0F, 0.0F);
			GL11.glScalef(renderScale, renderScale, renderScale);

			RenderItem.renderInFrame = true;
			dummyEntityItem.setEntityItemStack(itemstack);
			customRenderItem.doRenderItem(dummyEntityItem, 0, 0, 0, 0, 0);
			RenderItem.renderInFrame = false;
			GL11.glPopMatrix();
		}
		 */

	}
}
