package date.willnoy.amy.rts.render.util;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.*;

/**
 * @author amy
 * @since 12.01.15
 */
public final class DisplayUtil {
    private DisplayUtil() {
    }

    public static void buildDisplay(final int width, final int height) {
        buildDisplay(width, height, new PixelFormat());
    }

    public static void buildDisplay(final int width, final int height, final PixelFormat pixelFormat) {
        buildDisplay(width, height, pixelFormat, null, null);
    }

    public static void buildDisplay(final int width, final int height, final PixelFormat pixelFormat, final Drawable sharedDrawable) {
        buildDisplay(width, height, pixelFormat, sharedDrawable, null);
    }

    public static void buildDisplay(final int width, final int height, final PixelFormat pixelFormat, final ContextAttribs attribs) {
        buildDisplay(width, height, pixelFormat, null, attribs);
    }

    public static void buildDisplay(final int width, final int height, final PixelFormat pixelFormat, final Drawable sharedDrawable, final ContextAttribs attribs) {
        try {
            Display.setDisplayMode(new DisplayMode(width, height));
            Display.create(pixelFormat, sharedDrawable, attribs);
            GL11.glViewport(0, 0, width, height);
        } catch (final LWJGLException e) {
            throw new IllegalStateException(e);
        }
    }

    public static void basicOpenGLInit() {
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0, Display.getWidth(), 0, Display.getHeight(), 1, -1);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glClearColor(0.0F, 0.0F, 0.0F, 1.0F);
    }
}
