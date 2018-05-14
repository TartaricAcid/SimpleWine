package github.baka943.simplewine.jei;

import github.baka943.simplewine.block.BlockLoader;
import github.baka943.simplewine.item.ItemLoader;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.ingredients.IIngredientBlacklist;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

@JEIPlugin
public class JEILoader implements IModPlugin {
    public static JEILoader INSTANCE;

    @Override
    public void register(IModRegistry registry){
        INSTANCE = this;

        // 黑名单，隐藏不必要的东西
        IIngredientBlacklist blacklist = registry.getJeiHelpers().getIngredientBlacklist();
        blacklist.addIngredientToBlacklist(ItemLoader.itemWine.getDefaultInstance());

        // 注册自定义描述
        registry.addDescription(new ItemStack(ItemLoader.itemGrape), "jei.simple_wine.item_grape");
    }

}
