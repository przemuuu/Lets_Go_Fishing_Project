package pl.edu.agh.to2.lets_go_fishing.model;

public class SentenceSplit {

    private int splitWordNumber;
    private int splitFlag;

    public SentenceSplit(int splitWordNumber, int splitFlag) {
        this.splitWordNumber = splitWordNumber;
        this.splitFlag = splitFlag;
    }

    public int getSplitWordNumber() {
        return splitWordNumber;
    }

    public void setSplitWordNumber(int splitWordNumber) {
        this.splitWordNumber = splitWordNumber;
    }

    public int getSplitFlag() {
        return splitFlag;
    }

    public void setSplitFlag(int splitFlag) {
        this.splitFlag = splitFlag;
    }
}
