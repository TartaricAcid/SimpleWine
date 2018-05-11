package github.baka943.simplewine.block;

import github.baka943.simplewine.item.ItemLoader;
import github.baka943.simplewine.proxy.CommonProxy;
import github.baka943.simplewine.tiltentity.TileEntityBarrel;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class BlockBarrel extends Block implements ITileEntityProvider {
    public static final AxisAlignedBB BARREL_BLOCK_AABB = new AxisAlignedBB(0.1D, 0.0D, 0.1D, 0.9D, 0.95D, 0.9D);
    public static final PropertyDirection FACING = PropertyDirection.create("facing");

    public BlockBarrel() {
        super(Material.WOOD);
        this.setRegistryName("block_barrel");
        this.setUnlocalizedName("simple_wine.block_barrel");
        this.setCreativeTab(CommonProxy.simpleWineCreativeTabs);
        this.setHardness(3.0f);
        this.setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(this.getRegistryName(), "inventory"));
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return BARREL_BLOCK_AABB;
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        world.setBlockState(pos, state.withProperty(FACING, getFacingFromEntity(pos, placer)), 2);
    }

    public static EnumFacing getFacingFromEntity(BlockPos clickedBlock, EntityLivingBase entity) {
        return EnumFacing.getFacingFromVector(
                (float) (entity.posX - clickedBlock.getX()),
                (float) (entity.posY - clickedBlock.getY()),
                (float) (entity.posZ - clickedBlock.getZ()));
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(FACING, EnumFacing.getFront(meta & 7));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getIndex();
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING);
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityBarrel();
    }

    private TileEntityBarrel getTE(World world, BlockPos pos) {
        return (TileEntityBarrel) world.getTileEntity(pos);
    }

    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        // 相关数据初始化
        int sugar_num = this.getTE(worldIn, pos).getSugarNum();
        int tartaric_num = this.getTE(worldIn, pos).getTartaricNum();
        int grape_juice = this.getTE(worldIn, pos).getGrapeJuice();
        int wine_num = this.getTE(worldIn, pos).getWineNum();
        int wine_percent = this.getTE(worldIn, pos).getWinePercent();

        // 玩家空手
        if (player.getHeldItem(hand).isEmpty()) {
            if (!worldIn.isRemote) {
                // 数据本地化
                TextComponentTranslation sugar_num_info = new TextComponentTranslation("simple_wine.sugar_num.info", sugar_num);
                TextComponentTranslation tartaric_num_info = new TextComponentTranslation("simple_wine.tartaric_num.info", tartaric_num);
                TextComponentTranslation grape_juice_info = new TextComponentTranslation("simple_wine.grape_juice.info", grape_juice);
                TextComponentTranslation wine_num_info = new TextComponentTranslation("simple_wine.wine_num.info", wine_num);
                TextComponentTranslation wine_percent_info = new TextComponentTranslation("simple_wine.wine_percent.info", wine_percent);

                // 信息显示
                player.sendStatusMessage(sugar_num_info, false);
                player.sendStatusMessage(tartaric_num_info, false);
                player.sendStatusMessage(grape_juice_info, false);
                player.sendStatusMessage(wine_num_info, false);
                player.sendStatusMessage(wine_percent_info, false);
            }
            if (worldIn.isRemote) {
                worldIn.playSound(player, pos, SoundEvents.BLOCK_WOOD_HIT, SoundCategory.BLOCKS, 0.5F, 1.0F);
            }
            return true;
        }

        // 玩家手持葡萄汁桶
        if (player.getHeldItem(hand).getItem() == ItemLoader.itemGrapeJuice && grape_juice < 5 && wine_percent == 0 && tartaric_num == 0 && wine_num == 0) {
            if (!worldIn.isRemote) {
                this.getTE(worldIn, pos).setGrapeJuice(grape_juice + 1);
                player.setHeldItem(hand, Items.BUCKET.getDefaultInstance());
            }
            if (worldIn.isRemote) {
                worldIn.playSound(player, pos, SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
            }
            return true;
        }

        // 玩家手持糖
        if (player.getHeldItem(hand).getItem() == Items.SUGAR && sugar_num < 5 && wine_percent == 0 && tartaric_num == 0 && wine_num == 0) {
            if (!worldIn.isRemote) {
                int hold_sugar_num = player.getHeldItem(hand).getCount();
                this.getTE(worldIn, pos).setSugarNum(sugar_num + 1);
                player.setHeldItem(hand, new ItemStack(Items.SUGAR, hold_sugar_num - 1));
            }
            if (worldIn.isRemote) {
                worldIn.playSound(player, pos, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.BLOCKS, 1.0F, 1.0F);
            }
            return true;
        }

        // 玩家手持空瓶子
        if (player.getHeldItem(hand).getItem() == ItemLoader.itemBottle && wine_num > 0 && wine_percent == 0) {
            if (!worldIn.isRemote) {
                int bottle_num = player.getHeldItem(hand).getCount();
                player.setHeldItem(hand, new ItemStack(ItemLoader.itemBottle, bottle_num - 1));
                this.getTE(worldIn, pos).setWineNum(wine_num - 1);
                player.dropItem(ItemLoader.itemWine, 1);
            }
            return true;
        }

        // 玩家取出酒石(酸)
        if (player.getHeldItem(hand).getItem() == Items.STICK && tartaric_num > 0) {
            System.out.println(tartaric_num);
            if (!worldIn.isRemote) {
                this.getTE(worldIn, pos).setTartaricNum(tartaric_num - 1);
                player.dropItem(ItemLoader.itemTartaric, 1);
            }
            return true;
        }

        // 最后，别忘记返回值
        return true;
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        // 相关数据初始化
        int sugar_num = this.getTE(worldIn, pos).getSugarNum();
        int tartaric_num = this.getTE(worldIn, pos).getTartaricNum();

        // 开始判定掉落
        if (sugar_num > 0) {
            EntityItem entity_sugar = new EntityItem(worldIn, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(Items.SUGAR, sugar_num));
            worldIn.spawnEntity(entity_sugar);
        }
        if (tartaric_num > 0) {
            EntityItem entity_sugar = new EntityItem(worldIn, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(ItemLoader.itemTartaric, tartaric_num));
            worldIn.spawnEntity(entity_sugar);
        }
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, ITooltipFlag advanced) {
        tooltip.add(TextFormatting.GRAY + I18n.format("tooltips.simple_wine.block_barrel"));
    }
}
