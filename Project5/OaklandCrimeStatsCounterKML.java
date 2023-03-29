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




public class OaklandCrimeStatsCounterKML extends Configured implements Tool {


        public static class OaklandCrimeStatsCounterMap extends Mapper<LongWritable, Text, Text, NullWritable>
        {
                private final static IntWritable one = new IntWritable(1);
                private Text oaklandcrimestatsWord = new Text();
                

                @Override
                public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException
                {
                        String line = value.toString();
                        if(line.toLowerCase().contains("aggravated assault")){
                                float x_feet = Float.parseFloat(line.split("\t", -1)[0]);
                                float y_feet = Float.parseFloat(line.split("\t", -1)[1]);

                                float dist_feet = (float) Math.sqrt(Math.pow((x_feet-1354326.897),2) + Math.pow((y_feet-411447.7828),2));
                                
                                float m = 350.0f;
                                float ft = 3.28084f;
                         
                                // to store the multiplied value
                                float dist_bound= m * ft;

                                if(dist_feet < dist_bound){
                                        String s = "  <Placemark>\n"+
                                                   "    <name>" + line.split("\t", -1)[8] + "," + line.split("\t", -1)[7] + "</name>\n" +
                                                   "    <Point>\n" +
                                                   "      <coordinates>"+ line.split("\t", -1)[8] + "," + line.split("\t", -1)[7] + "</coordinates>\n" +
                                                   "    </Point>\n" +
                                                   "  </Placemark>";

                                        oaklandcrimestatsWord.set(s);
                                        context.write(oaklandcrimestatsWord, NullWritable.get());
                                }
                        }
                        
                }

        }
        
        public static class OaklandCrimeStatsCounterReducer extends Reducer<Text, NullWritable, Text, NullWritable>
        {
                protected void setup(Context context) throws IOException, InterruptedException {
                    context.write(new Text("<kml xmlns=\"http://www.opengis.net/kml/2.2\">\n<Document>\n  <name>Crime Data</name>"), NullWritable.get());
                }

                public void reduce(Text key, Iterable<NullWritable> values, Context context) throws IOException, InterruptedException
                {
                        context.write(key, NullWritable.get());      
                }


                protected void cleanup(Context context) throws IOException, InterruptedException {
                    context.write(new Text("</Document>\n</kml>"), NullWritable.get());
                }
                
        }
        
        public int run(String[] args) throws Exception  {
               
                Job job = new Job(getConf());
                job.setNumReduceTasks(1);
                job.setJarByClass(OaklandCrimeStatsCounterKML.class);
                job.setJobName("oaklandcrimestatscounterkml");
                
                
                job.setOutputKeyClass(Text.class);
                job.setOutputValueClass(NullWritable.class);
                
                job.setMapperClass(OaklandCrimeStatsCounterMap.class);
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
                int result = ToolRunner.run(new OaklandCrimeStatsCounterKML(), args);
                System.exit(result);
        }
       
} 
