package github.baka943.simplewine.block;

import github.baka943.simplewine.item.ItemLoader;
import net.minecraft.block.*;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CropsSoybean extends BlockCrops {
    public CropsSoybean() {
        this.setRegistryName("crops_soybean");
        this.setDefaultState(this.blockState.getBaseState().withProperty(this.getAgeProperty(), Integer.valueOf(0)));
        this.setTickRandomly(true);
        this.setHardness(0F);
        this.setSoundType(SoundType.PLANT);
        this.disableStats();
    }

    protected Item getSeed() {
        return ItemLoader.seedSoybean;
    }

    protected Item getCrop() {
        return ItemLoader.seedSoybean;
    }
}
