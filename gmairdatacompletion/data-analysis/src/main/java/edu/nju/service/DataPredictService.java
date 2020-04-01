package edu.nju.service;

import java.util.List;

/**
 * @author: Bright Chan
 * @date: 2020/3/7 14:59
 * @description: TODO
 */
public interface DataPredictService {

    //简单平移
    double previousPredict(List<Double> data);

    //简单平均
    double averagePredict(List<Double> data);

    //加权平均
    double weightedAveragePredict(List<Double> data);

    /**
     * 单指数平滑法
     *
     * @param data
     * @return 下期预测值
     */
    double singleExponentialSmoothingPredict(List<Double> data);

    /**
     * 单指数平滑法
     *
     * @param data
     * @param alpha 平滑参数
     * @return 下期预测值
     */
    double singleExponentialSmoothingPredict(List<Double> data, double alpha);

    /**
     * 双指数平滑法(霍尔特线性趋势)
     *
     * @param data
     * @return 下期预测值
     */
    double holtLinearTrendPredict(List<Double> data);

    /**
     * 双指数平滑法(霍尔特线性趋势)
     *
     * @param data
     * @param alpha 平滑参数
     * @param beta  趋势参数
     * @return  下期预测值
     */
    double holtLinearTrendPredict(List<Double> data, double alpha, double beta);

    /**
     * 三指数平滑法
     *
     * @param data
     * @return 下期预测值
     */
    double holtWinterPredict(List<Double> data);

    /**
     * 三指数平滑法
     *
     * @param data
     * @param alpha 平滑参数
     * @param beta  趋势参数
     * @param gamma 周期参数
     * @param k 周期
     * @return 下期预测值
     */
    double holtWinterPredict(List<Double> data, double alpha, double beta, double gamma, int k);
}
