import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

// This class takes care of reading all pentominoes and their permutations from a CSV
// See attached documentation for structure of CSV (pentomino.csv.README)
public class PentominoDatabase
{
    // Stores and loads the data on program initialization
    public static int[][][][] data = loadData("pentominos.csv");

    // Loads and decodes the CSV file
    private static int[][][][] loadData(String fileName)
    {
        // Create a temporary dynamic object to store the data, later to be converted to a static 4D array
        ArrayList<ArrayList<int[][]>> dynamicList =  new ArrayList<>();

        // Open the CSV file
        File file = new File(fileName);

        try
        {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) // For each line in the CSV file
            {
                // Read the line, and convert the string to a list of numbers
                String[] values = scanner.nextLine().split(",");

                // If this piece has a new ID, increase the list
                if(Integer.valueOf(values[0]) > dynamicList.size() - 1)
                {
                    dynamicList.add(new ArrayList<>());
                }

                int xSize = Integer.valueOf(values[2]);
                int ySize = Integer.valueOf(values[3]);
                int[][] piece = new int[xSize][ySize];

                // Convert 1D list to 2D list
                for(int i = 0; i < xSize * ySize; i++)
                {
                    piece[i / ySize][i % ySize] = Integer.valueOf(values[4 + i]);
                }

                // Add piece to the dynamic list
                dynamicList.get(dynamicList.size() - 1).add(piece);
            }
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
            System.exit(0);
        }

        // Arrays index easier than ArrayLists, so convert dynamic list to static list
        int[][][][] staticList = new int[dynamicList.size()][][][];
        for(int i = 0; i < dynamicList.size(); i++)
        {
            staticList[i] = dynamicList.get(i).toArray(new int[0][0][0]);
        }
        return staticList;
    }

    // Main function used for visualizing and debugging reading the csv file with pieces
    // Should not be called while searching for a solution
    public static void main(String[] args)
    {
        for(int i = 0; i < data.length; i++)
        {
            for(int j = 0; j<data[i].length; j++)
            {
                System.out.print(i + "," + j + "," + data[i][j].length + "," + data[i][j][0].length);

                for(int k = 0; k < data[i][j].length; k++)
                {
                    for(int l = 0; l < data[i][j][k].length; l++)
                    {
                        System.out.print("," + data[i][j][k][l]);
                    }
                }

                System.out.println();
            }
        }
    }
}
