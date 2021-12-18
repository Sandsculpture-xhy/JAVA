/*     */ package com.mojang.rubydung.level;
/*     */ import com.mojang.rubydung.phys.AABB;
/*     */ import java.io.DataInputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.zip.GZIPInputStream;
/*     */ import java.util.zip.GZIPOutputStream;
/*     */ 
/*     */ public class Level {
/*     */   public final int width;
/*  14 */   private ArrayList<LevelListener> levelListeners = new ArrayList<LevelListener>(); public final int height; public final int depth; private byte[] blocks;
/*     */   private int[] lightDepths;
/*     */   
/*     */   public Level(int w, int h, int d) {
/*  18 */     this.width = w;
/*  19 */     this.height = h;
/*  20 */     this.depth = d;
/*  21 */     this.blocks = new byte[w * h * d];
/*     */     
/*  23 */     this.lightDepths = new int[w * h];
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
/*     */ 
/*     */     
/*  64 */     for (int x = 0; x < w; x++) {
/*  65 */       for (int y = 0; y < d; y++) {
/*  66 */         for (int z = 0; z < h; z++) {
/*     */           
/*  68 */           int i = (y * this.height + z) * this.width + x;
/*  69 */           this.blocks[i] = (byte)((y <= d * 2 / 3) ? 1 : 0);
/*     */         } 
/*     */       } 
/*     */     } 
/*  73 */     calcLightDepths(0, 0, w, h);
/*     */     
/*  75 */     load();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void load() {
/*     */     try {
/*  82 */       DataInputStream dis = new DataInputStream(new GZIPInputStream(new FileInputStream(new File("level.dat"))));
/*  83 */       dis.readFully(this.blocks);
/*  84 */       calcLightDepths(0, 0, this.width, this.height);
/*  85 */       for (int i = 0; i < this.levelListeners.size(); i++)
/*  86 */         ((LevelListener)this.levelListeners.get(i)).allChanged(); 
/*  87 */       dis.close();
/*     */     }
/*  89 */     catch (Exception e) {
/*     */       
/*  91 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void save() {
/*     */     try {
/*  99 */       DataOutputStream dos = new DataOutputStream(new GZIPOutputStream(new FileOutputStream(new File("level.dat"))));
/* 100 */       dos.write(this.blocks);
/* 101 */       dos.close();
/*     */     }
/* 103 */     catch (Exception e) {
/*     */       
/* 105 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void calcLightDepths(int x0, int y0, int x1, int y1) {
/* 111 */     for (int x = x0; x < x0 + x1; x++) {
/* 112 */       for (int z = y0; z < y0 + y1; z++) {
/*     */         
/* 114 */         int oldDepth = this.lightDepths[x + z * this.width];
/* 115 */         int y = this.depth - 1;
/* 116 */         while (y > 0 && !isLightBlocker(x, y, z))
/* 117 */           y--; 
/* 118 */         this.lightDepths[x + z * this.width] = y;
/*     */         
/* 120 */         if (oldDepth != y) {
/*     */           
/* 122 */           int yl0 = (oldDepth < y) ? oldDepth : y;
/* 123 */           int yl1 = (oldDepth > y) ? oldDepth : y;
/* 124 */           for (int i = 0; i < this.levelListeners.size(); i++)
/* 125 */             ((LevelListener)this.levelListeners.get(i)).lightColumnChanged(x, z, yl0, yl1); 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void addListener(LevelListener levelListener) {
/* 132 */     this.levelListeners.add(levelListener);
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeListener(LevelListener levelListener) {
/* 137 */     this.levelListeners.remove(levelListener);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isTile(int x, int y, int z) {
/* 142 */     if (x < 0 || y < 0 || z < 0 || x >= this.width || y >= this.depth || z >= this.height) return false; 
/* 143 */     return (this.blocks[(y * this.height + z) * this.width + x] == 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSolidTile(int x, int y, int z) {
/* 148 */     return isTile(x, y, z);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isLightBlocker(int x, int y, int z) {
/* 153 */     return isSolidTile(x, y, z);
/*     */   }
/*     */ 
/*     */   
/*     */   public ArrayList<AABB> getCubes(AABB aABB) {
/* 158 */     ArrayList<AABB> aABBs = new ArrayList<AABB>();
/* 159 */     int x0 = (int)aABB.x0;
/* 160 */     int x1 = (int)(aABB.x1 + 1.0F);
/* 161 */     int y0 = (int)aABB.y0;
/* 162 */     int y1 = (int)(aABB.y1 + 1.0F);
/* 163 */     int z0 = (int)aABB.z0;
/* 164 */     int z1 = (int)(aABB.z1 + 1.0F);
/*     */     
/* 166 */     if (x0 < 0) x0 = 0; 
/* 167 */     if (y0 < 0) y0 = 0; 
/* 168 */     if (z0 < 0) z0 = 0; 
/* 169 */     if (x1 > this.width) x1 = this.width; 
/* 170 */     if (y1 > this.depth) y1 = this.depth; 
/* 171 */     if (z1 > this.height) z1 = this.height;
/*     */     
/* 173 */     for (int x = x0; x < x1; x++) {
/* 174 */       for (int y = y0; y < y1; y++) {
/* 175 */         for (int z = z0; z < z1; z++) {
/*     */           
/* 177 */           if (isSolidTile(x, y, z))
/*     */           {
/* 179 */             aABBs.add(new AABB(x, y, z, (x + 1), (y + 1), (z + 1))); } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 183 */     return aABBs;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getBrightness(int x, int y, int z) {
/* 188 */     float dark = 0.8F;
/* 189 */     float light = 1.0F;
/* 190 */     if (x < 0 || y < 0 || z < 0 || x >= this.width || y >= this.depth || z >= this.height) return light; 
/* 191 */     if (y < this.lightDepths[x + z * this.width]) return dark; 
/* 192 */     return light;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setTile(int x, int y, int z, int type) {
/* 197 */     if (x < 0 || y < 0 || z < 0 || x >= this.width || y >= this.depth || z >= this.height)
/* 198 */       return;  this.blocks[(y * this.height + z) * this.width + x] = (byte)type;
/* 199 */     calcLightDepths(x, z, 1, 1);
/* 200 */     for (int i = 0; i < this.levelListeners.size(); i++)
/* 201 */       ((LevelListener)this.levelListeners.get(i)).tileChanged(x, y, z); 
/*     */   }
/*     */ }


/* Location:              C:\Users\HaoyuanX36054\JAVA\.minecraft\versions\rd-132211\rd-132211.jar!\com\mojang\rubydung\level\Level.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */