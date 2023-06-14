package com.example.tipcalculator

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import androidx.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread
import com.example.tipcalculator.ui.theme.TipCalculatorTheme
import kotlinx.coroutines.delay
import org.junit.Rule
import org.junit.Test
import java.text.NumberFormat

class TipUITests {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun calculate_20_percent_tip(){
        composeTestRule.setContent {
            TipCalculatorTheme{
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TipTimeLayout()
                }
            }
        }
        composeTestRule.onNodeWithText("Bill Amount").performTextInput("10.00")
        composeTestRule.onNodeWithText("Tip Percentage").performTextInput("20.00")
        val tipAmount = NumberFormat.getCurrencyInstance().format(2)
        composeTestRule.onNodeWithText("Tip Amount: $tipAmount").assertExists("No node with this text was found.")
    }

}