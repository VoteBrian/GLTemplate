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

    private float mXAngle = 0;
    private float mYAngle = 0;

    // Buffers
    private ByteBuffer mVbb;
    private ByteBuffer mTriangles;
    private ByteBuffer mNbb;

    private FloatBuffer mVertexBuffer;
    private FloatBuffer mNormalBuffer;

    private float[] mVertices = {
        -0.5f,  0.5f,  0.5f,
        -0.5f, -0.5f,  0.5f,
         0.5f, -0.5f,  0.5f,

        -0.5f,  0.5f,  0.5f,
         0.5f, -0.5f,  0.5f,
         0.5f,  0.5f,  0.5f,

        -0.5f,  0.5f, -0.5f,
        -0.5f,  0.5f,  0.5f,
         0.5f,  0.5f,  0.5f,

        -0.5f,  0.5f, -0.5f,
         0.5f,  0.5f,  0.5f,
         0.5f,  0.5f, -0.5f,

         0.5f,  0.5f, -0.5f,
         0.5f,  0.5f,  0.5f,
         0.5f, -0.5f,  0.5f,

         0.5f,  0.5f, -0.5f,
         0.5f, -0.5f,  0.5f,
         0.5f, -0.5f, -0.5f,

         0.5f, -0.5f, -0.5f,
         0.5f, -0.5f,  0.5f,
        -0.5f, -0.5f,  0.5f,

         0.5f, -0.5f, -0.5f,
        -0.5f, -0.5f,  0.5f,
        -0.5f, -0.5f, -0.5f,

        -0.5f, -0.5f, -0.5f,
        -0.5f, -0.5f,  0.5f,
        -0.5f,  0.5f,  0.5f,

        -0.5f, -0.5f, -0.5f,
        -0.5f,  0.5f,  0.5f,
        -0.5f,  0.5f, -0.5f,

         0.5f,  0.5f, -0.5f,
         0.5f, -0.5f, -0.5f,
        -0.5f, -0.5f, -0.5f,

         0.5f,  0.5f, -0.5f,
        -0.5f, -0.5f, -0.5f,
        -0.5f,  0.5f, -0.5f
    };
    private int mNumVertices = mVertices.length;

    private float[] mNormals = {
         0,  0,  1,
         0,  0,  1,
         0,  0,  1,

         0,  0,  1,
         0,  0,  1,
         0,  0,  1,


         0,  1,  0,
         0,  1,  0,
         0,  1,  0,

         0,  1,  0,
         0,  1,  0,
         0,  1,  0,


         1,  0,  0,
         1,  0,  0,
         1,  0,  0,

         1,  0,  0,
         1,  0,  0,
         1,  0,  0,


         0, -1,  0,
         0, -1,  0,
         0, -1,  0,

         0, -1,  0,
         0, -1,  0,
         0, -1,  0,


        -1,  0,  0,
        -1,  0,  0,
        -1,  0,  0,

        -1,  0,  0,
        -1,  0,  0,
        -1,  0,  0,

         0,  0, -1,
         0,  0, -1,
         0,  0, -1,

         0,  0, -1,
         0,  0, -1,
         0,  0, -1
    };
    private int mNumNormals = mNormals.length;

    private byte[] mIndices = {
        0, 1, 2,
        0, 2, 3,
        4, 0, 3,
        4, 3, 7,
        7, 3, 2,
        7, 2, 6,
        6, 2, 1,
        6, 1, 5,
        5, 1, 0,
        5, 0, 4
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

        mNbb = ByteBuffer.allocateDirect(mNumNormals * VERTEX_PER_TRIANGLE * BYTES_PER_VERTEX);
        mNbb.order(ByteOrder.nativeOrder());
        mNormalBuffer = mNbb.asFloatBuffer();
        mNormalBuffer.put(mNormals);
        mNormalBuffer.position(0);
    }

    public void draw(GL10 gl) {
        gl.glPushMatrix();

        gl.glNormalPointer(GL10.GL_FLOAT, 0, mNormalBuffer);
        gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);

        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mVertexBuffer);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);

        gl.glTranslatef(mCentX, mCentY, mCentZ);

        gl.glRotatef(mXAngle, 0f, 1f, 0f);
        gl.glRotatef(mYAngle, 1f, 0f, 0f);

        gl.glDrawArrays(GL10.GL_TRIANGLES, 0, mNumVertices);

        gl.glPopMatrix();
    }

    public void setXAngle(float angle) {
        mXAngle += angle;
    }

    public void setYAngle(float angle) {
        mYAngle += angle;
    }
}