package com.cly.myapplication.shapes;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by 丛龙宇 on 17-2-14.
 */

public class Square extends Shape {

    // Our vertices.
    private float vertices[] = {
            -1.0f, 1.0f, 1.0f,      // 0, Top Left Front
            -1.0f, -1.0f, 1.0f,     // 1, Bottom Left Front
            1.0f, -1.0f, 1.0f,      // 2, Bottom Right Front
            1.0f, 1.0f, 1.0f,       // 3, Top Right Front
            -1.0f, 1.0f, -1.0f,     // 4, Top Left Behind
            -1.0f, -1.0f, -1.0f,    // 5, Bottom Left Behind
            1.0f, -1.0f, -1.0f,     // 6, Bottom Right Behind
            1.0f, 1.0f, -1.0f       // 7, Top Right Behind
    };


    // The order we like to connect them.
    private short[] indices = {
            0, 1, 2, 0, 2, 3,   // 0 Front
            0, 3, 7, 0, 7, 5,   // 1 Top
            0, 1, 6, 0, 6, 5,   // 2 Left
            3, 2, 6, 3, 6, 7,   // 3 Right
            1, 2, 6, 1, 6, 5,   // 4 Bottom
            4, 5, 6, 4, 6, 7    // 5 Behind
    };
    // Our vertex buffer.
    private FloatBuffer vertexBuffer;
    // Our index buffer.
    private ShortBuffer indexBuffer;

    public Square() {
        // a float is 4 bytes, therefore we
        // multiply the number if
        // vertices with 4.
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
        vbb.order(ByteOrder.nativeOrder());
        vertexBuffer = vbb.asFloatBuffer();
        vertexBuffer.put(vertices);
        vertexBuffer.position(0);
        // short is 2 bytes, therefore we multiply
        //the number if
        // vertices with 2.
        ByteBuffer ibb = ByteBuffer.allocateDirect(indices.length * 2);
        ibb.order(ByteOrder.nativeOrder());
        indexBuffer = ibb.asShortBuffer();
        indexBuffer.put(indices);
        indexBuffer.position(0);
    }

    /**
     * This function draws our square on screen.
     *
     * @param gl
     */
    @Override
    public void draw(GL10 gl) {
        // Counter-clockwise winding.
        gl.glFrontFace(GL10.GL_CCW);
        // Enable face culling.
        gl.glEnable(GL10.GL_CULL_FACE);
        // What faces to remove with the face culling.
        gl.glCullFace(GL10.GL_BACK);
        // Enabled the vertices buffer for writing
        //and to be used during
        // rendering.
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        // Specifies the location and data format of
        //an array of vertex
        // coordinates to use when rendering.
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0,
                vertexBuffer);
        gl.glDrawElements(GL10.GL_TRIANGLES, indices.length,
                GL10.GL_UNSIGNED_SHORT, indexBuffer);
        // Disable the vertices buffer.
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        // Disable face culling.
        gl.glDisable(GL10.GL_CULL_FACE);
    }
}
