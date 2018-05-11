package github.baka943.simplewine.block;

import github.baka943.simplewine.item.ItemLoader;
import github.baka943.simplewine.proxy.CommonProxy;
import github.baka943.simplewine.tiltentity.TileEntityPresser;

import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
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

public class BlockPresser extends Block implements ITileEntityProvider {
    public static final AxisAlignedBB PRESSER_BLOCK_AABB = new AxisAlignedBB(0.1D, 0.0D, 0.1D, 0.9D, 1.0D, 0.9D);


    public BlockPresser() {
        super(Material.WOOD);
        this.setRegistryName("block_presser");
        this.setUnlocalizedName("simple_wine.block_presser");
        this.setCreativeTab(CommonProxy.simpleWineCreativeTabs);
        this.setHardness(3.0f);
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(this.getRegistryName(), "inventory"));
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return PRESSER_BLOCK_AABB;
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
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityPresser();
    }

    private TileEntityPresser getTE(World world, BlockPos pos) {
        return (TileEntityPresser) world.getTileEntity(pos);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {

        // 初始化，获取相关数据
        int grape_num = this.getTE(worldIn, pos).getGrapeNum();
        int residue_num = this.getTE(worldIn, pos).getResidueNum();
        int grape_juice = this.getTE(worldIn, pos).getJuiceNum();

        // 玩家空手，获取信息
        if (player.getHeldItem(hand).isEmpty()) {
            if (!worldIn.isRemote) {
                TextComponentTranslation grape_num_info = new TextComponentTranslation("simple_wine.grape_num.info", grape_num);
                TextComponentTranslation residue_num_info = new TextComponentTranslation("simple_wine.residue_num.info", residue_num);
                TextComponentTranslation grape_juice_info = new TextComponentTranslation("simple_wine.grape_juice.info", grape_juice);
                player.sendStatusMessage(grape_num_info, false);
                player.sendStatusMessage(residue_num_info, false);
                player.sendStatusMessage(grape_juice_info, false);
            }
            if (worldIn.isRemote) {
                worldIn.playSound(player, pos, SoundEvents.BLOCK_WOOD_HIT, SoundCategory.BLOCKS, 0.3F, 1.0F);
            }
            return true;
        }

        // 玩家塞葡萄发生的事情
        if (player.getHeldItem(hand).getItem() == ItemLoader.itemGrape && grape_num < 64) {
            if (!worldIn.isRemote) {
                int player_hold_num = player.getHeldItem(hand).getCount();
                if (player_hold_num + this.getTE(worldIn, pos).getGrapeNum() > 64) {
                    int grape_num_change = this.getTE(worldIn, pos).grape_increment(64 - grape_num);
                    player.setHeldItem(hand, new ItemStack(ItemLoader.itemGrape, player_hold_num - grape_num_change));
                    TextComponentTranslation grape_num_full_info = new TextComponentTranslation("simple_wine.grape_num.is_full.info");
                    player.sendStatusMessage(grape_num_full_info, true);
                }
                if (player_hold_num + grape_num <= 64) {
                    this.getTE(worldIn, pos).grape_increment(player_hold_num);
                    player.setHeldItem(hand, ItemStack.EMPTY);
                }
            }
            if (worldIn.isRemote) {
                worldIn.playSound(player, pos, SoundEvents.BLOCK_WOOD_HIT, SoundCategory.BLOCKS, 0.3F, 1.0F);
            }
            return true;
        }

        // 玩家用桶右击发生的事情
        if (player.getHeldItem(hand).getItem() == Items.BUCKET && grape_juice > 0) {
            if (!worldIn.isRemote) {
                int bucket_num = player.getHeldItem(hand).getCount();
                this.getTE(worldIn, pos).juice_decrement();
                if (bucket_num == 1) {
                    player.setHeldItem(hand, ItemLoader.itemGrapeJuice.getDefaultInstance());
                } else {
                    player.setHeldItem(hand, new ItemStack(Items.BUCKET, bucket_num - 1));
                    player.dropItem(ItemLoader.itemGrapeJuice, 1);
                }
            }
            if (worldIn.isRemote) {
                worldIn.playSound(player, pos, SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
            }
            return true;
        }

        // 玩家用棍子右击取出残渣
        if (player.getHeldItem(hand).getItem() == Items.STICK && this.getTE(worldIn, pos).getResidueNum() > 0) {
            if (!worldIn.isRemote) {
                this.getTE(worldIn, pos).residue_decrement();
                player.dropItem(ItemLoader.itemResidue, 1);
            }
            if (worldIn.isRemote) {
                worldIn.playSound(player, pos, SoundEvents.BLOCK_WOOD_HIT, SoundCategory.BLOCKS, 0.3F, 1.0F);
            }
            return true;
        }

        // 最后别忘记返回值
        return true;
    }

    @Override
    public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn) {
        // 获取相关数据
        int grape_num = this.getTE(worldIn, pos).getGrapeNum();
        int residue_num = this.getTE(worldIn, pos).getResidueNum();
        int grape_juice = this.getTE(worldIn, pos).getJuiceNum();
        int grape_percent = this.getTE(worldIn, pos).getPercent();

        // 开始判定是否压制
        if (!worldIn.isRemote && grape_num > 0 && residue_num < 10 && grape_juice < 10) {
            if (grape_percent == 0) {
                this.getTE(worldIn, pos).grape_decrement();
            }
            if (grape_percent == 99) {
                this.getTE(worldIn, pos).juice_increment();
                Random random = new Random();
                if (random.nextInt(5) == 0) {
                    this.getTE(worldIn, pos).residue_increment();
                }
            }
            this.getTE(worldIn, pos).percent_increment();
        }
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        // 数据初始化
        int grape_num = this.getTE(worldIn, pos).getGrapeNum();
        int residue_num = this.getTE(worldIn, pos).getResidueNum();

        // 开始判定掉落
        if (grape_num > 0) {
            EntityItem entity_grape = new EntityItem(worldIn, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(ItemLoader.itemGrape, grape_num));
            worldIn.spawnEntity(entity_grape);
        }
        if (residue_num > 0) {
            EntityItem entity_residue = new EntityItem(worldIn, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(ItemLoader.itemResidue, residue_num));
            worldIn.spawnEntity(entity_residue);
        }
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, ITooltipFlag advanced) {
        tooltip.add(TextFormatting.GRAY + I18n.format("tooltips.simple_wine.block_presser"));
    }

    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        List<EntityLivingBase> entities = worldIn.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(pos.getX() + 0.1, pos.getY() + 1, pos.getZ() + 0.1, pos.getX() + 0.9, pos.getY() + 1.001, pos.getZ() + 0.9));

        if (entities.size() > 0) {
            if (worldIn.isRemote) {
                double xpos = pos.getX() + 0.5;
                double ypos = pos.getY() + 1.0;
                double zpos = pos.getZ() + 0.5;
                double velocityX = 0;
                double velocityY = 0;
                double velocityZ = 0;
                int[] extraInfo = new int[0];

                worldIn.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, xpos, ypos, zpos, velocityX, velocityY, velocityZ, extraInfo);
            }
        }
    }
}
