package com.jlyang.hadoop.movierecommend;

import com.jlyang.hadoop.util.ExcelInputFormat;
import com.jlyang.hadoop.util.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

import java.io.IOException;

public class Step1 {

    private static class Step1Mapper extends Mapper<IntWritable, Text, IntWritable, Text> {
        private final IntWritable keyOut = new IntWritable();
        private final Text valueOut = new Text();

        @Override
        protected void map(IntWritable key, Text value, Context context)
                throws IOException, InterruptedException {
            String line = value.toString();
            String[] vals = line.split(" ");
            if (vals.length == 3) {
                if (StringUtils.isNumeric(vals[0]) && StringUtils.isNumeric(vals[1])) {
                    keyOut.set(toInt(vals[0]));
                    valueOut.set(toInt(vals[1]) + ":" + vals[2]);
                    context.write(keyOut, valueOut);
                }
            }
        }

        private int toInt(String origin){
            return Integer.parseInt(origin.split("\\.")[0]);
        }
    }

    private static class Step1Reducer extends Reducer<IntWritable, Text, IntWritable, Text> {
        private final Text valueOut = new Text();

        @Override
        protected void reduce(IntWritable key, Iterable<Text> iterator, Context context)
                throws IOException, InterruptedException {
            StringBuilder builder = new StringBuilder();
            iterator.forEach(v -> builder.append(v).append(","));
            valueOut.set(builder.deleteCharAt(builder.length() - 1).toString());
            context.write(key, valueOut);
        }
    }

    public void run(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);
        job.setJarByClass(this.getClass());
        job.setJobName(args[0]);
        job.setInputFormatClass(ExcelInputFormat.class);
//        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);
        job.setOutputKeyClass(IntWritable.class);
        job.setOutputValueClass(Text.class);
        job.setMapperClass(Step1Mapper.class);
        job.setReducerClass(Step1Reducer.class);
        FileInputFormat.addInputPath(job, new Path(args[1]));
        FileOutputFormat.setOutputPath(job, new Path(args[2]));
        job.waitForCompletion(true);
    }
}
