import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ChessKnight {
    private static final int BOARD_SIZE = 8;
    //Все возможные перемещения коня, константы
    private static final int[] X_MOVES = {2, 1, -1, -2, -2, -1, 1, 2};
    private static final int[] Y_MOVES = {1, 2, 2, 1, -1, -2, -2, -1};
    private int[][] board; //Доску представляю в виде двумерного массива

    public ChessKnight() {
        //Конструктор для доски, если конь еще не был в клетке, то ее значение -100 по умолчанию
        board = new int[BOARD_SIZE][BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = -100;
            }
        }
    }

    private boolean canMove(int x, int y) {
        //Проверяю возможность коня пойти в данную клетку (Клетка должна быть -100 и в пределах доски)
        return (x >= 0 && x < BOARD_SIZE && y >= 0 && y < BOARD_SIZE && board[x][y] == -100);
    }

    private int getNumberOfMoves(int x, int y) {
        //Получаю количество возможных ходов
        int numberOfMoves = 0;
        for (int i = 0; i < X_MOVES.length; i++) {
            int nx = x + X_MOVES[i];
            int ny = y + Y_MOVES[i];
            if (canMove(nx, ny)) {
                numberOfMoves++;
            }
        }
        return numberOfMoves;
    }

    private List<int[]> sortMoves(int x, int y) {
        List<int[]> nextMoves = new ArrayList<>();
        //Список массивов, которые представляют возможный ход коня XY и количество ходов из данной клетки
        for (int i = 0; i < X_MOVES.length; i++) {
            int nextX = x + X_MOVES[i];
            int nextY = y + Y_MOVES[i];
            if (canMove(nextX, nextY)) {
                nextMoves.add(new int[]{nextX, nextY, getNumberOfMoves(nextX, nextY)});
            }
        }
        //Сортировка по количеству возможных ходов(использую лямбд-выражение)
        nextMoves.sort(Comparator.comparingInt(a -> a[2]));
        return nextMoves;
    }

    private boolean solveKnightsTour(int x, int y, int movei ) {
        //Рекурсивный метод, принимает текущие xy коня и номер хода (номер начиная с 1, а не 0)
        if (movei == BOARD_SIZE * BOARD_SIZE) {
            return true;
        }

        List<int[]> nextMoves = sortMoves(x, y);
        for (int[] move : nextMoves) {
            int nextX = move[0];
            int nextY = move[1];
            board[nextX][nextY] = movei;
            if (solveKnightsTour(nextX, nextY, movei + 1)) {
                return true;
            }
            board[nextX][nextY] = -100;
        }

        return false;
    }

    public void start(int startX, int startY) {
        board[startX][startY] = 0;
        if (!solveKnightsTour(startX, startY, 1)) {
            System.out.println("Невозможно");
        } else {
            printBoard();
        }
    }

    private void printBoard() {
        for (int[] row : board) {
            for (int cell : row) {
                System.out.printf("%4d ", cell);
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        ChessKnight chessKnight = new ChessKnight();
        chessKnight.start(0, 0); // Начальная позиция коня
    }
}
