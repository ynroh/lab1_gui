package src.main.kotlin.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import viewModel.ScannerViewModel

@Composable
public fun ResultField(viewModel: ScannerViewModel){
    Column(modifier = Modifier.fillMaxSize().padding(23.dp)){
        OutlinedTextField(
            viewModel.scanResultText,
            enabled = false,
            modifier = Modifier.fillMaxWidth().fillMaxHeight(),
            textStyle  = TextStyle(fontSize = 14.sp, color = Color.Black),
            onValueChange = {
            }
        )
    }
}