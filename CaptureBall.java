public class CaptureBall {
    private String name; // 名称
    private int correctValue; // 捕獲率の補正値
    private int count; // 所持数
    private int selectNumber; // 選択番号
    private boolean isCaptured; // 捕獲できたかどうか

    CaptureBall(String name, int correctValue, int count, int selectNumber) {
        this.name = name;
        this.correctValue = correctValue;
        this.count = count;
        this.selectNumber = selectNumber;
        isCaptured = false;
    }

    /**
     * @return 名称
     */
    public String getName() {
        return name;
    }

    /**
     * @return 捕獲率の補正値
     */
    public int getCorrectValue() {
        return correctValue;
    }

    /**
     * @return 所持数
     */
    public int getCount() {
        return count;
    }

    /**
     * @return 選択番号
     */
    public int getSelectNumber() {
        return selectNumber;
    }

    /**
     * @return 捕獲できたかどうか  true: 捕獲成功 / false: 捕獲失敗
     */
    public boolean getIsCaptured() {
        return isCaptured;
    }

    /**
     * @param value 捕獲できたかどうか  true: 捕獲成功 / false: 捕獲失敗
     */
    public void setIsCaptured(boolean value) {
        isCaptured = value;
    }

    /**
    * 捕獲玉を使用する
    */
    public void use(Monster monster) {
        if(count == 0){
            System.out.println(name + "はもうない！");
            isCaptured = false;
            return;
        }
        if (monster.canCaputured(correctValue)) {
            System.out.println(monster.getName() + "を捕獲した！");
            GameManager.addCapturedMonster(monster);
            isCaptured = true;
        } else {
            System.out.println(monster.getName() + "を捕獲できなかった！");
            isCaptured = false;
        }
        count--;
    }
}