package com.example.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.example.ui.theme.ExampleTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FormExample()
        }
    }
}

@Composable
fun FormExample() {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val dataStore = DataStoreClass(context)
    val savedName = dataStore.getUser.collectAsState(initial = "")
    val savedSwitch = dataStore.getSwitch.collectAsState(initial = false)

    var name = remember { mutableStateOf("") }
    var surname = remember { mutableStateOf("") }
    var check = remember { mutableStateOf(false) }
    var background = remember { mutableStateOf(Color.White) }

    if(savedSwitch.value!!) {
        background.value = Color.Gray
    } else {
        background.value = Color.White
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(background.value)
            .padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {


        Text(
            text = savedName.value!!,
            fontWeight = FontWeight.Bold,
            style = TextStyle(color = Color.Blue),
            fontSize = 20.sp
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "FORM EXAMPLE",
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp
        )


        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextField(
            value = name.value,
            onValueChange = { name.value = it },
            label = { Text(text = "Name")},
            modifier = Modifier.fillMaxWidth()
        )
        if(name.value.isEmpty() && check.value == true){
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Name is required", style = TextStyle(color = Color.Red))
        }

        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            value = surname.value,
            onValueChange = { surname.value = it },
            label = { Text(text = "Surname")},
            modifier = Modifier.fillMaxWidth()
        )
        if(surname.value.isEmpty() && check.value == true){
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Surname is required", style = TextStyle(color = Color.Red))
        }

        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = {
                scope.launch {
                    check.value = true
                    if(!surname.value.isEmpty() && !name.value.isEmpty())
                        dataStore.saveName(name.value + " " + surname.value)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "ENTER", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,


        ){
            Text(text = "Dark mode", fontSize = 18.sp, fontWeight = FontWeight.Bold)

            Switch(
                checked = savedSwitch.value!!,
                onCheckedChange = { newState ->
                    scope.launch {
                        dataStore.saveSwitch(newState)
                    } },

                )
        }


    }
}

@Preview
@Composable
fun PreviewFormExample() {
    FormExample()
}