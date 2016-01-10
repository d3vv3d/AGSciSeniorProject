package com.d3v.senior.project.chunk;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;


public class ChunkRender 
{	
	public void render(SpriteBatch batch, Texture tex, Texture tex2, Array<ChunkObject> chunkArray)
	{
		for (ChunkObject chunk : chunkArray)
		{
			for (byte tileX = 0; tileX <= 15; tileX++)
			{
				for (byte tileY = 0; tileY <= 15; tileY++)
				{
					if (chunk.tiles[tileX][tileY] == TileType.Water)
						batch.draw(tex, (chunk.position.x * Chunk.ChunkSize) + (tileX * Chunk.TileSize), 
								(chunk.position.y * Chunk.ChunkSize) + (tileY * Chunk.TileSize));
					else if (chunk.tiles[tileX][tileY] == TileType.Sand)
						batch.draw(tex2, (chunk.position.x * Chunk.ChunkSize) + (tileX * Chunk.TileSize), 
								(chunk.position.y * Chunk.ChunkSize) + (tileY * Chunk.TileSize));
				}
			}
		}
	}
}
