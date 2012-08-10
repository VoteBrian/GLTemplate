package net.votebrian.demo.gltemplate;

import javax.microedition.khronos.opengles.GL10;

import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class Model {
    private static int VERTEX_PER_TRIANGLE = 3;
    private static int BYTES_PER_VERTEX = 4;

    private int mCentX;
    private int mCentY;
    private int mCentZ;

    // Buffers
    private ByteBuffer mVbb;
    private ByteBuffer mTriangles;
    private FloatBuffer mVertexBuffer;

    private float[] mVertices = {
        -0.5f,  0.5f, -5.0f,
        -0.5f, -0.5f, -5.0f,
         0.5f, -0.5f, -5.0f,
         0.5f,  0.5f, -5.0f
    };
    private int mNumVertices = mVertices.length;

    private byte[] mIndices = {
        0, 1, 2,
        2, 3, 0
    };
    private int mNumIndices = mIndices.length;

    public Model(int centX, int centY, int centZ) {
        mCentX = centX;
        mCentY = centY;
        mCentZ = centZ;

        buildBuffers();
    }

    private void buildBuffers() {
        mVbb = ByteBuffer.allocateDirect(mNumVertices * VERTEX_PER_TRIANGLE * BYTES_PER_VERTEX);
        mVbb.order(ByteOrder.nativeOrder());
        mVertexBuffer = mVbb.asFloatBuffer();
        mVertexBuffer.put(mVertices);
        mVertexBuffer.position(0);

        mTriangles = ByteBuffer.allocateDirect(mNumIndices);
        mTriangles.put(mIndices);
        mTriangles.position(0);
    }

    public void draw(GL10 gl) {
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mVertexBuffer);
        gl.glDrawElements(GL10.GL_TRIANGLES, mNumIndices, GL10.GL_UNSIGNED_BYTE, mTriangles);
    }
}