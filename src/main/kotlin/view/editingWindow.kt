package src.main.kotlin.view


import androidx.compose.foundation.layout.*
import androidx.compose.material.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import src.main.kotlin.viewModel.ScannerViewModel

@Composable
fun EditingTextField(viewModel: ScannerViewModel){
    val textState = remember { mutableStateOf(viewModel.currentContent) }

    Column(modifier = Modifier.fillMaxSize().padding(23.dp)) {
        val lineNumber = remember{mutableStateOf("1")}
        var lineCounter = "1"
        Row(){
            Column() {
                OutlinedTextField(
                    lineNumber.value,
                    onValueChange= {},
                    enabled = false,
                    minLines = 18,
                    modifier = Modifier.width(50.dp),
                    textStyle  = androidx.compose.ui.text.TextStyle(fontSize = 16.sp, textAlign = TextAlign.Center, color = Color.Gray),
                )
            }
            Column(modifier = Modifier.weight(1f)){
                OutlinedTextField(
                    //value = textState.value,
                    value = viewModel.currentContent,
                    minLines = 18,
                    modifier = Modifier.fillMaxWidth(),
                    textStyle  = androidx.compose.ui.text.TextStyle(fontSize = 16.sp),
                    onValueChange = {
                        textState.value = it
                        viewModel.currentContent = it
                        viewModel.isChangesSaved = false
                        val numberOfLines = it.count { char -> char == '\n' } + 1
                        lineCounter = (1..numberOfLines).joinToString("\n") { it.toString() }
                        lineNumber.value = lineCounter
                    }
                )
            }
        }

    }
}

