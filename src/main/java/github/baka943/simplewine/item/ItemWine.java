package github.baka943.simplewine.item;

import github.baka943.simplewine.SimpleWine;
import github.baka943.simplewine.proxy.CommonProxy;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class ItemWine extends ItemPotion {
    public ItemWine(){
        this.setRegistryName("item_wine");
        this.setCreativeTab(CommonProxy.simpleWineCreativeTabs);
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack){
        return I18n.format("item.simple_wine.item_wine.name");
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {

        // 定义随机的药水属性
        // 1 速度；3 急迫；5 力量；12 抗火
        Random random = new Random();
        // 选择随机的药水类型
        byte id = 12;

        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setByte("Id", id);
        nbt.setByte("Amplifier", (byte)0);
        nbt.setInteger("Duration", random.nextInt(10000) + 2000);
        nbt.setBoolean("Ambient", true);
        nbt.setBoolean("ShowParticles", false);
        entityLiving.addPotionEffect(PotionEffect.readCustomPotionEffectFromNBT(nbt));
        stack = new ItemStack(ItemLoader.itemBottle,1);
        return stack;
    }

    @SideOnly(Side.CLIENT)
    public void initModel(){
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add(I18n.format("tooltips." + SimpleWine.MODID + ".item_wine"));
    }
}
