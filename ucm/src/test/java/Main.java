import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class Main {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("d:\\x1.csv"));
		FileOutputStream out = new FileOutputStream("d:\\x2.csv");
		String line = null;
		long last = 0;
		while ((line = reader.readLine()) != null) {
			String[] x = line.split(",");
			if (x.length == 2) {
				try {
					long l = Long.parseLong(x[0]);
					long s = l - last;
					out.write(String.valueOf(s).getBytes());
					out.write(String.valueOf("\n").getBytes());
					last = l;
				} catch (NumberFormatException e) {

					e.printStackTrace();
				}
			}
		}
		out.close();
		reader.close();
	}

}
