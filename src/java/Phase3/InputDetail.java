package Phase3;

/**
 * This is just a wrapper class to allow the use of a single array to hold all the input details
 */
public class InputDetail {
    String type;
    int amount;
    float value;

    public InputDetail(String type, int amount, float value) {
        this.amount = amount;
        this.value = value;
        this.type = type;
    }
}