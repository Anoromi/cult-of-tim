package com.example.cult_of_tim.cultoftim.util;

import ch.qos.logback.core.AppenderBase;
import ch.qos.logback.core.encoder.Encoder;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class TimConsoleAppender<E> extends AppenderBase<E> {

    private BufferedOutputStream writer;


    protected Encoder<E> encoder;


    @Override
    public void start() {
        super.start();
        try {
            writer = new BufferedOutputStream(new FileOutputStream("./logs.txt"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void append(E event) {
        try {
            writer.write(this.encoder.encode(event));
        } catch (IOException ignored) {
        }
    }

    @Override
    public void stop() {
        try {
            writer.flush();
        } catch (IOException ignored) {
        }
        super.stop();
    }

    public Encoder<E> getEncoder() {
        return encoder;
    }

    public void setEncoder(Encoder<E> encoder) {
        this.encoder = encoder;
    }
}
