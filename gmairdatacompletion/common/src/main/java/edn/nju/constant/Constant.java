package edn.nju.constant;

import java.util.Date;

/**
 * @author ：tsl
 * @date ：Created in 2020/2/23 17:56
 * @description：constant class
 */

public class Constant {
    public static class Admin {
        public static final String DEFAULT_ADMIN_USERNAME = "admin";
    }

    public static class User {
        public static final Date LATEST_USER_BIND_TIME = new Date("2020/02/21 07:34:21");
    }

    public static class Completion {
        public static final double PARTIAL_INTERVAL = 3600000.0;
        public static final double PARTIAL_BIAS = 1800000.0;
        public static final double V2V3_INTERVAL = 30000.0;
        public static final double V2V3_BIAS = 10000.0;
    }
}
