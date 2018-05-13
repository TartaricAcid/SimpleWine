package github.baka943.simplewine.block;

import github.baka943.simplewine.SimpleWine;
import github.baka943.simplewine.proxy.CommonProxy;
import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.Random;

public class BlockBambooShoot extends Block implements IGrowable {
    public static final AxisAlignedBB SHOOT_BLOCK_AABB = new AxisAlignedBB(0.22, 0d, 0.22d, 0.78d, 0.85d, 0.78d);

    public BlockBambooShoot() {
        super(Material.GRASS);
        this.setRegistryName("block_bamboo_shoot");
        this.setUnlocalizedName(SimpleWine.MODID + ".block_bamboo_shoot");
        this.setCreativeTab(CommonProxy.simpleWineCreativeTabs);
        this.setHardness(0.2f);
        this.setTickRandomly(true);
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(this.getRegistryName(), "inventory"));
    }

    @Override
    public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
        return true;
    }

    @Override
    public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
        if (rand.nextInt(3) == 0) {
            worldIn.setBlockState(pos, BlockLoader.blockBamboo.getDefaultState());
        }
    }

    @Override
    public void randomTick(World worldIn, BlockPos pos, IBlockState state, Random random) {
        if (random.nextInt(2) == 0) {
            worldIn.setBlockState(pos, BlockLoader.blockBamboo.getDefaultState());
        }
    }

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Nullable
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return NULL_AABB;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return SHOOT_BLOCK_AABB;
    }

}
