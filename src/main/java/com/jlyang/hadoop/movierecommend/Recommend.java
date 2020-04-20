package com.jlyang.hadoop.movierecommend;

import com.jlyang.hadoop.runner.HadoopRunner;

import java.io.IOException;

public class Recommend implements HadoopRunner {

    @Override
    public void run(String[] args) {
        try {
            new Step1().run(args);
        } catch (IOException | ClassNotFoundException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
