/*     */ package com.mojang.rubydung.level;
/*     */ 
/*     */ public class Tile
/*     */ {
/*   5 */   public static Tile rock = new Tile(0);
/*   6 */   public static Tile grass = new Tile(1);
/*     */   
/*   8 */   private int tex = 0;
/*     */ 
/*     */   
/*     */   private Tile(int tex) {
/*  12 */     this.tex = tex;
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(Tesselator t, Level level, int layer, int x, int y, int z) {
/*  17 */     float u0 = this.tex / 16.0F;
/*  18 */     float u1 = u0 + 0.0624375F;
/*  19 */     float v0 = 0.0F;
/*  20 */     float v1 = v0 + 0.0624375F;
/*  21 */     float c1 = 1.0F;
/*  22 */     float c2 = 0.8F;
/*  23 */     float c3 = 0.6F;
/*     */     
/*  25 */     float x0 = x + 0.0F;
/*  26 */     float x1 = x + 1.0F;
/*  27 */     float y0 = y + 0.0F;
/*  28 */     float y1 = y + 1.0F;
/*  29 */     float z0 = z + 0.0F;
/*  30 */     float z1 = z + 1.0F;
/*     */     
/*  32 */     if (!level.isSolidTile(x, y - 1, z)) {
/*     */       
/*  34 */       float br = level.getBrightness(x, y - 1, z) * c1;
/*  35 */       if ((((br == c1) ? 1 : 0) ^ ((layer == 1) ? 1 : 0)) != 0) {
/*     */         
/*  37 */         t.color(br, br, br);
/*  38 */         t.tex(u0, v1);
/*  39 */         t.vertex(x0, y0, z1);
/*  40 */         t.tex(u0, v0);
/*  41 */         t.vertex(x0, y0, z0);
/*  42 */         t.tex(u1, v0);
/*  43 */         t.vertex(x1, y0, z0);
/*  44 */         t.tex(u1, v1);
/*  45 */         t.vertex(x1, y0, z1);
/*     */       } 
/*     */     } 
/*     */     
/*  49 */     if (!level.isSolidTile(x, y + 1, z)) {
/*     */       
/*  51 */       float br = level.getBrightness(x, y, z) * c1;
/*  52 */       if ((((br == c1) ? 1 : 0) ^ ((layer == 1) ? 1 : 0)) != 0) {
/*     */         
/*  54 */         t.color(br, br, br);
/*  55 */         t.tex(u1, v1);
/*  56 */         t.vertex(x1, y1, z1);
/*  57 */         t.tex(u1, v0);
/*  58 */         t.vertex(x1, y1, z0);
/*  59 */         t.tex(u0, v0);
/*  60 */         t.vertex(x0, y1, z0);
/*  61 */         t.tex(u0, v1);
/*  62 */         t.vertex(x0, y1, z1);
/*     */       } 
/*     */     } 
/*     */     
/*  66 */     if (!level.isSolidTile(x, y, z - 1)) {
/*     */       
/*  68 */       float br = level.getBrightness(x, y, z - 1) * c2;
/*  69 */       if ((((br == c2) ? 1 : 0) ^ ((layer == 1) ? 1 : 0)) != 0) {
/*     */         
/*  71 */         t.color(br, br, br);
/*  72 */         t.tex(u1, v0);
/*  73 */         t.vertex(x0, y1, z0);
/*  74 */         t.tex(u0, v0);
/*  75 */         t.vertex(x1, y1, z0);
/*  76 */         t.tex(u0, v1);
/*  77 */         t.vertex(x1, y0, z0);
/*  78 */         t.tex(u1, v1);
/*  79 */         t.vertex(x0, y0, z0);
/*     */       } 
/*     */     } 
/*     */     
/*  83 */     if (!level.isSolidTile(x, y, z + 1)) {
/*     */       
/*  85 */       float br = level.getBrightness(x, y, z + 1) * c2;
/*  86 */       if ((((br == c2) ? 1 : 0) ^ ((layer == 1) ? 1 : 0)) != 0) {
/*     */         
/*  88 */         t.color(br, br, br);
/*  89 */         t.tex(u0, v0);
/*  90 */         t.vertex(x0, y1, z1);
/*  91 */         t.tex(u0, v1);
/*  92 */         t.vertex(x0, y0, z1);
/*  93 */         t.tex(u1, v1);
/*  94 */         t.vertex(x1, y0, z1);
/*  95 */         t.tex(u1, v0);
/*  96 */         t.vertex(x1, y1, z1);
/*     */       } 
/*     */     } 
/*     */     
/* 100 */     if (!level.isSolidTile(x - 1, y, z)) {
/*     */       
/* 102 */       float br = level.getBrightness(x - 1, y, z) * c3;
/* 103 */       if ((((br == c3) ? 1 : 0) ^ ((layer == 1) ? 1 : 0)) != 0) {
/*     */         
/* 105 */         t.color(br, br, br);
/* 106 */         t.tex(u1, v0);
/* 107 */         t.vertex(x0, y1, z1);
/* 108 */         t.tex(u0, v0);
/* 109 */         t.vertex(x0, y1, z0);
/* 110 */         t.tex(u0, v1);
/* 111 */         t.vertex(x0, y0, z0);
/* 112 */         t.tex(u1, v1);
/* 113 */         t.vertex(x0, y0, z1);
/*     */       } 
/*     */     } 
/*     */     
/* 117 */     if (!level.isSolidTile(x + 1, y, z)) {
/*     */       
/* 119 */       float br = level.getBrightness(x + 1, y, z) * c3;
/* 120 */       if ((((br == c3) ? 1 : 0) ^ ((layer == 1) ? 1 : 0)) != 0) {
/*     */         
/* 122 */         t.color(br, br, br);
/* 123 */         t.tex(u0, v1);
/* 124 */         t.vertex(x1, y0, z1);
/* 125 */         t.tex(u1, v1);
/* 126 */         t.vertex(x1, y0, z0);
/* 127 */         t.tex(u1, v0);
/* 128 */         t.vertex(x1, y1, z0);
/* 129 */         t.tex(u0, v0);
/* 130 */         t.vertex(x1, y1, z1);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderFace(Tesselator t, int x, int y, int z, int face) {
/* 137 */     float x0 = x + 0.0F;
/* 138 */     float x1 = x + 1.0F;
/* 139 */     float y0 = y + 0.0F;
/* 140 */     float y1 = y + 1.0F;
/* 141 */     float z0 = z + 0.0F;
/* 142 */     float z1 = z + 1.0F;
/*     */     
/* 144 */     if (face == 0) {
/*     */       
/* 146 */       t.vertex(x0, y0, z1);
/* 147 */       t.vertex(x0, y0, z0);
/* 148 */       t.vertex(x1, y0, z0);
/* 149 */       t.vertex(x1, y0, z1);
/*     */     } 
/*     */     
/* 152 */     if (face == 1) {
/*     */       
/* 154 */       t.vertex(x1, y1, z1);
/* 155 */       t.vertex(x1, y1, z0);
/* 156 */       t.vertex(x0, y1, z0);
/* 157 */       t.vertex(x0, y1, z1);
/*     */     } 
/*     */     
/* 160 */     if (face == 2) {
/*     */       
/* 162 */       t.vertex(x0, y1, z0);
/* 163 */       t.vertex(x1, y1, z0);
/* 164 */       t.vertex(x1, y0, z0);
/* 165 */       t.vertex(x0, y0, z0);
/*     */     } 
/*     */     
/* 168 */     if (face == 3) {
/*     */       
/* 170 */       t.vertex(x0, y1, z1);
/* 171 */       t.vertex(x0, y0, z1);
/* 172 */       t.vertex(x1, y0, z1);
/* 173 */       t.vertex(x1, y1, z1);
/*     */     } 
/*     */     
/* 176 */     if (face == 4) {
/*     */       
/* 178 */       t.vertex(x0, y1, z1);
/* 179 */       t.vertex(x0, y1, z0);
/* 180 */       t.vertex(x0, y0, z0);
/* 181 */       t.vertex(x0, y0, z1);
/*     */     } 
/*     */     
/* 184 */     if (face == 5) {
/*     */       
/* 186 */       t.vertex(x1, y0, z1);
/* 187 */       t.vertex(x1, y0, z0);
/* 188 */       t.vertex(x1, y1, z0);
/* 189 */       t.vertex(x1, y1, z1);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\HaoyuanX36054\JAVA\.minecraft\versions\rd-132211\rd-132211.jar!\com\mojang\rubydung\level\Tile.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */