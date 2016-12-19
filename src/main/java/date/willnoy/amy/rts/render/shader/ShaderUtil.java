package date.willnoy.amy.rts.render.shader;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.*;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

@SuppressWarnings("unused")
public final class ShaderUtil {
    private static boolean debug;
    
    private ShaderUtil() {
    }
    
    public static int loadShader(final String file, final ShaderType type) {
        final int shaderProgram = ARBShaderObjects.glCreateShaderObjectARB(
                type == ShaderType.FRAGMENT
                        ? ARBFragmentShader.GL_FRAGMENT_SHADER_ARB
                        : ARBVertexShader.GL_VERTEX_SHADER_ARB
        );

        final ByteBuffer shaderTextBuffer = stringToByteBuffer(fileToString(file));
        ARBShaderObjects.glShaderSourceARB(shaderProgram, shaderTextBuffer);
        ARBShaderObjects.glCompileShaderARB(shaderProgram);
        printLogInfo(shaderProgram);
        return shaderProgram;
    }

    public static int buildShader(final String vertex, final String fragment) {
        final int vert = loadShader(vertex, ShaderType.VERTEX);
        final int frag = loadShader(fragment, ShaderType.FRAGMENT);
        final int program = ARBShaderObjects.glCreateProgramObjectARB();
        ARBShaderObjects.glAttachObjectARB(program, vert);
        ARBShaderObjects.glAttachObjectARB(program, frag);
        ARBShaderObjects.glLinkProgramARB(program);
        // ARBShaderObjects.glValidateProgramARB(program);
        return program;
    }

    private static void printLogInfo(final int obj) {
        if(!debug) {
            return;
        }
        final IntBuffer iVal = BufferUtils.createIntBuffer(1);
        ARBShaderObjects.glGetObjectParameterARB(obj, ARBShaderObjects.GL_OBJECT_INFO_LOG_LENGTH_ARB, iVal);

        final int length = iVal.get();
        System.out.println("Info log length:"+length);
        if (length > 0)	{
            final ByteBuffer infoLog = BufferUtils.createByteBuffer(length);
            iVal.flip();
            ARBShaderObjects.glGetInfoLogARB(obj,  iVal, infoLog);
            final byte[] infoBytes = new byte[length];
            infoLog.get(infoBytes);
            final String out = new String(infoBytes);
            System.out.println("Info log:\n"+out);
        }
        Util.checkGLError();
    }

    private static String fileToString(final String filename) {
        try {
            /*new FileReader(filename)*/
            final BufferedReader br = new BufferedReader(
                    new InputStreamReader(
                            ShaderUtil.class.getClassLoader().getResourceAsStream(filename)
                    )
            );
            String line;
            String res = "";
            while ((line=br.readLine()) != null) {
                res += line + '\n';
            }
            return res;
        } catch (final IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private static ByteBuffer stringToByteBuffer(final String s) {
        final ByteBuffer bb = ByteBuffer.allocateDirect(s.length());
        try {
            bb.put(s.getBytes("US-ASCII"));
        } catch (final UnsupportedEncodingException e) {
            //return null;
            throw new IllegalArgumentException(e);
        }
        return (ByteBuffer) bb.flip();
    }

    public static void setDebug(final boolean b) {
        debug = b;
    }

    public static boolean getDebug() {
        return debug;
    }

    public enum ShaderType {
        FRAGMENT, VERTEX
    }
}
