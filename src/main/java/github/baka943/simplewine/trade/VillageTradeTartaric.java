package github.baka943.simplewine.trade;

import github.baka943.simplewine.item.ItemLoader;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;

import java.util.Random;

public class VillageTradeTartaric implements EntityVillager.ITradeList {
    @Override
    public void addMerchantRecipe(IMerchant merchant, MerchantRecipeList recipeList, Random random) {
        NBTTagCompound nbt = new NBTTagCompound();
        NBTTagCompound nbt_id = new NBTTagCompound();

        nbt_id.setString("Name", "tartaric_acid");
        nbt.setTag("SkullOwner", nbt_id);

        recipeList.add(new MerchantRecipe(new ItemStack(ItemLoader.itemTartaric, 9), new ItemStack(Items.SKULL, 1, 3, nbt)));
    }
}
