package com.jrblanco.proyectoguachat

import com.jrblanco.proyectoguachat.aplication.usecase.LoginUseCase
import com.jrblanco.proyectoguachat.domain.repository.LoginRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class LoginUseCaseTest {

    @Mock
    private lateinit var mockLoginRepository: LoginRepository

    private lateinit var loginUseCase: LoginUseCase
    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        loginUseCase = LoginUseCase(mockLoginRepository)
    }
    @Test
    fun `invoke should return true when login is successful`() = runBlocking {
        // Arrange
        val email = "test@example.com"
        val password = "password123"
        val expectedResult = true
        `when`(mockLoginRepository.loginClassic(email, password)).thenReturn(expectedResult)
        // Act
        val result = loginUseCase.invoke(email, password)
        // Assert
        assertEquals(expectedResult, result)
    }
}

