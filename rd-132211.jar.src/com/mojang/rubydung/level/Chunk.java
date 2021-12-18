/*    */ package com.mojang.rubydung.level;
/*    */ 
/*    */ import com.mojang.rubydung.Textures;
/*    */ import com.mojang.rubydung.phys.AABB;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ 
/*    */ public class Chunk
/*    */ {
/*    */   public AABB aabb;
/*    */   public final Level level;
/*    */   public final int x0;
/*    */   public final int y0;
/* 14 */   private int lists = -1; public final int z0; public final int x1; public final int y1; public final int z1; private boolean dirty = true;
/* 15 */   private static int texture = Textures.loadTexture("/terrain.png", 9728);
/*    */   
/* 17 */   private static Tesselator t = new Tesselator();
/*    */   
/* 19 */   public static int rebuiltThisFrame = 0;
/* 20 */   public static int updates = 0;
/*    */ 
/*    */   
/*    */   public Chunk(Level level, int x0, int y0, int z0, int x1, int y1, int z1) {
/* 24 */     this.level = level;
/* 25 */     this.x0 = x0;
/* 26 */     this.y0 = y0;
/* 27 */     this.z0 = z0;
/* 28 */     this.x1 = x1;
/* 29 */     this.y1 = y1;
/* 30 */     this.z1 = z1;
/*    */     
/* 32 */     this.aabb = new AABB(x0, y0, z0, x1, y1, z1);
/* 33 */     this.lists = GL11.glGenLists(2);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private void rebuild(int layer) {
/* 39 */     if (rebuiltThisFrame == 2)
/* 40 */       return;  this.dirty = false;
/*    */     
/* 42 */     updates++;
/*    */     
/* 44 */     rebuiltThisFrame++;
/*    */ 
/*    */     
/* 47 */     GL11.glNewList(this.lists + layer, 4864);
/* 48 */     GL11.glEnable(3553);
/* 49 */     GL11.glBindTexture(3553, texture);
/* 50 */     t.init();
/* 51 */     int tiles = 0;
/* 52 */     for (int x = this.x0; x < this.x1; x++) {
/* 53 */       for (int y = this.y0; y < this.y1; y++) {
/* 54 */         for (int z = this.z0; z < this.z1; z++) {
/*    */           
/* 56 */           if (this.level.isTile(x, y, z))
/*    */           
/*    */           { 
/* 59 */             int tex = (y == this.level.depth * 2 / 3) ? 0 : 1;
/* 60 */             tiles++;
/* 61 */             if (tex == 0)
/* 62 */             { Tile.rock.render(t, this.level, layer, x, y, z); }
/*    */             else
/* 64 */             { Tile.grass.render(t, this.level, layer, x, y, z); }  } 
/*    */         } 
/*    */       } 
/* 67 */     }  t.flush();
/* 68 */     GL11.glDisable(3553);
/* 69 */     GL11.glEndList();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void render(int layer) {
/* 76 */     if (this.dirty) {
/*    */       
/* 78 */       rebuild(0);
/* 79 */       rebuild(1);
/*    */     } 
/*    */     
/* 82 */     GL11.glCallList(this.lists + layer);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void setDirty() {
/* 88 */     this.dirty = true;
/*    */   }
/*    */ }


/* Location:              C:\Users\HaoyuanX36054\JAVA\.minecraft\versions\rd-132211\rd-132211.jar!\com\mojang\rubydung\level\Chunk.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */