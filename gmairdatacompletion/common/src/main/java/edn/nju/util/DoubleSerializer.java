package edn.nju.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.text.DecimalFormat;

/**
 * @author ：tsl
 * @date ：Created in 2020/3/8 16:58
 * @description：
 */

public class DoubleSerializer extends JsonSerializer<Double> {
    private DecimalFormat df = new DecimalFormat("##.00");

    @Override
    public void serialize(Double aDouble, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (aDouble != null) {
            jsonGenerator.writeString(df.format(aDouble));
        }
    }
}
