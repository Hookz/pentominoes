import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Wrapper {
    static float score = 0;
    static String inputType = ""; //can be parcel or pentomino
    static Map<String, List<inputDetail>> inputDetails = new HashMap<>(); //It will have the format ['Type': inputDetail] with inputDetail having amount and value as fields.
}
