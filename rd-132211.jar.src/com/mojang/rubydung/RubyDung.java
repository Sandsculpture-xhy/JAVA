/*     */ package com.mojang.rubydung;
/*     */ 
/*     */ import com.mojang.rubydung.level.Chunk;
/*     */ import com.mojang.rubydung.level.Level;
/*     */ import com.mojang.rubydung.level.LevelRenderer;
/*     */ import java.io.IOException;
/*     */ import java.nio.FloatBuffer;
/*     */ import java.nio.IntBuffer;
/*     */ import javax.swing.JOptionPane;
/*     */ import org.lwjgl.BufferUtils;
/*     */ import org.lwjgl.LWJGLException;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ import org.lwjgl.input.Mouse;
/*     */ import org.lwjgl.opengl.Display;
/*     */ import org.lwjgl.opengl.DisplayMode;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import org.lwjgl.util.glu.GLU;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RubyDung
/*     */   implements Runnable
/*     */ {
/*     */   private static final boolean FULLSCREEN_MODE = false;
/*     */   private int width;
/*     */   private int height;
/*  28 */   private FloatBuffer fogColor = BufferUtils.createFloatBuffer(4);
/*  29 */   private Timer timer = new Timer(60.0F);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Level level;
/*     */ 
/*     */ 
/*     */   
/*     */   private LevelRenderer levelRenderer;
/*     */ 
/*     */ 
/*     */   
/*     */   private Player player;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void init() throws LWJGLException, IOException {
/*  48 */     int col = 920330;
/*  49 */     float fr = 0.5F;
/*  50 */     float fg = 0.8F;
/*  51 */     float fb = 1.0F;
/*  52 */     this.fogColor.put(new float[] { (col >> 16 & 0xFF) / 255.0F, (col >> 8 & 0xFF) / 255.0F, (col & 0xFF) / 255.0F, 1.0F });
/*  53 */     this.fogColor.flip();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  62 */     Display.setDisplayMode(new DisplayMode(1024, 768));
/*     */ 
/*     */     
/*  65 */     Display.create();
/*  66 */     Keyboard.create();
/*  67 */     Mouse.create();
/*     */     
/*  69 */     this.width = Display.getDisplayMode().getWidth();
/*  70 */     this.height = Display.getDisplayMode().getHeight();
/*     */     
/*  72 */     GL11.glEnable(3553);
/*  73 */     GL11.glShadeModel(7425);
/*  74 */     GL11.glClearColor(fr, fg, fb, 0.0F);
/*  75 */     GL11.glClearDepth(1.0D);
/*  76 */     GL11.glEnable(2929);
/*  77 */     GL11.glDepthFunc(515);
/*     */     
/*  79 */     GL11.glMatrixMode(5889);
/*  80 */     GL11.glLoadIdentity();
/*     */     
/*  82 */     GL11.glMatrixMode(5888);
/*     */     
/*  84 */     this.level = new Level(256, 256, 64);
/*  85 */     this.levelRenderer = new LevelRenderer(this.level);
/*  86 */     this.player = new Player(this.level);
/*     */     
/*  88 */     Mouse.setGrabbed(true);
/*     */   }
/*     */ 
/*     */   
/*     */   public void destroy() {
/*  93 */     this.level.save();
/*     */     
/*  95 */     Mouse.destroy();
/*  96 */     Keyboard.destroy();
/*  97 */     Display.destroy();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void run() {
/*     */     try {
/* 104 */       init();
/*     */     }
/* 106 */     catch (Exception e) {
/*     */       
/* 108 */       JOptionPane.showMessageDialog(null, e.toString(), "Failed to start RubyDung", 0);
/* 109 */       System.exit(0);
/*     */     } 
/*     */     
/* 112 */     long lastTime = System.currentTimeMillis();
/* 113 */     int frames = 0;
/*     */     
/*     */     try {
/* 116 */       while (!Keyboard.isKeyDown(1) && !Display.isCloseRequested())
/*     */       {
/* 118 */         this.timer.advanceTime();
/* 119 */         for (int i = 0; i < this.timer.ticks; i++)
/*     */         {
/* 121 */           tick();
/*     */         }
/* 123 */         render(this.timer.a);
/* 124 */         frames++;
/*     */         
/* 126 */         while (System.currentTimeMillis() >= lastTime + 1000L)
/*     */         {
/* 128 */           System.out.println(String.valueOf(frames) + " fps, " + Chunk.updates);
/* 129 */           Chunk.updates = 0;
/*     */           
/* 131 */           lastTime += 1000L;
/* 132 */           frames = 0;
/*     */         }
/*     */       
/*     */       }
/*     */     
/* 137 */     } catch (Exception e) {
/*     */       
/* 139 */       e.printStackTrace();
/*     */     }
/*     */     finally {
/*     */       
/* 143 */       destroy();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void tick() {
/* 150 */     this.player.tick();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void moveCameraToPlayer(float a) {
/* 158 */     GL11.glTranslatef(0.0F, 0.0F, -0.3F);
/* 159 */     GL11.glRotatef(this.player.xRot, 1.0F, 0.0F, 0.0F);
/* 160 */     GL11.glRotatef(this.player.yRot, 0.0F, 1.0F, 0.0F);
/*     */     
/* 162 */     float x = this.player.xo + (this.player.x - this.player.xo) * a;
/* 163 */     float y = this.player.yo + (this.player.y - this.player.yo) * a;
/* 164 */     float z = this.player.zo + (this.player.z - this.player.zo) * a;
/* 165 */     GL11.glTranslatef(-x, -y, -z);
/*     */   }
/*     */ 
/*     */   
/*     */   private void setupCamera(float a) {
/* 170 */     GL11.glMatrixMode(5889);
/* 171 */     GL11.glLoadIdentity();
/* 172 */     GLU.gluPerspective(70.0F, this.width / this.height, 0.05F, 1000.0F);
/* 173 */     GL11.glMatrixMode(5888);
/* 174 */     GL11.glLoadIdentity();
/* 175 */     moveCameraToPlayer(a);
/*     */   }
/*     */   
/* 178 */   private IntBuffer viewportBuffer = BufferUtils.createIntBuffer(16);
/*     */ 
/*     */   
/*     */   private void setupPickCamera(float a, int x, int y) {
/* 182 */     GL11.glMatrixMode(5889);
/* 183 */     GL11.glLoadIdentity();
/* 184 */     this.viewportBuffer.clear();
/* 185 */     GL11.glGetInteger(2978, this.viewportBuffer);
/* 186 */     this.viewportBuffer.flip();
/* 187 */     this.viewportBuffer.limit(16);
/* 188 */     GLU.gluPickMatrix(x, y, 5.0F, 5.0F, this.viewportBuffer);
/* 189 */     GLU.gluPerspective(70.0F, this.width / this.height, 0.05F, 1000.0F);
/* 190 */     GL11.glMatrixMode(5888);
/* 191 */     GL11.glLoadIdentity();
/* 192 */     moveCameraToPlayer(a);
/*     */   }
/*     */   
/* 195 */   private IntBuffer selectBuffer = BufferUtils.createIntBuffer(2000);
/* 196 */   private HitResult hitResult = null;
/*     */ 
/*     */   
/*     */   private void pick(float a) {
/* 200 */     this.selectBuffer.clear();
/* 201 */     GL11.glSelectBuffer(this.selectBuffer);
/* 202 */     GL11.glRenderMode(7170);
/* 203 */     setupPickCamera(a, this.width / 2, this.height / 2);
/* 204 */     this.levelRenderer.pick(this.player);
/* 205 */     int hits = GL11.glRenderMode(7168);
/* 206 */     this.selectBuffer.flip();
/* 207 */     this.selectBuffer.limit(this.selectBuffer.capacity());
/*     */     
/* 209 */     long closest = 0L;
/* 210 */     int[] names = new int[10];
/* 211 */     int hitNameCount = 0;
/* 212 */     for (int i = 0; i < hits; i++) {
/*     */       
/* 214 */       int nameCount = this.selectBuffer.get();
/* 215 */       long minZ = this.selectBuffer.get();
/* 216 */       this.selectBuffer.get();
/*     */       
/* 218 */       long dist = minZ;
/*     */       
/* 220 */       if (dist < closest || i == 0) {
/*     */         
/* 222 */         closest = dist;
/* 223 */         hitNameCount = nameCount;
/* 224 */         for (int j = 0; j < nameCount; j++) {
/* 225 */           names[j] = this.selectBuffer.get();
/*     */         }
/*     */       } else {
/*     */         
/* 229 */         for (int j = 0; j < nameCount; j++) {
/* 230 */           this.selectBuffer.get();
/*     */         }
/*     */       } 
/*     */     } 
/* 234 */     if (hitNameCount > 0) {
/*     */       
/* 236 */       this.hitResult = new HitResult(names[0], names[1], names[2], names[3], names[4]);
/*     */     }
/*     */     else {
/*     */       
/* 240 */       this.hitResult = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(float a) {
/* 246 */     float xo = Mouse.getDX();
/* 247 */     float yo = Mouse.getDY();
/* 248 */     this.player.turn(xo, yo);
/* 249 */     pick(a);
/*     */     
/* 251 */     while (Mouse.next()) {
/*     */       
/* 253 */       if (Mouse.getEventButton() == 1 && Mouse.getEventButtonState())
/*     */       {
/* 255 */         if (this.hitResult != null)
/*     */         {
/* 257 */           this.level.setTile(this.hitResult.x, this.hitResult.y, this.hitResult.z, 0);
/*     */         }
/*     */       }
/* 260 */       if (Mouse.getEventButton() == 0 && Mouse.getEventButtonState())
/*     */       {
/* 262 */         if (this.hitResult != null) {
/*     */           
/* 264 */           int x = this.hitResult.x;
/* 265 */           int y = this.hitResult.y;
/* 266 */           int z = this.hitResult.z;
/*     */           
/* 268 */           if (this.hitResult.f == 0) y--; 
/* 269 */           if (this.hitResult.f == 1) y++; 
/* 270 */           if (this.hitResult.f == 2) z--; 
/* 271 */           if (this.hitResult.f == 3) z++; 
/* 272 */           if (this.hitResult.f == 4) x--; 
/* 273 */           if (this.hitResult.f == 5) x++;
/*     */           
/* 275 */           this.level.setTile(x, y, z, 1);
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 280 */     while (Keyboard.next()) {
/*     */       
/* 282 */       if (Keyboard.getEventKey() == 28 && Keyboard.getEventKeyState())
/*     */       {
/* 284 */         this.level.save();
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 289 */     GL11.glClear(16640);
/* 290 */     setupCamera(a);
/*     */     
/* 292 */     GL11.glEnable(2884);
/* 293 */     GL11.glEnable(2912);
/* 294 */     GL11.glFogi(2917, 2048);
/* 295 */     GL11.glFogf(2914, 0.2F);
/* 296 */     GL11.glFog(2918, this.fogColor);
/*     */     
/* 298 */     GL11.glDisable(2912);
/* 299 */     this.levelRenderer.render(this.player, 0);
/* 300 */     GL11.glEnable(2912);
/* 301 */     this.levelRenderer.render(this.player, 1);
/* 302 */     GL11.glDisable(3553);
/*     */     
/* 304 */     if (this.hitResult != null)
/*     */     {
/* 306 */       this.levelRenderer.renderHit(this.hitResult);
/*     */     }
/* 308 */     GL11.glDisable(2912);
/*     */     
/* 310 */     Display.update();
/*     */   }
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
/*     */   public static void checkError() {
/* 324 */     int e = GL11.glGetError();
/* 325 */     if (e != 0)
/*     */     {
/* 327 */       throw new IllegalStateException(GLU.gluErrorString(e));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static void main(String[] args) throws LWJGLException {
/* 333 */     (new Thread(new RubyDung())).start();
/*     */   }
/*     */ }


/* Location:              C:\Users\HaoyuanX36054\JAVA\.minecraft\versions\rd-132211\rd-132211.jar!\com\mojang\rubydung\RubyDung.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */