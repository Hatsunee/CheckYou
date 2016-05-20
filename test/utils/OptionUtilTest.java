package utils;

import org.junit.Test;
import play.libs.F;
import java.util.ArrayList;
import java.util.List;
import static org.fest.assertions.Assertions.assertThat;

public class OptionUtilTest {

    @Test
    public void testApplyShouldSome() throws Exception {
        String test1 = "abc";
        F.Option<String> test1Result = OptionUtil.apply(test1);
        assertThat(test1Result.getClass()).isEqualTo(F.Some.class);
        assertThat(test1Result.getClass()).isNotEqualTo(F.None.class);
    }

    @Test
    public void testApplyShouldNone() throws Exception {
        String test2 = null;
        F.Option<String> test2Result = OptionUtil.apply(test2);
        assertThat(test2Result.getClass()).isEqualTo(F.None.class);
        assertThat(test2Result.getClass()).isNotEqualTo(F.Some.class);
    }

    // リストSome型 "a" "b" "c"（リスト型ある人）
    @Test
    public void testListApplyShouldSome() throws Exception {
        List<String> test3 = new ArrayList<String>();
        test3.add("a");
        test3.add("b");
        test3.add("c");

        String judge = null;
        if(!test3.isEmpty()){
            judge = "値が入ってる";
        }
        System.out.println("入っていてほしい："+judge);   

        F.Option<String> test3Result = OptionUtil.apply(judge);
        assertThat(test3Result.getClass()).isEqualTo(F.Some.class);
        assertThat(test3Result.getClass()).isNotEqualTo(F.None.class);
    }

    // リストNone型（リスト型ある人）
    @Test
    public void testListApplyShouldNone() throws Exception {
        List<String> test4 = new ArrayList<String>();

        String judge = null;
        if(!test4.isEmpty()){
            judge = "値が入ってる";
        }
        System.out.println("からが欲しい："+judge);        

        F.Option<String> test4Result = OptionUtil.apply(judge);
        assertThat(test4Result.getClass()).isEqualTo(F.None.class);
        assertThat(test4Result.getClass()).isNotEqualTo(F.Some.class);
    }

    // 文字列Some型
    @Test
    public void testStringApplyShouldSome() throws Exception {
        String test5 = "abc";
        F.Option<String> test5Result = OptionUtil.applyWithString(test5);
        assertThat(test5Result.getClass()).isEqualTo(F.Some.class);
        assertThat(test5Result.getClass()).isNotEqualTo(F.None.class);
    }

    // 文字列None型
    @Test
    public void testStringApplyShouldNone() throws Exception {
        String test6 = null;
        F.Option<String> test6Result = OptionUtil.applyWithString(test6);
        assertThat(test6Result.getClass()).isEqualTo(F.None.class);
        assertThat(test6Result.getClass()).isNotEqualTo(F.Some.class);
    }    
}