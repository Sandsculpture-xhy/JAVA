/*     */ package com.mojang.rubydung;
/*     */ 
/*     */ import com.mojang.rubydung.level.Level;
/*     */ import com.mojang.rubydung.phys.AABB;
/*     */ import java.util.List;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Player
/*     */ {
/*     */   private Level level;
/*     */   public float xo;
/*     */   public float yo;
/*     */   public float zo;
/*     */   public float x;
/*     */   public float y;
/*     */   public float z;
/*     */   
/*     */   public Player(Level level) {
/*  22 */     this.level = level;
/*  23 */     resetPos();
/*     */   }
/*     */   public float xd; public float yd; public float zd; public float yRot; public float xRot; public AABB bb; public boolean onGround = false;
/*     */   
/*     */   private void resetPos() {
/*  28 */     float x = (float)Math.random() * this.level.width;
/*  29 */     float y = (this.level.depth + 10);
/*  30 */     float z = (float)Math.random() * this.level.height;
/*  31 */     setPos(x, y, z);
/*     */   }
/*     */ 
/*     */   
/*     */   private void setPos(float x, float y, float z) {
/*  36 */     this.x = x;
/*  37 */     this.y = y;
/*  38 */     this.z = z;
/*  39 */     float w = 0.3F;
/*  40 */     float h = 0.9F;
/*  41 */     this.bb = new AABB(x - w, y - h, z - w, x + w, y + h, z + w);
/*     */   }
/*     */ 
/*     */   
/*     */   public void turn(float xo, float yo) {
/*  46 */     this.yRot = (float)(this.yRot + xo * 0.15D);
/*  47 */     this.xRot = (float)(this.xRot - yo * 0.15D);
/*  48 */     if (this.xRot < -90.0F) this.xRot = -90.0F; 
/*  49 */     if (this.xRot > 90.0F) this.xRot = 90.0F;
/*     */   
/*     */   }
/*     */   
/*     */   public void tick() {
/*  54 */     this.xo = this.x;
/*  55 */     this.yo = this.y;
/*  56 */     this.zo = this.z;
/*  57 */     float xa = 0.0F;
/*  58 */     float ya = 0.0F;
/*     */     
/*  60 */     if (Keyboard.isKeyDown(19))
/*     */     {
/*  62 */       resetPos();
/*     */     }
/*  64 */     if (Keyboard.isKeyDown(200) || Keyboard.isKeyDown(17)) ya--; 
/*  65 */     if (Keyboard.isKeyDown(208) || Keyboard.isKeyDown(31)) ya++; 
/*  66 */     if (Keyboard.isKeyDown(203) || Keyboard.isKeyDown(30)) xa--; 
/*  67 */     if (Keyboard.isKeyDown(205) || Keyboard.isKeyDown(32)) xa++; 
/*  68 */     if (Keyboard.isKeyDown(57) || Keyboard.isKeyDown(219))
/*     */     {
/*  70 */       if (this.onGround)
/*     */       {
/*  72 */         this.yd = 0.12F;
/*     */       }
/*     */     }
/*     */     
/*  76 */     moveRelative(xa, ya, this.onGround ? 0.02F : 0.005F);
/*     */     
/*  78 */     this.yd = (float)(this.yd - 0.005D);
/*  79 */     move(this.xd, this.yd, this.zd);
/*  80 */     this.xd *= 0.91F;
/*  81 */     this.yd *= 0.98F;
/*  82 */     this.zd *= 0.91F;
/*     */     
/*  84 */     if (this.onGround) {
/*     */       
/*  86 */       this.xd *= 0.8F;
/*  87 */       this.zd *= 0.8F;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void move(float xa, float ya, float za) {
/*  93 */     float xaOrg = xa;
/*  94 */     float yaOrg = ya;
/*  95 */     float zaOrg = za;
/*     */     
/*  97 */     List<AABB> aABBs = this.level.getCubes(this.bb.expand(xa, ya, za));
/*     */     int i;
/*  99 */     for (i = 0; i < aABBs.size(); i++)
/* 100 */       ya = ((AABB)aABBs.get(i)).clipYCollide(this.bb, ya); 
/* 101 */     this.bb.move(0.0F, ya, 0.0F);
/*     */     
/* 103 */     for (i = 0; i < aABBs.size(); i++)
/* 104 */       xa = ((AABB)aABBs.get(i)).clipXCollide(this.bb, xa); 
/* 105 */     this.bb.move(xa, 0.0F, 0.0F);
/*     */     
/* 107 */     for (i = 0; i < aABBs.size(); i++)
/* 108 */       za = ((AABB)aABBs.get(i)).clipZCollide(this.bb, za); 
/* 109 */     this.bb.move(0.0F, 0.0F, za);
/*     */     
/* 111 */     this.onGround = (yaOrg != ya && yaOrg < 0.0F);
/*     */     
/* 113 */     if (xaOrg != xa) this.xd = 0.0F; 
/* 114 */     if (yaOrg != ya) this.yd = 0.0F; 
/* 115 */     if (zaOrg != za) this.zd = 0.0F;
/*     */     
/* 117 */     this.x = (this.bb.x0 + this.bb.x1) / 2.0F;
/* 118 */     this.y = this.bb.y0 + 1.62F;
/* 119 */     this.z = (this.bb.z0 + this.bb.z1) / 2.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public void moveRelative(float xa, float za, float speed) {
/* 124 */     float dist = xa * xa + za * za;
/* 125 */     if (dist < 0.01F)
/*     */       return; 
/* 127 */     dist = speed / (float)Math.sqrt(dist);
/* 128 */     xa *= dist;
/* 129 */     za *= dist;
/*     */ 
/*     */     
/* 132 */     float sin = (float)Math.sin(this.yRot * Math.PI / 180.0D);
/* 133 */     float cos = (float)Math.cos(this.yRot * Math.PI / 180.0D);
/*     */     
/* 135 */     this.xd += xa * cos - za * sin;
/* 136 */     this.zd += za * cos + xa * sin;
/*     */   }
/*     */ }


/* Location:              C:\Users\HaoyuanX36054\JAVA\.minecraft\versions\rd-132211\rd-132211.jar!\com\mojang\rubydung\Player.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */