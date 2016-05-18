package utils;

import play.libs.F;
import java.util.List;

// Option用ユーティリティクラス
public class OptionUtil {

    public static <A> F.Option<A> apply(A value) {
        if(value != null) {
            return F.Option.Some(value);
        } else {
            return F.Option.None();
        }
    }

    public static <String> F.Option<String> applyWithString(String value) {
        if(value != null && !value.equals("")) {
            return F.Option.Some(value);
        } else {
            return F.Option.None();
        }
    }

    public static <T> F.None<T> none() {
        return new F.None<T>();
    }
}
