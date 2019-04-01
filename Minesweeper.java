import java.util.ArrayList;
import java.util.Arrays;
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
  public static final int WIDTH = Minesweeper.GAME_ROW_LEN * Cell.CELL_LEN;
  public static final int HEIGHT = Minesweeper.GAME_COLUMN_LEN * Cell.CELL_LEN;
  private static final Random RANDOBJ = new Random(1);

  ArrayList<Posn> mines;
  ArrayList<ArrayList<Cell>> board;

  Minesweeper() {
    this.mines = this.setMines();
    this.board = this.makeMultRows(this.makeColumn());
  }

  // Visualizes the world
  public WorldScene makeScene() {
    WorldScene scene = new WorldScene(Minesweeper.WIDTH, Minesweeper.HEIGHT);
    scene.placeImageXY(this.drawMultRows(board), (Minesweeper.GAME_ROW_LEN * Cell.CELL_LEN / 2),
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

  // Handles all right and left button clicking functionality,
  // which entails flagging, revealing, and flooding cells.
  public void onMouseClicked(Posn mousePos, String button) {
    int posX = mousePos.x / Cell.CELL_LEN;
    int posY = mousePos.y / Cell.CELL_LEN;
    if (posX < Minesweeper.GAME_ROW_LEN || posY < Minesweeper.GAME_COLUMN_LEN) {
      Cell cell = board.get(posY).get(posX);
      if (button.equals("LeftButton")) {
        if (cell.isFlagged) {
          cell.isFlagged = false;
        }
        if (cell.hasMine) {
          cell.isClicked = true;
        }
        else if (cell.countMines() > 0) {
          cell.isClicked = true;
        }
        else {
          cell.floodCells(new ArrayList<Cell>(Arrays.asList(cell)));
        }
      }
      else if (button.equals("RightButton")) {
        if (posX < Minesweeper.GAME_ROW_LEN || posY < Minesweeper.GAME_COLUMN_LEN) {
          if (!(cell.isClicked)) {
            cell.isFlagged = !(cell.isFlagged);
          }
        }
      }
    }
  }

  // Ends the world if a mine has been hit, or if all cells
  // non-mine cells have been found
  public WorldEnd worldEnds() {
    if (this.hitMine()) {
      return new WorldEnd(true, this.makeFinalScene("Game over: LOSER!"));
    }
    else if (this.allCellsFound()) {
      return new WorldEnd(true, this.makeFinalScene("Congratulations: WINNER!"));
    }
    else {
      return new WorldEnd(false, this.makeScene());
    }
  }

  // Determines if a mine has been hit.
  boolean hitMine() {
    for (int r = 0; r < this.board.size(); r++) {
      for (int c = 0; c < this.board.get(r).size(); c++) {
        if (this.board.get(r).get(c).hasMine && this.board.get(r).get(c).isClicked) {
          return true;
        }
      }
    }
    return false;
  }

  // Determines if all non-mine cells have been found.
  boolean allCellsFound() {
    for (int r = 0; r < this.board.size(); r++) {
      for (int c = 0; c < this.board.get(r).size(); c++) {
        if (!(this.board.get(r).get(c).hasMine) && !(this.board.get(r).get(c).isClicked)) {
          return false;
        }
      }
    }
    return true;
  }

  // Represents the final scene with the appropriate win/lose message.
  public WorldScene makeFinalScene(String msg) {
    WorldScene scene = new WorldScene(Minesweeper.WIDTH, Minesweeper.HEIGHT);
    this.revealMines();
    WorldImage finalImage = new OverlayImage(
        new TextImage(msg, Minesweeper.GAME_ROW_LEN * 2.2, Color.PINK),
        this.drawMultRows(this.board));
    // Place the final image on the current game screen.
    scene.placeImageXY(finalImage, (Minesweeper.GAME_ROW_LEN * Cell.CELL_LEN / 2),
        (Minesweeper.GAME_COLUMN_LEN * Cell.CELL_LEN / 2));
    return scene;
  }

  // Reveals all the mines by changing each mine's isClicked field
  // to true, which draws each mine as if it has been clicked on.
  void revealMines() {
    for (int r = 0; r < this.board.size(); r++) {
      for (int c = 0; c < this.board.get(r).size(); c++) {
        if (this.board.get(r).get(c).hasMine) {
          this.board.get(r).get(c).isClicked = true;
        }
      }
    }
  }
}

// Represents a single cell
class Cell {
  public static final int CELL_LEN = 35;
  static OutlineMode FILL = OutlineMode.SOLID;
  static Color CCOLOR = Color.GRAY;

  ArrayList<Cell> neighbors;
  Boolean hasMine;
  Boolean isClicked;
  Boolean isFlagged;

  Cell() {
    this.neighbors = new ArrayList<Cell>();
    this.hasMine = false;
    this.isClicked = false;
    this.isFlagged = false;
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

  // Draws each cell.
  WorldImage drawCell() {
    if (this.isFlagged) {
      return new OverlayImage(new EquilateralTriangleImage(10.0, OutlineMode.SOLID, Color.PINK),
          new OverlayImage(
              new RectangleImage(Cell.CELL_LEN, Cell.CELL_LEN, OutlineMode.OUTLINE, Color.black),
              new RectangleImage(Cell.CELL_LEN, Cell.CELL_LEN, Cell.FILL, Cell.CCOLOR)));
    }
    else if (!this.isClicked) {
      return new OverlayImage(
          new RectangleImage(Cell.CELL_LEN, Cell.CELL_LEN, OutlineMode.OUTLINE, Color.black),
          new RectangleImage(Cell.CELL_LEN, Cell.CELL_LEN, Cell.FILL, Cell.CCOLOR));
    }
    else if (this.hasMine) {
      return new OverlayImage(new TextImage("*", Color.black),
          new OverlayImage(
              new RectangleImage(Cell.CELL_LEN, Cell.CELL_LEN, OutlineMode.OUTLINE, Color.black),
              new RectangleImage(Cell.CELL_LEN, Cell.CELL_LEN, Cell.FILL, Color.RED)));
    }
    else {
      return new OverlayImage(new TextImage(((Integer) this.countMines()).toString(), Color.BLACK),
          new OverlayImage(
              new RectangleImage(Cell.CELL_LEN, Cell.CELL_LEN, OutlineMode.OUTLINE, Color.black),
              new RectangleImage(Cell.CELL_LEN, Cell.CELL_LEN, Cell.FILL, Color.GREEN)));
    }
  }

  // Opens a non-mine cell and its neighbors if appropriate.
  void floodCells(ArrayList<Cell> acc) {
    for (Cell c : this.neighbors) {
      if (c.countMines() == 0 && !(acc.contains(c))) {
        acc.add(c);
        c.isClicked = true;
        c.floodCells(acc);
      }
      else {
        c.isClicked = true;
      }
    }
  }
}

// Testing the program. Program is tested
// with a 5x5 with 10 mines game
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
    this.aMine.isClicked = true;
    this.aCell1 = new Cell();
    this.aCell2 = new Cell();
    this.aCell2.isFlagged = true;
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

  // Testing the main program
//  void testMain(Tester t) {
//    initData();
//    test.bigBang(Minesweeper.WIDTH, Minesweeper.HEIGHT, .003);
//  }

  // testing drawing one cell
  void testDrawCell(Tester t) {
    initData();
    // A mine that has been clicked
    t.checkExpect(this.aMine.drawCell(),
        new OverlayImage(new TextImage("*", Color.black),
            new OverlayImage(
                new RectangleImage(Cell.CELL_LEN, Cell.CELL_LEN, OutlineMode.OUTLINE, Color.black),
                new RectangleImage(Cell.CELL_LEN, Cell.CELL_LEN, Cell.FILL, Color.RED))));
    // A cell that hasn't been clicked.
    t.checkExpect(this.aCell1.drawCell(),
        new OverlayImage(
            new RectangleImage(Cell.CELL_LEN, Cell.CELL_LEN, OutlineMode.OUTLINE, Color.black),
            new RectangleImage(Cell.CELL_LEN, Cell.CELL_LEN, Cell.FILL, Cell.CCOLOR)));

    // A cell that has been flagged
    t.checkExpect(this.aCell2.drawCell(),
        new OverlayImage(new EquilateralTriangleImage(10.0, OutlineMode.SOLID, Color.PINK),
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

  // Testing creating multiple rows, including setting neighbors to each cell
  void testMakeMultRows(Tester t) {
    initData();

    ArrayList<ArrayList<Cell>> testResult = this.test.makeMultRows(this.initCol);

    t.checkExpect(this.test.makeMultRows(initCol), testResult);
    // Tutor told us we cannot test sufficiently due to randomness
    t.checkExpect(testResult.get(0).size(), 5);
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

  // Testing onMouseClicked()
  void testOnMouseClick(Tester t) {
    initData();
    this.test.board.get(0).get(0).isClicked = false;
    this.test.board.get(0).get(0).isFlagged = false;
    this.test.board.get(1).get(0).isClicked = false;
    this.test.board.get(1).get(0).isFlagged = false;
    t.checkExpect(this.test.board.get(0).get(0).isClicked, false);
    this.test.onMouseClicked(new Posn(5, 5), "LeftButton"); // Top left most cell
    t.checkExpect(this.test.board.get(0).get(0).isClicked, true);

    t.checkExpect(this.test.board.get(1).get(0).isFlagged, false);
    this.test.onMouseClicked(new Posn(5, 40), "RightButton"); // One cell down.
    t.checkExpect(this.test.board.get(1).get(0).isFlagged, true);
  }

  // Testing worldEnds()
  void testingWorldEnds(Tester t) {
    initData();
    t.checkExpect(this.test.worldEnds(), new WorldEnd(false, this.test.makeScene()));
    this.test.board.get(0).get(4).hasMine = true;
    t.checkExpect(this.test.board.get(0).get(4).hasMine, true);
    this.test.onMouseClicked(new Posn(35 * 4 + 1, 5), "LeftButton");
    t.checkExpect(this.test.hitMine(), true);
    t.checkExpect(this.test.worldEnds(),
        new WorldEnd(true, this.test.makeFinalScene("Game over: LOSER!")));
  }

  // Testing hitMine()
  void testingHitMine(Tester t) {
    initData();
    t.checkExpect(this.test.hitMine(), false);
    this.test.board.get(0).get(0).hasMine = false;
    t.checkExpect(this.test.board.get(0).get(0).hasMine, false);
    this.test.board.get(0).get(4).hasMine = true;
    t.checkExpect(this.test.board.get(0).get(4).hasMine, true);
    this.test.onMouseClicked(new Posn(35 * 4 + 1, 5), "LeftButton");
    t.checkExpect(this.test.hitMine(), true);
  }

  // Testing allCellsFound()
  void testAllCellsFound(Tester t) {
    initData();
    t.checkExpect(this.test.allCellsFound(), false);
    for (int i = 0; i < this.test.board.size(); i++) {
      for (int j = 0; j < this.test.board.get(i).size(); j++) {
        if (!this.test.board.get(i).get(j).hasMine) {
          this.test.board.get(i).get(j).isClicked = true;
        }
      }
    }
    t.checkExpect(this.test.allCellsFound(), true);
  }

  // Testing makeFinalScene()
  void testMakeFinalScene(Tester t) {
    initData();
    this.test.revealMines();

    WorldScene testScene = new WorldScene(Minesweeper.WIDTH, Minesweeper.HEIGHT);
    WorldImage testFinalImage = new OverlayImage(
        new TextImage("You Lose!", Minesweeper.GAME_ROW_LEN * 2.2, Color.PINK),
        this.test.drawMultRows(this.test.board));
    testScene.placeImageXY(testFinalImage, (Minesweeper.GAME_ROW_LEN * Cell.CELL_LEN / 2),
        (Minesweeper.GAME_COLUMN_LEN * Cell.CELL_LEN / 2));

    t.checkExpect(this.test.makeFinalScene("You Lose!"), testScene);

    initData();
    this.test.revealMines();

    WorldScene testScene1 = new WorldScene(Minesweeper.WIDTH, Minesweeper.HEIGHT);
    WorldImage testFinalImage1 = new OverlayImage(
        new TextImage("You Win!", Minesweeper.GAME_ROW_LEN * 2.2, Color.PINK),
        this.test.drawMultRows(this.test.board));
    testScene1.placeImageXY(testFinalImage1, (Minesweeper.GAME_ROW_LEN * Cell.CELL_LEN / 2),
        (Minesweeper.GAME_COLUMN_LEN * Cell.CELL_LEN / 2));
    t.checkExpect(this.test.makeFinalScene("You Win!"), testScene1);
  }

  // Testing revealMine().
  void testRevealMine(Tester t) {
    initData();
    // Cell forced to have a mine.
    this.test.board.get(0).get(0).hasMine = true;
    // Cell forced to be unclicked initially.
    this.test.board.get(0).get(0).isClicked = false;
    // Checking above actions.
    t.checkExpect(this.test.board.get(0).get(0).hasMine, true);
    t.checkExpect(this.test.board.get(0).get(0).isClicked, false);
    // Calling revealMines()
    this.test.revealMines();
    // Testing if revealMines() changed the cell which was previously
    // unclicked to now being clicked.
    t.checkExpect(this.test.board.get(0).get(0).isClicked, true);
  }
}
