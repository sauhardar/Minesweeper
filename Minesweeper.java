import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import tester.*;
import javalib.impworld.*;
import java.awt.Color;
import javalib.worldimages.*;

// The main game class
class Minesweeper extends World {
  private static final int GAME_COLUMN_LEN = 5;
  private static final int GAME_ROW_LEN = 5;
  private static final int MINES = 10;
  public static final int WIDTH = 1200;
  public static final int HEIGHT = 800;
  private static final Random RANDOBJ = new Random(1);

  ArrayList<Posn> mines;

  Minesweeper() {
    this.mines = this.setMines();
  }

  // Visualizes the world
  public WorldScene makeScene() {
    WorldScene scene = new WorldScene(Minesweeper.WIDTH, Minesweeper.HEIGHT);
    scene.placeImageXY(this.drawMultRows(this.makeMultRows(this.makeColumn())),
        (Minesweeper.GAME_ROW_LEN * Cell.CELL_LEN / 2),
        (Minesweeper.GAME_COLUMN_LEN * Cell.CELL_LEN / 2));
    return scene;
  }

  // Creates the singular column at the left side with each
  // element being an ArrayList<Cell>, representing a singular row.
  public ArrayList<Cell> makeColumn() {
    // Creates an ArrayList with the number of elements
    // (columns) we want it to have.
    ArrayList<Cell> column = new ArrayList<Cell>(Minesweeper.GAME_COLUMN_LEN);
    while (column.size() < Minesweeper.GAME_COLUMN_LEN) {
      column.add(new Cell());
    }
    return column;
  }

  // Given one ArrayList representing the left most column,
  // creates an ArrayList for each element.
  public ArrayList<ArrayList<Cell>> makeMultRows(ArrayList<Cell> leftMostCol) {
    int rowLength = Minesweeper.GAME_ROW_LEN - 1;
    int colLength = Minesweeper.GAME_COLUMN_LEN - 1;

    ArrayList<ArrayList<Cell>> starter = new ArrayList<ArrayList<Cell>>();
    // Adds a row (ArrayList<Cell>)to each element in the column leftMostCol
    for (Cell cell : leftMostCol) {
      starter.add(this.makeRow());
    }

    for (int y = 0; y < starter.size(); y++) {
      for (int x = 0; x < starter.get(y).size(); x++) {
        if (this.mines.contains(new Posn(x + 1, y + 1))) {
          starter.get(y).get(x).hasMine = true;
        }
        if (y == 0 && x == 0) {
          ArrayList<Cell> neighbhors = new ArrayList<Cell>();
          // find its neighbhors:
          neighbhors.add(starter.get(1).get(0)); // y value first, then x value
          neighbhors.add(starter.get(1).get(1)); // starter.get(y-value).get(x-value)
          neighbhors.add(starter.get(0).get(1));

          // Add all the neighbhors here:
          starter.get(y).get(x).neighbors.addAll(neighbhors);
        }
        else if (y == 0 && x == rowLength) {
          ArrayList<Cell> neighbhors = new ArrayList<Cell>();
          neighbhors.add(starter.get(0).get(rowLength - 1));
          neighbhors.add(starter.get(1).get(rowLength));
          neighbhors.add(starter.get(1).get(rowLength - 1));

          starter.get(y).get(x).neighbors.addAll(neighbhors);
        }
        else if (y == colLength && x == 0) {
          ArrayList<Cell> neighbhors = new ArrayList<Cell>();
          neighbhors.add(starter.get(colLength - 1).get(0));
          neighbhors.add(starter.get(colLength - 1).get(1));
          neighbhors.add(starter.get(colLength).get(1));

          starter.get(y).get(x).neighbors.addAll(neighbhors);
        }
        else if (y == colLength && x == rowLength) {
          ArrayList<Cell> neighbhors = new ArrayList<Cell>();
          neighbhors.add(starter.get(colLength - 1).get(rowLength));
          neighbhors.add(starter.get(colLength - 1).get(rowLength - 1));
          neighbhors.add(starter.get(colLength).get(rowLength - 1));

          starter.get(y).get(x).neighbors.addAll(neighbhors);
        }
        else if (y == 0) {
          ArrayList<Cell> neighbhors = new ArrayList<Cell>();
          neighbhors.add(starter.get(y).get(x - 1));
          neighbhors.add(starter.get(y).get(x + 1));
          neighbhors.add(starter.get(y + 1).get(x - 1));
          neighbhors.add(starter.get(y + 1).get(x));
          neighbhors.add(starter.get(y + 1).get(x + 1));

          starter.get(y).get(x).neighbors.addAll(neighbhors);
        }
        else if (y == colLength) {
          ArrayList<Cell> neighbhors = new ArrayList<Cell>();
          neighbhors.add(starter.get(y).get(x - 1));
          neighbhors.add(starter.get(y).get(x + 1));
          neighbhors.add(starter.get(y - 1).get(x - 1));
          neighbhors.add(starter.get(y - 1).get(x));
          neighbhors.add(starter.get(y - 1).get(x + 1));

          starter.get(y).get(x).neighbors.addAll(neighbhors);
        }
        else if (x == 0) {
          ArrayList<Cell> neighbhors = new ArrayList<Cell>();
          neighbhors.add(starter.get(y - 1).get(x));
          neighbhors.add(starter.get(y + 1).get(x));
          neighbhors.add(starter.get(y - 1).get(x + 1));
          neighbhors.add(starter.get(y).get(x + 1));
          neighbhors.add(starter.get(y + 1).get(x + 1));

          starter.get(y).get(x).neighbors.addAll(neighbhors);
        }
        else if (x == rowLength) {
          ArrayList<Cell> neighbhors = new ArrayList<Cell>();
          neighbhors.add(starter.get(y - 1).get(x));
          neighbhors.add(starter.get(y + 1).get(x));
          neighbhors.add(starter.get(y - 1).get(x - 1));
          neighbhors.add(starter.get(y).get(x - 1));
          neighbhors.add(starter.get(y + 1).get(x - 1));

          starter.get(y).get(x).neighbors.addAll(neighbhors);
        }
        else { // Everything else that is not on the edges
          ArrayList<Cell> neighbhors = new ArrayList<Cell>();
          neighbhors.add(starter.get(y - 1).get(x - 1)); // up, left
          neighbhors.add(starter.get(y - 1).get(x)); // up
          neighbhors.add(starter.get(y - 1).get(x + 1)); // up, right
          neighbhors.add(starter.get(y).get(x + 1)); // right
          neighbhors.add(starter.get(y + 1).get(x + 1)); // right, down
          neighbhors.add(starter.get(y + 1).get(x)); // down
          neighbhors.add(starter.get(y + 1).get(x - 1)); // down, left
          neighbhors.add(starter.get(y).get(x - 1)); // left

          starter.get(y).get(x).neighbors.addAll(neighbhors);
        }
      }
    }
    return starter;
  }

  // Creates a singular row.
  public ArrayList<Cell> makeRow() {
    ArrayList<Cell> row = new ArrayList<Cell>();
    for (int i = 0; i < Minesweeper.GAME_ROW_LEN; i++) {
      row.add(new Cell());
    }
    return row;
  }

  // Draws multiple rows (given 2D array)
  public WorldImage drawMultRows(ArrayList<ArrayList<Cell>> ar) {
    WorldImage rows = new EmptyImage();

    for (ArrayList<Cell> oneRow : ar) {
      rows = new AboveImage(rows, drawRow(oneRow));
    }
    return rows;
  }

  // Draws a singular row
  public WorldImage drawRow(ArrayList<Cell> ar) {
    WorldImage answer = new EmptyImage();
    for (Cell cell : ar) {
      answer = new BesideImage(answer, cell.drawCell());
    }
    return answer;
  }

  // Creates an ArrayList<Posn>, representing coordinates of mines
  ArrayList<Posn> setMines() {
    ArrayList<Posn> answer = new ArrayList<Posn>();
    while (answer.size() < Minesweeper.MINES) {
      int x = 1 + RANDOBJ.nextInt(Minesweeper.GAME_ROW_LEN);
      int y = 1 + RANDOBJ.nextInt(Minesweeper.GAME_COLUMN_LEN);
      Posn newPosn = new Posn(x, y);
      // If the list of coordinates doesn't have the coordinate, add it to the list.
      // Else, keep going until
      // the size is the number of positions we want.
      if (!answer.contains(newPosn)) {
        answer.add(newPosn);
      }
    }
    return answer;
  }
}

// Represents a single cell
class Cell {
  public static final int CELL_LEN = 35;
  static OutlineMode FILL = OutlineMode.SOLID; // not working
  static Color CCOLOR = Color.GRAY;

  ArrayList<Cell> neighbors;
  Boolean hasMine;

  Cell() {
    this.neighbors = new ArrayList<Cell>();
    this.hasMine = false;
  }

  // Produces the number of mines surrounding THIS Cell.
  int countMines() {
    int counter = 0;
    for (Cell cell : this.neighbors) {
      if (cell.hasMine) {
        counter++;
      }
    }
    return counter;
  }

  WorldImage drawCell() {
    if (this.hasMine) {
      return new OverlayImage(new TextImage("*", Color.black),
          // Displays size of neighbhors on each cell
          new OverlayImage(
              new RectangleImage(Cell.CELL_LEN, Cell.CELL_LEN, OutlineMode.OUTLINE, Color.black),
              new RectangleImage(Cell.CELL_LEN, Cell.CELL_LEN, Cell.FILL, Color.GREEN)));
    }
    else {
      return new OverlayImage(new TextImage(((Integer) this.countMines()).toString(), Color.BLACK),
          new OverlayImage(
              new RectangleImage(Cell.CELL_LEN, Cell.CELL_LEN, OutlineMode.OUTLINE, Color.black),
              new RectangleImage(Cell.CELL_LEN, Cell.CELL_LEN, Cell.FILL, Cell.CCOLOR)));
    }
  }
}

// Testing the program
class MinesweeperExamples {
  Minesweeper test;
  Cell aMine;
  Cell aCell1;
  Cell aCell2;
  Cell aCell3;
  Cell initCell;
  ArrayList<Cell> exCells;
  ArrayList<Cell> initRow;
  Random randTest;
  ArrayList<Cell> initCol;

  // Initializing data
  void initData() {
    this.test = new Minesweeper();
    this.aMine = new Cell();
    this.aMine.hasMine = true;
    this.aCell1 = new Cell();
    this.aCell2 = new Cell();
    this.aCell3 = new Cell();
    this.initCell = new Cell();
    this.aCell1.neighbors.add(aMine);
    this.aCell1.neighbors.add(aCell2);
    this.aCell1.neighbors.add(aCell3);
    this.exCells = new ArrayList<Cell>(
        Arrays.asList(this.aMine, this.aCell1, this.aCell2, this.aCell3));
    this.initRow = new ArrayList<Cell>(
        Arrays.asList(initCell, initCell, initCell, initCell, initCell));
    this.randTest = new Random(1);

    this.initCol = new ArrayList<Cell>(
        Arrays.asList(initCell, initCell, initCell, initCell, initCell));
  }

  // Testing the main program (booting up the program)
  void testMain(Tester t) {
    initData();
    test.bigBang(Minesweeper.WIDTH, Minesweeper.HEIGHT, 1);
  }

  // testing drawing one cell
  void testDrawCell(Tester t) {
    initData();
    t.checkExpect(this.aMine.drawCell(),
        new OverlayImage(new TextImage("*", Color.black),
            new OverlayImage(
                new RectangleImage(Cell.CELL_LEN, Cell.CELL_LEN, OutlineMode.OUTLINE, Color.black),
                new RectangleImage(Cell.CELL_LEN, Cell.CELL_LEN, Cell.FILL, Color.GREEN))));
    t.checkExpect(this.aCell1.drawCell(),
        new OverlayImage(
            new TextImage(((Integer) this.aCell1.countMines()).toString(), Color.BLACK),
            new OverlayImage(
                new RectangleImage(Cell.CELL_LEN, Cell.CELL_LEN, OutlineMode.OUTLINE, Color.black),
                new RectangleImage(Cell.CELL_LEN, Cell.CELL_LEN, Cell.FILL, Cell.CCOLOR))));
  }

  // Testing countMines(), which counts the number of mines around one cell.
  void testCountMines(Tester t) {
    initData();
    t.checkExpect(this.aMine.countMines(), 0);
    t.checkExpect(this.aCell2.countMines(), 0);
    t.checkExpect(this.aCell1.countMines(), 1);
  }

  // Testing the method that sets mines on the grid
  void testSetMines(Tester t) {
    initData();

    this.test.setMines();
    ArrayList<Posn> testResult = this.test.mines;
    int numMines = this.test.mines.size();

    t.checkExpect(this.test.mines, testResult);
    t.checkExpect(numMines, 10);
    // Tutor told us that we cannot test because of the randomness
  }

  // Testing drawing one row
  void testDrawRow(Tester t) {
    initData();

    WorldImage result = new EmptyImage();
    result = new BesideImage(result, aMine.drawCell());
    result = new BesideImage(result, aCell1.drawCell());
    result = new BesideImage(result, aCell2.drawCell());
    result = new BesideImage(result, aCell3.drawCell());

    t.checkExpect(this.test.drawRow(this.exCells), result);
    t.checkExpect(this.test.drawRow(new ArrayList<Cell>()), new EmptyImage());
  }

  // Testing drawing multiple rows.
  void testDrawMultRows(Tester t) {
    initData();

    WorldImage rows = new EmptyImage();
    rows = new AboveImage(rows, this.test.drawRow(this.exCells));
    rows = new AboveImage(rows, new EmptyImage());

    t.checkExpect(this.test.drawMultRows(
        new ArrayList<ArrayList<Cell>>(Arrays.asList(exCells, new ArrayList<Cell>()))), rows);
  }

  // Testing the creation of one row
  void testMakeRow(Tester t) {
    initData();

    t.checkExpect(this.test.makeRow(), this.initRow);
  }

  // Testing creating multiple rows, including setting neighbhors to each cell
  void testMakeMultRows(Tester t) {
    initData();

    ArrayList<ArrayList<Cell>> testResult = this.test.makeMultRows(this.initCol);

    t.checkExpect(this.test.makeMultRows(initCol), testResult);
    // Tutor told us we cannot test sufficiently due to randomness
    t.checkExpect(testResult.size() * testResult.get(0).size(), 25);
    t.checkExpect(testResult.get(0).get(0).neighbors.size(), 3); // Top left
    t.checkExpect(testResult.get(0).get(4).neighbors.size(), 3); // Top right
    t.checkExpect(testResult.get(0).get(2).neighbors.size(), 5); // Middle right
    t.checkExpect(testResult.get(4).get(0).neighbors.size(), 3); // Bottom left
    t.checkExpect(testResult.get(4).get(4).neighbors.size(), 3); // Bottom right
    t.checkExpect(testResult.get(4).get(2).neighbors.size(), 5); // Bottom middle
    t.checkExpect(testResult.get(2).get(0).neighbors.size(), 5); // Middle left
    t.checkExpect(testResult.get(0).get(2).neighbors.size(), 5); // Middle right
    t.checkExpect(testResult.get(2).get(2).neighbors.size(), 8); // Middle
  }

  // Testing the creation of one column
  void testMakeColumn(Tester t) {
    initData();

    t.checkExpect(this.test.makeColumn(), this.initCol);
  }

  // Testing the creation of the scene.
  void testMakeScene(Tester t) {
    initData();

    WorldScene scene = new WorldScene(Minesweeper.WIDTH, Minesweeper.HEIGHT);
    scene.placeImageXY(this.test.drawMultRows(this.test.makeMultRows(this.test.makeColumn())), 87,
        87);

    t.checkExpect(this.test.makeScene(), scene);
  }
}
