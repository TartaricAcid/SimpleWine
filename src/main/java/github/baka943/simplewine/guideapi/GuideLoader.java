package github.baka943.simplewine.guideapi;

import amerifrance.guideapi.api.GuideAPI;
import amerifrance.guideapi.api.GuideBook;
import amerifrance.guideapi.api.IGuideBook;
import amerifrance.guideapi.api.IPage;
import amerifrance.guideapi.api.impl.Book;
import amerifrance.guideapi.api.impl.abstraction.EntryAbstract;
import amerifrance.guideapi.category.CategoryItemStack;
import amerifrance.guideapi.entry.EntryItemStack;
import amerifrance.guideapi.page.PageImage;
import amerifrance.guideapi.page.PageJsonRecipe;
import amerifrance.guideapi.page.PageText;
import github.baka943.simplewine.SimpleWine;
import github.baka943.simplewine.block.BlockLoader;
import github.baka943.simplewine.item.ItemLoader;
import github.baka943.simplewine.proxy.CommonProxy;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

@GuideBook(priority = EventPriority.HIGHEST)
public class GuideLoader implements IGuideBook {
    public static final Book wineGuide = new Book();

    @Nonnull
    @Override
    public Book buildBook() {
        // 设置基本信息
        wineGuide.setTitle("book.simple_wine.tile");
        wineGuide.setDisplayName("book.simple_wine.display_name");
        wineGuide.setWelcomeMessage("book.simple_wine.welcome_message");
        wineGuide.setCreativeTab(CommonProxy.simpleWineCreativeTabs);

        // 设置手册背景和资源地址
        wineGuide.setColor(Color.blue);
        wineGuide.setRegistryName(new ResourceLocation(SimpleWine.MODID, "main_guide"));

        // 绘制分目录
        Map<ResourceLocation, EntryAbstract> get_grape_entries = new LinkedHashMap<>();
        Map<ResourceLocation, EntryAbstract> presser_entries = new LinkedHashMap<>();
        Map<ResourceLocation, EntryAbstract> barrel_entries = new LinkedHashMap<>();
        Map<ResourceLocation, EntryAbstract> tartaric_entries = new LinkedHashMap<>();

        // 绘制具体页面
        List<IPage> grape_wiki_page = new ArrayList<IPage>();
        grape_wiki_page.add(new PageText("book.simple_wine.grape_wiki.page"));
        get_grape_entries.put(new ResourceLocation(SimpleWine.MODID + "get_grape", "wiki"), new EntryItemStack(grape_wiki_page, "book.simple_wine.grape_wiki", Items.BOOK.getDefaultInstance()));
        List<IPage> get_grape_page = new ArrayList<IPage>();
        get_grape_page.add(new PageText("book.simple_wine.get_grape.page"));
        get_grape_page.add(new PageImage(new ResourceLocation("simple_wine:picture/village_trade.png")));
        get_grape_entries.put(new ResourceLocation(SimpleWine.MODID + "get_grape", "main"), new EntryItemStack(get_grape_page, "book.simple_wine.get_grape", Items.BOOK.getDefaultInstance()));

        List<IPage> presser_page = new ArrayList<IPage>();
        presser_page.add(new PageText("book.simple_wine.presser.page"));
        presser_page.add(new PageJsonRecipe(new ResourceLocation("simple_wine:block_presser")));
        presser_page.add(new PageImage(new ResourceLocation("simple_wine:picture/presser.png")));
        presser_entries.put(new ResourceLocation(SimpleWine.MODID + "presser_usage", "main"), new EntryItemStack(presser_page, "book.simple_wine.presser_usage", Items.BOOK.getDefaultInstance()));

        List<IPage> barrel_page = new ArrayList<IPage>();
        barrel_page.add(new PageText("book.simple_wine.barrel.page"));
        barrel_page.add(new PageJsonRecipe(new ResourceLocation("simple_wine:block_barrel")));
        barrel_entries.put(new ResourceLocation(SimpleWine.MODID + "barrel_usage", "main"), new EntryItemStack(barrel_page, "book.simple_wine.barrel_usage", Items.BOOK.getDefaultInstance()));

        List<IPage> tartaric_page = new ArrayList<IPage>();
        tartaric_page.add(new PageText("book.simple_wine.tartaric.page"));
        tartaric_entries.put(new ResourceLocation(SimpleWine.MODID + "tartaric_usage", "main"), new EntryItemStack(tartaric_page, "book.simple_wine.tartaric_usage", Items.BOOK.getDefaultInstance()));

        // 设置主章节
        wineGuide.addCategory(new CategoryItemStack(get_grape_entries, "book.simple_wine.get_grape", new ItemStack(ItemLoader.itemGrape)));
        wineGuide.addCategory(new CategoryItemStack(presser_entries, "book.simple_wine.presser_usage", new ItemStack(BlockLoader.blockPresser)));
        wineGuide.addCategory(new CategoryItemStack(barrel_entries, "book.simple_wine.barrel_usage", new ItemStack(BlockLoader.blockBarrel)));
        wineGuide.addCategory(new CategoryItemStack(tartaric_entries, "book.simple_wine.tartaric_usage", new ItemStack(ItemLoader.itemTartaric)));

        return wineGuide;
    }

    @Nullable
    @Override
    public IRecipe getRecipe(@Nonnull ItemStack bookStack) {
        return new ShapelessOreRecipe(new ResourceLocation(SimpleWine.MODID, "guide"), GuideAPI.getStackFromBook(wineGuide), new ItemStack(Items.BOOK), "blockGlass", "feather").setRegistryName("simple_wine_guide");
    }
}
