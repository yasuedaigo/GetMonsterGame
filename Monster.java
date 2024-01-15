import java.util.Random;

public class Monster {
    Random random = new Random();
    private final int MAX_RANDOM_RANGE = 100; // 捕獲判定で使う乱数の最大値
    private String name; // 名前
    private int hpValue; // HP
    private int powerValue; // 攻撃
    private int defenseValue; // 防御
    private int encountRate; // 発生率
    private int captureRate; // 捕獲率

    public Monster(String name, int hpValue, int powerValue, int defenseValue, int encountRate, int captureRate) {
        this.name = name;
        this.hpValue = hpValue;
        this.powerValue = powerValue;
        this.defenseValue = defenseValue;
        this.encountRate = encountRate;
        this.captureRate = captureRate;
    }

    /*
     * @return 名前
     */
    public String getName() {
        return name;
    }

    /*
     * @return HP
     */
    public int getHpValue() {
        return hpValue;
    }

    /*
     * @return 攻撃力
     */
    public int getPowerValue() {
        return powerValue;
    }

    /*
     * @return 防御力
     */
    public int getDefenceValue() {
        return defenseValue;
    }

    /*
     * @return 発生率
     */
    public int getEncountRate() {
        return encountRate;
    }

    /*
     * @return 捕獲率
     */
    public int getCaptureRate() {
        return captureRate;
    }

    /**
    * 捕獲ポイントを取得（式：(HP+攻撃+防御)*10）
    * @return 捕獲ポイント
     */
    public int calcCaputuredPoint() {
        return (hpValue + powerValue + defenseValue) * 10;
    }
    /**
    * 捕獲できたかどうかの判定を行う
    * @param correctValue 捕獲率の補正値
    * @return true: 捕獲成功 / false: 捕獲失敗
    */
    public boolean canCaputured(int correctValue) {
        return captureRate + correctValue > random.nextInt(MAX_RANDOM_RANGE);
    }

    /**
     * モンスターが現れたときのメッセージを表示する
     */
    public void appearMessage(){
        System.out.println(name + "が現れた！");
    }
}