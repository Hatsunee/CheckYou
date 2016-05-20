package utils;

import org.junit.*;
import play.mvc.*;
import play.test.*;
import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;

public class PageUtilTest {

    /**
     * ページ数が0のとき
     * @throws Exception
     */
    @Test
    public void testRightPageTo0Should0() throws Exception {
        assertThat(PageUtil.rightPage(0)).isEqualTo(0);
    }
    /**
     * ページ数が-1のとき
     * @throws Exception
     */
    @Test
    public void testRightPageToMinus1Should0() throws Exception {
        assertThat(PageUtil.rightPage(-1)).isEqualTo(0);
    }
    /**
     * ページ数が1のとき
     * @throws Exception
     */
    @Test
    public void testRightPageTo1Should0() throws Exception {
        assertThat(PageUtil.rightPage(1)).isEqualTo(0);
    }

    /**
     * ページ数が2のとき
     * @throws Exception
     */
    @Test
    public void testRightPageTo2Should0() throws Exception {
        assertThat(PageUtil.rightPage(2)).isEqualTo(1);
    }

    /**
     * ページ数がnullのとき
     * @throws Exception
     */
    @Test
    public void testRightPageToNullShould0() throws Exception {
        assertThat(PageUtil.rightPage(null)).isEqualTo(0);
    }

}