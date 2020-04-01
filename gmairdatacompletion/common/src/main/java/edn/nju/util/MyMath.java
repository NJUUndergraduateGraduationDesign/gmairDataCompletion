package edn.nju.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public static Object getMostValue(Object[] store) {
        Map<Object, Integer> valueCount = new HashMap<>();
        for (Object one : store) {
            if (valueCount.containsKey(one)) {
                valueCount.put(one, valueCount.get(one) + 1);
            } else {
                valueCount.put(one, 1);
            }
        }
        Object res = null;
        int mostNum = 0;
        for (Object key : valueCount.keySet()) {
            if (mostNum <= valueCount.get(key)) {
                mostNum = valueCount.get(key);
                res = key;
            }
        }
        return res;
    }

    public static int getAvgWithKNNWeight(int[] store) {
        int n = store.length;
        Double[] weights = getKNNWeights(n);
        double res = 0;
        for (int i = 0; i < n; i++) {
            res += store[i] * weights[i];
        }
        return (int) Math.round(res);
    }

    private static Double[] getKNNWeights(int k) {
        Double[] res = new Double[k];
        int n = k / 2;
        double y;
        for (int x = 1; x <= n; x++) {
            y = (1.0 / (n * (n + 1))) * x;
            res[x - 1] = y;
            res[k - x] = y;
        }
        return res;
    }
}
