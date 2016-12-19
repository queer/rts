package date.willnoy.amy.rts.render.vbo;

import date.willnoy.amy.rts.render.util.BufferUtil;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Collection;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;

@SuppressWarnings("unused")
public class Vbo implements BufferUtil {
    private final int id;
    private final Collection<Float> points = new ArrayList<>();
    private final int mode;
    private int count;
    private int color;
    private int stride;
    private boolean compiled;
    
    public Vbo(final int mode) {
        id = glGenBuffers();
        this.mode = mode;
    }
    
    /*
     * Assumes that a correctly-formed point list was passed in.
     */
    public Vbo(final int mode, final Collection<Float> points) {
        id = glGenBuffers();
        this.mode = mode;
        this.points.addAll(points);
    }
    
    public Vbo vertex(final float x, final float y, final float z) {
        compiled = false;
        points.add(x);
        points.add(y);
        points.add(z);
        final float[] e = splitColor(color);
        points.add(e[0]);
        points.add(e[1]);
        points.add(e[2]);
        points.add(e[3]);
        ++count;
        return this;
    }
    
    public Vbo color(final int color) {
        compiled = false;
        this.color = color;
        return this;
    }
    
    public Vbo compile() {
        final FloatBuffer buffer = getFloatBuffer(points.size());
        stride = 28;
        // Fill buffer
        points.forEach(buffer::put);
        buffer.flip();
        points.clear();
        // Bind and buffer
        glBindBuffer(GL_ARRAY_BUFFER, id);
        glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        compiled = true;
        return this;
    }
    
    public void render() {
        if(!compiled) {
            throw new IllegalStateException("VBO " + id + " is not compiled!");
        }
        
        glBindBuffer(GL_ARRAY_BUFFER, id);
        glVertexPointer(3, GL_FLOAT, stride, 0L);
        glColorPointer(4, GL_FLOAT, stride, 12L);
        
        glEnableClientState(GL_VERTEX_ARRAY);
        glEnableClientState(GL_COLOR_ARRAY);
        
        glDrawArrays(mode, 0, count);
        
        glDisableClientState(GL_COLOR_ARRAY);
        glDisableClientState(GL_VERTEX_ARRAY);
        
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }
    
    /**
     * @param color 0xAARRGGBB
     *
     * @return {R, G, B, A}
     */
    private float[] splitColor(final int color) {
        final float a = (color >> 24 & 255) / 255.0F;
        final float r = (color >> 16 & 255) / 255.0F;
        final float g = (color >> 8 & 255) / 255.0F;
        final float b = (color & 255) / 255.0F;
        return new float[] {r, g, b, a};
    }
    
    public int getId() {
        return id;
    }
}
