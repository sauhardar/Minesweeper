import java.util.ArrayList;
import java.util.Random;
import tester.*;
import javalib.impworld.*;
import java.awt.Color;
import javalib.worldimages.*;

// The main game class

class Minesweeper extends World {
  public static final int GAME_COLUMN_LEN = 5;
  public static final int GAME_ROW_LEN = 5;
  public static final int MINES = 10;
  // cannot be more than GAME_COLUMN_LEN*GAME_ROW_LEN -- how can we limit that?
  static final int WIDTH = 500;
  static final int HEIGHT = 500;

  ArrayList<Posn> mines;
  Random randObj;

  Minesweeper() {
    this.mines = this.setMines();
    this.randObj = new Random(1); //this isn't being used.
  }

  public WorldScene makeScene() {
    WorldScene scene = new WorldScene(Minesweeper.WIDTH, Minesweeper.HEIGHT);
    scene.placeImageXY(this.drawMultRows(makeMultRows(makeColumn())),
        (Minesweeper.GAME_ROW_LEN * Cell.CELL_LEN / 2),
        (Minesweeper.GAME_COLUMN_LEN * Cell.CELL_LEN / 2));
    return scene;
  }

  // Creates the singular column at the left side with each
  // element being an ArrayList<Cell>, representing a singular row.
  public ArrayList<Cell> makeColumn() {
    // Creates an ArrayList with the number of elements
    // (columns) we want it to have.
    ArrayList<Cell> row = new ArrayList<Cell>(Minesweeper.GAME_COLUMN_LEN);
    for (int i = 0; i < Minesweeper.GAME_COLUMN_LEN; i++) {
      row.add(new Cell());
    }
    return row;
  }

  // Given one ArrayList representing the left most column,
  // creates an ArrayList for each element.
  public ArrayList<ArrayList<Cell>> makeMultRows(ArrayList<Cell> leftMostCol) { // This might be
                                                                                // overkill. How to
                                                                                // improve?
    int rowLength = Minesweeper.GAME_ROW_LEN - 1;
    int colLength = Minesweeper.GAME_COLUMN_LEN - 1;

    ArrayList<ArrayList<Cell>> starter = new ArrayList<ArrayList<Cell>>();
    // Adds a row (ArrayList<Cell>)to each element in the column leftMostCol
    for (Cell cell : leftMostCol) {
      starter.add(makeRow());
    }

    for (int y = 0; y < starter.size(); y++) {
      for (int x = 0; x < starter.get(y).size(); x++) {
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
          // find its neighbhors:
          neighbhors.add(starter.get(0).get(rowLength - 1)); // y value first, then x value
          neighbhors.add(starter.get(1).get(rowLength)); // starter.get(y-value).get(x-value)
          neighbhors.add(starter.get(1).get(rowLength - 1));

          // Add all the neighbhors here:
          starter.get(y).get(x).neighbors.addAll(neighbhors);
        }
        else if (y == colLength && x == 0) {
          ArrayList<Cell> neighbhors = new ArrayList<Cell>();
          // find its neighbhors:
          neighbhors.add(starter.get(colLength - 1).get(0)); // y value first, then x value
          neighbhors.add(starter.get(colLength - 1).get(1)); // starter.get(y-value).get(x-value)
          neighbhors.add(starter.get(colLength).get(1));

          // Add all the neighbhors here:
          starter.get(y).get(x).neighbors.addAll(neighbhors);
        }
        else if (y == colLength && x == rowLength) {
          ArrayList<Cell> neighbhors = new ArrayList<Cell>();
          // find its neighbhors:
          neighbhors.add(starter.get(colLength - 1).get(rowLength)); // y value first, then x value
          neighbhors.add(starter.get(colLength - 1).get(rowLength - 1)); // starter.get(y-value).get(x-value)
          neighbhors.add(starter.get(colLength).get(rowLength - 1));

          // Add all the neighbhors here:
          starter.get(y).get(x).neighbors.addAll(neighbhors);
        }
        else if (y == 0) {
          ArrayList<Cell> neighbhors = new ArrayList<Cell>();
          // find its neighbhors:
          // neighbhors.add(starter.get(...).get(...)); // y value first, then x value

          neighbhors.add(starter.get(y).get(x - 1));
          neighbhors.add(starter.get(y).get(x + 1));
          neighbhors.add(starter.get(y + 1).get(x - 1));
          neighbhors.add(starter.get(y + 1).get(x));
          neighbhors.add(starter.get(y + 1).get(x + 1));

          // Add all the neighbhors here:
          starter.get(y).get(x).neighbors.addAll(neighbhors);
        }
        else if (y == colLength) {
          ArrayList<Cell> neighbhors = new ArrayList<Cell>();
          // find its neighbhors:
          // neighbhors.add(starter.get(...).get(...)); // y value first, then x value

          neighbhors.add(starter.get(y).get(x - 1));
          neighbhors.add(starter.get(y).get(x + 1));
          neighbhors.add(starter.get(y - 1).get(x - 1));
          neighbhors.add(starter.get(y - 1).get(x));
          neighbhors.add(starter.get(y - 1).get(x + 1));

          // Add all the neighbhors here:
          starter.get(y).get(x).neighbors.addAll(neighbhors);
        }
        else if (x == 0) {
          ArrayList<Cell> neighbhors = new ArrayList<Cell>();
          // find its neighbhors:
          // neighbhors.add(starter.get(...).get(...)); // y value first, then x value

          neighbhors.add(starter.get(y - 1).get(x));
          neighbhors.add(starter.get(y + 1).get(x));
          neighbhors.add(starter.get(y - 1).get(x + 1));
          neighbhors.add(starter.get(y).get(x + 1));
          neighbhors.add(starter.get(y + 1).get(x + 1));

          // Add all the neighbhors here:
          starter.get(y).get(x).neighbors.addAll(neighbhors);
        }
        else if (x == rowLength) {
          ArrayList<Cell> neighbhors = new ArrayList<Cell>();
          // find its neighbhors:
          // neighbhors.add(starter.get(...).get(...)); // y value first, then x value

          neighbhors.add(starter.get(y - 1).get(x));
          neighbhors.add(starter.get(y + 1).get(x));
          neighbhors.add(starter.get(y - 1).get(x - 1));
          neighbhors.add(starter.get(y).get(x - 1));
          neighbhors.add(starter.get(y + 1).get(x - 1));

          // Add all the neighbhors here:
          starter.get(y).get(x).neighbors.addAll(neighbhors);
        }
        else { // Everything else that is not on the edges
          ArrayList<Cell> neighbhors = new ArrayList<Cell>();
          // find its neighbhors:
          // neighbhors.add(starter.get(...).get(...)); // y value first, then x value

          neighbhors.add(starter.get(y - 1).get(x - 1)); // up, left
          neighbhors.add(starter.get(y - 1).get(x)); // up
          neighbhors.add(starter.get(y - 1).get(x + 1)); // up, right
          neighbhors.add(starter.get(y).get(x + 1)); // right
          neighbhors.add(starter.get(y + 1).get(x + 1)); // right, down
          neighbhors.add(starter.get(y + 1).get(x)); // down
          neighbhors.add(starter.get(y + 1).get(x - 1)); // down, left
          neighbhors.add(starter.get(y).get(x - 1)); // left

          // Add all the neighbhors here:
          starter.get(y).get(x).neighbors.addAll(neighbhors);
        }
      }
    }

    for (int y = 0; y < starter.size(); y++) {
      for (int x = 0; x < starter.get(y).size(); x++) {
        if (this.mines.contains(new Posn(x + 1, y + 1))) {
          starter.get(y).get(x).hasMine = true;
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

  // Draws multiple rows
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

  // Creating ArrayList<Posn> representing coordinates of mines
  ArrayList<Posn> setMines() {
    ArrayList<Posn> answer = new ArrayList<Posn>();
    while (answer.size() < Minesweeper.MINES) {
      int x = 1 + new Random().nextInt(Minesweeper.GAME_ROW_LEN); 
      // THIS USES A NEW RANDOM() INSTEAD OF FIELD IN CONSTRUCTOR
      int y = 1 + new Random().nextInt(Minesweeper.GAME_ROW_LEN);
      Posn position = new Posn(x, y);
      // If the list of coordinates doesn't have the coordinate, add it to the list.
      // Else, keep going until
      // the size is the number of positions we want.
      if (!answer.contains(position)) {
        answer.add(position);
      }
    }
    return answer;
  }
}

// Represents a single cell
class Cell {
  public static final int CELL_LEN = 35;
  // static OutlineMode FILL = OutlineMode.SOLID; not working
  static Color CCOLOR = Color.GRAY;

  ArrayList<Cell> neighbors;
  Boolean hasMine;

  Cell() {
    this.neighbors = new ArrayList<Cell>();
    this.hasMine = false;
  }

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
              new RectangleImage(Cell.CELL_LEN, Cell.CELL_LEN, OutlineMode.SOLID, Color.GREEN)));
    }
    else {
      return new OverlayImage(new TextImage(((Integer) this.countMines()).toString(), Color.BLACK),
          new OverlayImage(
              new RectangleImage(Cell.CELL_LEN, Cell.CELL_LEN, OutlineMode.OUTLINE, Color.black),
              new RectangleImage(Cell.CELL_LEN, Cell.CELL_LEN, OutlineMode.SOLID, Cell.CCOLOR)));
    }
  }
}

class MinesweeperExamples {

  void testMain(Tester t) {
    Minesweeper test = new Minesweeper();
    test.bigBang(Minesweeper.WIDTH, Minesweeper.HEIGHT, 1);
    for (Posn p : test.mines) {
      System.out
          .println("(" + ((Integer) p.x).toString() + ", " + ((Integer) p.y).toString() + ")");
      // This is just to show the mine locations.
    }
  }
}