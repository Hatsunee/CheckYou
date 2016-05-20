package utils;

import static play.libs.F.*;

public class PageUtil {

    public static Integer rightPage(Integer i) {
        Option<Integer> iOps = OptionUtil.apply(i);
        return (iOps.getOrElse(0) - 1 < 0) ? 0 : i - 1;
    }
}