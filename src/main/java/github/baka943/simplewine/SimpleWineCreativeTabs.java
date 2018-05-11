package github.baka943.simplewine;

import github.baka943.simplewine.item.ItemLoader;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class SimpleWineCreativeTabs extends CreativeTabs {
    public SimpleWineCreativeTabs(String label){
        super(label);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ItemStack getTabIconItem() {
        return ItemLoader.itemWine.getDefaultInstance();
    }
}
