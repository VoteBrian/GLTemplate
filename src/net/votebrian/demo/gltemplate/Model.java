package net.votebrian.demo.gltemplate;

import javax.microedition.khronos.opengles.GL10;

import java.io.InputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

public class Model {
    private static int VERTEX_PER_TRIANGLE = 3;
    private static int BYTES_PER_VERTEX = 4;

    private Context mCtx;

    private int mCentX;
    private int mCentY;
    private int mCentZ;

    private float mXAngle = 0;
    private float mYAngle = 0;

    // Buffers
    private ByteBuffer mVbb;
    private ByteBuffer mNbb;
    private ByteBuffer mTbb;

    private FloatBuffer mVertexBuffer;
    private FloatBuffer mNormalBuffer;
    private FloatBuffer mTextureBuffer;

    private int[] mTextures = new int[3];
    private Bitmap mBitmap;

    private float[] mVertices = {
        -0.5f,  0.5f,  0.5f,      // Front
        -0.5f, -0.5f,  0.5f,
         0.5f, -0.5f,  0.5f,

        -0.5f,  0.5f,  0.5f,
         0.5f, -0.5f,  0.5f,
         0.5f,  0.5f,  0.5f,

        -0.5f,  0.5f, -0.5f,      // Top
        -0.5f,  0.5f,  0.5f,
         0.5f,  0.5f,  0.5f,

        -0.5f,  0.5f, -0.5f,
         0.5f,  0.5f,  0.5f,
         0.5f,  0.5f, -0.5f,

         0.5f,  0.5f, -0.5f,      // Right
         0.5f,  0.5f,  0.5f,
         0.5f, -0.5f,  0.5f,

         0.5f,  0.5f, -0.5f,
         0.5f, -0.5f,  0.5f,
         0.5f, -0.5f, -0.5f,

        -0.5f, -0.5f,  0.5f,      // Bottom
        -0.5f, -0.5f, -0.5f,
         0.5f, -0.5f, -0.5f,

        -0.5f, -0.5f,  0.5f,
         0.5f, -0.5f, -0.5f,
         0.5f, -0.5f,  0.5f,

        -0.5f, -0.5f, -0.5f,      // Left
        -0.5f, -0.5f,  0.5f,
        -0.5f,  0.5f,  0.5f,

        -0.5f, -0.5f, -0.5f,
        -0.5f,  0.5f,  0.5f,
        -0.5f,  0.5f, -0.5f,

         0.5f,  0.5f, -0.5f,      // Back
         0.5f, -0.5f, -0.5f,
        -0.5f, -0.5f, -0.5f,

         0.5f,  0.5f, -0.5f,
        -0.5f, -0.5f, -0.5f,
        -0.5f,  0.5f, -0.5f
    };
    private int mNumVertices = mVertices.length;

    private float[] mNormals = {
         0,  0,  1,      // Front
         0,  0,  1,
         0,  0,  1,

         0,  0,  1,
         0,  0,  1,
         0,  0,  1,


         0,  1,  0,      // Top
         0,  1,  0,
         0,  1,  0,

         0,  1,  0,
         0,  1,  0,
         0,  1,  0,


         1,  0,  0,      // Right
         1,  0,  0,
         1,  0,  0,

         1,  0,  0,
         1,  0,  0,
         1,  0,  0,


         0, -1,  0,      // Bottom
         0, -1,  0,
         0, -1,  0,

         0, -1,  0,
         0, -1,  0,
         0, -1,  0,


        -1,  0,  0,      // Left
        -1,  0,  0,
        -1,  0,  0,

        -1,  0,  0,
        -1,  0,  0,
        -1,  0,  0,

         0,  0, -1,      // Back
         0,  0, -1,
         0,  0, -1,

         0,  0, -1,
         0,  0, -1,
         0,  0, -1
    };
    private int mNumNormals = mNormals.length;

    private float[] mTexCoordinates = {
        0.25f, 0.00f,      // Front
        0.25f, 0.25f,
        0.50f, 0.25f,

        0.25f, 0.00f,
        0.50f, 0.25f,
        0.50f, 0.00f,

        0.25f, 0.75f,      // Top
        0.25f, 1.00f,
        0.50f, 1.00f,

        0.25f, 0.75f,
        0.50f, 1.00f,
        0.50f, 0.75f,

        0.75f, 0.25f,      // Right
        0.50f, 0.25f,
        0.50f, 0.50f,

        0.75f, 0.25f,
        0.50f, 0.50f,
        0.75f, 0.50f,

        0.25f, 0.25f,      // Bottom
        0.25f, 0.50f,
        0.50f, 0.50f,

        0.25f, 0.25f,
        0.50f, 0.50f,
        0.50f, 0.25f,

        0.00f, 0.50f,      // Left
        0.25f, 0.50f,
        0.25f, 0.25f,

        0.00f, 0.50f,
        0.25f, 0.25f,
        0.00f, 0.25f,

        0.50f, 0.75f,      // Back
        0.50f, 0.50f,
        0.25f, 0.50f,

        0.50f, 0.75f,
        0.25f, 0.50f,
        0.25f, 0.75f
    };
    private int mNumTexCoordinates = mTexCoordinates.length;

    public Model(int centX, int centY, int centZ, Context context) {
        mCentX = centX;
        mCentY = centY;
        mCentZ = centZ;

        mCtx = context;
    }

    private void buildBuffers() {
        mVbb = ByteBuffer.allocateDirect(mNumVertices * VERTEX_PER_TRIANGLE * BYTES_PER_VERTEX);
        mVbb.order(ByteOrder.nativeOrder());
        mVertexBuffer = mVbb.asFloatBuffer();
        mVertexBuffer.put(mVertices);
        mVertexBuffer.position(0);

        mNbb = ByteBuffer.allocateDirect(mNumNormals * VERTEX_PER_TRIANGLE * BYTES_PER_VERTEX);
        mNbb.order(ByteOrder.nativeOrder());
        mNormalBuffer = mNbb.asFloatBuffer();
        mNormalBuffer.put(mNormals);
        mNormalBuffer.position(0);

        mTbb = ByteBuffer.allocateDirect(mNumTexCoordinates * VERTEX_PER_TRIANGLE * BYTES_PER_VERTEX);
        mTbb.order(ByteOrder.nativeOrder());
        mTextureBuffer = mTbb.asFloatBuffer();
        mTextureBuffer.put(mTexCoordinates);
        mTextureBuffer.position(0);
    }

    public void loadTexture(GL10 gl) {
        InputStream is = mCtx.getResources().openRawResource(R.drawable.squares);
        mBitmap = null;

        try {
            mBitmap = BitmapFactory.decodeStream(is);
        } finally {
            try {
                is.close();
                is = null;
            } catch (IOException e) {
                is = null;
            }
        }

        gl.glGenTextures(3, mTextures, 0);

        gl.glBindTexture(GL10.GL_TEXTURE_2D, mTextures[0]);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, mBitmap, 0);

        buildBuffers();
    }

    public void draw(GL10 gl) {
        gl.glPushMatrix();

        gl.glEnable(GL10.GL_TEXTURE_2D);
        gl.glBindTexture(GL10.GL_TEXTURE_2D, mTextures[0]);

        gl.glNormalPointer(GL10.GL_FLOAT, 0, mNormalBuffer);
        gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);

        gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, mTextureBuffer);
        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);

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