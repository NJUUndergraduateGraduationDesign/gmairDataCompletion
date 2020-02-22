package edu.nju.gmairdatacompletion.dao;

import edu.nju.gmairdatacompletion.util.ResultData;

public interface Co2HourlyDao {

    ResultData insertBatch();

    ResultData query();

}
