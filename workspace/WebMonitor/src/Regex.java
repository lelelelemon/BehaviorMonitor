import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String str = "NullPointerException异常";
		mainInfoMatching(str);

	}

	public static List<String> mainInfoMatching(String str) {
		Pattern pattern = Pattern.compile("[A-Za-z0-9]+");
		Matcher matcher = pattern.matcher(str);
		List<String> result = new ArrayList<String>();
		while (matcher.find()) {
			String out = matcher.group();
			System.out.println(out);
			result.add(out);
		}
		return result;
	}
}
