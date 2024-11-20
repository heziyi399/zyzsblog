package com.zysblog.zysblog.common.util;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.CharArrayReader;


public class HttpServletRequestUriWrapper extends HttpServletRequestWrapper {
    private final String newUri;

    private final String modifiedBody;

    public HttpServletRequestUriWrapper(
            HttpServletRequest request, String newUri, String newRequestBody) {
        super(request);
        this.newUri = newUri;
        this.modifiedBody = newRequestBody;
    }

    @Override
    public String getRequestURI() {
        return newUri;
    }

    @Override
    public ServletInputStream getInputStream() {
        ByteArrayInputStream byteArrayInputStream =
                new ByteArrayInputStream(modifiedBody.getBytes());
        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return byteArrayInputStream.available() == 0;
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setReadListener(ReadListener readListener) {
                // nothing to do
            }

            @Override
            public int read() {
                return byteArrayInputStream.read();
            }
        };
    }

    @Override
    public BufferedReader getReader() {
        CharArrayReader charArrayReader = new CharArrayReader(modifiedBody.toCharArray());
        return new BufferedReader(charArrayReader);
    }
}
