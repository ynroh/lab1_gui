import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun fileItem(){
    var expanded by remember { mutableStateOf(false) }
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
            DropdownMenuItem(onClick = {/*TO DO*/}, modifier = Modifier.height(30.dp)){
                Text("Создать", fontSize = 14.sp)
            }
            DropdownMenuItem(onClick = {/*TO DO*/}, modifier = Modifier.height(30.dp)){
                Text("Открыть", fontSize = 14.sp)
            }
            DropdownMenuItem(onClick = {/*TO DO*/}, modifier = Modifier.height(30.dp)){
                Text("Сохранить", fontSize = 14.sp)
            }
            DropdownMenuItem(onClick = {/*TO DO*/}, modifier = Modifier.height(30.dp)){
                Text("Сохранить как", fontSize = 14.sp)
            }
            DropdownMenuItem(onClick = {/*TO DO*/}, modifier = Modifier.height(30.dp)){
                Text("Выход", fontSize = 14.sp)
            }
        }
    }

}

@Composable
fun correctionItem(){
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
            DropdownMenuItem(onClick = {/*TO DO*/}, modifier = Modifier.height(30.dp)){
                Text("Отменить", fontSize = 14.sp)
            }
            DropdownMenuItem(onClick = {/*TO DO*/}, modifier = Modifier.height(30.dp)){
                Text("Повторить", fontSize = 14.sp)
            }
            DropdownMenuItem(onClick = {/*TO DO*/}, modifier = Modifier.height(30.dp)){
                Text("Вырезать", fontSize = 14.sp)
            }
            DropdownMenuItem(onClick = {/*TO DO*/}, modifier = Modifier.height(30.dp)){
                Text("Копировать", fontSize = 14.sp)
            }
            DropdownMenuItem(onClick = {/*TO DO*/}, modifier = Modifier.height(30.dp)){
                Text("Вставить", fontSize = 14.sp)
            }
            DropdownMenuItem(onClick = {/*TO DO*/}, modifier = Modifier.height(30.dp)){
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
            DropdownMenuItem(onClick = {/*TO DO*/ }, modifier = Modifier.height(30.dp)) {
                Text("Вызов справки", fontSize = 14.sp)
            }
            DropdownMenuItem(onClick = {/*TO DO*/ }, modifier = Modifier.height(30.dp)) {
                Text("О программе", fontSize = 14.sp)
            }
        }
    }

}