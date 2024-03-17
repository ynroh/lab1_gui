

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import src.main.kotlin.model.Scanner
import viewModel.ScannerViewModel
import src.main.kotlin.model.UndoRedoState
import src.main.kotlin.view.*


@Composable
@Preview
fun App(viewModel: ScannerViewModel) {
    val scanner = Scanner()

    Column() {
        Row(
            horizontalArrangement = Arrangement.Start
        ) {
            fileItem(viewModel)
            correctionItem(viewModel)
            textItem()
            TextButton(
                onClick = {
                    scanner.analyzeCode(viewModel)},
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
            Toolbar(viewModel)
        }
        Column() {
            Row(modifier = Modifier.weight(1f)) {
                EditingTextField(viewModel)
            }
            Row(modifier = Modifier.weight(1f)) {
                ResultField(viewModel)
            }
        }
    }
}


fun main() = application {
    val undoRedoState: UndoRedoState = UndoRedoState()
    val viewModel: ScannerViewModel = ScannerViewModel(undoRedoState)
    Window(onCloseRequest = {
        if(viewModel.currentContent!="" && !viewModel.isChangesSaved)
        SaveFileAs(viewModel)
        System.exit(0) },
    title= if(viewModel.isChangesSaved) "Компилятор" else "Компилятор*") {
        App(viewModel)
    }

}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SaveBeforeExit(viewModel: ScannerViewModel){
    var showDialog by remember { mutableStateOf(false) }
    AlertDialog(
        onDismissRequest = {
            showDialog = false
        },
        text = { Text("Вы хотите сохранить изменения в файле?") },
        buttons = {
            Row(
                modifier= Modifier.fillMaxWidth().padding(5.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically) {
                Button(
                    onClick = {
                        showDialog = false
                        if(viewModel.currentFilePath == "")
                            SaveFileAs(viewModel)
                        else
                            SaveFile(viewModel)
                    }
                ) {
                    Text("Сохранить", fontSize = 10.sp, textAlign = TextAlign.Center)
                }
                Button(
                    onClick = {
                        showDialog = false
                    }
                ) {
                    Text("Не сохранять", fontSize = 10.sp)
                }
                Button(
                    onClick = { showDialog = false }
                ) {
                    Text("Отмена", fontSize = 10.sp)
                }
            }
        }
    )
}