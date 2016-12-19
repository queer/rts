package date.willnoy.amy.rts.render.obj;

import date.willnoy.amy.rts.render.tessellation.Tessellator;
import date.willnoy.amy.rts.render.tessellation.impl.VAOTessellator;
import org.lwjgl.opengl.GL11;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class OBJModel {
    /**
     * Vertex coordinates
     */
    private final List<float[]> vertices = new ArrayList<>();

    /**
     * Vertex normal coordinates
     */
    private final List<float[]> vertexNormals = new ArrayList<>();

    /**
     * Vertex texture coordinates
     */
    private final List<float[]> vertexTextureCoordinates = new ArrayList<>();

    /**
     * Faces
     */
    private final ArrayList<int[]> faces = new ArrayList<>();

    /**
     * Face textures
     */
    private final ArrayList<int[]> faceTextures = new ArrayList<>();

    /**
     * Face normals
     */
    private final ArrayList<int[]> faceNormals = new ArrayList<>();

    /**
     * Polygon count
     */
    private final int polygonCount;

    // Statistics for drawing
    public float topYPoint;
    public float bottomYPoint;
    public float leftXPoint;
    public float rightXPoint;
    public float farZPoint;
    public float nearZPoint;

    private final int displayList;

    private final Tessellator tess = new VAOTessellator(2*2*2*2*2*2*2*2*2*2*2*2*2*2*2*2);

    public OBJModel(final BufferedReader ref, final boolean center) {
        load(ref);
        if (center) {
            center();
        }
        displayList = toDisplayList();
        polygonCount = faces.size();
        cleanup();
        System.out.println(polygonCount);
    }

    private void cleanup() {
        vertices.clear();
        vertexNormals.clear();
        vertexTextureCoordinates.clear();
        faces.clear();
        faceTextures.clear();
        faceNormals.clear();
    }

    private void load(final BufferedReader br) {
        int lineCounter = 0;
        try {
            String line;
            boolean firstPass = true;
            while ((line = br.readLine()) != null) {
                lineCounter++;
                line = line.trim();
                if (!line.isEmpty()) {
                    if (line.charAt(0) == 'v' && line.charAt(1) == ' ') {
                        final float[] coords = new float[4];
                        final String[] coordinateText = line.split("\\s+");
                        for (int i = 1; i < coordinateText.length; i++) {
                            coords[i - 1] = Float.valueOf(coordinateText[i]);
                        }
                        if (firstPass) {
                            rightXPoint = coords[0];
                            leftXPoint = coords[0];
                            topYPoint = coords[1];
                            bottomYPoint = coords[1];
                            nearZPoint = coords[2];
                            farZPoint = coords[2];
                            firstPass = false;
                        }
                        if (coords[0] > rightXPoint) {
                            rightXPoint = coords[0];
                        }
                        if (coords[0] < leftXPoint) {
                            leftXPoint = coords[0];
                        }
                        if (coords[1] > topYPoint) {
                            topYPoint = coords[1];
                        }
                        if (coords[1] < bottomYPoint) {
                            bottomYPoint = coords[1];
                        }
                        if (coords[2] > nearZPoint) {
                            nearZPoint = coords[2];
                        }
                        if (coords[2] < farZPoint) {
                            farZPoint = coords[2];
                        }
                        vertices.add(coords);
                    }
                    if (line.charAt(0) == 'v' && line.charAt(1) == 't') {
                        final float[] coords = new float[4];
                        final String[] coordinateText = line.split("\\s+");
                        for (int i = 1; i < coordinateText.length; i++) {
                            coords[i - 1] = Float.valueOf(coordinateText[i]);
                        }
                        vertexTextureCoordinates.add(coords);
                    }
                    if (line.charAt(0) == 'v' && line.charAt(1) == 'n') {
                        final float[] coords = new float[4];
                        final String[] coordinateText = line.split("\\s+");
                        for (int i = 1; i < coordinateText.length; i++) {
                            coords[i - 1] = Float.valueOf(coordinateText[i]);
                        }
                        vertexNormals.add(coords);
                    }
                    if (line.charAt(0) == 'f' && line.charAt(1) == ' ') {
                        final String[] coordinateText = line.split("\\s+");
                        final int[] v = new int[coordinateText.length - 1];
                        final int[] vt = new int[coordinateText.length - 1];
                        final int[] vn = new int[coordinateText.length - 1];

                        for (int i = 1; i < coordinateText.length; i++) {
                            final String fixedString = coordinateText[i].replaceAll("//", "/0/");
                            final String[] tempString = fixedString.split("/");
                            v[i - 1] = Integer.valueOf(tempString[0]);
                            if (tempString.length > 1) {
                                vt[i - 1] = Integer.valueOf(tempString[1]);
                            } else {
                                vt[i - 1] = 0;
                            }
                            if (tempString.length > 2) {
                                vn[i - 1] = Integer.valueOf(tempString[2]);
                            } else {
                                vn[i - 1] = 0;
                            }
                        }
                        faces.add(v);
                        faceTextures.add(vt);
                        faceNormals.add(vn);
                    }
                }
            }
        } catch (final IOException e) {
            System.out.println("Failed to read file: " + br);
        } catch (final NumberFormatException e) {
            System.out.println("Malformed OBJ (on line " + lineCounter + "): " + br + "\r \r" + e.getMessage());
        }
    }

    private void center() {
        final float xshift = (rightXPoint - leftXPoint) / 2f;
        final float yshift = (topYPoint - bottomYPoint) / 2f;
        final float zshift = (nearZPoint - farZPoint) / 2f;

        for (int i = 0; i < vertices.size(); i++) {
            final float[] coords = new float[4];

            coords[0] = vertices.get(i)[0] - leftXPoint - xshift;
            coords[1] = vertices.get(i)[1] - bottomYPoint - yshift;
            coords[2] = vertices.get(i)[2] - farZPoint - zshift;

            vertices.set(i, coords); // = coords;
        }
    }

    public float getXWidth() {
        return rightXPoint - leftXPoint;
    }

    public float getYHeight() {
        return topYPoint - bottomYPoint;
    }

    public float getZDepth() {
        return nearZPoint - farZPoint;
    }

    public int getPolygonCount() {
        return polygonCount;
    }

    private int toDisplayList() {
        final int objectlist = GL11.glGenLists(1);

        GL11.glNewList(objectlist, GL11.GL_COMPILE);
        for (int i = 0; i < faces.size(); i++) {
            final int[] tempFaces = faces.get(i);
            final int[] tempFaceNormals = faceNormals.get(i);
            final int[] tempFaceTextures = faceTextures.get(i);

            final int polytype;
            if (tempFaces.length == 3) {
                polytype = GL11.GL_TRIANGLES;
            } else if (tempFaces.length == 4) {
                polytype = GL11.GL_QUADS;
            } else {
                polytype = GL11.GL_POLYGON;
            }
            GL11.glBegin(polytype);

            for (int w = 0; w < tempFaces.length; w++) {
                if (tempFaceNormals[w] != 0) {
                    final float tempNormalX = vertexNormals.get(tempFaceNormals[w] - 1)[0];
                    final float tempNormalY = vertexNormals.get(tempFaceNormals[w] - 1)[1];
                    final float tempNormalZ = vertexNormals.get(tempFaceNormals[w] - 1)[2];
                    GL11.glNormal3f(tempNormalX, tempNormalY, tempNormalZ);
                }
                if (tempFaceTextures[w] != 0) {
                    final float tempTexX = vertexTextureCoordinates.get(tempFaceTextures[w] - 1)[0];
                    final float tempTexY = vertexTextureCoordinates.get(tempFaceTextures[w] - 1)[1];
                    final float tempTexZ = vertexTextureCoordinates.get(tempFaceTextures[w] - 1)[2];
                    GL11.glTexCoord3f(tempTexX, 1f - tempTexY, tempTexZ);
                }

                final float tempX = vertices.get(tempFaces[w] - 1)[0];
                final float tempY = vertices.get(tempFaces[w] - 1)[1];
                final float tempZ = vertices.get(tempFaces[w] - 1)[2];
                GL11.glVertex3f(tempX, tempY, tempZ);
            }

            GL11.glEnd();
        }
        GL11.glEndList();

        return objectlist;
    }

    public int getDisplayList() {
        return displayList;
    }
}
