package me.superckl.betteroceans.common.handler;

import static net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.COAL;
import static net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.DIAMOND;
import static net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.DIRT;
import static net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.GOLD;
import static net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.GRAVEL;
import static net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.IRON;
import static net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.LAPIS;
import static net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.REDSTONE;

import java.util.Random;

import me.superckl.betteroceans.BetterOceans;
import me.superckl.betteroceans.common.event.TrenchChunkEvent;
import me.superckl.betteroceans.common.gen.MapGenBetterOceansCaves;
import me.superckl.betteroceans.common.gen.MapGenBetterOceansRavine;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.InitMapGenEvent;
import net.minecraftforge.event.terraingen.InitMapGenEvent.EventType;
import net.minecraftforge.event.terraingen.OreGenEvent;
import net.minecraftforge.event.terraingen.TerrainGen;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class GenEventHandler {

	public WorldGenerator dirtGen;
	public WorldGenerator gravelGen;
	public WorldGenerator coalGen;
	public WorldGenerator ironGen;
	public WorldGenerator goldGen;
	public WorldGenerator redstoneGen;
	public WorldGenerator diamondGen;
	public WorldGenerator lapisGen;

	public GenEventHandler() {
		this.dirtGen = new WorldGenMinable(Blocks.dirt, 32);
		this.gravelGen = new WorldGenMinable(Blocks.gravel, 32);
		this.coalGen = new WorldGenMinable(Blocks.coal_ore, 16);
		this.ironGen = new WorldGenMinable(Blocks.iron_ore, 8);
		this.goldGen = new WorldGenMinable(Blocks.gold_ore, 8);
		this.redstoneGen = new WorldGenMinable(Blocks.redstone_ore, 7);
		this.diamondGen = new WorldGenMinable(Blocks.diamond_ore, 7);
		this.lapisGen = new WorldGenMinable(Blocks.lapis_ore, 6);
	}

	@SubscribeEvent
	public void onInitMapGen(final InitMapGenEvent e){
		if(!BetterOceans.getInstance().getConfig().isOverrideOcean())
			return;
		if(e.type == EventType.CAVE)
			e.newGen = new MapGenBetterOceansCaves(e.newGen);
		else if(e.type == EventType.RAVINE)
			e.newGen = new MapGenBetterOceansRavine(e.newGen);
	}

	/**
	 * This is used to generate extra ores in trenches. NOTE: The same method can be used in integration modules to also generate other ores.
	 */
	@SubscribeEvent
	public void onTrenchLeaveChunk(final TrenchChunkEvent.Leave e){
		final World currentWorld = e.getChunk().worldObj;
		final Random randomGenerator = currentWorld.rand;
		final int chunk_X = e.getChunk().xPosition;
		final int chunk_Z = e.getChunk().zPosition;
		this.genTrenchVanillaOres(chunk_X, chunk_Z, randomGenerator, currentWorld);
	}

	//Taken from vanilla gen, some values tweaked
	//TODO COFHCore compatibility
	public void genTrenchVanillaOres(final int chunk_X, final int chunk_Z, final Random randomGenerator, final World currentWorld){
		MinecraftForge.ORE_GEN_BUS.post(new OreGenEvent.Pre(currentWorld, randomGenerator, chunk_X, chunk_Z));
		if (TerrainGen.generateOre(currentWorld, randomGenerator, this.dirtGen, chunk_X, chunk_Z, DIRT))
			this.genStandardOre1(15, this.dirtGen, 0, 256, chunk_X, chunk_Z, randomGenerator, currentWorld);
		if (TerrainGen.generateOre(currentWorld, randomGenerator, this.gravelGen, chunk_X, chunk_Z, GRAVEL))
			this.genStandardOre1(5, this.gravelGen, 0, 256, chunk_X, chunk_Z, randomGenerator, currentWorld);
		if (TerrainGen.generateOre(currentWorld, randomGenerator, this.coalGen, chunk_X, chunk_Z, COAL))
			this.genStandardOre1(20, this.coalGen, 0, 128, chunk_X, chunk_Z, randomGenerator, currentWorld);
		if (TerrainGen.generateOre(currentWorld, randomGenerator, this.ironGen, chunk_X, chunk_Z, IRON))
			this.genStandardOre1(35, this.ironGen, 0, 64, chunk_X, chunk_Z, randomGenerator, currentWorld);
		if (TerrainGen.generateOre(currentWorld, randomGenerator, this.goldGen, chunk_X, chunk_Z, GOLD))
			this.genStandardOre1(6, this.goldGen, 0, 32, chunk_X, chunk_Z, randomGenerator, currentWorld);
		if (TerrainGen.generateOre(currentWorld, randomGenerator, this.redstoneGen, chunk_X, chunk_Z, REDSTONE))
			this.genStandardOre1(12, this.redstoneGen, 0, 16, chunk_X, chunk_Z, randomGenerator, currentWorld);
		if (TerrainGen.generateOre(currentWorld, randomGenerator, this.diamondGen, chunk_X, chunk_Z, DIAMOND))
			this.genStandardOre1(4, this.diamondGen, 0, 16, chunk_X, chunk_Z, randomGenerator, currentWorld);
		if (TerrainGen.generateOre(currentWorld, randomGenerator, this.lapisGen, chunk_X, chunk_Z, LAPIS))
			this.genStandardOre2(4, this.lapisGen, 16, 16, chunk_X, chunk_Z, randomGenerator, currentWorld);
		MinecraftForge.ORE_GEN_BUS.post(new OreGenEvent.Post(currentWorld, randomGenerator, chunk_X, chunk_Z));
	}

	protected void genStandardOre1(final int p_76795_1_, final WorldGenerator p_76795_2_, final int p_76795_3_, final int p_76795_4_, final int chunk_X, final int chunk_Z, final Random randomGenerator, final World currentWorld)
	{
		for (int l = 0; l < p_76795_1_; ++l)
		{
			final int i1 = chunk_X + randomGenerator.nextInt(16);
			final int j1 = randomGenerator.nextInt(p_76795_4_ - p_76795_3_) + p_76795_3_;
			final int k1 = chunk_Z + randomGenerator.nextInt(16);
			p_76795_2_.generate(currentWorld, randomGenerator, i1, j1, k1);
		}
	}

	protected void genStandardOre2(final int p_76793_1_, final WorldGenerator p_76793_2_, final int p_76793_3_, final int p_76793_4_, final int chunk_X, final int chunk_Z, final Random randomGenerator, final World currentWorld)
	{
		for (int l = 0; l < p_76793_1_; ++l)
		{
			final int i1 = chunk_X + randomGenerator.nextInt(16);
			final int j1 = randomGenerator.nextInt(p_76793_4_) + randomGenerator.nextInt(p_76793_4_) + p_76793_3_ - p_76793_4_;
			final int k1 = chunk_Z + randomGenerator.nextInt(16);
			p_76793_2_.generate(currentWorld, randomGenerator, i1, j1, k1);
		}
	}

}
