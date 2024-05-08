package src.main.kotlin.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.onClick
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Healing
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import src.main.kotlin.model.ErrorNeutralizer
import src.main.kotlin.viewModel.ScannerViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
public fun ResultField(viewModel: ScannerViewModel, fixer: ErrorNeutralizer){
    Column(modifier = Modifier.fillMaxSize().padding(23.dp)){
        /*OutlinedTextField(
            viewModel.scanResultText,
            enabled = false,
            modifier = Modifier.fillMaxWidth().fillMaxHeight(),
            textStyle  = TextStyle(fontSize = 14.sp, color = Color.Black),
            onValueChange = {
            }
        )*/
        Box(modifier = Modifier.fillMaxWidth().fillMaxHeight()) {
            OutlinedTextField(
                viewModel.scanResultText,
                enabled = false,
                modifier = Modifier.fillMaxWidth().fillMaxHeight(),
                textStyle = TextStyle(fontSize = 14.sp, color = Color.Black),
                onValueChange = {
                }
            )
            Row(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(8.dp)
            ) {
               if(viewModel.parserErrors.size>0) {
                    Icon(
                        imageVector = Icons.Default.Error,
                        contentDescription = "",
                        tint = Color.Red,
                        modifier = Modifier.size(14.dp)
                    )
                    Text(
                        text = viewModel.parserErrors.size.toString()
                    )
                    Spacer(Modifier.width(8.dp))
                    Icon(
                        imageVector = Icons.Default.Healing,
                        contentDescription = "",
                        tint = Color.Blue,
                        modifier = Modifier.size(14.dp).onClick{ fixer.neutralizeErrors(viewModel)},

                    )
               }
            }
        }
    }
}