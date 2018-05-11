package github.baka943.simplewine.item;

import github.baka943.simplewine.SimpleWine;
import github.baka943.simplewine.proxy.CommonProxy;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class ItemBottle extends Item {
    public ItemBottle(){
        this.setRegistryName("item_bottle");
        this.setUnlocalizedName(SimpleWine.MODID + ".item_bottle");
        this.setCreativeTab(CommonProxy.simpleWineCreativeTabs);
        this.setMaxStackSize(16);
    }

    @SideOnly(Side.CLIENT)
    public void initModel(){
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add(I18n.format("tooltips." + SimpleWine.MODID + ".item_bottle"));
    }
}
