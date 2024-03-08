package com.ahcloud.edas.devops.jenkins.core.job.listener;


import com.offbytwo.jenkins.helper.BuildConsoleStreamListener;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2023/12/4 21:41
 **/
public class ConsoleStreamListener implements BuildConsoleStreamListener {
    private final HttpServletResponse response;

    public ConsoleStreamListener(HttpServletResponse response) {
        this.response = response;
    }

    @Override
    public void onData(String newLogChunk) {
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        writer.write(newLogChunk);
    }

    @Override
    public void finished() {
        PrintWriter writer;
        try {
            writer = response.getWriter();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        writer.close();
    }
}
