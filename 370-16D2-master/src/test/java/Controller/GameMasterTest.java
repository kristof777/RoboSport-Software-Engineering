package Controller;

import Model.Gang;
import org.junit.Test;

import java.util.ArrayList;

public class GameMasterTest {

    private ArrayList<Gang> gangs = new ArrayList<>();
    private final SetupMenuController setUpControl = new SetupMenuController();

    @SuppressWarnings("ConstantConditions")
    @Test
    public void testStatsMain() throws Exception {
        String[] bots = {"dat boi", "Fast Footed Fred", "Wow"};
        int colour = 0;
        boolean isAI = false;

        setUpControl.makeGangByColour(bots, colour, isAI);

        Gang[] allGangs = setUpControl.makeAllGangs();
    }

    @SuppressWarnings("EmptyMethod")
    public void getNextRobot() {

    }
}