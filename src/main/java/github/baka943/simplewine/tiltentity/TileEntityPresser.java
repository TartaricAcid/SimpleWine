package github.baka943.simplewine.tiltentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

public class TileEntityPresser extends TileEntity {
    private int grape_num = 0;
    private int residue_num = 0;
    private int grape_juice = 0;
    private int grape_percent = 0;

    // Increment
    public int grape_increment(int num) {
        grape_num = grape_num + num;
        markDirty();
        return num;
    }

    public int residue_increment() {
        residue_num++;
        markDirty();
        return residue_num;
    }

    public int juice_increment() {
        grape_juice++;
        markDirty();
        return grape_juice;
    }

    public int percent_increment() {
        if (grape_percent > 99) {
            grape_percent = 0;
        } else {
            grape_percent++;
            markDirty();
        }
        return grape_percent;
    }

    // Decrement
    public int grape_decrement() {
        if (grape_num > 0) {
            grape_num--;
            markDirty();
        }
        return grape_num;
    }

    public int residue_decrement() {
        if (residue_num > 0) {
            residue_num--;
            markDirty();
        }
        return residue_num;
    }

    public int juice_decrement() {
        if (grape_juice > 0) {
            grape_juice--;
            markDirty();
        }
        return grape_juice;
    }

    // Get
    public int getGrapeNum() {
        return grape_num;
    }

    public int getResidueNum() {
        return residue_num;
    }

    public int getJuiceNum() {
        return grape_juice;
    }

    public int getPercent() {
        return grape_percent;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        grape_num = compound.getInteger("grape_num");
        residue_num = compound.getInteger("residue_num");
        grape_juice = compound.getInteger("grape_juice");
        grape_percent = compound.getInteger("grape_percent");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setInteger("grape_num", grape_num);
        compound.setInteger("residue_num", residue_num);
        compound.setInteger("grape_juice", grape_juice);
        compound.setInteger("grape_percent", grape_percent);
        return compound;
    }
}
