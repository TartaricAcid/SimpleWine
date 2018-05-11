package github.baka943.simplewine.theoneprobe;

import github.baka943.simplewine.SimpleWine;
import github.baka943.simplewine.block.BlockBarrel;
import github.baka943.simplewine.tiltentity.TileEntityBarrel;
import mcjty.theoneprobe.api.*;
import mcjty.theoneprobe.apiimpl.styles.ProgressStyle;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class BarrelTheOneProbeProvider implements IProbeInfoProvider {
    @Override
    public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world, IBlockState blockState, IProbeHitData data) {
        if (blockState.getBlock() instanceof BlockBarrel) {
            // 数据初始化
            TileEntityBarrel barrel = (TileEntityBarrel) world.getTileEntity(data.getPos());
            int sugar_num = barrel.getSugarNum();
            int tartaric_num = barrel.getTartaricNum();
            int grape_juice = barrel.getGrapeJuice();
            int wine_num = barrel.getWineNum();
            int wine_percent = barrel.getWinePercent();
            int y = data.getPos().getY();

            // 普通状态，只显示进度
            if (mode == ProbeMode.NORMAL) {
                probeInfo.progress((wine_percent * 100 / 12000), 100, new wineProgressStyle());
                if (y >= 60) {
                    probeInfo.text(I18n.format("waila.simple_wine.y_is_too_high"));
                }
            }

            // Shift 状态，显示更多信息
            if (mode == ProbeMode.EXTENDED) {
                probeInfo.text(I18n.format("waila.simple_wine.sugar_num") + " " + sugar_num);
                probeInfo.text(I18n.format("waila.simple_wine.grape_juice") + " " + grape_juice + " B");
                probeInfo.text(I18n.format("waila.simple_wine.tartaric_num") + " " + tartaric_num);
                probeInfo.text(I18n.format("waila.simple_wine.wine_num") + " " + wine_num + " B");
                probeInfo.progress((wine_percent * 100 / 12000), 100, new wineProgressStyle());
            }
        }
    }

    public class wineProgressStyle extends ProgressStyle {
        @Override
        public String getPrefix() {
            return I18n.format("waila.simple_wine.wine_percent") + " ";
        }

        @Override
        public String getSuffix() {
            return "%";
        }
    }

    @Override
    public String getID() {
        return SimpleWine.MODID + ".barrel";
    }
}
