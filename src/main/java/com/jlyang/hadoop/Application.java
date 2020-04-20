package com.jlyang.hadoop;

import com.jlyang.hadoop.movierecommend.Recommend;
import com.jlyang.hadoop.runner.HadoopRunner;
import com.jlyang.hadoop.wordcount.WordCount;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.jlyang.hadoop.util.StringUtils.toLowerCase;

public class Application {
    private static final Map<String, HadoopRunner> RUNNER_MAP = new HashMap<String, HadoopRunner>() {{
        put(toLowerCase("wordcount"), new WordCount());
        put(toLowerCase("recommend"), new Recommend());
    }};

    public static void main(String[] args) {
        Optional.ofNullable(RUNNER_MAP.get(toLowerCase(args[0]))).ifPresent(r -> r.run(args));
    }
}
