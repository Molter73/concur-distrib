import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class LogCounterMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
	private static final IntWritable one = new IntWritable(1);
	private static final Pattern PATTERN = Pattern.compile("^wallet-rest-api\\[(INFO|SEVERE|WARN)\\]");

	@Override
	public void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {
		Matcher matcher = PATTERN.matcher(value.toString());

		if (!matcher.find()) {
			return;
		}

		Text severity = new Text(matcher.group(1));
		context.write(severity, one);
	}
}
