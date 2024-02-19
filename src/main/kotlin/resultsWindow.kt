import androidx.compose.foundation.layout.*
import androidx.compose.material.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ResultField(){
    Column(modifier = Modifier.fillMaxSize().padding(23.dp)){
        val text = remember{ mutableStateOf("") }
        OutlinedTextField(
            text.value,
            enabled = false,
            modifier = Modifier.fillMaxWidth().fillMaxHeight(),
            textStyle  = TextStyle(fontSize = 16.sp),
            onValueChange = {
            }
        )
    }
}