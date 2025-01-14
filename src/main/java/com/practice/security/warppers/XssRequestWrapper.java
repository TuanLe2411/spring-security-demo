package com.practice.security.warppers;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.owasp.encoder.Encode;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;


public class XssRequestWrapper extends HttpServletRequestWrapper {
    private byte[] sanitizedBody;

    public XssRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        sanitizeRequestBody(request);
    }

    @Override
    public String getHeader(String name) {
        String value = super.getHeader(name);
        return Encode.forHtml(value);
    }

    @Override
    public Enumeration<String> getHeaders(String name) {
        Enumeration<String> headers = super.getHeaders(name);
        return Collections.enumeration(Collections.list(headers).stream()
            .map(Encode::forHtml)
            .toList());
    }

    @Override
    public String getParameter(String name) {
        String value = super.getParameter(name);
        return Encode.forHtml(value);
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        Map<String, String[]> parameterMap = super.getParameterMap();
        parameterMap.forEach((_, values) -> {
            for (int i = 0; i < values.length; i++) {
                values[i] = Encode.forHtml(values[i]);
            }
        });
        return parameterMap;
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] values = super.getParameterValues(name);
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                values[i] = Encode.forHtml(values[i]);
            }
        }
        return values;
    }

    @Override
    public ServletInputStream getInputStream() {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(sanitizedBody);
        return new ServletInputStream() {
            @Override
            public int read() {
                return byteArrayInputStream.read();
            }

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
                // No implementation needed
            }
        };
    }

    private void sanitizeRequestBody(HttpServletRequest request) throws IOException {
        InputStream inputStream = request.getInputStream();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
            baos.write(buffer, 0, length);
        }
        String body = baos.toString(StandardCharsets.UTF_8);
        String sanitizedBodyString = Jsoup.clean(body, Safelist.basic());
        this.sanitizedBody = sanitizedBodyString.getBytes(StandardCharsets.UTF_8);
    }
}
