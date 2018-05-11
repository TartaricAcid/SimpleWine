package github.baka943.simplewine.waila;

import github.baka943.simplewine.block.BlockPresser;
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

public class WailaPresser implements IWailaDataProvider {
    public static final IWailaDataProvider INSTANCE = new WailaPresser();

    @Nonnull
    @Override
    public List<String> getWailaBody(ItemStack item, List<String> tip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        if (accessor.getBlock() instanceof BlockPresser) {
            NBTTagCompound tag = accessor.getNBTData();
            int grape_num = tag.getInteger("grape_num");
            int residue_num = tag.getInteger("residue_num");
            int grape_juice = tag.getInteger("grape_juice");
            int grape_percent = tag.getInteger("grape_percent");
            if (accessor.getPlayer().isSneaking()) {
                tip.add(I18n.format("waila.simple_wine.grape_num") + " " + grape_num);
                tip.add(I18n.format("waila.simple_wine.residue_num") + " " + residue_num);
                tip.add(I18n.format("waila.simple_wine.grape_juice") + " " + grape_juice + " B");
                tip.add(I18n.format("waila.simple_wine.grape_percent") + " " + grape_percent + "%");
            } else {
                tip.add(I18n.format("waila.simple_wine.grape_percent") + " " + grape_percent + "%");
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
