package com.group17.game.core;

import com.group17.game.model.entity.Direction;
import com.group17.game.model.entity.enemy.*;
import com.group17.game.model.entity.item.*;
import com.group17.game.model.world.*;

import java.io.File;
import java.io.IOException;
import java.util.*;

//File Layout
//This is how to formal level files.
/*

<Level File>                    //Level File Declarator
1; Level 1                      //Level Number; Level Name
10,10;1,1;8,8                   //Width, Height; Start x, Start y; Finish x, Finish y
2,2,7,7;3,3,6,6                 //Teleporters: Origin x, Origin y, Destination x, Destination y; ...
1,3,smart,down;8,6,line,left    //Enemies: Spawn x, Spawn y, Type (smart, dumb, wall, line), Direction (up, down, left, right); ...
1,5,fire;1,6,water              //Obstacles: Position x, Position y, Type (fire, water); ...
1,2,fire boots;1,3,water boots  //Items: Position x, Position y, Type (fire boots, water boots);  ...
##########                      //Level Layout - Walls, Ground, Obstacles, Doors and Items
#      @ #
#        #                      #       = Wall
#  @   a #                      'space' = Ground
#    @   #                      @       = Token
# #####A##                      1-9     = Token Door (Number = Token Cost)
# @      #                      A-Z     = Key Door (Matching key is corresponding lowercase letter)
#      ###                      a-z     = Key
# @    5 #
##########                      (Other characters can be used but will be ignored, helpful for visualising layout)

*/

/**
 * Methods to parse txt level files and build level objects from the data.
 *
 * @author Laurence Rawlings
 * @version 3.0
 */
public class LevelReader {
    private static final String LEVEL_DIR =
            "./src/com/group17/game/resources/levels";
    private static final String LEVEL_FILE_DECLARATOR = "<Level File>";
    private static final String LEVEL_FILE_EXTENSION = "txt";

    private static final int LEVEL_DETAILS = 0;
    private static final int LEVEL_POSITIONS = 1;
    private static final int LEVEL_TELEPORTERS = 2;
    private static final int LEVEL_ENEMIES = 3;
    private static final int LEVEL_OBSTACLES = 4;
    private static final int LEVEL_ITEMS = 5;
    private static final int LEVEL_GRID = 6;

    private static final Map cellMap =
            Collections.unmodifiableMap(new HashMap() {{
                put('#', new Wall());
                put(' ', new Ground());
                put("water", new Water());
                put("fire", new Fire());
                put('R', new RedDoor());
                put('G', new GreenDoor());
                put('B', new BlueDoor());
            }});

    private static final Map itemMap =
            Collections.unmodifiableMap(new HashMap() {{
                put('@', new Token());
                put("fire boots", new FireBoots());
                put("water boots", new WaterBoots());
                put('r', new Key(Key.KeyType.red));
                put('g', new Key(Key.KeyType.green));
                put('b', new Key(Key.KeyType.blue));
            }});

    private static final Map enemyMap =
            Collections.unmodifiableMap(new HashMap() {{
                put("smart", Enemy.EnemyType.smart);
                put("dumb", Enemy.EnemyType.dumb);
                put("wall", Enemy.EnemyType.wall);
                put("line", Enemy.EnemyType.line);
            }});

    private static final Map directionMap =
            Collections.unmodifiableMap(new HashMap() {{
                put("up", Direction.up);
                put("down", Direction.down);
                put("left", Direction.left);
                put("right", Direction.right);
            }});

    private static List<Level> levelQueue;

    /**
     * Get the list of levels sorted by level number.
     *
     * @return the queue of levels.
     */
    public static List<Level> getLevelQueue() {
        readLevels();
        return levelQueue;
    }

    /**
     * Gets a lists of all the level names.
     *
     * @return list of level names.
     */
    public static List<String> getLevelNames() {
        List<String> levelNames = new ArrayList<>();
        for (Level level : getLevelQueue()) {
            levelNames.add(level.toString());
        }
        return levelNames;
    }

    private static void readLevels() {
        File levelDirectory = new File(LEVEL_DIR);
        File[] levelFiles = levelDirectory.listFiles();
        List<Level> levels = new ArrayList<>();

        try {
            if (levelFiles != null) {
                for (File level : levelFiles) {
                    String[] levelPath = level.getAbsolutePath().split("\\.");
                    if (levelPath[levelPath.length - 1]
                            .equals(LEVEL_FILE_EXTENSION) && level.isFile()) {
                        Scanner scanner = new Scanner(level);
                        if (scanner.hasNextLine()) {
                            if (scanner.nextLine()
                                    .equals(LEVEL_FILE_DECLARATOR)) {
                                List<String> levelData = new ArrayList<>();
                                while (scanner.hasNextLine()) {
                                    levelData.add(scanner.nextLine());
                                }
                                levels.add(buildLevel(
                                        levelData.toArray(new String[0])));
                            }
                        }
                        scanner.close();
                    }
                }
            }
        } catch (IOException e) {
            System.out.println(levelDirectory + " not found!");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Collections.sort(levels);
        levelQueue = levels;
    }

    private static Level buildLevel(String[] levelData) {
        try {
            String[] details = levelData[LEVEL_DETAILS].split(";");
            String[] positions = levelData[LEVEL_POSITIONS].split(";");
            String[] dimensions = positions[0].split(",");
            String[] start = positions[1].split(",");
            String[] finish = positions[2].split(",");
            String[] teleporters = levelData[LEVEL_TELEPORTERS].split(";");
            String[] enemies = levelData[LEVEL_ENEMIES].split(";");
            String[] obstacles = levelData[LEVEL_OBSTACLES].split(";");
            String[] items = levelData[LEVEL_ITEMS].split(";");

            Level newLevel = new Level(new Position(Integer.parseInt(start[0]),
                    Integer.parseInt(start[1])),
                    new Position(Integer.parseInt(finish[0]),
                            Integer.parseInt(finish[1])),
                    Integer.parseInt(details[0]),
                    details[1],
                    Integer.parseInt(dimensions[0]),
                    Integer.parseInt(dimensions[1]));

            int yOffset = LEVEL_GRID;
            for (int y = yOffset;
                 y < Integer.parseInt(dimensions[1]) + yOffset; y++) {
                for (int x = 0; x < levelData[y].length(); x++) {
                    char current = levelData[y].charAt(x);
                    Position position = new Position(x, y - yOffset);
                    if (cellMap.containsKey(current)) {
                        newLevel.setCell(position, (Cell) cellMap.get(current));
                    } else if (itemMap.containsKey(current)) {
                        newLevel.setItem(position, (Item) itemMap.get(current));
                        newLevel.setCell(position, (Cell) cellMap.get(' '));
                    } else if (Character.isDigit(current)) {
                        newLevel.setCell(position, new TokenDoor(
                                Integer.parseInt(Character.toString(current))));
                    } else {
                        newLevel.setCell(position, (Cell) cellMap.get(' '));
                    }
                }
            }

            if (!teleporters[0].equals("")) {
                for (String teleporter : teleporters) {
                    String[] teleporterDetails = teleporter.split(",");
                    Teleporter t = new Teleporter(
                            new Position(Integer.parseInt(teleporterDetails[0]),
                                    Integer.parseInt(teleporterDetails[1])),
                            new Position(Integer.parseInt(teleporterDetails[2]),
                                    Integer.parseInt(teleporterDetails[3])));

                    newLevel.setCell(t.getPosition(), t);
                    newLevel.setCell(t.getDestination().getPosition(),
                            t.getDestination());
                }
            }

            if (!enemies[0].equals("")) {
                for (String enemy : enemies) {
                    String[] enemyDetails = enemy.split(",");

                    if (enemyMap.containsKey(enemyDetails[2]) &&
                            directionMap.containsKey(enemyDetails[3])) {
                        Direction direction =
                                (Direction) directionMap.get(enemyDetails[3]);
                        Position position =
                                new Position(Integer.parseInt(enemyDetails[0]),
                                        Integer.parseInt(enemyDetails[1]));
                        switch ((Enemy.EnemyType) enemyMap
                                .get(enemyDetails[2])) {
                            case smart:
                                newLevel.addEnemy(
                                        new SmartFollowingEnemy(position,
                                                direction, newLevel,
                                                new Position(Integer.parseInt(
                                                        start[0]),
                                                        Integer.parseInt(
                                                                start[1]))));
                                break;
                            case dumb:
                                newLevel.addEnemy(
                                        new DumbFollowingEnemy(position,
                                                direction, newLevel,
                                                new Position(Integer.parseInt(
                                                        start[0]),
                                                        Integer.parseInt(
                                                                start[1]))));
                                break;
                            case wall:
                                newLevel.addEnemy(
                                        new WallFollowingEnemy(position,
                                                direction, newLevel));
                                break;
                            case line:
                                newLevel.addEnemy(
                                        new LineFollowingEnemy(position,
                                                direction, newLevel));
                                break;
                        }
                    }
                }
            }

            if (!obstacles[0].equals("")) {
                for (String obstacle : obstacles) {
                    String[] obstacleDetails = obstacle.split(",");

                    if (cellMap.containsKey(obstacleDetails[2])) {
                        newLevel.setCell(new Position(
                                        Integer.parseInt(obstacleDetails[0]),
                                        Integer.parseInt(obstacleDetails[1])),
                                (Cell) cellMap.get(obstacleDetails[2]));
                    }
                }
            }

            if (!items[0].equals("")) {
                for (String item : items) {
                    String[] itemDetails = item.split(",");

                    if (itemMap.containsKey(itemDetails[2])) {
                        newLevel.setItem(
                                new Position(Integer.parseInt(itemDetails[0]),
                                        Integer.parseInt(itemDetails[1])),
                                (Item) itemMap.get(itemDetails[2]));
                    }
                }
            }

            newLevel.setCell(new Position(Integer.parseInt(finish[0]),
                    Integer.parseInt(finish[1])), new Goal());

            return newLevel;
        } catch (Exception e) {
            System.out.println("Invalid level file!");
            e.printStackTrace();
            return null;
        }
    }
}
