package com.example.tipcalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Money
import androidx.compose.material.icons.filled.Percent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tipcalculator.ui.theme.TipCalculatorTheme
import java.text.NumberFormat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TipCalculatorTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TipTimeLayout()
                }
            }
        }
    }
}

@Composable
fun TipTimeLayout() {
    var billInput by remember { mutableStateOf("0") }
    var tipInput by remember { mutableStateOf("0") }
    var isChecked by remember {
        mutableStateOf(false)
    }
    val percent = tipInput.toDoubleOrNull() ?: 0.0
    val amount = billInput.toDoubleOrNull() ?: 0.0
    val tip = calculateTip(amount, percent,isChecked)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(40.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.calculate_tip), modifier = Modifier
                .padding(bottom = 16.dp)
                .align(Alignment.Start)
        )
        EditNumberField(
            billInput, { billInput = it },
            Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp),
            stringResource(id = R.string.bill_amount),
            KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
            icon = Icons.Filled.Money
        )
        EditNumberField(
            tipInput, { tipInput = it },
            Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp),
            stringResource(id = R.string.how_was_the_service),
            KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
            icon = Icons.Filled.Percent
        )
        RoundUpRow(
            checked = isChecked,
            onCheckedChanged = { isChecked = it },
            modifier = Modifier.padding(bottom = 32.dp)
        )
        Text(
            textAlign = TextAlign.Center,
            text = stringResource(id = R.string.tip_amount, tip),
            style = MaterialTheme.typography.displaySmall
        )
        Spacer(modifier = Modifier.height(150.dp))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditNumberField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String,
    keyboardOptions: KeyboardOptions,
    icon: ImageVector
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier, singleLine = true,
        keyboardOptions = keyboardOptions,
        label = { Text(text = label) },
        leadingIcon = { Image(imageVector = icon, contentDescription = null)}
    )
}

@Composable
fun RoundUpRow(
    checked: Boolean,
    onCheckedChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Text(
            modifier = modifier.wrapContentWidth(align = Alignment.Start),
            text = stringResource(id = R.string.round_up_tip)
        )
        Switch(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentWidth(align = Alignment.End),
            checked = checked,
            onCheckedChange = onCheckedChanged
        )
    }
}

@VisibleForTesting
internal fun calculateTip(amount: Double, percent: Double = 15.00,isChecked:Boolean): String {
    var tip = amount * percent / 100
    if(isChecked){
        tip=kotlin.math.ceil(tip)
    }
    return NumberFormat.getCurrencyInstance().format(tip)
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TipCalculatorTheme {
        TipTimeLayout()
    }
}