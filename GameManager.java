import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;

public class GameManager {
    private final int FINAL_TURN_COUNT = 10; // 最終ターンの数
    private Random random = new Random();
    private Scanner scanner = new Scanner(System.in);
    private static List<Monster> capturedMonsters = new ArrayList<>(); // 捕獲したモンスター一覧
    private List<Monster> monsters = new ArrayList<>(); // モンスター一覧
    private List<CaptureBall> captureBalls = new ArrayList<>(); // 捕獲玉一覧
    private int selectedInt; // プレイヤーが選択した数字
    private int turnCount = 0;
    private int runNumber = 4; // 逃げる選択肢の番号
    
    public GameManager() {
        monsters.add(new Monster("ザコモン", 30, 20, 20, 30, 72));
        monsters.add(new Monster("フツモン", 50, 20, 30, 30, 50));
        monsters.add(new Monster("ツヨモン", 100, 50, 30, 25, 28));
        monsters.add(new Monster("ボスモン", 100, 50, 50, 10, 25));
        monsters.add(new Monster("レアモン", 150, 100, 100, 5, 14));
        captureBalls.add(new CaptureBall("ノーマル捕獲玉", 0, 6,1));
        captureBalls.add(new CaptureBall("スーパー捕獲玉", 20, 3,2));
        captureBalls.add(new CaptureBall("ミラクル捕獲玉", 50, 1,3)); 
    }

    public void start() {
        System.out.println("Game start!");
        while(turnCount < FINAL_TURN_COUNT){
            System.out.println("====================================");
            showturnCount();
            turnProcess();
            turnCount++;
        }
        showCapturedMonsters();
        showGetPoint();
    }

    /*
     * 1ターン毎の処理
     */
    private void turnProcess() {
        Monster choicedMonster = choiceMonster();
        choicedMonster.appearMessage();
        battle(choicedMonster);
    }

    /**
     * 捕獲に成功するか逃げるを選択するまで繰り返す
     * @param choicedMonster
     */
    private void battle(Monster choicedMonster) {
        CaptureBall selectedBall;
        do{
            selectMoveForInput();
            selectedBall = convertIntToBall(selectedInt);
            if(selectedBall == null){
                run(choicedMonster);
                return;
            }
            selectedBall.use(choicedMonster);
            printBlankLine();
        }while(!selectedBall.getIsCaptured());
    }

    /* 
     * 標準入力で選択肢を選択する
     * 正しい入力を受け付けるまで繰り返す
     */
    private void selectMoveForInput(){
        selectedInt = -1;
        while (!isInputValid(selectedInt)) {
            try {
                System.out.println("選択肢から数字を入力してください。");
                showChoices();
                selectedInt = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("無効な入力です。");
                scanner.nextLine();
            } catch (NoSuchElementException e) {
                System.out.println("入力が中断されました。");
                break;
            }
        }
    }

    /*
     * 選択肢を表示する
     */
    private void showChoices(){
        for (CaptureBall ball : captureBalls) {
            System.out.println(ball.getSelectNumber() + " : " + ball.getName() + "  残り" + ball.getCount() + "個");
        }
        System.out.println(runNumber + " : 逃がす");
    }

    /*
     * 発生率に応じたモンスターを選択する
     * @return 選択されたモンスター
     */
    private Monster choiceMonster() {
        int totalRate = 0;
        //すべてのモンスターの発生率の合計を計算する
        for (Monster monster : monsters) {
            totalRate += monster.getEncountRate();
        }
        int randomRate = random.nextInt(totalRate);
        int currentRate = 0;
        //ランダムで選択された発生率の合計が現在の合計発生率より小さい場合、そのモンスターを返す
        for (Monster monster : monsters) {
            currentRate += monster.getEncountRate();
            if (randomRate < currentRate) {
                return monster;
            }
        }
        return null;
    }

    /*
     * 捕獲したモンスターを捕獲リストに追加する
     * @param monster 捕獲したモンスター
     */
    public static void  addCapturedMonster(Monster monster) {
        capturedMonsters.add(monster);
    }

    /*  
     * ユーザが入力した数字に対応する捕獲玉のインスタンスを返す
     * @param selectedInt ユーザが入力した数字
     * @return 捕獲玉のインスタンス
     * @return null 入力された数字に対応する捕獲玉がない場合
     */
    private CaptureBall convertIntToBall(int selectedInt) {
        for (CaptureBall ball : captureBalls) {
            if(ball.getSelectNumber() == selectedInt){
                return ball;
            }
        }
        return null;
    }

    /*
     * 入力された数字が正しいかどうかを判定する
     * @param selectedInt ユーザが入力した数字
     * @return true: 正しい / false: 正しくない
     */
    private boolean isInputValid(int selectedInt) {
        for (CaptureBall ball : captureBalls) {
            if(ball.getSelectNumber() == selectedInt){
                return true;
            }
        }
        if(selectedInt == runNumber){
            return true;
        }
        return false;
    }

    /*
     * モンスターを逃がす
     */
    private void run(Monster choicedMonster) {
        System.out.println(choicedMonster.getName() +  "を逃がした");
    }

    private void printBlankLine(){
        System.out.println("");
    }

    /*  
     * 捕獲したモンスターを表示する
     */
    private void showCapturedMonsters() {
        printBlankLine();
        System.out.println("捕獲したモンスター一覧");
        for (Monster monster : capturedMonsters) {
            System.out.println(monster.getName());
        }
    }

    /*
     * 獲得ポイントを表示する
     */
    private void showGetPoint() {
        System.out.println("獲得ポイント : ");
        System.out.println(calcGetPoint());
    }

    /*
     * 獲得ポイントを計算する
     * @return 獲得ポイント
     */
    private int calcGetPoint() {
        int totalPoint = 0;
        for (Monster monster : capturedMonsters) {
            totalPoint += monster.calcCaputuredPoint();
        }
        return totalPoint;
    }

    /*  
     * ターン数を表示する
     */
    private void showturnCount() {
        System.out.println(turnCount + "ターン目");
    }
}