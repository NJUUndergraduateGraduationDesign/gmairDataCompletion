package edn.nju.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Bright Chan
 * @date: 2020/3/5 17:50
 * @description: TODO
 */
public class MyMath {

    public static int getAvgWithWeight(List<Double> store) {
        int n = store.size();
        List<Double> weights = getWeights(n);
        double res = 0;
        for (int i = 0; i < n; i++) {
            res += store.get(i) * weights.get(i);
        }
        return (int) Math.round(res);
    }

    public static double getDoubleAvgWithWeight(List<Double> store) {
        int n = store.size();
        List<Double> weights = getWeights(n);
        double res = 0;
        for (int i = 0; i < n; i++) {
            res += store.get(i) * weights.get(i);
        }
        return res;
    }

    /**
     * 得到n个数的权重，权重加和为1
     * @param n 权重个数
     * @return 权重列表
     */
    private static List<Double> getWeights(int n) {
        List<Double> res = new ArrayList<>();
        double y;
        for (int x = 1; x <= n; x++) {
            y = (2.0 / (n * (n + 1))) * x;
            res.add(y);
        }
        return res;
    }
}
