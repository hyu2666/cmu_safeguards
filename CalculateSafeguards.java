import java.io.*;
import java.util.*;

/**
 * Iterate all the time interval, find the max time that covered by all the safeguards
 * Also, find the [distinct time] of each safeguard
 * If current interval is covered by previous one, the distinct time = end - preIntervalEnd.
 * And update the distinct time of previous one = previousDistinctTime - (preIntervalEnd - start)
 * Otherwise, the distinct time = end - start
 * Finally, if there are more than 1 [included time interval], the result is max length.
 * Otherwise, the result is max length - min(distinct time[i])
 *
 * [distinct time]: The time when there is only one safeguard besides the swimming pool
 * for example, safeguard 1's time
 * interval is 1-4, safeguard 2's time interval is 3-7, safeguard 2's time interval is 5-9,
 * the distinct time of safeguard 2 is 4-5.
 */

public class CalculateSafeguards {

    /**
     * The max number of safeguards
     */
    private static final int MAX_N = 1000001;

    public static void main(String[] args) {

        /**
         * Address of input file
         */
        String input_address = "/Users/haitongyu/IdeaProjects/Safeguards/src/data/";
        Scanner sc = new Scanner(System.in);
        System.out.println("Input the file index：");
        String file_index = sc.nextLine();
        String input_file = file_index + ".in";
        System.out.println("Input file name: " + input_file);

        //output file
        String output_file = input_address + file_index + ".out";

        //The list contains safeguards object
        List<Safeguards> list = new LinkedList<>();
        //Read and sort the data
        int n = readFile(input_address + input_file, list);
        //The max coverage time of all the safeguards
        int maxLength = 0;
        //The end time of previous safeguard in the sorted list
        int preIntervalEnd = 0;
        //The number of time interval that is included by the other time interval
        int includedIntervalNum = 0;
        //The distinct time of fired safeguard
        int deletedInterval = Integer.MAX_VALUE;
        //The distinct time of each safeguard
        int[] distinctTime = new int[MAX_N];
        int final_result = 0;

        //Iterate all the safeguards' time interval
        for (int i = 0; i < n; i++) {
            Safeguards safeguards = list.get(i);
            int start = safeguards.getStart();
            int end = safeguards.getEnd();

            //Do the special case for i == 0
            if (i == 0) {
                maxLength = end - start;
                distinctTime[i] = end - start;
                preIntervalEnd = end;
            } else {
                //As the list is sorted by start time, if current safeguard end time is earlier
                //than the previous one, the current one if included by the other time interval
                if (end <= preIntervalEnd) {
                    includedIntervalNum++;
                } else {
                    //Update the distinct time for the previous safeguard
                    int min = Math.min(end - start, end - preIntervalEnd);
                    maxLength += min;
                    distinctTime[i] = min;
                    if (start < preIntervalEnd) {
                        int intersectTime = preIntervalEnd - start;
                        distinctTime[i - 1] -= intersectTime;
                    }
                    preIntervalEnd = end;
                }
            }
        }

        System.out.println("MaxLength = " + maxLength);

        // Finally, if there are more than 1 [included time interval], the result is max length.
        // Otherwise, the result is max length - min(distinct time[i])
        if (includedIntervalNum >= 1) {
            System.out.println("Final Result: maxLength = " + maxLength);
            final_result = maxLength;
        } else {
            for (int i = 0; i < n; i++) {
                deletedInterval = Math.min(distinctTime[i], deletedInterval);
            }
            System.out.println("Deleted time interval: " + deletedInterval);

            final_result = maxLength - deletedInterval;
            System.out.println("Final Result: maxLength - deletedInterval: " + final_result);
        }

        // Write the processed data into new file
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File(output_file), true));
            writer.write(Integer.toString(final_result));
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Read the safeguards time interval data into list and sort the data according to the
     * ascending order of start time
     * @param list the safeguards time interval list
     * @return the number of safeguards
     */
    private static int readFile(String input_address, List<Safeguards> list) {
        int n = 0;
        try {
            Scanner sc = new Scanner(new FileReader(input_address));
            n = sc.nextInt();
            System.out.println("Total safeguards number n: " + n);
            while(sc.hasNext()) {
                Safeguards tempSafeguards = new Safeguards(sc.nextInt(), sc.nextInt());
                list.add(tempSafeguards);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Collections.sort(list);
//        System.out.println(list);
        return n;
    }

}
