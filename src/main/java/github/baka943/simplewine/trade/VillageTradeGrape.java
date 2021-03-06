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

public class VillageTradeGrape implements EntityVillager.ITradeList {
    @Override
    public void addMerchantRecipe(IMerchant merchant, MerchantRecipeList recipeList, Random random) {
        recipeList.add(new MerchantRecipe(new ItemStack(Items.EMERALD, 1), new ItemStack(ItemLoader.itemGrape, random.nextInt(9) + 24)));
    }
}
