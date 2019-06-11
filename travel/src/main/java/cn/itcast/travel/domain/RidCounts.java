package cn.itcast.travel.domain;

public class RidCounts {
    private int rid;
    private int counts;

    @Override
    public String toString() {
        return "RidCounts{" +
                "rid=" + rid +
                ", counts=" + counts +
                '}';
    }

    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    public int getCounts() {
        return counts;
    }

    public void setCounts(int counts) {
        this.counts = counts;
    }
}
