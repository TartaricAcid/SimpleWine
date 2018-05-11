package github.baka943.simplewine.theoneprobe;

import github.baka943.simplewine.SimpleWine;
import github.baka943.simplewine.block.BlockPresser;
import github.baka943.simplewine.tiltentity.TileEntityPresser;
import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.IProbeInfoProvider;
import mcjty.theoneprobe.api.ProbeMode;
import mcjty.theoneprobe.apiimpl.styles.ProgressStyle;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class PresserTheOneProbeProvider implements IProbeInfoProvider {
    @Override
    public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world, IBlockState blockState, IProbeHitData data) {
        if (blockState.getBlock() instanceof BlockPresser) {
            // 数据初始化
            TileEntityPresser presser = (TileEntityPresser) world.getTileEntity(data.getPos());
            int residueNum = presser.getResidueNum();
            int juiceNum = presser.getJuiceNum();
            int grapeNum = presser.getGrapeNum();
            int percent = presser.getPercent();

            // 普通状态，只显示进度
            if (mode == ProbeMode.NORMAL) {
                probeInfo.progress(percent, 100, new wineProgressStyle());
            }

            // Shift 状态，显示更多信息
            if (mode == ProbeMode.EXTENDED) {                
                probeInfo.text(I18n.format("waila.simple_wine.grape_num") + " " + grapeNum);
                probeInfo.text(I18n.format("waila.simple_wine.residue_num") + " " + residueNum);
                probeInfo.text(I18n.format("waila.simple_wine.grape_juice") + " " + juiceNum + " B");
                probeInfo.progress(percent, 100, new wineProgressStyle());
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
        return SimpleWine.MODID + ".presser";
    }
}

