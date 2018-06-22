import java.io.*;
import java.util.Scanner;

public class Hungarian_Input {
	public static void main(String[] args) {

		int num;
		int[][] bipartite;

		Scanner sc = new Scanner(System.in);
		System.out.print("Enter filename: ");
		String file = sc.nextLine();

		String line = null;
		try {
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			num = Integer.parseInt(bufferedReader.readLine());

			bipartite = new int[num][num];
			for (int i = 0; i < num; i++) {
				for (int j = 0; j < num; j++) {
					bipartite[i][j] = 1;
				}
			}

			while ((line = bufferedReader.readLine()) != null) {
				String str[] = line.split(" ");
				int firstNum = Integer.parseInt(str[0]) - 1;
				int secondNum = Integer.parseInt(str[1]) - 1;

				bipartite[firstNum][secondNum] = 0;
				bipartite[secondNum][firstNum] = 0;
			}

			// long time = System.currentTimeMillis(); // Start time recording
			Hungarian_Array hungarian = new Hungarian_Array(bipartite);
			// System.out.println(String.format("Total time: %dms\n", System.currentTimeMillis() - time)); // Stop time recording and display time consumed

			// Display result on screen
			int[] result = hungarian.getResult();

			String output = file;
			output = file.split("\\.")[0];
			File outputFile = new File(output+"_hungarian_Array.txt");
			PrintWriter writer = new PrintWriter(new FileWriter(outputFile));
			writer.write("The best match set is:\n");
			for(int i = 0; i < result.length; i++)
			{
				System.out.println(String.format("%d ---> %d ", i + 1, result[i] + 1));
				writer.write((i+1) + "--->" + (result[i] + 1)+ "\n");
			}
			bufferedReader.close();
			fileReader.close();
			writer.close();
		}
		//catch errors with the file
		catch(FileNotFoundException ex) {
			System.out.println("Unable to open file '" + file + "'");
		}
		catch(IOException ex) {
			System.out.println("Error reading file '" + file + "'");
		}
	}
}
