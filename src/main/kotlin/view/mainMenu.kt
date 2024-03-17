package src.main.kotlin.view


import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import src.main.kotlin.model.ScannerViewModel
import java.awt.Desktop
import java.io.File
import java.io.IOException
import java.net.URISyntaxException


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun fileItem(viewModel: ScannerViewModel){
    var expanded by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    var isCreateCommand  by remember { mutableStateOf(false) }
    var isOpenCommand  by remember { mutableStateOf(false) }
    var isCloseCommand  by remember { mutableStateOf(false) }

    TextButton(onClick = { expanded = true },
        modifier = Modifier.height(32.dp)) {
        Text("Файл",
            fontSize = 14.sp,
            color = Color.Black
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ){
            DropdownMenuItem(onClick = {
                if( viewModel.currentContent != "" && !viewModel.isChangesSaved){
                    isCreateCommand = true
                    showDialog = true}
            }, modifier = Modifier.height(30.dp)){
                Text("Создать", fontSize = 14.sp)
            }
            DropdownMenuItem(onClick = {
                if(viewModel.currentContent != "" && !viewModel.isChangesSaved){
                    isOpenCommand = true
                    showDialog = true
                                       }}, modifier = Modifier.height(30.dp)){
                Text("Открыть", fontSize = 14.sp)
            }
            DropdownMenuItem(onClick = {
                if(viewModel.currentFilePath == "")
                    SaveFileAs(viewModel)
                else
                    SaveFile(viewModel)
            }, modifier = Modifier.height(30.dp)){
                Text("Сохранить", fontSize = 14.sp)
            }
            DropdownMenuItem(onClick = {
                SaveFileAs(viewModel)
            }, modifier = Modifier.height(30.dp)){
                Text("Сохранить как", fontSize = 14.sp)
            }
            DropdownMenuItem(onClick = {
                if(viewModel.currentContent != "" && !viewModel.isChangesSaved) {
                    isCloseCommand = true
                    showDialog = true
                }
            }, modifier = Modifier.height(30.dp)){
                Text("Выход", fontSize = 14.sp)
            }
        }
    }
    if(showDialog) {
        AlertDialog(
            onDismissRequest = {
                showDialog = false
            },
            text = { androidx.compose.material3.Text("Вы хотите сохранить изменения в файле?") },
            buttons = {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(5.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = {
                            showDialog = false
                            SaveFileAs(viewModel)
                        }
                    ) {
                        androidx.compose.material3.Text("Сохранить", fontSize = 10.sp, textAlign = TextAlign.Center)
                    }
                    Button(
                        onClick = {
                            showDialog = false
                            viewModel.currentContent = ""
                            if (isCreateCommand)
                                CreateFile()
                            else if (isOpenCommand)
                                OpenFile(viewModel)
                            else if (isCloseCommand)
                                System.exit(0);
                        }
                    ) {
                        androidx.compose.material3.Text("Не сохранять", fontSize = 10.sp)
                    }
                    Button(
                        onClick = { showDialog = false }
                    ) {
                        androidx.compose.material3.Text("Отмена", fontSize = 10.sp)
                    }
                }
            }
        )
    }
}

@Composable
fun correctionItem(viewModel: ScannerViewModel){
    var expanded by remember { mutableStateOf(false) }
    TextButton(onClick = { expanded = true },
        modifier = Modifier.height(32.dp)) {
        Text("Правка",
            fontSize = 14.sp,
            color = Color.Black
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ){
            DropdownMenuItem(onClick = {
                viewModel.undoRedoState.undo()
            }, modifier = Modifier.height(30.dp)){
                Text("Отменить", fontSize = 14.sp)
            }
            DropdownMenuItem(onClick = {
                viewModel.undoRedoState.redo()
            }, modifier = Modifier.height(30.dp)){
                Text("Повторить", fontSize = 14.sp)
            }
            DropdownMenuItem(onClick = {
                CopyToClipboard(viewModel.currentContent)
                viewModel.currentContent = ""
                                       }, modifier = Modifier.height(30.dp)){
                Text("Вырезать", fontSize = 14.sp)
            }
            DropdownMenuItem(onClick = {
                CopyToClipboard(viewModel.currentContent)
                                       }, modifier = Modifier.height(30.dp)){
                Text("Копировать", fontSize = 14.sp)
            }
            DropdownMenuItem(onClick = {
                viewModel.currentContent = PasteFromClipboard()
            }, modifier = Modifier.height(30.dp)){
                Text("Вставить", fontSize = 14.sp)
            }
            DropdownMenuItem(onClick = {
                viewModel.currentContent = ""
                viewModel.undoRedoState.input = TextFieldValue("")
            }, modifier = Modifier.height(30.dp)){
                Text("Удалить", fontSize = 14.sp)
            }
            DropdownMenuItem(onClick = {/*TO DO*/}, modifier = Modifier.height(30.dp)){
                Text("Выделить всё", fontSize = 14.sp)
            }
        }
    }

}

@Composable
fun textItem(){
    var expanded by remember { mutableStateOf(false) }
    TextButton(onClick = { expanded = true },
        modifier = Modifier.height(32.dp)) {
        Text("Текст",
            fontSize = 14.sp,
            color = Color.Black
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.width(300.dp)
        ) {
            DropdownMenuItem(onClick = {/*TO DO*/ }, modifier = Modifier.height(30.dp)) {
                Text("Постановка задачи", fontSize = 14.sp)
            }
            DropdownMenuItem(onClick = {/*TO DO*/ }, modifier = Modifier.height(30.dp)) {
                Text("Грамматика", fontSize = 14.sp)
            }
            DropdownMenuItem(onClick = {/*TO DO*/ }, modifier = Modifier.height(30.dp)) {
                Text("Классификация грамматики", fontSize = 14.sp)
            }
            DropdownMenuItem(onClick = {/*TO DO*/ }, modifier = Modifier.height(30.dp)) {
                Text("Метод анализа", fontSize = 14.sp)
            }
            DropdownMenuItem(onClick = {/*TO DO*/ }, modifier = Modifier.height(30.dp)) {
                Text("Диагностика и нейтрализация ошибок", fontSize = 14.sp)
            }
            DropdownMenuItem(onClick = {/*TO DO*/ }, modifier = Modifier.height(30.dp)) {
                Text("Тестовый пример", fontSize = 14.sp)
            }
            DropdownMenuItem(onClick = {/*TO DO*/ }, modifier = Modifier.height(30.dp)) {
                Text("Список литературы", fontSize = 14.sp)
            }
            DropdownMenuItem(onClick = {/*TO DO*/ }, modifier = Modifier.height(30.dp)) {
                Text("Исходный код программы", fontSize = 14.sp)
            }
        }
    }

}

@Composable
fun infoItem(){
    var expanded by remember { mutableStateOf(false) }
    val uriHandler = LocalUriHandler.current
    TextButton(onClick = { expanded = true },
        modifier = Modifier.height(32.dp)) {
        Text("Справка",
            fontSize = 14.sp,
            color = Color.Black
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(onClick = {
                val htmlFilePath = "D:\\Study\\Compiler theory\\lab1_gui\\src\\main\\kotlin\\Info.html"

                try {
                    val htmlFile = File(htmlFilePath)
                    Desktop.getDesktop().browse(htmlFile.toURI())
                } catch (e: IOException) {
                    e.printStackTrace()
                } catch (e: URISyntaxException) {
                    e.printStackTrace()
                }
            }, modifier = Modifier.height(30.dp)) {
                Text("Вызов справки", fontSize = 14.sp)
            }
            DropdownMenuItem(onClick = {
                val htmlFilePath = "D:\\Study\\Compiler theory\\lab1_gui\\src\\main\\kotlin\\About.html"

                try {
                    val htmlFile = File(htmlFilePath)
                    Desktop.getDesktop().browse(htmlFile.toURI())
                } catch (e: IOException) {
                    e.printStackTrace()
                } catch (e: URISyntaxException) {
                    e.printStackTrace()
                }
            }, modifier = Modifier.height(30.dp)) {
                Text("О программе", fontSize = 14.sp)
            }
        }
    }

}