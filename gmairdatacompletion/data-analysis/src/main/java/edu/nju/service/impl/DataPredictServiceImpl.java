package edu.nju.service.impl;

import com.google.common.collect.Lists;
import edn.nju.util.MyMath;
import edu.nju.service.DataPredictService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author: Bright Chan
 * @date: 2020/3/7 15:07
 * @description: TODO
 */

@Service
public class DataPredictServiceImpl implements DataPredictService {
    public static void main(String[] args) {
        /*
        DataPredictServiceImpl service = new DataPredictServiceImpl();
        List<Double> data=Lists.newArrayList(27.022727272727273,27.41666666666667,27.0,27.0,25.66666666666667,
                24.0,22.79166666666667,22.02083333333333,21.45833333333333,21.14583333333333,21.47916666666667,
                21.416666666666668,21.02083333333333,20.479166666666668,19.104166666666668,18.70833333333333,
                18.52083333333333,18.08333333333333,18.041666666666668,18.0,18.33333333333333,18.916666666666668,
                18.89583333333333,19.0,18.854166666666668,17.454545454545453);
        System.out.println(service.singleExponentialSmoothingPredict(data,0));
        System.out.println(service.holtLinearTrendPredict(data));
        */
    }

    @Override
    public double previousPredict(List<Double> data) {
        if (CollectionUtils.isEmpty(data)) {
            return 0;
        }
        return data.get(data.size() - 1);
    }

    @Override
    public double averagePredict(List<Double> data) {
        if (CollectionUtils.isEmpty(data)) {
            return 0;
        }
        return data.stream().mapToDouble(Double::doubleValue).average().orElse(0D);
    }

    @Override
    public double weightedAveragePredict(List<Double> data) {
        if (CollectionUtils.isEmpty(data)) {
            return 0;
        }
        return MyMath.getDoubleAvgWithWeight(data);
    }

    @Override
    public double singleExponentialSmoothingPredict(List<Double> data) {
        return singleExponentialSmoothingPredict(data, 0.7);
    }

    @Override
    public double singleExponentialSmoothingPredict(List<Double> data, double alpha) {
        if (CollectionUtils.isEmpty(data)) {
            return 0;
        }
        double smoothingValue = data.get(0);
        for (int i = 1; i <= data.size(); i++) {
            smoothingValue = alpha * data.get(i - 1) + (1 - alpha) * smoothingValue;
        }
        return smoothingValue;
    }

    @Override
    public double holtLinearTrendPredict(List<Double> data) {
        return holtLinearTrendPredict(data, 0.7, 0.7);
    }

    @Override
    public double holtLinearTrendPredict(List<Double> data, double alpha, double beta) {
        if (CollectionUtils.isEmpty(data)) {
            return 0;
        } else if (data.size() == 1) {
            return data.get(0);
        }
        double smoothingValue = data.get(0);
        double trendValue = data.get(1) - data.get(0);
        //System.out.println("i=" + 0 + ";S=" + smoothingValue + ";T=" + trendValue);
        for (int i = 1; i <= data.size(); i++) {
            double lastSmoothingValue = smoothingValue;
            smoothingValue = alpha * data.get(i - 1) + (1 - alpha) * (smoothingValue + trendValue);
            trendValue = beta * (smoothingValue - lastSmoothingValue) + (1 - beta) * trendValue;
            //System.out.println("i=" + i + ";S=" + smoothingValue + ";T=" + trendValue);
        }
        return smoothingValue + trendValue;
    }


    @Override
    public double holtWinterPredict(List<Double> data) {
        return holtWinterPredict(data, 0.7, 0.7, 0.7, 365);
    }

    @Override
    public double holtWinterPredict(List<Double> data, double alpha, double beta, double gamma, int k) {
        if (CollectionUtils.isEmpty(data)) {
            return 0;
        } else if (data.size() == 1) {
            return data.get(0);
        }
        int len = data.size();
        double[] smoothingValue = new double[len + 1];
        double[] trendValue = new double[len + 1];
        double[] periodValue = new double[len + 1];
        smoothingValue[0] = data.get(0);
        trendValue[0] = data.get(1) - data.get(0);
        periodValue[0] = 0;
        for (int i = 1; i <= len; i++) {
            if (i - k >= 0) {
                smoothingValue[i] = alpha * (data.get(i - 1) - periodValue[i - k]) + (1 - alpha) * (smoothingValue[i - 1] + trendValue[i - 1]);
                trendValue[i] = beta * (smoothingValue[i] - smoothingValue[i - 1]) + (1 - beta) * trendValue[i - 1];
                periodValue[i] = gamma * (data.get(i - 1) - smoothingValue[i - 1]) + (1 - gamma) * periodValue[i - k];
            } else {
                smoothingValue[i] = alpha * (data.get(i - 1)) + (1 - alpha) * (smoothingValue[i - 1] + trendValue[i - 1]);
                trendValue[i] = beta * (smoothingValue[i] - smoothingValue[i - 1]) + (1 - beta) * trendValue[i - 1];
                periodValue[i] = gamma * (data.get(i - 1) - smoothingValue[i - 1]);
            }
        }
        /*
        for (int i = 0; i < len + 1; i++) {
            System.out.println("i=" + i + ";S=" + smoothingValue[i] + ";T=" + trendValue[i] + ";P=" + periodValue[i]);
        }
        */
        return smoothingValue[len] + trendValue[len] + periodValue[len - k];
    }
}
