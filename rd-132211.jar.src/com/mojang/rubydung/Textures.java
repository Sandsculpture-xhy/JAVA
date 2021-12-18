/*    */ package com.mojang.rubydung;
/*    */ 
/*    */ import java.awt.image.BufferedImage;
/*    */ import java.io.IOException;
/*    */ import java.nio.ByteBuffer;
/*    */ import java.nio.IntBuffer;
/*    */ import java.util.HashMap;
/*    */ import javax.imageio.ImageIO;
/*    */ import org.lwjgl.BufferUtils;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ import org.lwjgl.util.glu.GLU;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Textures
/*    */ {
/* 18 */   private static HashMap<String, Integer> idMap = new HashMap<String, Integer>();
/*    */   
/* 20 */   private static int lastId = -9999999;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static int loadTexture(String resourceName, int mode) {
/*    */     try {
/* 28 */       if (idMap.containsKey(resourceName))
/*    */       {
/* 30 */         return ((Integer)idMap.get(resourceName)).intValue();
/*    */       }
/*    */       
/* 33 */       IntBuffer ib = BufferUtils.createIntBuffer(1);
/*    */       
/* 35 */       GL11.glGenTextures(ib);
/* 36 */       int id = ib.get(0);
/*    */       
/* 38 */       bind(id);
/*    */ 
/*    */ 
/*    */       
/* 42 */       GL11.glTexParameteri(3553, 10241, mode);
/* 43 */       GL11.glTexParameteri(3553, 10240, mode);
/*    */ 
/*    */ 
/*    */       
/* 47 */       BufferedImage img = ImageIO.read(Textures.class.getResourceAsStream(resourceName));
/* 48 */       int w = img.getWidth();
/* 49 */       int h = img.getHeight();
/*    */       
/* 51 */       ByteBuffer pixels = BufferUtils.createByteBuffer(w * h * 4);
/* 52 */       int[] rawPixels = new int[w * h];
/* 53 */       img.getRGB(0, 0, w, h, rawPixels, 0, w);
/* 54 */       for (int i = 0; i < rawPixels.length; i++) {
/*    */         
/* 56 */         int a = rawPixels[i] >> 24 & 0xFF;
/* 57 */         int r = rawPixels[i] >> 16 & 0xFF;
/* 58 */         int g = rawPixels[i] >> 8 & 0xFF;
/* 59 */         int b = rawPixels[i] & 0xFF;
/*    */         
/* 61 */         rawPixels[i] = a << 24 | b << 16 | g << 8 | r;
/*    */       } 
/* 63 */       pixels.asIntBuffer().put(rawPixels);
/* 64 */       GLU.gluBuild2DMipmaps(3553, 6408, w, h, 6408, 5121, pixels);
/*    */ 
/*    */       
/* 67 */       return id;
/*    */     }
/* 69 */     catch (IOException e) {
/*    */       
/* 71 */       throw new RuntimeException("!!");
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public static void bind(int id) {
/* 77 */     if (id != lastId) {
/*    */       
/* 79 */       GL11.glBindTexture(3553, id);
/* 80 */       lastId = id;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\HaoyuanX36054\JAVA\.minecraft\versions\rd-132211\rd-132211.jar!\com\mojang\rubydung\Textures.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */