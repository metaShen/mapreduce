package sum_avg_group.p3;

/*
 * Created by meta on 19-1-10 下午10:40
 *
 */
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class p3_Main extends Configured implements Tool {

    @Override
    public int run(String[] strings) throws Exception {
        if (strings.length < 3) {
            System.err.println("Usage: <smallFile(cacheFile)> <dataTable(big)> <Output>");
            System.exit(2);
        }
        Configuration configuration = new Configuration();
        configuration.set("cacheFile", strings[0]);
        Job job = Job.getInstance(configuration);
        // Jar class
        job.setJarByClass(p3_Main.class);
        // Mapper
        job.setMapperClass(p3_Mapper.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);
        // Reducer
        job.setReducerClass(p3_Reducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
        // input and output
        FileInputFormat.addInputPath(job, new Path(strings[1]));
        FileOutputFormat.setOutputPath(job, new Path(strings[2]));
        return job.waitForCompletion(true) ? 0 : 1;
    }

    public static void main(String[] args) throws Exception {
        System.exit(ToolRunner.run(new p3_Main(), args));
    }

}
