package src.main.kotlin.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.AlertDialog
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import src.main.kotlin.model.Scanner
import src.main.kotlin.model.parser.Parser
import src.main.kotlin.model.parser.states.DefinitionState
import src.main.kotlin.viewModel.ScannerViewModel
import java.awt.Toolkit
import java.awt.datatransfer.Clipboard
import java.awt.datatransfer.DataFlavor
import java.awt.datatransfer.StringSelection
import java.io.*
import java.util.*
import javax.swing.JFileChooser
import javax.swing.UIManager
import javax.swing.filechooser.FileNameExtensionFilter

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Toolbar(viewModel: ScannerViewModel, scanner: Scanner, parser: Parser){
    var showDialog by remember { mutableStateOf(false) }
    var isCreateCommand  by remember { mutableStateOf(false) }
    var isOpenCommand  by remember { mutableStateOf(false) }

    Row(horizontalArrangement = Arrangement.Start) {
        Spacer(modifier = Modifier.height(5.dp))
       IconButton(onClick = {
           if( viewModel.currentContent != "" && !viewModel.isChangesSaved){
               isCreateCommand = true
               showDialog = true
              }
           }, modifier = Modifier.height(14.dp))
        {
            Icon(Icons.Outlined.NoteAdd, "Add file")
        }

        IconButton(onClick = {
            if(viewModel.currentContent != "" && !viewModel.isChangesSaved){
                isOpenCommand = true
            showDialog = true
        }
        }, modifier = Modifier.height(14.dp))
        {
            Icon(Icons.Outlined.FileOpen, "Open file")
        }
        IconButton(onClick = {
            if(viewModel.currentFilePath == "")
                SaveFileAs(viewModel)
            else
            SaveFile(viewModel) }, modifier = Modifier.height(14.dp))
        {
            Icon(Icons.Outlined.Save, "Save file")
        }
        IconButton(onClick = {
            viewModel.undoRedoState.undo()
        }, modifier = Modifier.height(14.dp))
        {
            Icon(Icons.Default.TurnLeft, "Cancel changes")
        }
        IconButton(onClick = {
            viewModel.undoRedoState.redo()
        }, modifier = Modifier.height(14.dp))
        {
            Icon(Icons.Default.TurnRight, "Repeat changes")
        }
        IconButton(onClick = {
            CopyToClipboard(viewModel.currentContent)
                             }, modifier = Modifier.height(14.dp))
        {
            Icon(Icons.Default.ContentCopy, "Copy")
        }
        IconButton(onClick = {
            CopyToClipboard(viewModel.currentContent)
            viewModel.currentContent = ""
            viewModel.undoRedoState.input = TextFieldValue("")

        }, modifier = Modifier.height(14.dp))
        {
            Icon(Icons.Default.ContentCut, "Cut")
        }
        IconButton(onClick = {
            viewModel.currentContent = PasteFromClipboard()
            viewModel.undoRedoState.onInput(TextFieldValue(PasteFromClipboard()))
                             }, modifier = Modifier.height(14.dp))
        {
            Icon(Icons.Default.ContentPaste, "Paste")
        }
        IconButton(onClick = {
            scanner.analyzeCode(viewModel)
            parser.parseCode(viewModel)

           }, modifier = Modifier.height(14.dp))
        {
            Icon(Icons.Default.PlayArrow, "Play")
        }
        IconButton(onClick = {/*TO DO*/ }, modifier = Modifier.height(14.dp))
        {
            Icon(Icons.Outlined.Help, "Help")
        }
        IconButton(onClick = {/*TO DO*/ }, modifier = Modifier.height(14.dp))
        {
            Icon(Icons.Outlined.Info, "Info")
        }
    }

    if(showDialog){
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
                                SaveFileAs(viewModel)
                            }
                        ) {
                            Text("Сохранить", fontSize = 10.sp, textAlign = TextAlign.Center)
                        }
                        Button(
                            onClick = {
                                showDialog = false
                                viewModel.currentContent = ""
                                if(isCreateCommand)
                                    CreateFile()
                                else if(isOpenCommand)
                                    OpenFile(viewModel)
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
}


fun CreateFile(){
    UIManager.put("FileChooser.cancelButtonText", "Отмена");
    UIManager.put("FileChooser.saveButtonText", "Создать");
    UIManager.put("FileChooser.saveInLabelText", "Создать в: ");
    UIManager.put("FileChooser.fileNameLabelText", "Имя файла: ");
    UIManager.put("FileChooser.filesOfTypeLabelText", "Тип файла: ");


    val fileChooser = JFileChooser()

    fileChooser.setDialogTitle("Создать файл")
    val filter = FileNameExtensionFilter("Текстовые файлы (*.txt)", "txt")
    fileChooser.setFileFilter(filter)


    val userSelection = fileChooser.showSaveDialog(null)

    if (userSelection == JFileChooser.APPROVE_OPTION) {
        var selectedFile = fileChooser.selectedFile

        if (!selectedFile.getName().lowercase(Locale.getDefault()).endsWith(".txt")) {
            selectedFile = File(selectedFile.absolutePath + ".txt")
        }
        try {
            FileWriter(selectedFile).use { writer ->
                println("Файл сохранен: " + selectedFile.absolutePath)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}

fun OpenFile(viewModel: ScannerViewModel){
    UIManager.put("FileChooser.cancelButtonText", "Отмена");
    UIManager.put("FileChooser.saveButtonText", "Открыть");
    UIManager.put("FileChooser.saveInLabelText", "Открыть из: ");
    UIManager.put("FileChooser.fileNameLabelText", "Имя файла: ");
    UIManager.put("FileChooser.filesOfTypeLabelText", "Тип файла: ");


    val fileChooser = JFileChooser()

    fileChooser.setDialogTitle("Открыть файл")
    val filter = FileNameExtensionFilter("Текстовые файлы (*.txt)", "txt")
    fileChooser.setFileFilter(filter)


    val userSelection = fileChooser.showOpenDialog(null)

    if (userSelection == JFileChooser.APPROVE_OPTION) {
        var selectedFile = fileChooser.selectedFile

        if (!selectedFile.getName().lowercase(Locale.getDefault()).endsWith(".txt")) {
            selectedFile = File(selectedFile.absolutePath + ".txt")
        }
        try {
            BufferedReader(FileReader(selectedFile)).use { reader ->
                val content = StringBuilder()
                var line: String? = reader.readLine()
                while (line != null) {
                    content.append(line)
                    line = reader.readLine()
                }

                viewModel.currentContent = content.toString()
                viewModel.isChangesSaved = true
                viewModel.currentFilePath = selectedFile.absolutePath
                println("Файл открыт: ${selectedFile.absolutePath}")
                println(viewModel.currentFilePath)
            }
        }catch (e: IOException) {
            e.printStackTrace()
        }
    }
}
fun SaveFileAs(viewModel: ScannerViewModel){
    UIManager.put("FileChooser.cancelButtonText", "Отмена");
    UIManager.put("FileChooser.saveButtonText", "Сохранить");
    UIManager.put("FileChooser.saveInLabelText", "Сохранить в: ");
    UIManager.put("FileChooser.fileNameLabelText", "Имя файла: ");
    UIManager.put("FileChooser.filesOfTypeLabelText", "Тип файла: ");


    val fileChooser = JFileChooser()

    fileChooser.setDialogTitle("Сохранить файл")
    val filter = FileNameExtensionFilter("Текстовые файлы (*.txt)", "txt")
    fileChooser.setFileFilter(filter)


    val userSelection = fileChooser.showSaveDialog(null)

    if (userSelection == JFileChooser.APPROVE_OPTION) {
        var selectedFile = fileChooser.selectedFile

        if (!selectedFile.getName().lowercase(Locale.getDefault()).endsWith(".txt")) {
            selectedFile = File(selectedFile.absolutePath + ".txt")
        }
        try {
            FileWriter(selectedFile).use { writer ->
                writer.write(viewModel.currentContent)
                println("Файл сохранен: " + selectedFile.absolutePath)
                viewModel.isChangesSaved =true
                viewModel.currentFilePath = selectedFile.absolutePath
                println(viewModel.currentFilePath)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}


fun SaveFile(viewModel: ScannerViewModel){
        var selectedFile = viewModel.currentFilePath

        try {
            FileWriter(selectedFile).use { writer ->
                writer.write(viewModel.currentContent)
                viewModel.isChangesSaved =true
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
}


fun CopyToClipboard(text: String) {
    val clipboard: Clipboard = Toolkit.getDefaultToolkit().systemClipboard
    val stringSelection = StringSelection(text)
    clipboard.setContents(stringSelection, null)
}

fun PasteFromClipboard(): String {
    val clipboard = Toolkit.getDefaultToolkit().systemClipboard
    val transferable = clipboard.getContents(null)
    if (transferable.isDataFlavorSupported(DataFlavor.stringFlavor)) {
        return transferable.getTransferData(DataFlavor.stringFlavor) as String
    }
    return ""
}
