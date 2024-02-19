import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.graphics.Color
import java.awt.Color as AwtColor


@Composable
@Preview
fun App() {
    var expanded by remember { mutableStateOf(false) }
    Column() {
        Row(
            horizontalArrangement = Arrangement.Start
        ) {
            fileItem()
            correctionItem()
            textItem()
            TextButton(
                onClick = { expanded = true },
                modifier = Modifier.height(30.dp)
            ) {
                Text(
                    "Пуск",
                    fontSize = 14.sp,
                    color = Color.Black
                )
            }
            infoItem()
        }
        Row() {
            Toolbar()
        }
        Column() {
            Row(modifier = Modifier.weight(1f)) {
                EditingTextField()
            }
            Row(modifier = Modifier.weight(1f)) {
                ResultField()
            }
        }
    }
}

fun main() = application {
    val data = remember { Singletone }
    Window(onCloseRequest = ::exitApplication,
    title= if(Singletone.isChangesSaved) "Компилятор" else "Компилятор*") {
        App()
    }
}


