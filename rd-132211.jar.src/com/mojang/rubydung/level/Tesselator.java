/*    */ package com.mojang.rubydung.level;
/*    */ 
/*    */ import java.nio.FloatBuffer;
/*    */ import org.lwjgl.BufferUtils;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Tesselator
/*    */ {
/*    */   private static final int MAX_VERTICES = 100000;
/* 13 */   private FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(300000);
/* 14 */   private FloatBuffer texCoordBuffer = BufferUtils.createFloatBuffer(200000);
/* 15 */   private FloatBuffer colorBuffer = BufferUtils.createFloatBuffer(300000);
/* 16 */   private int vertices = 0;
/*    */   
/*    */   private float u;
/*    */   
/*    */   private float v;
/*    */   private float r;
/*    */   
/*    */   public void flush() {
/* 24 */     this.vertexBuffer.flip();
/* 25 */     this.texCoordBuffer.flip();
/* 26 */     this.colorBuffer.flip();
/*    */     
/* 28 */     GL11.glVertexPointer(3, 0, this.vertexBuffer);
/* 29 */     if (this.hasTexture) GL11.glTexCoordPointer(2, 0, this.texCoordBuffer); 
/* 30 */     if (this.hasColor) GL11.glColorPointer(3, 0, this.colorBuffer);
/*    */     
/* 32 */     GL11.glEnableClientState(32884);
/* 33 */     if (this.hasTexture) GL11.glEnableClientState(32888); 
/* 34 */     if (this.hasColor) GL11.glEnableClientState(32886);
/*    */ 
/*    */ 
/*    */     
/* 38 */     GL11.glDrawArrays(7, 0, this.vertices);
/*    */     
/* 40 */     GL11.glDisableClientState(32884);
/* 41 */     if (this.hasTexture) GL11.glDisableClientState(32888); 
/* 42 */     if (this.hasColor) GL11.glDisableClientState(32886);
/*    */     
/* 44 */     clear();
/*    */   }
/*    */   private float g; private float b; private boolean hasColor = false; private boolean hasTexture = false;
/*    */   
/*    */   private void clear() {
/* 49 */     this.vertices = 0;
/*    */     
/* 51 */     this.vertexBuffer.clear();
/* 52 */     this.texCoordBuffer.clear();
/* 53 */     this.colorBuffer.clear();
/*    */   }
/*    */ 
/*    */   
/*    */   public void init() {
/* 58 */     clear();
/* 59 */     this.hasColor = false;
/* 60 */     this.hasTexture = false;
/*    */   }
/*    */ 
/*    */   
/*    */   public void tex(float u, float v) {
/* 65 */     this.hasTexture = true;
/* 66 */     this.u = u;
/* 67 */     this.v = v;
/*    */   }
/*    */ 
/*    */   
/*    */   public void color(float r, float g, float b) {
/* 72 */     this.hasColor = true;
/* 73 */     this.r = r;
/* 74 */     this.g = g;
/* 75 */     this.b = b;
/*    */   }
/*    */ 
/*    */   
/*    */   public void vertex(float x, float y, float z) {
/* 80 */     this.vertexBuffer.put(this.vertices * 3 + 0, x).put(this.vertices * 3 + 1, y).put(this.vertices * 3 + 2, z);
/* 81 */     if (this.hasTexture) this.texCoordBuffer.put(this.vertices * 2 + 0, this.u).put(this.vertices * 2 + 1, this.v); 
/* 82 */     if (this.hasColor) this.colorBuffer.put(this.vertices * 3 + 0, this.r).put(this.vertices * 3 + 1, this.g).put(this.vertices * 3 + 2, this.b); 
/* 83 */     this.vertices++;
/* 84 */     if (this.vertices == 100000)
/*    */     {
/* 86 */       flush();
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\HaoyuanX36054\JAVA\.minecraft\versions\rd-132211\rd-132211.jar!\com\mojang\rubydung\level\Tesselator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */