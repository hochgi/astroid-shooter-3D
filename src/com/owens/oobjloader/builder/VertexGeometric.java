package com.owens.oobjloader.builder;

// Written by Sean R. Owens, sean at guild dot net, released to the
// public domain. Share and enjoy. Since some people argue that it is
// impossible to release software to the public domain, you are also free
// to use this code under any version of the GPL, LPGL, Apache, or BSD
// licenses, or contact me for use of another license.

import java.util.*;
import java.text.*;
import java.io.*;
import java.io.IOException;

public class VertexGeometric {

    public Float x = 0f;
    public Float y = 0f;
    public Float z = 0f;

    public VertexGeometric(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public String toString() {
        if (null == this) {
            return "null";
        } else {
            return x + "," + y + "," + z;
        }
    }
}