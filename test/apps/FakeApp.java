package apps;

import com.avaje.ebean.Ebean;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import play.test.FakeApplication;
import java.io.IOException;

import static play.test.Helpers.*;

public class FakeApp {
    public static FakeApplication app;
    public static String createDdl = "";
    public static String dropDdl = "";

    @BeforeClass  // テストクラス起動時に実行される
    public static void startApp() throws IOException {
        app = fakeApplication(inMemoryDatabase());
        start(app);

        String evolutionContent = FileUtils.readFileToString(app
                .getWrappedApplication().getFile("conf/testScheme/1.sql"));
        String[] splitEvolutionContent = evolutionContent.split("# --- !Ups");
        String[] upsDowns = splitEvolutionContent[1].split("# --- !Downs");
        createDdl = upsDowns[0];
        dropDdl = upsDowns[1];
    }

    @Before // テストメソッド実行前に都度実行される
    public void createCleanDb() {
        initDb();
    }

    public static void initDb() {
        // メモリデータベースのDROP
        Ebean.execute(Ebean.createCallableSql(dropDdl));
        // メモリデータベースのCREATE
        Ebean.execute(Ebean.createCallableSql(createDdl));

        // Ehcacheキャッシュのクリア
        CacheManager manager = CacheManager.create();
        Cache cache = manager.getCache("play");
        cache.removeAll();
    }

    public static void restartApp() {
        start(app);
    }

    @AfterClass  // テストクラス終了前に実行される
    public static void stopApp() {
        stop(app);
    }

}