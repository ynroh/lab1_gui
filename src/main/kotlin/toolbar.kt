import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.Button
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Toolbar(){
    Row(horizontalArrangement = Arrangement.Start) {
        Spacer(modifier = Modifier.height(5.dp))
       IconButton(onClick = {/*TO DO*/ }, modifier = Modifier.height(14.dp))
        {
            Icon(Icons.Outlined.NoteAdd, "Add file")
        }

        IconButton(onClick = {/*TO DO*/ }, modifier = Modifier.height(14.dp))
        {
            Icon(Icons.Outlined.FileOpen, "Open file")
        }
        IconButton(onClick = {/*TO DO*/ }, modifier = Modifier.height(14.dp))
        {
            Icon(Icons.Outlined.Save, "Save file")
        }
        IconButton(onClick = {/*TO DO*/ }, modifier = Modifier.height(14.dp))
        {
            Icon(Icons.Default.TurnLeft, "Cancel changes")
        }
        IconButton(onClick = {/*TO DO*/ }, modifier = Modifier.height(14.dp))
        {
            Icon(Icons.Default.TurnRight, "Repeat changes")
        }
        IconButton(onClick = {/*TO DO*/ }, modifier = Modifier.height(14.dp))
        {
            Icon(Icons.Default.ContentCopy, "")
        }
        IconButton(onClick = {/*TO DO*/ }, modifier = Modifier.height(14.dp))
        {
            Icon(Icons.Default.ContentCut, "")
        }
        IconButton(onClick = {/*TO DO*/ }, modifier = Modifier.height(14.dp))
        {
            Icon(Icons.Default.ContentPaste, "")
        }
        IconButton(onClick = {/*TO DO*/ }, modifier = Modifier.height(14.dp))
        {
            Icon(Icons.Outlined.PlayArrow, "")
        }
        IconButton(onClick = {/*TO DO*/ }, modifier = Modifier.height(14.dp))
        {
            Icon(Icons.Outlined.Help, "")
        }
        IconButton(onClick = {/*TO DO*/ }, modifier = Modifier.height(14.dp))
        {
            Icon(Icons.Outlined.Info, "")
        }
    }
}


