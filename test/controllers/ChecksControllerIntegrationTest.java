package controllers;

import apps.FakeApp;
import org.junit.Test;
import play.libs.F;
import play.test.TestBrowser;
import java.util.HashMap;
import java.util.Map;
import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.*;

public class ChecksControllerIntegrationTest {

    // 正常に判定が行われる値を入力し、送信
    @Test
    public void testResultShouldSuccessParamToName() throws Exception {

      // テストだとapplication.confが自動的に読み込まれないため、ChecksController>resultアクション内で
      // 使用する文言を指定してfakeApplicationに渡している。
        Map<String, String> map = new HashMap<String, String>();
        map.put("checkYou.setting.message.failCheck", "診断に失敗しました");
        map.put("checkYou.setting.message.resultTitle", "さんの診断結果");
        map.putAll(inMemoryDatabase());

        running(testServer(3333, fakeApplication(map)), HTMLUNIT, new F.Callback<TestBrowser>() {
            public void invoke(TestBrowser browser) {
                FakeApp.initDb();
                // 下記のURLに移動
                browser.goTo("http://localhost:3333");
                // id属性「name」インプットに「test_t」を入力
                browser.fill("#name").with("test_t");
                // id属性「checkYourName」というボタンをを押す
                browser.$("#checkYourName").submit();

                // $メソッドの引数はresult.scala.html参照
                assertThat(browser.url()).contains("http://localhost:3333/result");
                assertThat(browser.$("h2#title").getText()).isEqualTo("test_tさんの診断結果");
                assertThat(browser.$("#check-result-text").getText()).contains("test_t");
            }
        });
    }

    // バリデーションエラーになる名前を入力し、送信
    @Test
    public void testResultShouldWrongParamToName() throws Exception {

        Map<String, String> map = new HashMap<String, String>();
        map.put("checkYou.setting.message.failCheck", "診断に失敗しました");
        map.put("checkYou.setting.message.resultTitle", "さんの診断結果");
        map.putAll(inMemoryDatabase());        

        running(testServer(3333, fakeApplication(map)), HTMLUNIT, new F.Callback<TestBrowser>() {
            public void invoke(TestBrowser browser) {
                FakeApp.initDb();
                browser.goTo("http://localhost:3333");
                browser.fill("#name").with("あああ");
                browser.$("#checkYourName").submit();

                assertThat(browser.$(".error strong").getText()).contains("Twitter id形式ではありません");

            }
        });
    }
}