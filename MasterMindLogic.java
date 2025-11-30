import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MasterMindLogic {

    private final Color[] SECRET;
    private final Color[] PALETTE;
    private int rounds;
    private final String[] LABELS;

    // Constructor
    public MasterMindLogic(Color[] palette, int maxRounds, int secretLength, String[] labels) {
        this.PALETTE = palette;
        this.SECRET = getSecret(secretLength);
        this.rounds = maxRounds;
        this.LABELS = labels;
    }

    // Secret generator
    public Color[] getSecret(int secretLength) {
        Random random = new Random();
        Color[] newSecret = new Color[secretLength];

        int randomIndex = 0;
        for (int i = 0; i < secretLength; i++) {
            randomIndex = random.nextInt(PALETTE.length);
            newSecret[i] = PALETTE[randomIndex];
        }
        return newSecret;
    }

    // Check guess and return feedback
    public Result checkGuess(Color[] guess) {
        int blacks = 0;
        int whites = 0;

        ArrayList<Color> guessSlots = new ArrayList<>();
        ArrayList<Color> secretSlots = new ArrayList<>();

        // Count black pins
        for (int i = 0; i < SECRET.length; i++) {
            Color g = guess[i];
            Color s = SECRET[i];
            if (g == s) {
                blacks++;
            } else {
                guessSlots.add(g);
                secretSlots.add(s);
            }
        }

        // Count white pins
        for (Color guessSlot : guessSlots) {
            if (secretSlots.contains(guessSlot)) {
                whites++;
                secretSlots.remove(guessSlot);
            }
        }

        return new Result(blacks, whites);
    }

    public String showSecret() {
        StringBuilder sb = new StringBuilder();

        // Convert PALETTE (Colors) into a List<Color>
        List<Color> paletteList = List.of(PALETTE);

        for (Color c : SECRET) {
            int index = paletteList.indexOf(c);
            sb.append(LABELS[index]);
        }

        return sb.toString();
    }


    // Result structure
    public static class Result {
        public int blacks, whites;

        public Result(int b, int w) {
            blacks = b;
            whites = w;
        }
    }

}
