package tw.ysky.codenotes.utils

import com.google.common.truth.Truth
import org.junit.Test

class LoginUtilTest {
    @Test
    fun `empty username returns false`(){
        val result = LoginUtil.validateLogin("", "Aa123")

        Truth.assertThat(result).isFalse()
    }

    @Test
    fun `empty password returns false`(){
        val result = LoginUtil.validateLogin("", "")

        Truth.assertThat(result).isFalse()
    }

    @Test
    fun `account length less than 4 returns false`(){
        val result = LoginUtil.validateLogin("Ba3", "Aa2134321")

        Truth.assertThat(result).isFalse()
    }

    @Test
    fun `password length less than 4 returns false`(){
        val result = LoginUtil.validateLogin("Ba1213", "Aa")

        Truth.assertThat(result).isFalse()
    }
}