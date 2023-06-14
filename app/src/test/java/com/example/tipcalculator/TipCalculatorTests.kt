package com.example.tipcalculator

import org.junit.Assert.assertEquals
import org.junit.Test
import java.text.NumberFormat

class TipCalculatorTests {
    @Test
    fun calculateTip_20PercentNoRoundup(){
        //Arrange
        val amount = 10.00
        val percent = 20.00
        val expectedTip = NumberFormat.getCurrencyInstance().format(2.00)
        //Act
        val actualTip = calculateTip(amount = amount,percent=percent,isChecked = false)

        //Assert
        assertEquals(expectedTip,actualTip)
    }
}