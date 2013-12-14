package com.ironlionchefs.modjam.src.quest.gui;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import java.net.URI;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;

import org.apache.commons.lang3.Validate;
import org.lwjgl.opengl.GL11;

import com.ironlionchefs.modjam.src.QuestMod;
import com.ironlionchefs.modjam.src.quest.networking.server.ServerPacketRequestCurrentQuest;
import com.ironlionchefs.modjam.src.quest.networking.server.ServerPacketRequestQuestCompletion;

@SideOnly(Side.CLIENT)
public class GuiQuestActive extends GuiScreen
{
	private static final ResourceLocation field_110407_a = new ResourceLocation("textures/gui/demo_background.png");
	private EntityPlayer entityPlayer;

	public GuiQuestActive(EntityPlayer ep)
	{
		entityPlayer = ep;
	}

	public void initGui()
	{
		PacketDispatcher.sendPacketToServer(new ServerPacketRequestQuestCompletion(entityPlayer.username).makePacket());
		PacketDispatcher.sendPacketToServer(new ServerPacketRequestCurrentQuest(entityPlayer.username).makePacket());
		this.buttonList.clear();
		byte b0 = -16;
		this.buttonList.add(new GuiButton(1, this.width / 2 - 116, this.height / 2 + 62 + b0, 114, 20, "Finished Quest"));
		this.buttonList.add(new GuiButton(2, this.width / 2 + 2, this.height / 2 + 62 + b0, 114, 20, "Back"));
	}

	protected void actionPerformed(GuiButton par1GuiButton)
	{
		switch (par1GuiButton.id)
		{
			case 1:
				this.mc.displayGuiScreen((GuiScreen) null);
				this.mc.setIngameFocus();
				QuestMod.currentQuestForPlayer.onQuestEnd(entityPlayer, entityPlayer.worldObj);
				break;
			case 2:
				this.mc.displayGuiScreen((GuiScreen) null);
				this.mc.setIngameFocus();
		}
	}

	public void updateScreen()
	{
		super.updateScreen();
	}

	public boolean doesGuiPauseGame()
	{
		return false;
	}
	
	public void drawDefaultBackground()
	{
		super.drawDefaultBackground();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(field_110407_a);
		int i = (this.width - 248) / 2;
		int j = (this.height - 166) / 2;
		this.drawTexturedModalRect(i, j, 0, 0, 248, 166);
	}

	public void drawScreen(int par1, int par2, float par3)
	{
		this.drawDefaultBackground();
		int k = (this.width - 248) / 2 + 10;
		int l = (this.height - 166) / 2 + 8;

		this.fontRenderer.drawString("Hey there! You already have a quest.", (this.width / 2) - this.fontRenderer.getStringWidth("Hey there! You already have a quest.") / 2, l, 0x000000);
		l += 48;
		GameSettings gamesettings = this.mc.gameSettings;
		this.fontRenderer.drawString("You must complete this quest before", (this.width / 2) - this.fontRenderer.getStringWidth("You must complete this quest before") / 2, l, 0x000000);
		this.fontRenderer.drawString("moving on.", (this.width / 2) - this.fontRenderer.getStringWidth("moving on.") / 2, l + 12, 0x000000);
		super.drawScreen(par1, par2, par3);
	}
}