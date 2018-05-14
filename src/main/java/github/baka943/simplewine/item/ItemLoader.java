package github.baka943.simplewine.item;

import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemLoader {
    @GameRegistry.ObjectHolder("simple_wine:item_grape")
    public static ItemGrape itemGrape;
    @GameRegistry.ObjectHolder("simple_wine:item_residue")
    public static ItemResidue itemResidue;
    @GameRegistry.ObjectHolder("simple_wine:item_grape_juice")
    public static ItemGrapeJuice itemGrapeJuice;
    @GameRegistry.ObjectHolder("simple_wine:item_bottle")
    public static ItemBottle itemBottle;
    @GameRegistry.ObjectHolder("simple_wine:item_tartaric")
    public static ItemTartaric itemTartaric;
    @GameRegistry.ObjectHolder("simple_wine:item_wine")
    public static ItemWine itemWine;
    @GameRegistry.ObjectHolder("simple_wine:seed_soybean")
    public static SeedSoybean seedSoybean;

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        itemGrape.initModel();
        itemResidue.initModel();
        itemGrapeJuice.initModel();
        itemBottle.initModel();
        itemTartaric.initModel();
        itemWine.initModel();
        seedSoybean.initModel();
    }
}
