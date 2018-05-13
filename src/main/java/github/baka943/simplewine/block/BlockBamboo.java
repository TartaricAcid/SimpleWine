package github.baka943.simplewine.block;

import github.baka943.simplewine.SimpleWine;
import github.baka943.simplewine.proxy.CommonProxy;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BlockBamboo extends Block {
    public static final AxisAlignedBB BAMBOO_BLOCK_AABB = new AxisAlignedBB(0.3125d, 0d, 0.3125d, 0.6875d, 1d, 0.6875d);

    public BlockBamboo() {
        super(Material.WOOD);
        this.setRegistryName("block_bamboo");
        this.setUnlocalizedName(SimpleWine.MODID + ".block_bamboo");
        this.setHardness(2.0f);
        this.setCreativeTab(CommonProxy.simpleWineCreativeTabs);
        this.setTickRandomly(true);
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(this.getRegistryName(), "inventory"));
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return BAMBOO_BLOCK_AABB;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public void randomTick(World worldIn, BlockPos pos, IBlockState state, Random random) {
        List<Block> listBlock = new ArrayList<>(); // 存储校验方块信息
        int num = 0; // 存储竹子数量
        for (int i = -1; i <= 11; i++) {
            listBlock.add(worldIn.getBlockState(new BlockPos(pos.getX(), pos.getY() - i, pos.getZ())).getBlock());
            if (worldIn.getBlockState(new BlockPos(pos.getX(), pos.getY() - i, pos.getZ())).getBlock() == this) {
                num = num + 1;
            }
        }
        if ((listBlock.get(2) == Blocks.GRASS || listBlock.get(2) == Blocks.DIRT || listBlock.get(2) == this) && listBlock.get(0) == Blocks.AIR && num < 10) {
            worldIn.setBlockState(new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ()), this.getDefaultState());
        }
    }

    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        return false;
    }

    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        this.checkForDrop(worldIn, pos, state);
    }

    protected final boolean checkForDrop(World worldIn, BlockPos pos, IBlockState state) {
        Block block = worldIn.getBlockState(new BlockPos(pos.getX(), pos.getY() - 1, pos.getZ())).getBlock();
        if (block == Blocks.GRASS || block == Blocks.DIRT || block == this) {
            return true;
        } else {
            this.dropBlockAsItem(worldIn, pos, state, 0);
            worldIn.setBlockToAir(pos);
            return false;
        }
    }
}
