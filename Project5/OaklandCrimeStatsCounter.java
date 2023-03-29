// ======================= OaklandOaklandCrimeStatsStats.java ==========================================
package org.myorg;
import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.*;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.util.*;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

import java.lang.String;
import java.lang.Math;




public class OaklandCrimeStatsCounter extends Configured implements Tool {


        public static class OaklandCrimeStatsCounterMap extends Mapper<LongWritable, Text, Text, IntWritable>
        {
                private final static IntWritable one = new IntWritable(1);
                private Text oaklandcrimestatsWord = new Text();
                

                @Override
                public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException
                {
                        String line = value.toString();
                        if(line.toLowerCase().contains("aggravated assault")){
                                float x_feet = Float.parseFloat(line.split("\\s")[0]);
                                float y_feet = Float.parseFloat(line.split("\\s")[1]);

                                float dist_feet = (float) Math.sqrt(Math.pow((x_feet-1354326.897),2) + Math.pow((y_feet-411447.7828),2));
                                
                                float m = 350.0f;
                                float ft = 3.28084f;
                         
                                // to store the multiplied value
                                float dist_bound= m * ft;

                                if(dist_feet < dist_bound){
                                        oaklandcrimestatsWord.set("aggravated assault total: ");
                                        context.write(oaklandcrimestatsWord, one);
                                }
                                
                        }

                        
                }
        }
        
        public static class OaklandCrimeStatsCounterReducer extends Reducer<Text, IntWritable, Text, IntWritable>
        {
                public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException
                {
                        int sum = 0;
                        for(IntWritable value: values)
                        {
                                sum += value.get();
                        }
                        context.write(key, new IntWritable(sum));
                }
                
        }
        
        public int run(String[] args) throws Exception  {
               
                Job job = new Job(getConf());
                job.setJarByClass(OaklandCrimeStatsCounter.class);
                job.setJobName("oaklandcrimestatscounter");
                
                job.setOutputKeyClass(Text.class);
                job.setOutputValueClass(IntWritable.class);
                
                job.setMapperClass(OaklandCrimeStatsCounterMap.class);
                job.setCombinerClass(OaklandCrimeStatsCounterReducer.class);
                job.setReducerClass(OaklandCrimeStatsCounterReducer.class);
                
                
                job.setInputFormatClass(TextInputFormat.class);
                job.setOutputFormatClass(TextOutputFormat.class);
                
                
                FileInputFormat.setInputPaths(job, new Path(args[0]));
                FileOutputFormat.setOutputPath(job, new Path(args[1]));
                
                boolean success = job.waitForCompletion(true);
                return success ? 0: 1;
        }
        
       
        public static void main(String[] args) throws Exception {
                // TODO Auto-generated method stub
                int result = ToolRunner.run(new OaklandCrimeStatsCounter(), args);
                System.exit(result);
        }
       
} 
