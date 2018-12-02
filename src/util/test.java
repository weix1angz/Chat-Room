package util;

public class test {
	public static void main(String[] args) {
		NBAbot bot = new NBAbot('%');
		System.out.println(bot.getResponses("%team Indiana", null));
	}
}
