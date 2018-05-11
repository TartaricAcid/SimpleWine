package github.baka943.simplewine.waila;


import github.baka943.simplewine.block.BlockBarrel;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.List;

public class WailaBarrel implements IWailaDataProvider {
    public static final IWailaDataProvider INSTANCE = new WailaBarrel();

    @Nonnull
    @Override
    public List<String> getWailaBody(ItemStack item, List<String> tip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        if (accessor.getBlock() instanceof BlockBarrel) {
            NBTTagCompound tag = accessor.getNBTData();
            int sugar_num = tag.getInteger("sugar_num");
            int tartaric_num = tag.getInteger("tartaric_num");
            int grape_juice = tag.getInteger("grape_juice");
            int wine_num = tag.getInteger("wine_num");
            int wine_percent = tag.getInteger("wine_percent");
            int y = tag.getInteger("y");
            if (accessor.getPlayer().isSneaking()) {
                tip.add(I18n.format("waila.simple_wine.sugar_num") + " " + sugar_num);
                tip.add(I18n.format("waila.simple_wine.grape_juice") + " " + grape_juice + " B");
                tip.add(I18n.format("waila.simple_wine.tartaric_num") + " " + tartaric_num);
                tip.add(I18n.format("waila.simple_wine.wine_num") + " " + wine_num + " B");
                tip.add(I18n.format("waila.simple_wine.wine_percent") + " " + (wine_percent * 100 / 12000) + "%");
            } else {
                tip.add(I18n.format("waila.simple_wine.wine_percent") + " " + (wine_percent * 100 / 12000) + "%");
                if (y >= 60) {
                    tip.add(I18n.format("waila.simple_wine.y_is_too_high"));
                }
            }
        }
        return tip;
    }

    @Override
    public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, BlockPos pos) {
        if (te != null)
            te.writeToNBT(tag);
        return tag;
    }
}