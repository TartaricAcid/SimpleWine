package github.baka943.simplewine.tiltentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;

public class TileEntityBarrel extends TileEntity implements ITickable {
    private int sugar_num = 0;
    private int tartaric_num = 0;
    private int grape_juice = 0;
    private int wine_num = 0;
    private int wine_percent = 0;

    // Set
    public void setSugarNum(int sugar_num) {
        this.sugar_num = sugar_num;
    }

    public void setTartaricNum(int tartaric_num) {
        this.tartaric_num = tartaric_num;
    }

    public void setGrapeJuice(int grape_juice) {
        this.grape_juice = grape_juice;
    }

    public void setWineNum(int wine_num) {
        this.wine_num = wine_num;
    }

    public void setWinePercent(int wine_percent) {
        this.wine_percent = wine_percent;
    }

    // Get
    public int getSugarNum() {
        return sugar_num;
    }

    public int getTartaricNum() {
        return tartaric_num;
    }

    public int getGrapeJuice() {
        return grape_juice;
    }

    public int getWineNum() {
        return wine_num;
    }

    public int getWinePercent() {
        return wine_percent;
    }


    // Add
    public int addPercent() {
        if (wine_percent < 12000) {
            wine_percent++;
        } else {
            wine_percent = 0;
        }
        return wine_percent;
    }

    @Override
    public void update() {
        // 开始酿造判定
        if (sugar_num >= 5 && grape_juice >= 5 && !world.canSeeSky(pos) && pos.getY() < 60 && tartaric_num == 0 && wine_num == 0 && wine_percent == 0) {
            wine_percent = 1;
            sugar_num = 0;
            grape_juice = 0;
        }

        // 酿造完成判定
        if (sugar_num == 0 && grape_juice == 0 && !world.canSeeSky(pos) && pos.getY() < 60 && tartaric_num == 0 && wine_num == 0 && wine_percent != 0) {
            int i = addPercent();
            if (i >= 12000) {
                setWineNum(5);
                setTartaricNum(1);
                wine_percent = 0;
            }
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        sugar_num = compound.getInteger("sugar_num");
        tartaric_num = compound.getInteger("tartaric_num");
        grape_juice = compound.getInteger("grape_juice");
        wine_num = compound.getInteger("wine_num");
        wine_percent = compound.getInteger("wine_percent");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setInteger("sugar_num", sugar_num);
        compound.setInteger("tartaric_num", tartaric_num);
        compound.setInteger("grape_juice", grape_juice);
        compound.setInteger("wine_num", wine_num);
        compound.setInteger("wine_percent", wine_percent);
        return compound;
    }
}
