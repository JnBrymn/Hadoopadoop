package org.myorg;

import java.io.IOException;
import java.util.*;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

import java.util.Map.Entry;
import java.util.*;

public class MatrixTranspose {

 public static class Map extends Mapper<LongWritable, Text, LongWritable, MapWritable> {
    private Text word = new Text();

    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
      String[] s = value.toString().split("\\t");
      int row = Integer.parseInt(s[0].trim());
      String[] vals = s[1].trim().split("\\s+");
      MapWritable map = new MapWritable();
      int col = 0;
      for(String v : vals) {
        double val = Double.parseDouble(v);
        map.put(new LongWritable(row), new DoubleWritable(val));
        context.write(new LongWritable(col), map);
        col++;
      }
    }
 }

 public static class Reduce extends Reducer<LongWritable, MapWritable, LongWritable, Text> {

    public void reduce(LongWritable key, Iterable<MapWritable> maps, Context context)
      throws IOException, InterruptedException {
        SortedMap<LongWritable,DoubleWritable> rowVals = new TreeMap<LongWritable,DoubleWritable>();
        for (MapWritable map : maps) {
          for(Entry<Writable, Writable>  entry : map.entrySet()) {
            rowVals.put((LongWritable) entry.getKey(),(DoubleWritable) entry.getValue());
          }
        }

        StringBuffer sb = new StringBuffer();
        for(DoubleWritable rowVal : rowVals.values()) {
          sb.append(rowVal.toString());
          sb.append(" ");
        }
        context.write(key,new Text(sb.toString()));
    }
 }

 public static void main(String[] args) throws Exception {
    Configuration conf = new Configuration();

        Job job = new Job(conf, "matrixtranspose");

    job.setJarByClass(org.myorg.MatrixTranspose.class);    

    job.setOutputKeyClass(LongWritable.class);
    job.setOutputValueClass(MapWritable.class);

    job.setMapperClass(Map.class);
    job.setReducerClass(Reduce.class);

    job.setInputFormatClass(TextInputFormat.class);
    job.setOutputFormatClass(TextOutputFormat.class);

    FileInputFormat.addInputPath(job, new Path(args[0]));
    FileOutputFormat.setOutputPath(job, new Path(args[1]));

    job.waitForCompletion(true);
 }

}
