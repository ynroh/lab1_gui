import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

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
        Toolbar()
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication,
    title= "M E O W") {
        App()
    }
}


