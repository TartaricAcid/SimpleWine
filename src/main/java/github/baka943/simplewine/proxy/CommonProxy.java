package github.baka943.simplewine.proxy;

import github.baka943.simplewine.SimpleWine;
import github.baka943.simplewine.SimpleWineCreativeTabs;
import github.baka943.simplewine.block.*;
import github.baka943.simplewine.entity.EntityLoader;
import github.baka943.simplewine.item.*;
import github.baka943.simplewine.tiltentity.TileEntityBarrel;
import github.baka943.simplewine.tiltentity.TileEntityPresser;
import github.baka943.simplewine.trade.VillageTradeGrape;
import github.baka943.simplewine.trade.VillageTradeTartaric;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod.EventBusSubscriber
public class CommonProxy {
    public static CreativeTabs simpleWineCreativeTabs;

    public void preInit(FMLPreInitializationEvent event) {
        simpleWineCreativeTabs = new SimpleWineCreativeTabs(SimpleWine.MODID + ".tabs");
        EntityLoader.registerEntities();
        EntityLoader.registerEntitiesSpawn();
    }

    public void init(FMLInitializationEvent event) {
        FMLInterModComms.sendFunctionMessage("theoneprobe", "getTheOneProbe", "github.baka943.simplewine.theoneprobe.TheOneProbeLoader");

        ForgeRegistries.VILLAGER_PROFESSIONS.getValue(new ResourceLocation("minecraft:farmer")).getCareer(0).addTrade(1, new VillageTradeGrape());
        ForgeRegistries.VILLAGER_PROFESSIONS.getValue(new ResourceLocation("minecraft:farmer")).getCareer(0).addTrade(2, new VillageTradeTartaric());
    }

    public void postInit(FMLPostInitializationEvent event) {
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(new ItemGrape());
        event.getRegistry().register(new ItemResidue());
        event.getRegistry().register(new ItemGrapeJuice());
        event.getRegistry().register(new ItemBottle());
        event.getRegistry().register(new ItemTartaric());
        event.getRegistry().register(new ItemWine());
        event.getRegistry().register(new SeedSoybean());

        event.getRegistry().register(new ItemBlock(BlockLoader.blockPresser).setRegistryName(BlockLoader.blockPresser.getRegistryName()));
        event.getRegistry().register(new ItemBlock(BlockLoader.blockBarrel).setRegistryName(BlockLoader.blockBarrel.getRegistryName()));
        event.getRegistry().register(new ItemBlock(BlockLoader.blockBamboo).setRegistryName(BlockLoader.blockBamboo.getRegistryName()));
        event.getRegistry().register(new ItemBlock(BlockLoader.blockBambooShoot).setRegistryName(BlockLoader.blockBambooShoot.getRegistryName()));
        event.getRegistry().register(new ItemBlock(BlockLoader.cropsSoybean).setRegistryName(BlockLoader.cropsSoybean.getRegistryName()));
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        event.getRegistry().register(new BlockPresser());
        event.getRegistry().register(new BlockBarrel());
        event.getRegistry().register(new BlockBamboo());
        event.getRegistry().register(new BlockBambooShoot());
        event.getRegistry().register(new CropsSoybean());

        GameRegistry.registerTileEntity(TileEntityPresser.class, SimpleWine.MODID + ".tileentity_presser");
        GameRegistry.registerTileEntity(TileEntityBarrel.class, SimpleWine.MODID + ".tileentity_barrel");
    }
}
