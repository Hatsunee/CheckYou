package models.request.Check;

import org.junit.Test;
import play.data.Form;
import static org.fest.assertions.Assertions.assertThat;
import java.util.HashMap;
import java.util.Map;

import static play.data.Form.form;

public class ResultPostRequestTest {

// OKケース
    /**
     * 正しいId形式
     */
    @Test
    public void testValidationToRightParam() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("name", "test_v");
        Form<ResultPostRequest> form = form(ResultPostRequest.class).bind(map);
        assertThat(form.hasErrors()).isFalse();
    }

    // アンダーバーなし（testv）
    @Test
    public void test2ValidationToRightParam() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("name", "test");
        Form<ResultPostRequest> form = form(ResultPostRequest.class).bind(map);
        assertThat(form.hasErrors()).isFalse();
    }

    // 数字のみ
    @Test
    public void test3ValidationToRightParam() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("name", "123456");
        Form<ResultPostRequest> form = form(ResultPostRequest.class).bind(map);
        assertThat(form.hasErrors()).isFalse();
    }

    // 数値、英数、アンダーバー混合
    @Test
    public void test4ValidationToRightParam() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("name", "test_123456");
        Form<ResultPostRequest> form = form(ResultPostRequest.class).bind(map);
        assertThat(form.hasErrors()).isFalse();
    }    

// NGケース
    /**
     * ハイフンつき
     */
    @Test
    public void testValidationToWrongParamWithHyphen() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("name", "test-v");
        Form<ResultPostRequest> form = form(ResultPostRequest.class).bind(map);
        assertThat(form.hasErrors()).isTrue();
    }

    // 禁止文字">"を含む
    @Test
    public void test2ValidationToWrongParamWithHyphen() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("name", "test>v");
        Form<ResultPostRequest> form = form(ResultPostRequest.class).bind(map);
        assertThat(form.hasErrors()).isTrue();
    }

    // ひらがなを含む
    @Test
    public void test3ValidationToWrongParamWithHyphen() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("name", "testあv");
        Form<ResultPostRequest> form = form(ResultPostRequest.class).bind(map);
        assertThat(form.hasErrors()).isTrue();
    }    
}