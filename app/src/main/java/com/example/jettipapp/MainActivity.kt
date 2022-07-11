package com.example.jettipapp

import android.os.Bundle
import android.util.Log
import android.widget.Space
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Slider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jettipapp.components.InputField
import com.example.jettipapp.ui.theme.JetTipAppTheme
import com.example.jettipapp.widgets.RoundIconButton

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp {
                TopHeader()
            }
        }

    }
}
@Preview
@Composable
fun TopHeader(totalPerPerson: Double=134.0){
    Surface(modifier = Modifier
        .fillMaxWidth()
        .height(150.dp)
        //.clip(shape = RoundedCornerShape(corner = CornerSize(12.dp))
        .clip(CircleShape.copy(all = CornerSize(12.dp))),
        color = Color(0xFFE9D7F7)
    ){
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val total = "%.2f".format(totalPerPerson)
            Text(text = "Total per Person", style = MaterialTheme.typography.h5)
            Text(text = "$$total",
                style = MaterialTheme.typography.h4,
                fontWeight = FontWeight.ExtraBold)
        }
    }
}




@ExperimentalComposeUiApi
@Preview
@Composable
fun MainContent(){
    BillForm(){ billAmt ->
        Log.d("AMT", "Main content: $billAmt")
    }
}

@ExperimentalComposeUiApi
@Composable
fun BillForm(modifier: Modifier = Modifier, onValChange: (String) -> Unit = {}){
    val totalBillState = remember{
        mutableStateOf("")
    }
    val validState = remember(totalBillState.value){
        totalBillState.value.trim().isNotEmpty()

    }
    val sliderPositionState = remember {
        mutableStateOf(0f)
    }
    val split = remember {
        mutableStateOf(1)
    }

    val keyboardController = LocalSoftwareKeyboardController.current

    TopHeader()

    Surface(modifier = Modifier
        .padding(2.dp)
        .fillMaxWidth(),
        shape = RoundedCornerShape(corner = CornerSize(8.dp)),
        border = BorderStroke(width = 2.dp, color = Color.LightGray)
    ){
        Column(modifier = Modifier.padding(6.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start) {
            InputField(valueState = totalBillState,
                labelId = "Enter bill",
                enabled = true,
                isSingleLine = true,
                onAction = KeyboardActions{
                    if(!validState) return@KeyboardActions
                    onValChange(totalBillState.value.trim())

                    keyboardController?.hide()
                })
            if (validState){
                Row(modifier = Modifier.padding(3.dp),
                horizontalArrangement = Arrangement.Start){
                    Text(text = "split", modifier = Modifier.align(
                        alignment = Alignment.CenterVertically
                    ))
                    Spacer(modifier = Modifier.width(120.dp))
                    Row(modifier = Modifier.padding(horizontal = 3.dp),
                        horizontalArrangement = Arrangement.End){
                        RoundIconButton(
                            imageVector = Icons.Default.Remove,
                            onClick = {  })
                        Text(text = "3", modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(start = 9.dp, end = 9.dp))

                        RoundIconButton(
                            imageVector = Icons.Default.Add,
                            onClick = {  })
                    }
                }
                Row {
                    Text(text = "Text", modifier = Modifier.align(alignment = Alignment.CenterVertically))
                    Spacer(modifier = Modifier.width(200.dp))
                    Text(text = "$33.00")
                }
                Column(verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "33%")

                    Spacer(modifier = Modifier.height(14.dp))
                    //Slider
                    Slider(value = sliderPositionState.value,
                        onValueChange = { newVal ->
                            sliderPositionState.value = newVal
                        }, modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                    steps = 5)
                }
            }else{
                Box(){}
            }
        }
    }
}









@Composable
fun MyApp(content: @Composable () -> Unit) {
    JetTipAppTheme {
        // A surface container using the 'background' color from the theme
        Surface(color = MaterialTheme.colors.background){
            content()
        }
    }
}

//@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    JetTipAppTheme {
        MyApp {
            Text(text = "Hello again")
        }
    }
}