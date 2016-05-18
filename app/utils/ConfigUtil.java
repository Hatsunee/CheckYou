package utils;

import play.Play;
import java.util.List;
import static play.libs.F.*;

// Configユーティリティクラス
public class ConfigUtil {

  // Configから指定した文字列で値取得
    public static Option<String> get(String key) {
        return OptionUtil.apply(Play.application().configuration().getString(key));
    }

  // Configから指定した文字列（リスト）で値取得
    public static Option<List<String>> getByList(String key) {
        return OptionUtil.apply(Play.application().configuration().getStringList(key));
    }

}