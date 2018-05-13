package github.baka943.simplewine.block;

import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockLoader {
    @ObjectHolder("simple_wine:block_presser")
    public static BlockPresser blockPresser;
    @ObjectHolder("simple_wine:block_barrel")
    public static BlockBarrel blockBarrel;
    @ObjectHolder("simple_wine:block_bamboo")
    public static BlockBamboo blockBamboo;
    @ObjectHolder("simple_wine:block_bamboo_shoot")
    public static BlockBambooShoot blockBambooShoot;

    public BlockLoader() {
    }

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        blockPresser.initModel();
        blockBarrel.initModel();
        blockBamboo.initModel();
        blockBambooShoot.initModel();
    }
}
