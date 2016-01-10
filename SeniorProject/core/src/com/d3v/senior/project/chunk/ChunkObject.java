package com.d3v.senior.project.chunk;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool.Poolable;

public class ChunkObject implements Poolable {
	// To allow for chunk generation
	public ChunkGenerator chunk = new ChunkGenerator();
	//public Chunk manager;
	
	// Where the chunk is located for rendering
	public Vector2 position;
	// What tiles are in the chunk
	public byte[][] tiles;
	
	public ChunkObject() {
		position = new Vector2();
		tiles = new byte[Chunk.TilesPerChunk][Chunk.TilesPerChunk];
	}
	
	public void init(int ChunkX, int ChunkY) {
		position.set(ChunkX, ChunkY);
		tiles = chunk.genChunk(ChunkX, ChunkY);
	}
	
	@Override
	public void reset() {
		position.setZero();
		for (byte tileX = 0; tileX < Chunk.TilesPerChunk; tileX++)
		{
			for (byte tileY = 0; tileY < Chunk.TilesPerChunk; tileY++)
			{
				tiles[tileX][tileY] = 0;
			}
		}
	}
}
