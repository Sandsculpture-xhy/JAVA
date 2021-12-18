/*     */ package com.mojang.rubydung.level;
/*     */ 
/*     */ import com.mojang.rubydung.HitResult;
/*     */ import com.mojang.rubydung.Player;
/*     */ import com.mojang.rubydung.phys.AABB;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LevelRenderer
/*     */   implements LevelListener
/*     */ {
/*     */   private static final int CHUNK_SIZE = 16;
/*     */   private Level level;
/*     */   private Chunk[] chunks;
/*     */   private int xChunks;
/*     */   private int yChunks;
/*     */   private int zChunks;
/*     */   Tesselator t;
/*     */   
/*     */   public LevelRenderer(Level level) {
/*  57 */     this.t = new Tesselator(); this.level = level; level.addListener(this); this.xChunks = level.width / 16; this.yChunks = level.depth / 16; this.zChunks = level.height / 16; this.chunks = new Chunk[this.xChunks * this.yChunks * this.zChunks]; for (int x = 0; x < this.xChunks; x++) { for (int y = 0; y < this.yChunks; y++) { for (int z = 0; z < this.zChunks; z++) { int x0 = x * 16; int y0 = y * 16; int z0 = z * 16; int x1 = (x + 1) * 16; int y1 = (y + 1) * 16; int z1 = (z + 1) * 16; if (x1 > level.width)
/*     */             x1 = level.width;  if (y1 > level.depth)
/*     */             y1 = level.depth;  if (z1 > level.height)
/*     */             z1 = level.height;  this.chunks[(x + y * this.xChunks) * this.zChunks + z] = new Chunk(level, x0, y0, z0, x1, y1, z1); }  }  }
/*  61 */      } public void pick(Player player) { float r = 3.0F;
/*  62 */     AABB box = player.bb.grow(r, r, r);
/*  63 */     int x0 = (int)box.x0;
/*  64 */     int x1 = (int)(box.x1 + 1.0F);
/*  65 */     int y0 = (int)box.y0;
/*  66 */     int y1 = (int)(box.y1 + 1.0F);
/*  67 */     int z0 = (int)box.z0;
/*  68 */     int z1 = (int)(box.z1 + 1.0F);
/*     */     
/*  70 */     GL11.glInitNames();
/*  71 */     for (int x = x0; x < x1; x++) {
/*     */       
/*  73 */       GL11.glPushName(x);
/*  74 */       for (int y = y0; y < y1; y++) {
/*     */         
/*  76 */         GL11.glPushName(y);
/*  77 */         for (int z = z0; z < z1; z++) {
/*     */           
/*  79 */           GL11.glPushName(z);
/*  80 */           if (this.level.isSolidTile(x, y, z)) {
/*     */             
/*  82 */             GL11.glPushName(0);
/*  83 */             for (int i = 0; i < 6; i++) {
/*     */               
/*  85 */               GL11.glPushName(i);
/*  86 */               this.t.init();
/*  87 */               Tile.rock.renderFace(this.t, x, y, z, i);
/*  88 */               this.t.flush();
/*  89 */               GL11.glPopName();
/*     */             } 
/*  91 */             GL11.glPopName();
/*     */           } 
/*  93 */           GL11.glPopName();
/*     */         } 
/*  95 */         GL11.glPopName();
/*     */       } 
/*  97 */       GL11.glPopName();
/*     */     }  }
/*     */   public void render(Player player, int layer) { Chunk.rebuiltThisFrame = 0; Frustum frustum = Frustum.getFrustum();
/*     */     for (int i = 0; i < this.chunks.length; i++) {
/*     */       if (frustum.cubeInFrustum((this.chunks[i]).aabb))
/*     */         this.chunks[i].render(layer); 
/* 103 */     }  } public void renderHit(HitResult h) { GL11.glEnable(3042);
/*     */     
/* 105 */     GL11.glBlendFunc(770, 1);
/* 106 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, (float)Math.sin(System.currentTimeMillis() / 100.0D) * 0.2F + 0.4F);
/* 107 */     this.t.init();
/* 108 */     Tile.rock.renderFace(this.t, h.x, h.y, h.z, h.f);
/* 109 */     this.t.flush();
/* 110 */     GL11.glDisable(3042); }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDirty(int x0, int y0, int z0, int x1, int y1, int z1) {
/* 115 */     x0 /= 16;
/* 116 */     x1 /= 16;
/* 117 */     y0 /= 16;
/* 118 */     y1 /= 16;
/* 119 */     z0 /= 16;
/* 120 */     z1 /= 16;
/*     */     
/* 122 */     if (x0 < 0) x0 = 0; 
/* 123 */     if (y0 < 0) y0 = 0; 
/* 124 */     if (z0 < 0) z0 = 0; 
/* 125 */     if (x1 >= this.xChunks) x1 = this.xChunks - 1; 
/* 126 */     if (y1 >= this.yChunks) y1 = this.yChunks - 1; 
/* 127 */     if (z1 >= this.zChunks) z1 = this.zChunks - 1;
/*     */     
/* 129 */     for (int x = x0; x <= x1; x++) {
/* 130 */       for (int y = y0; y <= y1; y++) {
/* 131 */         for (int z = z0; z <= z1; z++)
/*     */         {
/* 133 */           this.chunks[(x + y * this.xChunks) * this.zChunks + z].setDirty(); } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void tileChanged(int x, int y, int z) {
/* 139 */     setDirty(x - 1, y - 1, z - 1, x + 1, y + 1, z + 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public void lightColumnChanged(int x, int z, int y0, int y1) {
/* 144 */     setDirty(x - 1, y0 - 1, z - 1, x + 1, y1 + 1, z + 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public void allChanged() {
/* 149 */     setDirty(0, 0, 0, this.level.width, this.level.depth, this.level.height);
/*     */   }
/*     */ }


/* Location:              C:\Users\HaoyuanX36054\JAVA\.minecraft\versions\rd-132211\rd-132211.jar!\com\mojang\rubydung\level\LevelRenderer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */