import java.io.*;
import java.util.Scanner;

// Hungarian Algorithm: DFS
public class Hungarian
{
    static int[][] bipartite;	// array of bipartite: num * num;
    // 0 means not connected, 1 means connected
    static int num;			// number of vertex
    static int[] match;		// -1 means not matched, match[v1] = v2 means v1 connected with v2
    static boolean[] visited;	// if it's visited, then mark true

    // constructor
    public Hungarian(int[][] bipartite)
    {
        this.bipartite = bipartite;
        match = new int[num + 1];
        visited = new boolean[num + 1];

        for(int i = 1; i <= num; i++)
        {
            match[i] = -1;		// initialize as no vertex is connected with this vertex
            visited[i] = false;
        }
    }

    // search
    void search()
    {
        // traverse each vertex
        for(int i = 1; i <= num; i++)
        {
            // if the vertex has not matched
            if(match[i] == -1)
            {
                // refresh the visited array and traverse again
                refresh_search();
                // start to find that vertex's augment path
                findAugmentPath(i);
            }
        }
    }

    // findAugmentPath: to find the augment path of vertex i
    //                  if there is one, then return true; otherwise, return false
    boolean findAugmentPath(int v)
    {
        for(int i = 1; i <= num; i++)
        {
            // if connected
            if(bipartite[v][i] == 1)
            {
                // if not visited
                if(visited[i] == false)
                {
                    visited[i] = true;
                    // if i is not matched with others, then match them
                    // if i is matched with j, then test if j can change to another vertex
                    if(match[i] == -1 || findAugmentPath(match[i]))
                    {
                        match[v] = i;
                        match[i] = v;
                        return true;
                    }
                }
            }
        }
        return false;
    }



    // clear visited array
    void refresh_search()
    {
        for(int i = 1; i <= num; i++)
        {
            visited[i] = false;
        }
    }

    // main
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter filename: ");
        String file = sc.nextLine();

        String line = null;
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            num = Integer.parseInt(bufferedReader.readLine());

            bipartite = new int[num + 1][num + 1];
            for(int i = 1; i <= num; i++)
            {
                for(int j = 1; j <= num; j++)
                {
                    bipartite[i][j] = 0;
                }
            }

            while((line = bufferedReader.readLine()) != null)
            {
                String str[] = line.split(" ");
                int firstNum = Integer.parseInt(str[0]);
                int secondNum = Integer.parseInt(str[1]);

                bipartite[firstNum][secondNum] = 1;
                bipartite[secondNum][firstNum] = 1;
            }

            new Hungarian(bipartite).search();

            String output = file;
            output = file.split("\\.")[0];
            File outputFile = new File(output+"_hungarian_DFS.txt");
            PrintWriter writer = new PrintWriter(new FileWriter(outputFile));
            writer.write("The best match set is:\n");
            for(int i = 1; i <= num; i++)
            {
                System.out.println(i + "--->" + match[i]);
                writer.write(i + "--->" + match[i] + "\n");
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