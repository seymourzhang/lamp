package com.on.util.common;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class BufferedServletInputStream extends ServletInputStream {
    private ByteArrayInputStream inputStream;
    public BufferedServletInputStream(byte[] buffer) {
        this.inputStream = new ByteArrayInputStream( buffer );
    }
    @Override
    public int available() throws IOException {
        return inputStream.available();
    }
    @Override
    public int read() throws IOException {
        return inputStream.read();
    }
    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        return inputStream.read( b, off, len );
    }
    @Override
    public boolean markSupported() {
        return true;
    }
    @Override
    public boolean isFinished() {
        return true;
    }
    @Override
    public boolean isReady() {
        return true;
    }
    @Override
    public void setReadListener(ReadListener var1) {

    }
}
