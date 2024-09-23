import java.util.HashMap;

public class MainClass {
    final static HashMap<Character, Integer> ORIENTATION_MAP = new HashMap<Character, Integer>() {{
        put('N', 0);
        put('E', 90);
        put('S', 180);
        put('W', 270);
    }};
    
    final static HashMap<Integer, Character> ANGLE_MAP = new HashMap<Integer, Character>() {{
        put(0, 'N');
        put(90, 'E');
        put(180, 'S');
        put(270, 'W');
    }};
    
    static class Rover {
        int xPosition;
        int yPosition;
        int angle;
        
        Rover(int xPosition, int yPosition, int angle) {
            this.xPosition = xPosition;
            this.yPosition = yPosition;
            this.angle = angle;
        }
        
        public String toString(char orientation) {
            return xPosition + " " + yPosition + " " + orientation;
        }
    }
    
    public static void main(String args[]) {
        int[] upperRightCoordinates = {5, 5};
        String[][] instructions = {{"1 2 N", "LMLMLMLMM"}, {"3 3 E", "MMRMMRMRRM"}};
        int numberOfRovers = instructions.length;
        
        Rover[] rovers = new Rover[numberOfRovers];
        
        rovers = sendRovers(upperRightCoordinates, instructions, rovers);
        for(int i = 0; i < rovers.length; i++) {
            final char roverOrientation = ANGLE_MAP.get((rovers[i].angle));
            System.out.println(rovers[i].toString(roverOrientation));
        }
    }
    
    public static Rover[] sendRovers(   int[] upperRightCoordinates, 
                                        String[][] instructions,
                                        Rover[] rovers) {
        
        for(int i = 0; i < rovers.length; i++) {
            String roverStartingPositionStr = instructions[i][0].replaceAll(" ", "");
            String roverMovementInstructions = instructions[i][1];
            
            Rover rover = new Rover(
                Character.getNumericValue(roverStartingPositionStr.charAt(0)),
                Character.getNumericValue(roverStartingPositionStr.charAt(1)),
                ORIENTATION_MAP.get((roverStartingPositionStr.charAt(2)))
            );

            for(int j = 0; j < roverMovementInstructions.length(); j++) {
                char instruction = roverMovementInstructions.charAt(j);
                if(instruction == 'L' || instruction == 'R') {
                    rover.angle = (rover.angle + spin(instruction) + 360) % 360;
                } else if(instruction == 'M') {
                    move(rover);
                }
            }
            
            rovers[i] = rover;
        }
        return rovers;
    }
    
    public static int spin(char movementInstruction) {
        int angle = 0;
        if(movementInstruction == 'L') {
            angle = -90;
        } else {
            angle = 90;
        }
        return angle;
    }

    public static void move(Rover rover) {
        switch (rover.angle) {
            case 0:
                rover.yPosition++;
                break;
            case 90:
                rover.xPosition++;
                break;
            case 180:
                rover.yPosition--;
                break;
            case 270:
                rover.xPosition--;
                break;
        }
    }
}
