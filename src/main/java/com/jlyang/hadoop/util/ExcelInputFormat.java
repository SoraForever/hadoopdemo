package com.jlyang.hadoop.util;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

import java.io.IOException;
import java.io.InputStream;

public class ExcelInputFormat extends FileInputFormat<IntWritable, Text> {

    @Override
    public RecordReader<IntWritable, Text> createRecordReader(InputSplit inputSplit, TaskAttemptContext taskAttemptContext) {
        return new ExcelRecordReader();
    }

    private static class ExcelRecordReader extends RecordReader<IntWritable, Text> {
        private InputStream inputStream;
        private IntWritable key;
        private Text value;
        private String[] records;

        @Override
        public void initialize(InputSplit inputSplit, TaskAttemptContext taskAttemptContext)
                throws IOException {
            FileSplit split = (FileSplit) inputSplit;
            Configuration conf = taskAttemptContext.getConfiguration();
            Path path = split.getPath();
            FileSystem fs = path.getFileSystem(conf);
            inputStream = fs.open(path);
            records = ExcelParser.parse(inputStream);
        }

        @Override
        public boolean nextKeyValue() {
            if (key == null) {
                key = new IntWritable(0);
                value = new Text(records[0]);
                return true;
            } else {
                int idx = key.get();
                if (idx < records.length - 1) {
                    ++idx;
                    key.set(idx);
                    value.set(records[idx]);
                    return true;
                } else {
                    return false;
                }
            }
        }

        @Override
        public IntWritable getCurrentKey() {
            return key;
        }

        @Override
        public Text getCurrentValue() {
            return value;
        }

        @Override
        public float getProgress() {
            return 0;
        }

        @Override
        public void close() throws IOException {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }
}
