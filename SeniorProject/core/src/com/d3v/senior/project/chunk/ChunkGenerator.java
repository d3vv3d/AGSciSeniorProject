package com.d3v.senior.project.chunk;

import com.d3v.senior.project.noise.SimplexNoise;

public class ChunkGenerator 
{
	byte[][] tiles;
	//Stores raw noise output
	double[][] results;
	int seed = Chunk.currentSeed;

	public ChunkGenerator()
	{
		tiles = new byte[Chunk.TilesPerChunk][Chunk.TilesPerChunk];
		results = new double[Chunk.TilesPerChunk][Chunk.TilesPerChunk];
		System.out.println(seed);
	}
	
	SimplexNoise simplexNoise = new SimplexNoise(100 ,0.1, seed);//The last number here is the seed change it to change the results

	byte[][] genChunk(int ChunkX, int ChunkY)
	{
		getNoise(ChunkX, ChunkY);
		
		for (byte tileX = 0; tileX < Chunk.TilesPerChunk; tileX++)
		{
			for (byte tileY = 0; tileY < Chunk.TilesPerChunk; tileY++)
			{
				if(results[tileX][tileY] <= .5)
					tiles[tileX][tileY] = TileType.Water;
				else if(results[tileX][tileY] > .5)
					tiles[tileX][tileY] = TileType.Sand;
			}
		}
		
		return tiles;
	}
	
	void getNoise(int ChunkX, int ChunkY)
	{
		for (byte tileX = 0; tileX < Chunk.TilesPerChunk; tileX++)
		{
			for (byte tileY = 0; tileY < Chunk.TilesPerChunk; tileY++)
			{
				results[tileX][tileY] = 0.5 * (1 + simplexNoise.getNoise((Chunk.TilesPerChunk * ChunkX) + tileX,
						(Chunk.TilesPerChunk * ChunkY) + tileY));
			}
		}
	}
}

/*if (tileX == 0 || tileX == 2 || tileX == 4 || tileX == 6 || tileX == 8 || tileX == 10 || tileX == 12 || tileX == 14)
{
	if (tileY == 0)
		tiles[tileX][tileY] = 1;
	else if (tileY == 15)
		tiles[tileX][tileY] = 2;
}
else if (tileX == 1 || tileX == 3 || tileX == 5 || tileX == 7 || tileX == 9 || tileX == 11 || tileX == 13 || tileX == 15)
{
	if (tileY == 0)
		tiles[tileX][tileY] = 2;
	else if (tileY == 15)
		tiles[tileX][tileY] = 1;
}*/