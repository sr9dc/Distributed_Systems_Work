// ======================= FactCounter.java ==========================================
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


public class FactCounter extends Configured implements Tool {

        public static class FactCounterMap extends Mapper<LongWritable, Text, Text, NullWritable>
        {
                private Text factWord = new Text();

                
                @Override
                public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException
                {
                        String line = value.toString();
                        StringTokenizer tokenizer = new StringTokenizer(line);

                        while(tokenizer.hasMoreTokens())
                        {
                                String token = tokenizer.nextToken();

                                if(token.toLowerCase().contains("fact")){
                                        factWord.set(token);
                                        context.write(factWord, NullWritable.get());
                                }

                        }
                }
        }
        
        public static class FactCounterReducer extends Reducer<Text, NullWritable, Text, NullWritable>
        {
                public void reduce(Text key, Iterable<NullWritable> values, Context context) throws IOException, InterruptedException
                {
                        // int sum = 0;
                        // for(IntWritable value: values)
                        // {
                        //         sum += value.get();
                        // }
                        context.write(key, NullWritable.get());
                }
                
        }
        
        public int run(String[] args) throws Exception  {
               
                Job job = new Job(getConf());
                job.setJarByClass(FactCounter.class);
                job.setJobName("factcounter");
                
                job.setOutputKeyClass(Text.class);
                job.setOutputValueClass(NullWritable.class);
                
                job.setMapperClass(FactCounterMap.class);
                job.setCombinerClass(FactCounterReducer.class);
                job.setReducerClass(FactCounterReducer.class);
                
                
                job.setInputFormatClass(TextInputFormat.class);
                job.setOutputFormatClass(TextOutputFormat.class);
                
                
                FileInputFormat.setInputPaths(job, new Path(args[0]));
                FileOutputFormat.setOutputPath(job, new Path(args[1]));
                
                boolean success = job.waitForCompletion(true);
                return success ? 0: 1;
        }
        
       
        public static void main(String[] args) throws Exception {
                // TODO Auto-generated method stub
                int result = ToolRunner.run(new FactCounter(), args);
                System.exit(result);
        }
       
} 
